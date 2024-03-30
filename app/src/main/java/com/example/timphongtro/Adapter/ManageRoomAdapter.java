package com.example.timphongtro.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.timphongtro.Activity.DetailRoomActivity;
import com.example.timphongtro.Activity.UpdatePostRoomActivity;
import com.example.timphongtro.Entity.Address;
import com.example.timphongtro.Entity.ImagesRoomClass;
import com.example.timphongtro.Entity.Room;
import com.example.timphongtro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ManageRoomAdapter extends RecyclerView.Adapter<ManageRoomAdapter.MyViewHolder> {
    ArrayList<Room> list;
    Context context;

    public ManageRoomAdapter(ArrayList<Room> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @NonNull
    @Override
    public ManageRoomAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.view_holder_manage_room, parent, false);
        return new ManageRoomAdapter.MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ManageRoomAdapter.MyViewHolder holder, int position) {
        Room room = list.get(position);
        if (room != null) {
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
            holder.constraintViewDetail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent detailRoom = new Intent(context, DetailRoomActivity.class);
                    detailRoom.putExtra("DataRoom", room.toString());
                    context.startActivity(detailRoom);
                }
            });

            holder.imageViewDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setTitle("Xác nhận") // Thiết lập tiêu đề của Dialog
                            .setMessage("Bạn chắc chắn muốn xóa không?")
                            .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String path = "Tro";
                                    int type_room = room.getType_room();
                                    if (type_room == 1) {
                                        path = "ChungCuMini";
                                    }
                                    FirebaseDatabase database = FirebaseDatabase.getInstance();
                                    DatabaseReference myRef = database.getReference("rooms/" + path + "/" + room.getId_room());
                                    FirebaseUser userCurrent = FirebaseAuth.getInstance().getCurrentUser();
//                                    DatabaseReference myPostRef = null;
//
//                                    myPostRef = database.getReference("myRooms/" + userCurrent.getUid() + "/" + room.getId_room());
//                                    myPostRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
//                                        @Override
//                                        public void onComplete(@NonNull Task<Void> task) {
////                                                Toast.makeText(context, "Xóa bài thành công", Toast.LENGTH_SHORT).show();
//                                        }
//                                    });
                                    myRef.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            Toast.makeText(context, "Xóa bài thành công", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            })
                                    .

                            setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            });

            holder.imageViewEdit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent updateRoom = new Intent(context, UpdatePostRoomActivity.class);
                    updateRoom.putExtra("DataRoom", room.toString());
                    context.startActivity(updateRoom);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        if (list == null) {
            return 0;
        }
        return list.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView people_room, price_room, area_room, city, district, detail, title_room;
        CardView cardViewRoom;
        ImageView img_post, imageViewEdit, imageViewDelete;
        ConstraintLayout constraintViewDetail;

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
            imageViewEdit = itemView.findViewById(R.id.imageViewEdit);
            imageViewDelete = itemView.findViewById(R.id.imageViewDelete);
            constraintViewDetail = itemView.findViewById(R.id.constraintViewDetail);
        }
    }
}
