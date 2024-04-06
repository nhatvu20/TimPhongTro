package com.example.timphongtro.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timphongtro.Adapter.RoomAdapter;
import com.example.timphongtro.Entity.Room;
import com.example.timphongtro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MyLovePostActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myLovePostRef, roomRef, myLovePostRemoveRef;
    ArrayList<String> roomsLove;
    ArrayList<Room> rooms;
    RoomAdapter roomAdapter;
    RecyclerView rcvLovePost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_love_post);

        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        ImageView imageViewBack = findViewById(R.id.imageViewBack);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        rcvLovePost = findViewById(R.id.rcvLovePost);
        roomsLove = new ArrayList<>();
        rooms = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyLovePostActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvLovePost.setLayoutManager(linearLayoutManager);

        if (user != null) {
            myLovePostRef = database.getReference("LovePost/" + user.getUid());
            myLovePostRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            String roomInLove = dataSnapshot.getKey();
                            roomsLove.add(roomInLove);
                        }
                        roomRef = database.getReference("rooms");
                        roomRef.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                rooms.clear();
                                if (snapshot.exists()) {
                                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                        String key = dataSnapshot.getKey();
                                        if (key.equals("Tro") || key.equals("ChungCuMini")) {
                                            for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                                if (childSnapshot.exists()) {
                                                    if (roomsLove.contains(childSnapshot.getKey())) {
                                                        Room room = childSnapshot.getValue(Room.class);
//                                            if (room != null) {
                                                        rooms.add(room);
//                                            }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                roomAdapter.notifyDataSetChanged();

                                if (rooms.isEmpty()) {
                                    rcvLovePost.setVisibility(View.GONE);
                                    findViewById(R.id.nohistory).setVisibility(View.VISIBLE);
                                } else {
                                    rcvLovePost.setVisibility(View.VISIBLE);
                                    findViewById(R.id.nohistory).setVisibility(View.GONE);
                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
//                    roomAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
//            roomRef = database.getReference("rooms");
//            roomRef.addValueEventListener(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    rooms.clear();
//                    if (snapshot.exists()) {
//                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            String key = dataSnapshot.getKey();
//                            if (key.equals("Tro") || key.equals("ChungCuMini")) {
//                                for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
//                                    if(childSnapshot.exists()){
//                                        if (roomsLove.contains(childSnapshot.getKey())){
//                                            Room room = childSnapshot.getValue(Room.class);
////                                            if (room != null) {
//                                                rooms.add(room);
////                                            }
//                                        }
//                                    }
//                                }
//                            }
//                        }
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError error) {
//
//                }
//            });
        }


        roomAdapter = new RoomAdapter(MyLovePostActivity.this, rooms);
        rcvLovePost.setAdapter(roomAdapter);
    }

}