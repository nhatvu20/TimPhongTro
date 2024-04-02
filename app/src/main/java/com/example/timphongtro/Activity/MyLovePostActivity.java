package com.example.timphongtro.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timphongtro.Adapter.RoomAdapter;
import com.example.timphongtro.Entity.Address;
import com.example.timphongtro.Entity.ExtensionRoom_class;
import com.example.timphongtro.Entity.FurnitureClass;
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
    DatabaseReference myLovePostRef, roomdatabase, myLovePostRemoveRef;
    ArrayList<Room> roomsLove;
    RoomAdapter roomAdapter;
    RecyclerView rcvLovePost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_my_love_post);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(MyLovePostActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvLovePost.setLayoutManager(linearLayoutManager);

        if (user != null) {
            myLovePostRef = database.getReference("LovePost/" + user.getUid());
            roomdatabase = database.getReference("rooms");
            myLovePostRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                            lay phong trong yeu thich
                            Room roomInLove = dataSnapshot.getValue(Room.class);
                            //lay phong trong rooms
                            roomdatabase.addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshotRoom) {
                                    roomsLove.clear();
                                    if (snapshotRoom.exists()) {
                                        for (DataSnapshot dataSnapshot : snapshotRoom.getChildren()) {
                                            if (dataSnapshot.getKey().equals("Tro") || dataSnapshot.getKey().equals("ChungCuMini")) {
                                                // Lấy dữ liệu từ child "Tro"
                                                if (dataSnapshot.getKey().equals("Tro") && dataSnapshot.exists()) {
                                                    for (DataSnapshot troSnapshot : dataSnapshot.getChildren()) {
                                                        if (troSnapshot.exists()) {
                                                            Room room = troSnapshot.getValue(Room.class);
                                                            if (room != null && roomInLove != null) {
                                                                //neu phong trong yeu thich == phong trong rooms
                                                                if (roomInLove.getId_room().equals(room.getId_room())) {
                                                                    roomsLove.add(room);
                                                                } else {
                                                                    //nếu phòng đó không tồn tại trong rooms là khong có trong database thi phai xoa
//                                                                    database.getReference("LovePost/" + user.getUid() + "/" + roomInLove.getId_room()).removeValue();
                                                                }
                                                            }
                                                        }
                                                    }
//                                manageRoomAdapter.notifyDataSetChanged();
                                                }
                                                // Lấy dữ liệu từ child "ChungCu"
                                                else if (dataSnapshot.getKey().equals("ChungCuMini") && dataSnapshot.exists()) {
                                                    for (DataSnapshot chungCuSnapshot : dataSnapshot.getChildren()) {
                                                        if (chungCuSnapshot.exists()) {
                                                            Room room = chungCuSnapshot.getValue(Room.class);
                                                            if (room != null && roomInLove != null) {
                                                                //neu phong trong yeu thich == phong trong rooms
                                                                if (roomInLove.getId_room().equals(room.getId_room())) {
                                                                    roomsLove.add(room);
                                                                } else {
                                                                    //nếu phòng đó không tồn tại trong rooms là khong có trong database thi phai xoa
                                                                    database.getReference("LovePost/" + user.getUid() + "/" + roomInLove.getId_room()).removeValue();
                                                                }
                                                            }
                                                        }
                                                    }
//                                manageRoomAdapter.notifyDataSetChanged();
                                                }
                                            }
                                        }
//                    manageRoomAdapter.notifyDataSetChanged();
                                    } else {
                                        // Không có dữ liệu tồn tại
                                    }
                                    roomAdapter.notifyDataSetChanged();
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {
                                    // Xử lý lỗi nếu có
                                }
                            });
//                            Room roomInLove = dataSnapshot.getValue(Room.class);
//                            roomsLove.add(roomInLove);
                        }

                    }
                    roomAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        }
        roomAdapter = new RoomAdapter(MyLovePostActivity.this, roomsLove);
        rcvLovePost.setAdapter(roomAdapter);
    }

}