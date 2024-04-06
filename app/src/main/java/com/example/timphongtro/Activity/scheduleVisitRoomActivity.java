package com.example.timphongtro.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.timphongtro.R;
import com.google.android.material.tabs.TabLayout;

public class scheduleVisitRoomActivity extends AppCompatActivity {
    ImageView imageViewBack;
    TabLayout tablayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schedule_visit_room);
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
//            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
//            return insets;
//        });
        imageViewBack = findViewById(R.id.imageViewBack);
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