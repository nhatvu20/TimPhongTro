package com.example.timphongtro.Database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timphongtro.R;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.MyViewHolder> {

    Context context;

    ArrayList<RoomViewHolderData> list;

    public SearchAdapter(Context context, ArrayList<RoomViewHolderData> list) {
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
        RoomViewHolderData roomViewHolderData = list.get(position);
        holder.title_room.setText(roomViewHolderData.getTitle_room());
        holder.price_room.setText(String.valueOf(roomViewHolderData.getPrice_room()));
        holder.area_room.setText(String.valueOf(roomViewHolderData.getArea_room()));
        holder.people_room.setText(String.valueOf(roomViewHolderData.getPeople_room()));

        Addresse addresse = roomViewHolderData.getAddresse();
        holder.city.setText(addresse.getCity());
        holder.district.setText(addresse.getDistrict());
        holder.detail.setText(addresse.getDetail());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void searchDataList(ArrayList<RoomViewHolderData> searchList){
        list = searchList;
        notifyDataSetChanged();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView people_room, price_room, area_room, city, district, detail, title_room;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title_room = itemView.findViewById(R.id.PostTitle);
            price_room = itemView.findViewById(R.id.RoomCost);
            city = itemView.findViewById(R.id.CityName);
            area_room = itemView.findViewById(R.id.DienTich);
            district = itemView.findViewById(R.id.DistrictName);
            detail = itemView.findViewById(R.id.DetailName);
            people_room = itemView.findViewById(R.id.Size);
        }
    }
}

