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
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class HistoryActivity extends AppCompatActivity {
    private FirebaseDatabase database;
    private ArrayList<Room> roomArrayList;
    private RoomAdapter roomAdapter;
    private RecyclerView rcvHistory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        database = FirebaseDatabase.getInstance();
        DatabaseReference myHistoryRef = database.getReference("History/" + user.getUid());

        ImageView imageViewBack = findViewById(R.id.imageView_back);
        ImageView button_clear = findViewById(R.id.button_clear);

        imageViewBack.setColorFilter(ContextCompat.getColor(this, R.color.white));
        button_clear.setColorFilter(ContextCompat.getColor(this, R.color.white));

        button_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(HistoryActivity.this);
                builder.setTitle("Delete history").setMessage("Are you sure want to delete all?").setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        FirebaseDatabase database = FirebaseDatabase.getInstance();
                        myHistoryRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                roomArrayList.clear();
                                roomAdapter.notifyDataSetChanged();
                                updateRecyclerViewVisibility(roomArrayList, rcvHistory, findViewById(R.id.nohistory));
                                Toast.makeText(HistoryActivity.this, "Successful", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }).setNegativeButton("No", new DialogInterface.OnClickListener() {
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
        roomAdapter = new RoomAdapter(HistoryActivity.this, roomArrayList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(HistoryActivity.this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rcvHistory.setLayoutManager(linearLayoutManager);
        rcvHistory.setAdapter(roomAdapter);

        myHistoryRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                roomArrayList.clear();
                Map<String, Long> roomTimeMap = new HashMap<>();
                ArrayList<String> roomIds = new ArrayList<>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    String roomId = dataSnapshot.getKey();
                    long timestamp = dataSnapshot.getValue(Long.class);
                    roomIds.add(roomId);
                    roomTimeMap.put(roomId, timestamp);
                }

                DatabaseReference roomsRef = database.getReference("rooms");
                roomsRef.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot roomsSnapshot) {
                        // Duyệt qua các nhánh to, ở đây là "Tro" và "ChungCuMini"
                        if (roomsSnapshot.exists()) {
                            for (DataSnapshot roomTypeSnapshot : roomsSnapshot.getChildren()) {
                                // Duyệt qua các phòng trong nhánh
                                for (DataSnapshot roomSnapshot : roomTypeSnapshot.getChildren()) {
                                    String roomId = roomSnapshot.getKey();
                                    if (roomIds.contains(roomId)) {
                                        Room roomCur = roomSnapshot.getValue(Room.class);
                                        roomArrayList.add(roomCur);
                                    }
                                }
                            }
                        }
                        roomArrayList.sort(new Comparator<Room>() {
                            @Override
                            public int compare(Room o1, Room o2) {
                                Long timestamp1 = roomTimeMap.get(o1.getId_room());
                                Long timestamp2 = roomTimeMap.get(o2.getId_room());
                                if (timestamp1 != null && timestamp2 != null) {
                                    return Long.compare(timestamp2, timestamp1);
                                } else if (timestamp1 != null) {
                                    return -1; // timestamp2 is null, consider timestamp1 smaller
                                } else if (timestamp2 != null) {
                                    return 1; // timestamp1 is null, consider timestamp2 smaller
                                } else {
                                    return 0; // both timestamps are null, consider them equal
                                }
                            }
                        });

                        roomAdapter.notifyDataSetChanged();
                        updateRecyclerViewVisibility(roomArrayList, rcvHistory, findViewById(R.id.nohistory));
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(HistoryActivity.this, "Errors while getting history!", Toast.LENGTH_SHORT).show();
                    }
                });

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(HistoryActivity.this, "Errors while getting history!", Toast.LENGTH_SHORT).show();
            }
        });

        roomAdapter = new RoomAdapter(HistoryActivity.this, roomArrayList);
        rcvHistory.setAdapter(roomAdapter);
    }

    private void updateRecyclerViewVisibility(ArrayList<Room> roomArrayList, RecyclerView rcvHistory, View noHistoryView) {
        if (roomArrayList.isEmpty()) {
            rcvHistory.setVisibility(View.GONE);
            noHistoryView.setVisibility(View.VISIBLE);
        } else {
            rcvHistory.setVisibility(View.VISIBLE);
            noHistoryView.setVisibility(View.GONE);
        }
    }
}