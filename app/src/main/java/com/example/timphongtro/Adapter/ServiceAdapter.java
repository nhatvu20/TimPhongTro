package com.example.timphongtro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.timphongtro.Activity.DetailRoomActivity;
import com.example.timphongtro.Activity.LoginActivity;
import com.example.timphongtro.Activity.ServiceDetailActivity;
import com.example.timphongtro.Entity.Service;
import com.example.timphongtro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ServiceViewHolder> {

    private ArrayList<Service> serviceList, cartItemList;
    private Context context;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();

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
        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        decimalFormat.setDecimalSeparatorAlwaysShown(false);
        Service service = serviceList.get(position);
        holder.name.setText(service.getTitle());
        holder.price.setText(decimalFormat.format(service.getPrice()) + " VNĐ");
        Glide.with(context).load(service.getImg1()).centerCrop().into(holder.image);

        holder.btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user != null) {
                    cartItemList.add(service);
                String userID = user.getUid();
                DatabaseReference serviceRef = FirebaseDatabase.getInstance().getReference("Cart/" + userID);

                serviceRef.orderByChild("title").equalTo(service.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        boolean serviceExists = false;
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            String key = snapshot.getKey();
                            int currentAmount = snapshot.getValue(Service.class).getAmount();
                            int updatedAmount = currentAmount + 1;

                            Service updatedService = snapshot.getValue(Service.class);
                            updatedService.setAmount(updatedAmount);

                            snapshot.getRef().setValue(updatedService);
                            serviceExists = true;
                            break;
                        }

                        if (!serviceExists) {
                            DatabaseReference newServiceRef = serviceRef.push();

                            service.setAmount(1);
                            newServiceRef.setValue(service);
                        }

                        Toast.makeText(context, service.getTitle() + " added!", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            } else {
                    Intent intent = new Intent(context, LoginActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
        }
        });

        holder.cardService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ServiceDetailActivity.class);
                intent.putExtra("ServiceData", service.toString());
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
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
        LinearLayout cardService;

        public ServiceViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.service_name);
            price = itemView.findViewById(R.id.service_price);
            image = itemView.findViewById(R.id.service_img);
            btn_add = itemView.findViewById(R.id.btn_buynow);
            cardService = itemView.findViewById(R.id.cardService);
        }
    }
}