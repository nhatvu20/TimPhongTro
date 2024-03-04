package com.example.timphongtro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timphongtro.Database.Room;
import com.example.timphongtro.HomePage.MainActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SplashScreen extends AppCompatActivity {

    public static int SPLASH_TIMER = 3000;
    EditText edtTitleRoom;
    EditText edtPrice;
    Button btn_create_room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_post_room);
        btn_create_room = (Button) findViewById(R.id.btn_create_room);
        edtTitleRoom = (EditText) findViewById(R.id.edtTitleRoom);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        btn_create_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                onClickPushData();
            }
        });


//        setContentView(R.layout.activity_splash_screen);
//
//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, SPLASH_TIMER);
    }

    void onClickPushData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("roomsTest/Tro");
        //tao doi tuong / chu y tao doi tuong phai co du ham tao co tham so khong tham so, du getter setter va co toString mac dinh cua class
//        Room room = new Room("Phòng đẹp giá rẻ", 10000);
        Room room = new Room(edtTitleRoom.getText().toString(), Integer.parseInt(String.valueOf(edtPrice.getText())));
        myRef.child(room.getTitle_room()).setValue(room);
        //setValue myRef.setValue(doi tuong, new DatabaseReference.Completionlistener )
        //myRef.child("tạo id").setValue()
    }
}