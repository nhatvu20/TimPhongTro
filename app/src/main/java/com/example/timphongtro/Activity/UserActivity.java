package com.example.timphongtro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timphongtro.Adapter.ExtensionAdapter;
import com.example.timphongtro.Adapter.RoomAdapter;
import com.example.timphongtro.Entity.ExtensionRoom_class;
import com.example.timphongtro.Entity.Room;
import com.example.timphongtro.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private ImageView imageView_back;
    private TextView username, number, email;
    private RecyclerView rcvpost;
    private RoomAdapter roomAdapter;
    private FirebaseDatabase database;
    private DatabaseReference postRef, userRef;
    private ArrayList<Room> roomArrayList;
    private Room room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Bundle bundle = getIntent().getExtras();
        username = findViewById(R.id.username);
        imageView_back = findViewById(R.id.imageView_back);
        imageView_back.setColorFilter(ContextCompat.getColor(this, R.color.white));
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        roomArrayList = new ArrayList<>();
        roomAdapter = new RoomAdapter(UserActivity.this, roomArrayList);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rcvpost.setLayoutManager(layoutManager);
        rcvpost.setAdapter(roomAdapter);

        postRef = database.getReference("rooms/");

        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                roomArrayList.clear();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        if (bundle != null) {
            String roomString = bundle.getString("DataUser");
            Gson gson = new Gson();
            room = gson.fromJson(roomString, Room.class);
            String userID = room.getId_own_post();
            userRef = database.getReference("users/" +userID);

            userRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    String name = snapshot.getValue(String.class);
                    username.setText(name);

                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}