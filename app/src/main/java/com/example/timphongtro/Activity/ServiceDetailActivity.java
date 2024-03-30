package com.example.timphongtro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.timphongtro.Entity.Service;
import com.example.timphongtro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class ServiceDetailActivity extends AppCompatActivity {

    private TextView service_title, service_price, service_sold_count, service_description;
    private Button btn_add_to_cart;
    private ImageView button_cart, imageView_back;
    private Service service;
    private ArrayList<Service> cartItemList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_service_detail);

        Bundle bundle = getIntent().getExtras();
        ImageSlider imageSlider = findViewById(R.id.ServiceImage);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        service_title = findViewById(R.id.service_title);
        service_price = findViewById(R.id.service_price);
        service_sold_count = findViewById(R.id.service_sold_count);
        service_description = findViewById(R.id.service_description);
        btn_add_to_cart = findViewById(R.id.btn_add_to_cart);
        button_cart = findViewById(R.id.button_cart);
        imageView_back = findViewById(R.id.imageView_back);

        button_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ServiceDetailActivity.this, CartActivity.class);
                startActivity(intent);
            }
        });
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btn_add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cartItemList.add(service);
                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                String userID = user.getUid();
                DatabaseReference serviceRef = FirebaseDatabase.getInstance().getReference("users").child(userID).child("cart");

                serviceRef.orderByChild("title").equalTo(service.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean serviceExists = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            snapshot.getKey();
                            int currentAmount = snapshot.child("amount").getValue(Integer.class);
                            int updatedAmount = currentAmount + 1;
                            snapshot.getRef().child("amount").setValue(updatedAmount);
                            serviceExists = true;
                            break;
                        }

                        if (!serviceExists) {
                            DatabaseReference newServiceRef = serviceRef.push();
                            newServiceRef.child("title").setValue(service.getTitle());
                            newServiceRef.child("img1").setValue(service.getImg1());
                            newServiceRef.child("price").setValue(service.getPrice());
                            newServiceRef.child("amount").setValue(1);
                        }

                        Toast.makeText(ServiceDetailActivity.this, service.getTitle() + " added!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });

        if (bundle != null){
            String serviceString = bundle.getString("ServiceData");
            Gson gson = new Gson();
            service = gson.fromJson(serviceString, Service.class);

            service_title.setText(service.getTitle());
            String price = String.valueOf(service.getPrice()) + " VNĐ";
            service_price.setText(price);
            service_sold_count.setText(String.valueOf(service.getSold()));
            service_description.setText(service.getDescription());

            slideModels.add(new SlideModel(service.getImg1(), ScaleTypes.FIT));
            slideModels.add(new SlideModel(service.getImg2(), ScaleTypes.FIT));
            imageSlider.setImageList(slideModels, ScaleTypes.FIT);
        }
    }
}