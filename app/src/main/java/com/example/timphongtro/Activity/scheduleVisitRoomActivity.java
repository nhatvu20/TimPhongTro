package com.example.timphongtro.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timphongtro.R;
import com.google.android.material.tabs.TabLayout;

public class scheduleVisitRoomActivity extends AppCompatActivity {
    ImageView imageViewBack;
    TabLayout tablayout;
    RecyclerView rcvScheduleVisit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule_visit_room);

        imageViewBack = findViewById(R.id.imageViewBack);
        rcvScheduleVisit = findViewById(R.id.rcvScheduleVisit);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(scheduleVisitRoomActivity.this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rcvScheduleVisit.setLayoutManager(linearLayoutManager);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        tablayout = findViewById(R.id.tablayout);
        tablayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                int position = tab.getPosition();


                switch (position) {
                    case 0:
                        Toast.makeText(scheduleVisitRoomActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(scheduleVisitRoomActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(scheduleVisitRoomActivity.this, String.valueOf(position), Toast.LENGTH_SHORT).show();
                        break;
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}