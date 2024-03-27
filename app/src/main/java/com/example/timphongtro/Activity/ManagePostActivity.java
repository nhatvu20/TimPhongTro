package com.example.timphongtro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
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
    Spinner spinnerStatusRoom;
    RecyclerView rcvMyPost;
    ArrayList<Room> roomlist;

    DatabaseReference roomdatabase;
    ManageRoomAdapter manageRoomAdapter;

    FirebaseUser userCurrent;

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
        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewPost = findViewById(R.id.imageViewPost);
        spinnerStatusRoom = findViewById(R.id.spinnerStatusRoom);
        rcvMyPost = findViewById(R.id.rcvMyPost);

        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent main = new Intent(ManagePostActivity.this, MainActivity.class);
                startActivity(main);
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

        String[] data = {"Phòng trống", "Đã cho thuê"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerStatusRoom.setAdapter(adapter);

//        roomdatabase = FirebaseDatabase.getInstance().getReference("myRooms/");

        if (userCurrent != null) {
            roomlist = new ArrayList<>();
            roomdatabase = FirebaseDatabase.getInstance().getReference("myRooms/" + userCurrent.getUid());
            roomdatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            if (dataSnapshot.exists()) {
                                //
                                Room room = dataSnapshot.getValue(Room.class);
                                roomlist.add(room);
                                System.out.println("vào for");
                            }
                        }
                        manageRoomAdapter.notifyDataSetChanged();
                        System.out.println("ko vào for");
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
            rcvMyPost.setLayoutManager(new LinearLayoutManager(ManagePostActivity.this, LinearLayoutManager.VERTICAL, false));
            manageRoomAdapter = new ManageRoomAdapter(roomlist, ManagePostActivity.this);
            rcvMyPost.setAdapter(manageRoomAdapter);
        }
//        fetchroomrecyclerviewdatabse();
    }

//    private void fetchroomrecyclerviewdatabse() {
//        roomlist.clear();
//        roomdatabase.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if (snapshot.exists()) {
//                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
//                        if (dataSnapshot.getKey().equals("Tro") || dataSnapshot.getKey().equals("ChungCuMini")) {
//                            // Lấy dữ liệu từ child "Tro"
//                            if (dataSnapshot.getKey().equals("Tro") && dataSnapshot.exists()) {
//                                for (DataSnapshot troSnapshot : dataSnapshot.getChildren()) {
//                                    if (troSnapshot.exists()) {
//                                        Room room = troSnapshot.getValue(Room.class);
//                                        roomlist.add(room);
//                                    }
//                                }
//                            }
//                            // Lấy dữ liệu từ child "ChungCu"
//                            else if (dataSnapshot.getKey().equals("ChungCuMini") && dataSnapshot.exists()) {
//                                for (DataSnapshot chungCuSnapshot : dataSnapshot.getChildren()) {
//                                    if (chungCuSnapshot.exists()) {
//                                        Room room = chungCuSnapshot.getValue(Room.class);
//                                        roomlist.add(room);
//                                    }
//                                }
//                            }
//                        }
//                    }
//                    manageRoomAdapter.notifyDataSetChanged();
//                } else {
//                    // Không có dữ liệu tồn tại
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//                // Xử lý lỗi nếu có
//            }
//        });
//    }
}