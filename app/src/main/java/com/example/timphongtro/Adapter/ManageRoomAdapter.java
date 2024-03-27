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
import com.example.timphongtro.Activity.PostRoomActivity;
import com.example.timphongtro.Entity.Address;
import com.example.timphongtro.Entity.ImagesRoomClass;
import com.example.timphongtro.Entity.Room;
import com.example.timphongtro.R;

import java.util.ArrayList;

public class ManageRoomAdapter extends RecyclerView.Adapter<ManageRoomAdapter.MyViewHolder>{
    ArrayList<Room> list;
    Context context;

    public ManageRoomAdapter(ArrayList<Room> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ManageRoomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_holder_manage_room,parent,false);
        return new ManageRoomAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageRoomAdapter.MyViewHolder holder, int position) {
        Room room = list.get(position);
        if(room != null){
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
                    Intent detailRoom = new Intent(context, PostRoomActivity.class);
//                detailRoom.putExtra("Title",list.get(holder.getAdapterPosition()).getTitle_room());
//                detailRoom.putExtra("Price",String.valueOf(list.get(holder.getAdapterPosition()).getPrice_room()));
//                detailRoom.putExtra("CombineAddress",list.get(holder.getAdapterPosition()).getAddress().getAddress_combine());
//                detailRoom.putExtra("Phone",list.get(holder.getAdapterPosition()).getPhone());
//                ImagesRoomClass images = list.get(holder.getAdapterPosition()).getImages();
//                detailRoom.putExtra("Id_Room",list.get(holder.getAdapterPosition()).getId_room());
//                detailRoom.putExtra("Image1",images.getImg1());
//                detailRoom.putExtra("Image2",images.getImg2());
//                    int typeRoom = list.get(holder.getAdapterPosition()).getType_room();
                    //Truyền object qua intent
//                    detailRoom.putExtra("DataRoom", room.toString());

//                    context.startActivity(detailRoom);
                }
            });

        }
    }

    @Override
    public int getItemCount() {
        if(list == null) {
            return 0;
        }
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView people_room, price_room, area_room, city, district, detail, title_room;
        CardView cardViewRoom;
        ImageView img_post,imageViewEdit,imageViewDelete;
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
//            imageViewEdit = itemView.findViewById(R.id.imageViewEdit);
//            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
        }
    }
}
