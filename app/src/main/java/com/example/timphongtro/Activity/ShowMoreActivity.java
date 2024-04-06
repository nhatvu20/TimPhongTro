package com.example.timphongtro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timphongtro.Entity.Room;
import com.example.timphongtro.Adapter.ShowmoreAdapter;
import com.example.timphongtro.R;
import com.facebook.shimmer.ShimmerFrameLayout;
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
    ShimmerFrameLayout roomShimmer;
    ImageView backbutton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_more);

        roomShimmer = findViewById(R.id.room_shimmer);
        backbutton = findViewById(R.id.backbutton);
        backbutton.setOnClickListener(v -> {
            finish();
        });

        roomShimmer.startShimmer();
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
                roomlist.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        String key = dataSnapshot.getKey();
                        if (key.equals("Tro") || key.equals("ChungCuMini")) {
                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                Room room = childSnapshot.getValue(Room.class);
                                if (room != null && room.getStatus_room() != 1) {
                                    roomlist.add(room);
                                }
                            }
                        }
                    }
                }
                roomShimmer.stopShimmer();
                roomShimmer.setVisibility(View.GONE);
                showmoreAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}