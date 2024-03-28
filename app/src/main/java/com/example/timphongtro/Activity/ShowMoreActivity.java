package com.example.timphongtro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
 
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timphongtro.Entity.Room;
import com.example.timphongtro.Adapter.ShowmoreAdapter;
import com.example.timphongtro.R;
import com.google.firebase.database.DataSnapshot; 
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShowMoreActivity extends AppCompatActivity {
    RecyclerView roomrecyclerView;
    DatabaseReference roomdatabase;
    ShowmoreAdapter showmoreAdapter;
    ArrayList<Room> roomlist;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more);

        ImageView backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(v -> {
            Intent intent = new Intent(ShowMoreActivity.this, MainActivity.class);
            startActivity(intent);
        });

        roomrecyclerView = findViewById(R.id.showmorelist);
        roomrecyclerView.setHasFixedSize(true);
        roomdatabase = FirebaseDatabase.getInstance().getReference("rooms");
        roomrecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        roomlist = new ArrayList<>();
        showmoreAdapter = new ShowmoreAdapter(this, roomlist);
        roomrecyclerView.setAdapter(showmoreAdapter);
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
                showmoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}