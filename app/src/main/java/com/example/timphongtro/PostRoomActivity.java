package com.example.timphongtro;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.timphongtro.Database.Addresse;
import com.example.timphongtro.Database.ImagesClass;
import com.example.timphongtro.Database.Room;
import com.example.timphongtro.Database.furnitureClass;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.UUID;

public class PostRoomActivity extends AppCompatActivity {
    EditText edtTitleRoom;

    //Còn cần spinner cho address

    EditText edtDeposit;
    EditText edtPrice;
    Button btn_create_room;
    EditText edtInternet;
    EditText edtElectric;
    EditText edtWater;
    RadioButton radiobtnChungCu;
    RadioButton radiobtnTro;
    EditText edtArea;
    EditText edtPhone;
    EditText edtFloor;
    EditText edtPerson;
    EditText edtDescriptionRoom;
    EditText edtPark;
    CheckBox checkboxtoilet;
    CheckBox checkboxfloor;
    CheckBox checkbox_time_flex;
    CheckBox checkboxfingerprint;
    CheckBox checkboxbacony;
    CheckBox checkboxpet;
    CheckBox checkbox_w_owner;
    CheckBox checkbox_air_condition;
    CheckBox checkbox_heater;
    CheckBox checkbox_curtain;
    CheckBox checkboxfridge;
    CheckBox checkboxbed;
    CheckBox checkboxwardrobe;
    CheckBox checkbox_washing_machine;
    CheckBox checkbosofa;
    CheckBox checkboxNam;
    CheckBox checkboxNu;
    RadioButton radiobtnPhongTrong;
    RadioButton radiobtnDaChoThue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_room);

        edtTitleRoom = (EditText) this.<View>findViewById(R.id.edtTitleRoom);
        edtPrice = (EditText) this.<View>findViewById(R.id.edtPrice);
        edtDeposit = (EditText) this.<View>findViewById(R.id.edtDeposit);

        edtInternet = (EditText) this.<View>findViewById(R.id.edtInternet);
        edtElectric = (EditText) this.<View>findViewById(R.id.edtElectric);
        edtWater = (EditText) this.<View>findViewById(R.id.edtWater);

        radiobtnChungCu = (RadioButton) this.<View>findViewById(R.id.radiobtnChungCu);
        radiobtnTro = (RadioButton) this.<View>findViewById(R.id.radiobtnTro);

        edtArea = (EditText) this.<View>findViewById(R.id.edtArea);
        edtPhone = (EditText) this.<View>findViewById(R.id.edtPhone);
        edtFloor = (EditText) this.<View>findViewById(R.id.edtFloor);
        edtPerson = (EditText) this.<View>findViewById(R.id.edtPerson);
        edtDescriptionRoom = (EditText) this.<View>findViewById(R.id.edtDescriptionRoom);
        edtPark = (EditText) this.<View>findViewById(R.id.edtPark);

        checkboxtoilet = (CheckBox) this.<View>findViewById(R.id.checkboxtoilet);
        checkboxfloor = (CheckBox) this.<View>findViewById(R.id.checkboxfloor);
        checkbox_time_flex = (CheckBox) this.<View>findViewById(R.id.checkbox_time_flex);
        checkboxfingerprint = (CheckBox) this.<View>findViewById(R.id.checkboxfingerprint);
        checkboxbacony = (CheckBox) this.<View>findViewById(R.id.checkboxbacony);
        checkboxpet = (CheckBox) this.<View>findViewById(R.id.checkboxpet);
        checkbox_w_owner = (CheckBox) this.<View>findViewById(R.id.checkbox_w_owner);

        checkbox_air_condition = (CheckBox) this.<View>findViewById(R.id.checkbox_air_condition);
        checkbox_heater = (CheckBox) this.<View>findViewById(R.id.checkbox_heater);
        checkbox_curtain = (CheckBox) this.<View>findViewById(R.id.checkbox_curtain);
        checkboxfridge = (CheckBox) this.<View>findViewById(R.id.checkboxfridge);
        checkboxbed = (CheckBox) this.<View>findViewById(R.id.checkboxbed);
        checkboxwardrobe = (CheckBox) this.<View>findViewById(R.id.checkboxwardrobe);
        checkbox_washing_machine = (CheckBox) this.<View>findViewById(R.id.checkbox_washing_machine);
        checkbosofa = (CheckBox) this.<View>findViewById(R.id.checkbosofa);

        checkboxNam = (CheckBox) this.<View>findViewById(R.id.checkboxNam);
        checkboxNu = (CheckBox) this.<View>findViewById(R.id.checkboxNu);

        radiobtnPhongTrong = (RadioButton) this.<View>findViewById(R.id.radiobtnPhongTrong);
        radiobtnDaChoThue = (RadioButton) this.<View>findViewById(R.id.radiobtnDaChoThue);

        btn_create_room = (Button) this.<View>findViewById(R.id.btn_create_room);

        btn_create_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
                onClickPushData();
            }
        });
    }

    void onClickPushData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("roomsTest/Tro");
        //tao doi tuong / chu y tao doi tuong phai co du ham tao co tham so khong tham so, du getter setter va co toString mac dinh cua class
        String id_room = UUID.randomUUID().toString();
        String title_room = String.valueOf(edtTitleRoom.getText());
//        long price_room = Long.parseLong(String.valueOf(edtPrice.getText()));
        long price_room = 0;
        String city = "Hà Nội";
        String district = "Nam Từ Liêm";
        String detail = "Ngõ 352";
        String ward = "Phương Canh";
        String address_combine = detail + ", " + ward + ", " + district + ", " + city;

        Addresse address = new Addresse(city, district, detail, ward, address_combine);
        String area_room = edtArea.getText().toString();
        long deposit_room = 0;
        String description_room = edtDescriptionRoom.getText().toString();
        String gender_room = "Nam/Nữ"; //thiếu edittextNay
        int park_slot = Integer.parseInt(edtPark.getText().toString());
        int person_in_room = Integer.parseInt(edtPerson.getText().toString());
        int status_room = 0;
        String phone = edtPhone.getText().toString();

        boolean isValid = true;

        if (isEmpty(edtDeposit)) {
            edtDeposit.setError("Vui lòng nhập tiền cọc");
        } else {
            deposit_room = Long.parseLong(edtDeposit.getText().toString());
        }

        if (isEmpty(edtPrice)) {
            edtPrice.setError("Vui lòng nhập tiền cọc");
        } else {
            deposit_room = Long.parseLong(edtPrice.getText().toString());
        }

        int type_room = 0;
        String path = "Tro";
        if (radiobtnChungCu.isChecked() || radiobtnTro.isChecked()) {
            if (radiobtnChungCu.isChecked()) {
                path = "ChungCuMini";
                type_room = 1;
            } else {
                type_room = 0;
            }
        } else {
//            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();
            isValid = false;
            radiobtnTro.setError("Vui lòng chọn");
        }

        if (radiobtnPhongTrong.isChecked() || radiobtnDaChoThue.isChecked()) {
            if (radiobtnDaChoThue.isChecked()) {
                status_room = 1;
            } else {
                status_room = 0;
            }
        } else {
            isValid = false;
//            Toast.makeText(getApplicationContext(), "Vui lòng nhập đầy đủ các trường", Toast.LENGTH_SHORT).show();
            radiobtnTro.setError("Vui lòng chọn");
        }

        ArrayList<furnitureClass> furnitures = new ArrayList<>();
        if (checkbox_air_condition.isChecked()) {
            furnitures.add(new furnitureClass(checkbox_air_condition.getText().toString(), "gs://my-application-67ef3.appspot.com/ic-air-condittion.svg"));
        }

        if (checkbox_heater.isChecked()) {
            furnitures.add(new furnitureClass(checkbox_heater.getText().toString(), "gs://my-application-67ef3.appspot.com/ic-heater.svg"));
        }

        if (checkbox_curtain.isChecked()) {
            furnitures.add(new furnitureClass(checkbox_curtain.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/ic-curtain.svg?alt=media&token=fb16bddd-da81-4a9c-bcdf-4bf2b3b13439"));
        }
        if (checkboxfridge.isChecked()) {
            furnitures.add(new furnitureClass(checkboxfridge.getText().toString(), "gs://my-application-67ef3.appspot.com/ic-fridge.svg"));
        }
        ImagesClass images = new ImagesClass("", "", "", "", "");

//        Room room = new Room(id_room, title_room, price_room, address, area_room,
//                deposit_room, description_room, gender_room, park_slot,
//                person_in_room, status_room, type_room, phone, images
//                , furnitures, ArrayList < service_roomClass > services_room);
//        myRef.setValue(room);
        //setValue myRef.setValue(doi tuong, new DatabaseReference.Completionlistener )
        //myRef.child("tạo id").setValue()
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}