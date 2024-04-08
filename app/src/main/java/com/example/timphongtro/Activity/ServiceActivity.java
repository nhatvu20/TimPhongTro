package com.example.timphongtro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timphongtro.Adapter.ServiceAdapter;
import com.example.timphongtro.Entity.Service;
import com.example.timphongtro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ServiceActivity extends AppCompatActivity {

    private RecyclerView rcv_service;
    private ImageView cart_button, back_button;
    private ServiceAdapter serviceAdapter;
    private ArrayList<Service> serviceArrayList;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    private String item;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service);
        cart_button = findViewById(R.id.button_cart);
        back_button = findViewById(R.id.imageView_back);
        back_button.setColorFilter(ContextCompat.getColor(this, R.color.white));
        cart_button.setColorFilter(ContextCompat.getColor(this, R.color.white));

        item = "";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            item = bundle.getString("item");
            back_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            cart_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user != null) {
                        openServiceActivity(item);
                    } else {
                        Intent intent = new Intent(ServiceActivity.this, LoginActivity.class);
                        startActivity(intent);
                    }
                }
            });

            UIprocess();
            fetchservicefromDB();
        }
    }

    private void UIprocess() {
        rcv_service = findViewById(R.id.rcv_service);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcv_service.setLayoutManager(gridLayoutManager);

        serviceArrayList = new ArrayList<>();
        serviceAdapter = new ServiceAdapter(getApplicationContext(), serviceArrayList);
        rcv_service.setAdapter(serviceAdapter);
    }

    private void fetchservicefromDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = database.getReference("service/" + item);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                serviceArrayList.clear();
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Service product = dataSnapshot.getValue(Service.class);
                    serviceArrayList.add(product);
                }
                serviceAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void openServiceActivity(String item) {
        Intent intent = new Intent(ServiceActivity.this, CartActivity.class);
        Intent intent1 = new Intent(ServiceActivity.this, ServiceDetailActivity.class);
        intent.putExtra("item", item);
        intent1.putExtra("item", item);
        startActivity(intent);
    }
}