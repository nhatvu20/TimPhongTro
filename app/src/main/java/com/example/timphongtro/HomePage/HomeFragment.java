package com.example.timphongtro.HomePage;

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
import android.widget.Spinner;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.timphongtro.Database.DistrictAdapter;
import com.example.timphongtro.Database.DistrictData;
import com.example.timphongtro.Database.RoomAdapter;
import com.example.timphongtro.Database.RoomViewHolderData;
import com.example.timphongtro.R;
import com.example.timphongtro.SearchPage.SearchActivity;
import com.example.timphongtro.ShowMore;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class HomeFragment extends Fragment {
    String path = "/HaNoi";
    RecyclerView districtrecyclerView, roomrecyclerView;
    DatabaseReference districtdatabase, roomdatabase, spinnerdatabase;
    DistrictAdapter districtAdapter;
    RoomAdapter roomAdapter;
    ArrayList<DistrictData> districtlist;
    ArrayList<RoomViewHolderData> roomlist;
    Spinner spinner;
    ArrayList<String> spinnerlist;
    ArrayAdapter<String> spinneradapter;
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
        ImageSlider imageSlider = view.findViewById(R.id.ImageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.image1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image4, ScaleTypes.FIT));
        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        //Bấm vào ô search nhảy sang activity mới
        TextView searchTextView = view.findViewById(R.id.searchEditText);
        searchTextView.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), SearchActivity.class);
            startActivity(intent);
        });

        TextView showmore = view.findViewById(R.id.showmore);
        showmore.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), ShowMore.class);
            startActivity(intent);
        });


        //Lấy dữ liệu từ database truyền vào spinner
        spinner = view.findViewById(R.id.cityspinner);
        spinnerdatabase = FirebaseDatabase.getInstance().getReference("city");
        spinnerlist = new ArrayList<>();
        fetchspinnerdatabase();

        //Lấy dữ liệu từ database truyền vào districtrecyclerview
        districtrecyclerView = view.findViewById(R.id.LocationExplore);
        districtrecyclerView.setHasFixedSize(true);
        districtrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        districtlist = new ArrayList<>();
        districtAdapter = new DistrictAdapter(getContext(), districtlist);
        districtrecyclerView.setAdapter(districtAdapter);
        fetchcityrecyclerviewdatabase();

        //Lấy dữ liệu từ database truyền vào roomrecyclerview
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
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    if (dataSnapshot.getKey().equals("Tro") || dataSnapshot.getKey().equals("ChungCuMini")) {
                        // Lấy dữ liệu từ child "Tro"
                        if (dataSnapshot.getKey().equals("Tro")) {
                            for (DataSnapshot troSnapshot : dataSnapshot.getChildren()) {
                                RoomViewHolderData roomViewHolderData = troSnapshot.getValue(RoomViewHolderData.class);
                                roomlist.add(roomViewHolderData);
                            }
                        }
                        // Lấy dữ liệu từ child "ChungCu"
                        else if (dataSnapshot.getKey().equals("ChungCuMini")) {
                            for (DataSnapshot chungCuSnapshot : dataSnapshot.getChildren()) {
                                RoomViewHolderData roomViewHolderData = chungCuSnapshot.getValue(RoomViewHolderData.class);
                                roomlist.add(roomViewHolderData);
                            }
                        }
                    }
                }
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
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (dataSnapshot.getKey().equals("district")) {
                        for (DataSnapshot districtSnapshot : dataSnapshot.getChildren()) {
                            DistrictData districtData = districtSnapshot.getValue(DistrictData.class);
                            districtlist.add(districtData);
                        }
                    }
                }
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
                for (DataSnapshot dataSnapshot:snapshot.getChildren()) {
                    String spinnername = dataSnapshot.child("name").getValue(String.class);
                    spinnerlist.add(spinnername);
                }
                spinneradapter = new ArrayAdapter<>(requireContext(), android.R.layout.simple_spinner_item,spinnerlist);
                spinneradapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner.setAdapter(spinneradapter);

                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String selectedspinner = spinnerlist.get(position);
                        if (selectedspinner.equals("Hà Nội")){
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

}