package com.example.timphongtro.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timphongtro.Adapter.RoomAdapter;
import com.example.timphongtro.Entity.Room;
import com.example.timphongtro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class HistoryActivity extends AppCompatActivity {
    FirebaseUser user;
    FirebaseDatabase database;
    DatabaseReference myHistoryRef;
    ArrayList<Room> roomArrayList;
    RoomAdapter roomAdapter;
    RecyclerView rcvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        ImageView imageViewBack = findViewById(R.id.imageView_back);
        ImageView button_clear = findViewById(R.id.button_clear);

        imageViewBack.setColorFilter(ContextCompat.getColor(this, R.color.white));
        button_clear.setColorFilter(ContextCompat.getColor(this, R.color.white));

        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                builder.setTitle("Xác nhận").setMessage("Bạn có muốn xoá toàn bộ lịch sử?").setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        DatabaseReference myRef = database.getReference("History/" + user.getUid());
                        myRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                Toast.makeText(HistoryActivity.this, "Xóa lịch sử thành công", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        rcvHistory = findViewById(R.id.rcv_history);
        roomArrayList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvHistory.setLayoutManager(linearLayoutManager);

            myHistoryRef = database.getReference("History/" + user.getUid());
            myHistoryRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    roomArrayList.clear();
                    if (snapshot.exists()) {
                        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                            Room roomCur = dataSnapshot.getValue(Room.class);
                            roomArrayList.add(roomCur);
                        }
                        Collections.reverse(roomArrayList);
                    }
                    roomAdapter.notifyDataSetChanged();

                    if (roomArrayList.isEmpty()) {
                        rcvHistory.setVisibility(View.GONE);
                        findViewById(R.id.nohistory).setVisibility(View.VISIBLE);
                    } else {
                        rcvHistory.setVisibility(View.VISIBLE);
                        findViewById(R.id.nohistory).setVisibility(View.GONE);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        roomAdapter = new RoomAdapter(HistoryActivity.this, roomArrayList);
        rcvHistory.setAdapter(roomAdapter);
    }
}