package com.example.timphongtro.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timphongtro.Adapter.CartAdapter;
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

public class CartActivity extends AppCompatActivity {

    private TextView textView_total;
    private RecyclerView rcvcart;
    private Button btn_checkout;
    private ImageView imageView_back;
    private CartAdapter cartAdapter;
    private ArrayList<Service> cartList;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    private String item;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        imageView_back = findViewById(R.id.imageView_back);
        btn_checkout = findViewById(R.id.button_checkout);
        textView_total = findViewById(R.id.Total_price);

            btn_checkout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    long totalprice = calculateTotalPrice(cartList);
                    Toast.makeText(CartActivity.this, totalprice + " VNĐ has been paid!", Toast.LENGTH_SHORT).show();
                }
            });
            imageView_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    item = "";
                    Bundle bundle = getIntent().getExtras();
                    if (bundle != null) {
                        item = bundle.getString("item");
                        openServiceActivity(item);
                    } else {
                        Intent intent = new Intent(CartActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                }
            });
            UIproccess();
            fetchproductfromDB();
    }

    private void UIproccess() {
        rcvcart = findViewById(R.id.rcvcart);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rcvcart.setLayoutManager(linearLayoutManager);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        cartList = new ArrayList<>();
        cartAdapter = new CartAdapter(getApplicationContext(), cartList);
        rcvcart.setAdapter(cartAdapter);
    }

    private void fetchproductfromDB() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String userID = user.getUid();
        DatabaseReference databaseReference = database.getReference("users").child(userID).child("cart");

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                cartList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Service service = childSnapshot.getValue(Service.class);
                    cartList.add(service);
                }
                cartAdapter.notifyDataSetChanged();

                long totalPrice = calculateTotalPrice(cartList);
                textView_total.setText(String.valueOf(totalPrice));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    private long calculateTotalPrice(ArrayList<Service> cartList) {
        long totalPrice = 0;

        for (Service service : cartList) {
            totalPrice += (long) service.getAmount() * service.getPrice();
        }

        return totalPrice;
    }

    private void openServiceActivity(String item) {
        Intent intent = new Intent(CartActivity.this, ServiceActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }
}