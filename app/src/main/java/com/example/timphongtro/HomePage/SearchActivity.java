package com.example.timphongtro.HomePage;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.SearchView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timphongtro.Database.Room;
import com.example.timphongtro.Database.RoomAdapter;
import com.example.timphongtro.Database.RoomViewHolderData;
import com.example.timphongtro.Database.SearchAdapter;
import com.example.timphongtro.Database.ShowmoreAdapter;
import com.example.timphongtro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity {
    RecyclerView roomrecyclerView;
    DatabaseReference roomdatabase;
    SearchAdapter searchAdapter;
    ArrayList<Room> roomlist;
    SearchView searchView;
    String district;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        roomdatabase = FirebaseDatabase.getInstance().getReference("rooms");
        roomlist = new ArrayList<>();
        fetchroomrecyclerviewdatabse();


        ImageView backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(v -> {
            Intent intent = new Intent(SearchActivity.this, MainActivity.class);
            startActivity(intent);
        });
        district = "";
        searchView = findViewById(R.id.search_room);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            district = bundle.getString("District");
            searchView.setQuery(district, true);
        }
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Toast.makeText(getApplicationContext(), district, Toast.LENGTH_SHORT).show();
                searchList(newText);
                return true;
            }
        });

        roomrecyclerView = findViewById(R.id.showmorelist);
        roomrecyclerView.setHasFixedSize(true);
        roomrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        searchAdapter = new SearchAdapter(this, roomlist);
        roomrecyclerView.setAdapter(searchAdapter);

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
                                Room room = troSnapshot.getValue(Room.class);
                                roomlist.add(room);
                            }
                        }
                        // Lấy dữ liệu từ child "ChungCu"
                        else if (dataSnapshot.getKey().equals("ChungCuMini")) {
                            for (DataSnapshot chungCuSnapshot : dataSnapshot.getChildren()) {
                                Room room = chungCuSnapshot.getValue(Room.class);
                                roomlist.add(room);
                            }
                        }
                    }
                }

                if (!"".equals(searchView.getQuery().toString())) {
                    searchList(searchView.getQuery().toString());
                }

                searchAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void searchList(String text) {
        ArrayList<Room> searchList = new ArrayList<>();
        for (Room room : roomlist) {
            if (room.getTitle_room().toLowerCase().contains(text.toLowerCase()) || room.getAddress().getDistrict().toLowerCase().contains(text.toLowerCase())) {
                searchList.add(room);
            }
        }
        searchAdapter.searchDataList(searchList);
    }
}