package com.example.timphongtro;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.timphongtro.Database.FurnitureAdapter;
import com.example.timphongtro.Database.FurnitureClass;
import com.example.timphongtro.Database.Room;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DetailRoomActivity extends AppCompatActivity {
    TextView textViewTitle, textViewPrice, textViewCombine_address, textViewPhone, textViewTypeRoom, textViewFloor, textViewArea, textViewDeposit, textViewPersonInRoom, textViewGender,
            textViewWater,textViewInternet,textViewElectric;
    RecyclerView recycleviewFuniture;
    FurnitureAdapter furnitureAdapter;

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
        textViewWater = findViewById(R.id.textViewWater);
        textViewInternet = findViewById(R.id.textViewInternet);
        textViewElectric = findViewById(R.id.textViewElectric);

        if (bundle != null) {
            String roomString = bundle.getString("DataRoom");
            Gson gson = new Gson();
            Room roomData = gson.fromJson(roomString, Room.class);
            textViewTypeRoom.setText(bundle.getString("TypeRoom"));
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
            recycleviewFuniture.setLayoutManager(layoutManager);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            recycleviewFuniture.setAdapter(furnitureAdapter);

        }
    }
}