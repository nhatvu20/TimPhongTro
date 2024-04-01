package com.example.timphongtro.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.timphongtro.Activity.DetailRoomActivity;
import com.example.timphongtro.Entity.Address;
import com.example.timphongtro.Entity.ImagesRoomClass;
import com.example.timphongtro.Entity.Room;
import com.example.timphongtro.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MyViewHolder> {

    Context context;
    ArrayList<Room> list;
    int maxitemcount = 10;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    String userID = user.getUid();
    Room room;


    public RoomAdapter(Context context, ArrayList<Room> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.room_view_holder, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Room room = list.get(position);
        holder.title_room.setText(room.getTitle_room());
        holder.price_room.setText(String.valueOf(room.getPrice_room()));
        holder.area_room.setText(String.valueOf(room.getArea_room()));
        holder.people_room.setText(String.valueOf(room.getPerson_in_room()));

        ImagesRoomClass imagesRoomClass = room.getImages();
        Glide.with(context).load(imagesRoomClass.getImg1()).centerCrop().into(holder.img_post);

        Address address = room.getAddress();
        holder.city.setText(address.getCity());
        holder.district.setText(address.getDistrict());
        holder.detail.setText(address.getDetail());
        holder.cardViewRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailRoom = new Intent(context, DetailRoomActivity.class);
                int typeRoom = list.get(holder.getAdapterPosition()).getType_room();
                detailRoom.putExtra("DataRoom", room.toString());
                context.startActivity(detailRoom);
                RecentlyRead(userID,holder);
            }

        });
    }

    private void RecentlyRead(String userID, MyViewHolder holder) {
        room = list.get(holder.getAdapterPosition());
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("users/" + userID + "/history");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                ArrayList<Room> recently_read = snapshot.getValue(new GenericTypeIndicator<ArrayList<Room>>() {});
                if (recently_read == null) {
                    recently_read = new ArrayList<>();
                } else {
                    // Tìm kiếm phần tử trong mảng và xoá nếu cần
                    int existingIndex = -1;
                    for (int i = 0; i < recently_read.size(); i++) {
                        Room existingRoom = recently_read.get(i);
                        if (existingRoom.getId_room().equals(room.getId_room())) {
                            existingIndex = i;
                            break;
                        }
                    }
                    if (existingIndex != -1) {
                        recently_read.remove(existingIndex);
                    }
                }
                recently_read.add(room);
                databaseReference.setValue(recently_read).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {

                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                    }
                });
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return Math.min(list.size(), maxitemcount);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView people_room, price_room, area_room, city, district, detail, title_room;
        CardView cardViewRoom;
        ImageView img_post;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_room = itemView.findViewById(R.id.PostTitle);
            price_room = itemView.findViewById(R.id.RoomCost);
            city = itemView.findViewById(R.id.CityName);
            area_room = itemView.findViewById(R.id.DienTich);
            district = itemView.findViewById(R.id.DistrictName);
            detail = itemView.findViewById(R.id.DetailName);
            people_room = itemView.findViewById(R.id.Size);
            cardViewRoom = itemView.findViewById(R.id.cardViewRoom);
            img_post = itemView.findViewById(R.id.img_post);
        }
    }
}

