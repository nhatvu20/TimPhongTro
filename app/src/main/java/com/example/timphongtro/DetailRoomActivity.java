package com.example.timphongtro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.timphongtro.Database.ExtensionAdapter;
import com.example.timphongtro.Database.ExtensionRoom_class;
import com.example.timphongtro.Database.FurnitureAdapter;
import com.example.timphongtro.Database.FurnitureClass;
import com.example.timphongtro.Database.Room;
import com.example.timphongtro.HomePage.MainActivity;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DetailRoomActivity extends AppCompatActivity {
    TextView textViewTitle, textViewPrice, textViewCombine_address, textViewPhone, textViewTypeRoom, textViewFloor, textViewArea, textViewDeposit, textViewPersonInRoom, textViewGender,
            textViewWater,textViewInternet,textViewElectric;
    RecyclerView recycleviewFuniture;
    RecyclerView recycleviewExtension;
    FurnitureAdapter furnitureAdapter;
    ExtensionAdapter extensionAdapter;
    ImageView imageViewBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        Bundle bundle = getIntent().getExtras();
        ImageSlider imageSlider = (ImageSlider) findViewById(R.id.ImageRoomPrd);
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
        recycleviewFuniture = (RecyclerView) findViewById(R.id.recycleviewFuniture);
        recycleviewExtension = (RecyclerView) findViewById(R.id.recycleviewExtension);
        textViewWater = findViewById(R.id.textViewWater);
        textViewInternet = findViewById(R.id.textViewInternet);
        textViewElectric = findViewById(R.id.textViewElectric);
        imageViewBack = findViewById(R.id.imageViewBack);

        if (bundle != null) {
            String roomString = bundle.getString("DataRoom");
            Gson gson = new Gson();
            Room roomData = gson.fromJson(roomString, Room.class);
            String typeRoomStr = "";
            if(roomData.getType_room()==0){
                typeRoomStr = "Trọ";
            }else{
                typeRoomStr = "Chung cư mini";
            }
            textViewTypeRoom.setText(typeRoomStr);
            textViewTitle.setText(roomData.getTitle_room());
            String price = roomData.getPrice_room() + " đ/tháng";
            textViewPrice.setText(price);
            textViewCombine_address.setText(roomData.getAddress().getAddress_combine());
            textViewPhone.setText(roomData.getPhone());
            textViewFloor.setText(String.valueOf(roomData.getFloor()));
            textViewArea.setText(roomData.getArea_room());
            textViewDeposit.setText(String.valueOf(roomData.getDeposit_room()));
            textViewPersonInRoom.setText(String.valueOf(roomData.getPerson_in_room()));
            textViewGender.setText(roomData.getGender_room());
            textViewWater.setText(String.valueOf(roomData.getPrice_water()));
            textViewWater.setText(String.valueOf(roomData.getPrice_water()));
            textViewInternet.setText(String.valueOf(roomData.getPrice_internet()));
            textViewElectric.setText(String.valueOf(roomData.getPrice_electric()));


            slideModels.add(new SlideModel(roomData.getImages().getImg1(), ScaleTypes.FIT));
            slideModels.add(new SlideModel(roomData.getImages().getImg2(), ScaleTypes.FIT));
            slideModels.add(new SlideModel(roomData.getImages().getImg3(), ScaleTypes.FIT));
            slideModels.add(new SlideModel(roomData.getImages().getImg4(), ScaleTypes.FIT));

            imageSlider.setImageList(slideModels, ScaleTypes.FIT);

            ArrayList<FurnitureClass> furnitures = roomData.getFurniture();

            furnitureAdapter = new FurnitureAdapter(DetailRoomActivity.this, furnitures);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            recycleviewFuniture.setLayoutManager(layoutManager);
            recycleviewFuniture.setAdapter(furnitureAdapter);

            ArrayList<ExtensionRoom_class> extensions = roomData.getExtension_room();
            extensionAdapter = new ExtensionAdapter(DetailRoomActivity.this, extensions);
            LinearLayoutManager layoutManager1 = new LinearLayoutManager(this);
            layoutManager1.setOrientation(RecyclerView.HORIZONTAL);
            recycleviewExtension.setLayoutManager(layoutManager1);
            recycleviewExtension.setAdapter(extensionAdapter);
            imageViewBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent main = new Intent(DetailRoomActivity.this, MainActivity.class);
                    startActivity(main);
                }
            });
        }
    }
}