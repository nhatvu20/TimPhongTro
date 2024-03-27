package com.example.timphongtro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
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

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private ArrayList<Service> serviceList, cartItemList;
    private Context context;

    public ServiceAdapter(Context context, ArrayList<Service> serviceList) {
        this.context = context;
        this.serviceList = serviceList;
        this.cartItemList = new ArrayList<>();

    }

    @NonNull
    @Override
    public ServiceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.service_view_holder, parent, false);
        return new ServiceViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ServiceAdapter.ServiceViewHolder holder, int position) {
        Service service = serviceList.get(position);
        holder.name.setText(service.getTitle());
        holder.price.setText(service.getPrice() + " VNĐ");
        Glide.with(context).load(service.getImg()).centerCrop().into(holder.image);

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
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
                            newServiceRef.child("img").setValue(service.getImg());
                            newServiceRef.child("price").setValue(service.getPrice());
                            newServiceRef.child("amount").setValue(1);
                        }

                        Toast.makeText(context, service.getTitle() + " added!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return serviceList.size();
    }

    public static class ServiceViewHolder extends RecyclerView.ViewHolder {

        TextView name, price;
        ImageView image;
        Button btn_add;

        public ServiceViewHolder(View itemview) {
            super(itemview);
            name = itemview.findViewById(R.id.service_name);
            price = itemview.findViewById(R.id.service_price);
            image = itemview.findViewById(R.id.service_img);
            btn_add = itemview.findViewById(R.id.btn_buynow);
        }
    }
}
