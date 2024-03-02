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
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Intent;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.timphongtro.Database.DataClass;
import com.example.timphongtro.Database.DistrictAdapter;
import com.example.timphongtro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment {
    String path = "/HaNoi";
    RecyclerView districtrecyclerView;
    DatabaseReference districtdatabase, spinnerdatabase;
    DistrictAdapter districtAdapter;
    ArrayList<DataClass> list;
    Spinner spinner;
    List<String> spinnerlist;
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

        spinner = view.findViewById(R.id.cityspinner);
        spinnerdatabase = FirebaseDatabase.getInstance().getReference("city");
        spinnerlist = new ArrayList<>();
        fetchspinnerdatabase();

        //Lấy dữ liệu từ database truyền vào recyclerview
        districtrecyclerView = view.findViewById(R.id.LocationExplore);
        districtrecyclerView.setHasFixedSize(true);
        districtrecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        list = new ArrayList<>();
        districtAdapter = new DistrictAdapter(getContext(),list);
        districtrecyclerView.setAdapter(districtAdapter);
        fetchrecyclerviewdatabase();

        //Auto Image Slider
        ImageSlider imageSlider = view.findViewById(R.id.ImageSlider);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        slideModels.add(new SlideModel(R.drawable.image1, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image2, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image3, ScaleTypes.FIT));
        slideModels.add(new SlideModel(R.drawable.image4, ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        TextView searchTextView = view.findViewById(R.id.searchEditText);
        searchTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), SearchActivity.class);
                startActivity(intent);
            }
        });
    }

    private void fetchrecyclerviewdatabase() {
        districtdatabase = FirebaseDatabase.getInstance().getReference("city" + path + "/district");
        districtdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                list.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    DataClass dataClass = dataSnapshot.getValue(DataClass.class);
                    list.add(dataClass);
                }
                districtAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void fetchspinnerdatabase() {
        spinnerdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot1:snapshot.getChildren()) {
                    String spinnername = snapshot1.child("name").getValue(String.class);
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
                        fetchrecyclerviewdatabase();
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}