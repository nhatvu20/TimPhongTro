package com.example.timphongtro.Database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.timphongtro.R;

import java.util.ArrayList;

public class RoomAdapter extends RecyclerView.Adapter<RoomAdapter.MyViewHolder> {

    Context context;

    ArrayList<Room> list;
    int maxitemcount = 10;

    public RoomAdapter(Context context, ArrayList<Room> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.room_view_holder,parent,false);
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

        Addresse addresse = room.getAddress();
        holder.city.setText(addresse.getCity());
        holder.district.setText(addresse.getDistrict());
        holder.detail.setText(addresse.getDetail());

    }

    @Override
    public int getItemCount() {
        return Math.min(list.size(), maxitemcount);
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView people_room, price_room, area_room, city, district, detail, title_room;
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
            img_post = itemView.findViewById(R.id.img_post);
        }
    }
}

