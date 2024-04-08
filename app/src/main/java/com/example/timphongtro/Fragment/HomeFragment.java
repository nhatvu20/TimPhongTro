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
    private String path = "/HaNoi", selectedSpinner = "Hà Nội";;
    private ShimmerFrameLayout districtShimmer, roomShimmer;
    private DatabaseReference spinnerRef;
    private DistrictAdapter districtAdapter;
    private RoomAdapter roomAdapter;
    private ArrayList<DistrictData> districtArrayList;
    private ArrayList<Room> roomArrayList;
    private Spinner spinner;
    private ArrayList<String> spinnerArrayList;
    private ArrayAdapter<String> spinnerAdapter;
    private FirebaseStorage storage = FirebaseStorage.getInstance();
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //Auto Image Slider
        StorageReference storageReference = storage.getReference().child("HomeImageSlider");
        ImageSlider imageSlider = view.findViewById(R.id.imageSlider);
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

        TextView searchTextView = view.findViewById(R.id.searchEditText);
        searchTextView.setOnClickListener(v -> {
            FirebaseUser user = firebaseAuth.getCurrentUser();
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivity(intent);
        });

        TextView showMore = view.findViewById(R.id.showMore);
        showMore.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ShowMoreActivity.class);
            startActivity(intent);
        });

        LinearLayout tin_dang_cho_thue = view.findViewById(R.id.tin_dang_cho_thue);
        LinearLayout tim_phong = view.findViewById(R.id.find_room);
        LinearLayout doi_binh_ga = view.findViewById(R.id.doi_binh_ga);
        LinearLayout doi_binh_nuoc = view.findViewById(R.id.doi_binh_nuoc);
        LinearLayout giat_la = view.findViewById(R.id.giat_la);
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
        giat_la.setOnClickListener(v -> {
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
        spinner = view.findViewById(R.id.city_spinner);
        spinnerRef = FirebaseDatabase.getInstance().getReference("city");
        spinnerArrayList = new ArrayList<>();
        fetchSpinnerDatabase();

        //Lấy dữ liệu từ database truyền vào rcv_district
        districtShimmer.startShimmer();
        RecyclerView rcv_district = view.findViewById(R.id.rcv_district);
        rcv_district.setHasFixedSize(true);
        rcv_district.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        districtArrayList = new ArrayList<>();
        districtAdapter = new DistrictAdapter(getContext(), districtArrayList);
        rcv_district.setAdapter(districtAdapter);
        fetchCityDatabase();

        //Lấy dữ liệu từ database truyền vào rcv_room
        roomShimmer.startShimmer();
        RecyclerView rcv_room = view.findViewById(R.id.rcv_room);
        rcv_room.setHasFixedSize(true);
        rcv_room.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        roomArrayList = new ArrayList<>();
        roomAdapter = new RoomAdapter(getContext(), roomArrayList);
        rcv_room.setAdapter(roomAdapter);
        fetchRoomDatabase();
    }

    private void fetchRoomDatabase() {
        DatabaseReference roomRef = FirebaseDatabase.getInstance().getReference("rooms");
        roomRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomArrayList.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot roomType : snapshot.getChildren()) {

                        for (DataSnapshot roomSnapshot : roomType.getChildren()) {
                            Room room = roomSnapshot.getValue(Room.class);
                            if (room != null && room.getStatus_room() != 1 && room.getAddress().getCity().equals(selectedSpinner)) {
                                roomArrayList.add(room);
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
                Toast.makeText(getContext(), "Can't fetch room from firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void fetchCityDatabase() {

        DatabaseReference districtRef = FirebaseDatabase.getInstance().getReference("city" + path);
        districtRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                districtArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals("district")) {
                        for (DataSnapshot districtSnapshot : dataSnapshot.getChildren()) {
                            DistrictData districtData = districtSnapshot.getValue(DistrictData.class);
                            districtArrayList.add(districtData);
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

    private void fetchSpinnerDatabase() {
        spinnerRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                spinnerArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String spinner_name = dataSnapshot.child("name").getValue(String.class);
                    spinnerArrayList.add(spinner_name);
                }
                spinnerAdapter = new ArrayAdapter<>(requireContext(), R.layout.spinner_style, spinnerArrayList);
                spinnerAdapter.setDropDownViewResource(R.layout.spinner_dropdown);
                spinner.setAdapter(spinnerAdapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        selectedSpinner = spinnerArrayList.get(position);
                        if (selectedSpinner.equals("Hà Nội")) {
                            path = "/HaNoi";
                        } else if (selectedSpinner.equals("Hồ Chí Minh")) {
                            path = "/HoChiMinh";
                        }
                        fetchCityDatabase();
                        fetchRoomDatabase();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {
                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), "Can't fetch spinner's items from firebase", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void openServiceActivity(String item) {
        Intent intent = new Intent(getContext(), ServiceActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }

}