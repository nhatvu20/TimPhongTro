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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {
    Context context;
    ArrayList<Room> list;
    FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    FirebaseUser user = firebaseAuth.getCurrentUser();
    Room room;
    public SearchAdapter(Context context, ArrayList<Room> list) {
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
        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        decimalFormat.setDecimalSeparatorAlwaysShown(false);
        Room room = list.get(position);
        holder.title_room.setText(room.getTitle_room());
        holder.price_room.setText(decimalFormat.format(room.getPrice_room()));
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
                String userID = "";
                if(user != null) {
                    userID = user.getUid();
                }
                Intent detailRoom = new Intent(context, DetailRoomActivity.class);
                detailRoom.putExtra("DataRoom", room.toString());
                context.startActivity(detailRoom);
                RecentlyRead(userID,holder);
            }
        });
    }

    private void RecentlyRead(String userID, SearchAdapter.MyViewHolder holder) {
        if(user != null) {

            room = list.get(holder.getAdapterPosition());
            Date timeRead = new Date();
            long timestamp = timeRead.getTime();
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("History/" + userID);
            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    databaseReference.child(room.getId_room()).setValue(timestamp);
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public void searchDataList(ArrayList<Room> searchList) {
        list = searchList;
        notifyDataSetChanged();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {

        TextView people_room, price_room, area_room, city, district, detail, title_room;
        ImageView img_post;
        CardView cardViewRoom;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_room = itemView.findViewById(R.id.PostTitle);
            price_room = itemView.findViewById(R.id.RoomCost);
            city = itemView.findViewById(R.id.CityName);
            area_room = itemView.findViewById(R.id.DienTich);
            district = itemView.findViewById(R.id.DistrictName);
            detail = itemView.findViewById(R.id.DetailName);
            people_room = itemView.findViewById(R.id.Size);
            img_post = itemView.findViewById(R.id.img_post);
            cardViewRoom = itemView.findViewById(R.id.cardViewRoom);
        }
    }
}

