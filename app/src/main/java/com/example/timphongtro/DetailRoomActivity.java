package com.example.timphongtro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.timphongtro.Database.ExtensionRoom_class;
import com.example.timphongtro.Database.FurnitureClass;
import com.example.timphongtro.Database.Room;
import com.example.timphongtro.Database.Service_roomClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class DetailRoomActivity extends AppCompatActivity {
    TextView textViewTitle,textViewPrice,textViewCombine_address,textViewPhone,textViewTypeRoom,textViewFloor,textViewArea,textViewDeposit,textViewPersonInRoom,textViewGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        Bundle bundle = getIntent().getExtras();
        ImageSlider imageSlider = (ImageSlider)findViewById(R.id.ImageRoomPrd);
        ArrayList<SlideModel> slideModels = new ArrayList<>();

        textViewTypeRoom = (TextView) findViewById(R.id.textViewTypeRoom);
        textViewTitle = (TextView) findViewById(R.id.textViewTitle);
        textViewPrice = (TextView) findViewById(R.id.textViewPrice);
        textViewCombine_address = (TextView) findViewById(R.id.textViewCombine_address);
        textViewPhone = (TextView) findViewById(R.id.textViewPhone);
        textViewFloor = (TextView) findViewById(R.id.textViewFloor);
        textViewArea = (TextView) findViewById(R.id.textViewArea);
        textViewDeposit = (TextView) findViewById(R.id.textViewDeposit);
        textViewPersonInRoom = (TextView) findViewById(R.id.textViewPersonInRoom);
        textViewGender = (TextView) findViewById(R.id.textViewGender);

////        Room roomData = (Room) getIntent().getSerializableExtra("roomData");
//        Room roomData = (Room) getIntent().getParcelableExtra("roomData");
//        String title = roomData.getTitle_room().toString();
//        textViewTitle.setText(title);

        if(bundle != null){
            textViewTypeRoom.setText(bundle.getString("TypeRoom"));
            textViewTitle.setText(bundle.getString("Title"));
            String price = bundle.getString("Price")+" đ/tháng";
            textViewPrice.setText(price);
            textViewCombine_address.setText(bundle.getString("CombineAddress"));
            textViewPhone.setText(bundle.getString("Phone"));
            textViewFloor.setText(String.valueOf(bundle.getInt("Floor")));
            textViewArea.setText(bundle.getString("Area"));
            textViewDeposit.setText(String.valueOf(bundle.getLong("Deposit")));
            textViewPersonInRoom.setText(String.valueOf(bundle.getInt("PersonInRoom")));
            textViewGender.setText(bundle.getString("Gender"));

            String img1 = bundle.getString("Image1");
            String img2 = bundle.getString("Image2");
            String IdRoom = bundle.getString("Id_Room");
            slideModels.add(new SlideModel(img1, ScaleTypes.FIT));
            slideModels.add(new SlideModel(img2, ScaleTypes.FIT));
            imageSlider.setImageList(slideModels, ScaleTypes.FIT);

            ArrayList<FurnitureClass> furnitures = new ArrayList<>();

            String path = "rooms";
            FirebaseDatabase database = FirebaseDatabase.getInstance();

            if(bundle.getString("TypeRoom").equals("Trọ")){
                path="rooms/Tro"+IdRoom;
            }else{
                path="rooms/ChungCuMini"+IdRoom;
            }
            path = path+"/furniture";
            DatabaseReference refFurniture = database.getReference(path);
            refFurniture.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        // Get the value of the data
                        
                        FurnitureClass furniture = new FurnitureClass();
//                        snapshot.getKey()
                        // Use the retrieved data as needed
                        // ...
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });
        }
    }
}