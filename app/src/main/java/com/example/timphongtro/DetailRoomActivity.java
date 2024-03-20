package com.example.timphongtro;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.timphongtro.Database.ExtensionRoom_class;
import com.example.timphongtro.Database.FurnitureAdapter;
import com.example.timphongtro.Database.FurnitureClass;
import com.example.timphongtro.Database.Room;
import com.example.timphongtro.Database.Service_roomClass;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class DetailRoomActivity extends AppCompatActivity {
    TextView textViewTitle, textViewPrice, textViewCombine_address, textViewPhone, textViewTypeRoom, textViewFloor, textViewArea, textViewDeposit, textViewPersonInRoom, textViewGender;
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

////        Room roomData = (Room) getIntent().getSerializableExtra("roomData");
//        Room roomData = (Room) getIntent().getParcelableExtra("roomData");
//        String title = roomData.getTitle_room().toString();
//        textViewTitle.setText(title);

        if (bundle != null) {
            textViewTypeRoom.setText(bundle.getString("TypeRoom"));
            textViewTitle.setText(bundle.getString("Title"));
            String price = bundle.getString("Price") + " đ/tháng";
            textViewPrice.setText(price);
            textViewCombine_address.setText(bundle.getString("CombineAddress"));
            textViewPhone.setText(bundle.getString("Phone"));
            textViewFloor.setText(String.valueOf(bundle.getInt("Floor")));
            textViewArea.setText(bundle.getString("Area"));
            textViewDeposit.setText(String.valueOf(bundle.getLong("Deposit")));
            textViewPersonInRoom.setText(String.valueOf(bundle.getInt("PersonInRoom")));
            textViewGender.setText(bundle.getString("Gender"));
            String roomString = bundle.getString("DataRoom");
            Gson gson = new Gson();
            Room roomData = gson.fromJson(roomString, Room.class);

            String img1 = bundle.getString("Image1", "");
            String img2 = bundle.getString("Image2", "");
            String IdRoom = bundle.getString("Id_Room");

            if (!TextUtils.isEmpty(img1)) {
                slideModels.add(new SlideModel(img1, ScaleTypes.FIT));
            }

            if (!TextUtils.isEmpty(img2)) {
                slideModels.add(new SlideModel(img2, ScaleTypes.FIT));
            }

            imageSlider.setImageList(slideModels, ScaleTypes.FIT);

            ArrayList<FurnitureClass> furnitures = new ArrayList<>();
            FurnitureClass f1 = new FurnitureClass("Test", "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-Wardrobe.svg?alt=media&token=0853bfec-58f6-4865-acb0-8892729708c8");
            FurnitureClass f2 = new FurnitureClass("Test", "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-Wardrobe.svg?alt=media&token=0853bfec-58f6-4865-acb0-8892729708c8");
            FurnitureClass f3 = new FurnitureClass("Test", "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-Wardrobe.svg?alt=media&token=0853bfec-58f6-4865-acb0-8892729708c8");
            FurnitureClass f4 = new FurnitureClass("Test", "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-Wardrobe.svg?alt=media&token=0853bfec-58f6-4865-acb0-8892729708c8");
            furnitures.add(f1);
            furnitures.add(f2);
            furnitures.add(f3);
            furnitures.add(f4);
            //            ArrayList<FurnitureClass> furnitures = roomData.getFurniture();

            furnitureAdapter = new FurnitureAdapter(DetailRoomActivity.this, furnitures);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            recycleviewFuniture.setLayoutManager(layoutManager);
            recycleviewFuniture.setAdapter(furnitureAdapter);

        }
    }
}