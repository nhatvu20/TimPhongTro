package com.example.timphongtro.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.timphongtro.Activity.LoginActivity;
import com.example.timphongtro.Activity.MyLovePostActivity;
import com.example.timphongtro.Adapter.RoomAdapter;
import com.example.timphongtro.Entity.Room;
import com.example.timphongtro.R;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class NotificationFragment extends Fragment {
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myLovePostRef, roomRef, myLovePostRemoveRef;
    ArrayList<String> roomsLove;
    ArrayList<Room> rooms;
    RoomAdapter roomAdapter;
    RecyclerView rcvLovePost;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        ImageView imageViewBack = view.findViewById(R.id.imageViewBack);

        rcvLovePost = view.findViewById(R.id.rcvLovePost);
        roomsLove = new ArrayList<>();
        rooms = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
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


        roomAdapter = new RoomAdapter(getContext(), rooms);
        rcvLovePost.setAdapter(roomAdapter);
    }
}