package com.example.timphongtro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.timphongtro.Database.Room;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class PostRoomActivity extends AppCompatActivity {
    EditText edtTitleRoom;
    EditText edtPrice;
    Button btn_create_room;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        edtTitleRoom = (EditText) this.<View>findViewById(R.id.edtTitleRoom);
        edtPrice = (EditText) this.<View>findViewById(R.id.edtPrice);
        btn_create_room = (Button) this.<View>findViewById(R.id.btn_create_room);
        setContentView(R.layout.activity_post_room);
        btn_create_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),"OK",Toast.LENGTH_SHORT).show();
            }
        });
    }

    void onClickPushData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("roomsTest/Tro");
        //tao doi tuong / chu y tao doi tuong phai co du ham tao co tham so khong tham so, du getter setter va co toString mac dinh cua class
        Room room = new Room("Phòng đẹp giá rẻ", 10000);
        myRef.setValue(room);
        //setValue myRef.setValue(doi tuong, new DatabaseReference.Completionlistener )
        //myRef.child("tạo id").setValue()
    }


}