package com.example.timphongtro.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.timphongtro.Activity.PostRoomActivity;
import com.example.timphongtro.Activity.SearchActivity;
import com.example.timphongtro.Activity.ServiceActivity;
import com.example.timphongtro.Activity.ShowMoreActivity;
import com.example.timphongtro.Adapter.DistrictAdapter;
import com.example.timphongtro.Entity.DistrictData;
import com.example.timphongtro.Entity.Room;
import com.example.timphongtro.Adapter.RoomAdapter;
import com.example.timphongtro.Activity.LoginActivity;
import com.example.timphongtro.R;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    String path = "/HaNoi";
    RecyclerView districtrecyclerView, roomrecyclerView;
    ShimmerFrameLayout districtShimmer, roomShimmer;
    DatabaseReference districtdatabase, roomdatabase, spinnerdatabase;
    DistrictAdapter districtAdapter;
    RoomAdapter roomAdapter;
    ArrayList<DistrictData> districtlist;
    ArrayList<Room> roomlist;
    Spinner spinner;
    ArrayList<String> spinnerlist;
    ArrayAdapter<String> spinneradapter;
    FirebaseStorage storage = FirebaseStorage.getInstance();
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Auto Image Slider
        StorageReference storageReference = storage.getReference().child("HomeImageSlider");
        ImageSlider imageSlider = view.findViewById(R.id.ImageSlider);
        storageReference.listAll().addOnSuccessListener(listResult -> {
            ArrayList<SlideModel> slideModels = new ArrayList<>();
            for (StorageReference item : listResult.getItems()) {
                item.getDownloadUrl().addOnSuccessListener(uri -> {
                    slideModels.add(new SlideModel(uri.toString(), ScaleTypes.FIT));
                    imageSlider.setImageList(slideModels, ScaleTypes.FIT);
                });
            }
        }).addOnFailureListener(e -> {

        });

        //Bấm vào ô search nhảy sang activity mới
        TextView searchTextView = view.findViewById(R.id.searchEditText);
        searchTextView.setOnClickListener(v -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivity(intent);
        });

        TextView showmore = view.findViewById(R.id.showmore);
        showmore.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ShowMoreActivity.class);
            startActivity(intent);
        });

        LinearLayout tin_dang_cho_thue = view.findViewById(R.id.tin_dang_cho_thue);
        LinearLayout tim_phong = view.findViewById(R.id.find_room);
        LinearLayout doi_binh_ga = view.findViewById(R.id.doi_binh_ga);
        LinearLayout doi_binh_nuoc = view.findViewById(R.id.doi_binh_nuoc);
        LinearLayout giatla = view.findViewById(R.id.giat_la);
        LinearLayout sua_chua_dien_nuoc = view.findViewById(R.id.sua_chua_dien_nuoc);
        LinearLayout tu_van_thiet_ke_phong = view.findViewById(R.id.tu_van_thiet_ke_phong);
        LinearLayout cho_thue_noi_that = view.findViewById(R.id.cho_thue_noi_that);

        districtShimmer = view.findViewById(R.id.district_shimmer);
        roomShimmer = view.findViewById(R.id.room_shimmer);

        tim_phong.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivity(intent);
        });

        tin_dang_cho_thue.setOnClickListener(v -> {
            Intent intent;
            if (user != null) {
                intent = new Intent(getContext(), PostRoomActivity.class);
            } else {
                intent = new Intent(getContext(), LoginActivity.class);
            }
            startActivity(intent);
        });
        doi_binh_ga.setOnClickListener(v -> {
            openServiceActivity("doibinhga");
        });

        doi_binh_nuoc.setOnClickListener(v -> {
            openServiceActivity("doibinhnuoc");
        });
        giatla.setOnClickListener(v -> {
            openServiceActivity("giatla");
        });

        sua_chua_dien_nuoc.setOnClickListener(v -> {
            openServiceActivity("suachuadiennuoc");
        });
        tu_van_thiet_ke_phong.setOnClickListener(v -> {
            openServiceActivity("tuvanthietkephong");
        });

        cho_thue_noi_that.setOnClickListener(v -> {
            openServiceActivity("chothuenoithat");
        });

        //Lấy dữ liệu từ database truyền vào spinner
        spinner = view.findViewById(R.id.cityspinner);
        spinnerdatabase = FirebaseDatabase.getInstance().getReference("city");
        spinnerlist = new ArrayList<>();
        fetchspinnerdatabase();

        //Lấy dữ liệu từ database truyền vào districtrecyclerview
        districtShimmer.startShimmer();
        districtrecyclerView = view.findViewById(R.id.LocationExplore);
        districtrecyclerView.setHasFixedSize(true);
        districtrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        districtlist = new ArrayList<>();
        districtAdapter = new DistrictAdapter(getContext(), districtlist);
        districtrecyclerView.setAdapter(districtAdapter);
        fetchcityrecyclerviewdatabase();

        //Lấy dữ liệu từ database truyền vào roomrecyclerview
        roomShimmer.startShimmer();
        roomrecyclerView = view.findViewById(R.id.RoomrecyclerView);
        roomrecyclerView.setHasFixedSize(true);
        roomdatabase = FirebaseDatabase.getInstance().getReference("rooms");
        roomrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        roomlist = new ArrayList<>();
        roomAdapter = new RoomAdapter(getContext(), roomlist);
        roomrecyclerView.setAdapter(roomAdapter);
        fetchroomrecyclerviewdatabse();
    }

    private void fetchroomrecyclerviewdatabse() {
        roomdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomlist.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.getKey().equals("Tro") || dataSnapshot.getKey().equals("ChungCuMini")) {
                            // Lấy dữ liệu từ child "Tro"
                            if (dataSnapshot.getKey().equals("Tro")) {
                                for (DataSnapshot troSnapshot : dataSnapshot.getChildren()) {
                                    Room room = troSnapshot.getValue(Room.class);
                                    if (room != null) {
                                        if (room.getStatus_room() != 1) {
                                            roomlist.add(room);
                                        }
                                    }
                                }
                            }
                            // Lấy dữ liệu từ child "ChungCu"
                            else if (dataSnapshot.getKey().equals("ChungCuMini")) {
                                for (DataSnapshot chungCuSnapshot : dataSnapshot.getChildren()) {
                                    Room room = chungCuSnapshot.getValue(Room.class);
                                    if (room != null) {
                                        if (room.getStatus_room() != 1) {
                                            roomlist.add(room);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                roomShimmer.stopShimmer();
                roomShimmer.setVisibility(View.GONE);
                roomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fetchcityrecyclerviewdatabase() {

        districtdatabase = FirebaseDatabase.getInstance().getReference("city" + path);
        districtdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                districtlist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals("district")) {
                        for (DataSnapshot districtSnapshot : dataSnapshot.getChildren()) {
                            DistrictData districtData = districtSnapshot.getValue(DistrictData.class);
                            districtlist.add(districtData);
                        }
                    }
                }
                districtShimmer.stopShimmer();
                districtShimmer.setVisibility(View.GONE);
                districtAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Can't fetch district from firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchspinnerdatabase() {
        spinnerdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                spinnerlist.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String spinnername = dataSnapshot.child("name").getValue(String.class);
                    spinnerlist.add(spinnername);
                }
                spinneradapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_style, spinnerlist);
                spinneradapter.setDropDownViewResource(R.layout.spinner_dropdown);
                spinner.setAdapter(spinneradapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedspinner = spinnerlist.get(position);
                        if (selectedspinner.equals("Hà Nội")) {
                            path = "/HaNoi";
                        } else if (selectedspinner.equals("Hồ Chí Minh")) {
                            path = "/HoChiMinh";
                        }
                        fetchcityrecyclerviewdatabase();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                        Toast.makeText(getContext(), "Can't fetch spinner's items from firebase", Toast.LENGTH_SHORT).show();
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openServiceActivity(String item) {
        Intent intent = new Intent(getContext(), ServiceActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

}