package com.example.timphongtro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timphongtro.Adapter.ManageRoomAdapter;
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

public class ManagePostActivity extends AppCompatActivity {
    ImageView imageViewBack, imageViewPost;
    //    Spinner spinnerStatusRoom;
    RecyclerView rcvMyPost;
    ArrayList<Room> roomlist;

    DatabaseReference roomdatabase;
    ManageRoomAdapter manageRoomAdapter;

    FirebaseUser userCurrent;
    TabLayout tabLayout;
    int statusRoomInt; //da cho thue la 1 ; chua cho thue la 0

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_manage_post);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });


//        Sửa
        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewPost = findViewById(R.id.imageViewPost);
//        spinnerStatusRoom = findViewById(R.id.spinnerStatusRoom);
        rcvMyPost = findViewById(R.id.rcvMyPost);
        tabLayout = findViewById(R.id.tabLayout);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        imageViewPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent post = new Intent(ManagePostActivity.this, PostRoomActivity.class);
                startActivity(post);
            }
        });
        userCurrent = FirebaseAuth.getInstance().getCurrentUser();


//        String[] data = {"Phòng trống", "Đã cho thuê"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        spinnerStatusRoom.setAdapter(adapter);

//        roomdatabase = FirebaseDatabase.getInstance().getReference("myRooms/");

        if (userCurrent != null) {
            roomlist = new ArrayList<>();
//            roomdatabase = FirebaseDatabase.getInstance().getReference("myRooms/" + userCurrent.getUid());
            roomdatabase = FirebaseDatabase.getInstance().getReference("rooms/");

//            fecthRoomData_statusRoom();
            rcvMyPost.setLayoutManager(new LinearLayoutManager(ManagePostActivity.this, LinearLayoutManager.VERTICAL, false));
            manageRoomAdapter = new ManageRoomAdapter(roomlist, ManagePostActivity.this);
            rcvMyPost.setAdapter(manageRoomAdapter);
        }
        statusRoomInt = 0;
        fecthRoomData_statusRoom();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();
                switch (position) {
                    case 0:
                        statusRoomInt = 0;
                        break;
                    case 1:
                        statusRoomInt = 1;
                        break;
                }
                fecthRoomData_statusRoom();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
//        spinnerStatusRoom.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                String statusRoom = parent.getItemAtPosition(position).toString();
//                if ("Phòng trống".equals(statusRoom)) {
//                    statusRoomInt = 0;
//                } else if ("Đã cho thuê".equals(statusRoom)) {
//                    statusRoomInt = 1;
//                } else {
//                    statusRoomInt = 0;
//                }
//
//                fecthRoomData_statusRoom();
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

    }

    private void fecthRoomData_statusRoom() {
        roomdatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomlist.clear();
                if (snapshot.exists()) {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        if (dataSnapshot.getKey().equals("Tro") || dataSnapshot.getKey().equals("ChungCuMini")) {
                            // Lấy dữ liệu từ child "Tro"
                            if (dataSnapshot.getKey().equals("Tro") && dataSnapshot.exists()) {
                                for (DataSnapshot troSnapshot : dataSnapshot.getChildren()) {
                                    if (troSnapshot.exists()) {
                                        Room room = troSnapshot.getValue(Room.class);
                                        if (room != null) {
                                            if (userCurrent.getUid().equals(room.getId_own_post()) && room.getStatus_room() == statusRoomInt) {
                                                roomlist.add(room);
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
                                        if (room != null) {
                                            if (userCurrent.getUid().equals(room.getId_own_post()) && room.getStatus_room() == statusRoomInt) {
                                                roomlist.add(room);
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
                manageRoomAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                // Xử lý lỗi nếu có
            }
        });
    }
}