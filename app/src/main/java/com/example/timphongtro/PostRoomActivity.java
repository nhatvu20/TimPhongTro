package com.example.timphongtro;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.timphongtro.Database.Addresse;
import com.example.timphongtro.Database.ExtensionRoom_class;
import com.example.timphongtro.Database.ImagesRoomClass;
import com.example.timphongtro.Database.Room;
import com.example.timphongtro.Database.FurnitureClass;
import com.example.timphongtro.Database.Service_roomClass;
import com.example.timphongtro.HomePage.MainActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

public class PostRoomActivity extends AppCompatActivity {

    ImageView btnBack;
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
    CheckBox checkboxsofa;
    CheckBox checkboxNam;
    CheckBox checkboxNu;
    RadioButton radiobtnPhongTrong;
    RadioButton radiobtnDaChoThue;

    ImageView uploadPicture1, uploadPicture2;
    String imageURL1;
    String imageURL2;
    Uri uri;

    boolean isUploadImg1;
    boolean isUploadImg2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_room);
        btnBack = (ImageView) this.<View>findViewById(R.id.btnBack);

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
        checkboxsofa = (CheckBox) this.<View>findViewById(R.id.checkboxsofa);

        checkboxNam = (CheckBox) this.<View>findViewById(R.id.checkboxNam);
        checkboxNu = (CheckBox) this.<View>findViewById(R.id.checkboxNu);

        radiobtnPhongTrong = (RadioButton) this.<View>findViewById(R.id.radiobtnPhongTrong);
        radiobtnDaChoThue = (RadioButton) this.<View>findViewById(R.id.radiobtnDaChoThue);

        btn_create_room = (Button) this.<View>findViewById(R.id.btn_create_room);

        uploadPicture1 = (ImageView) findViewById(R.id.imageViewP1);
        uploadPicture2 = (ImageView) findViewById(R.id.imageViewP2);
        isUploadImg1 = false;
        isUploadImg2 = false;
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent main = new Intent(PostRoomActivity.this, MainActivity.class);
                startActivity(main);
            }
        });
        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    uri = data.getData();
                    uploadPicture1.setImageURI(uri);
                    isUploadImg1 = true;
                } else {
                    Toast.makeText(PostRoomActivity.this, "No image selected", Toast.LENGTH_SHORT);
                }
            }
        });
        ActivityResultLauncher<Intent> activityResultLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Intent data = result.getData();
                    uri = data.getData();
                    uploadPicture2.setImageURI(uri);
                    isUploadImg2 = true;
                } else {
                    Toast.makeText(PostRoomActivity.this, "No image selected", Toast.LENGTH_SHORT);
                }
            }
        });
        btn_create_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
//                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
//                onClickPushData();
                AlertDialog.Builder builder = new AlertDialog.Builder(PostRoomActivity.this);
                builder.setTitle("Confirm") // Thiết lập tiêu đề của Dialog
                        .setMessage("Are you sure?")
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (!isUploadImg1 || !isUploadImg2) onClickPushData();
                                else
                                    Toast.makeText(getApplicationContext(),"Vui lòng chọn 2 tấm ảnh",Toast.LENGTH_SHORT);
                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // Xử lý khi người dùng chọn No
                            }
                        });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        uploadPicture1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        uploadPicture2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher2.launch(photoPicker);
            }
        });
    }

    public void saveImage() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("roomImgage").child(Objects.requireNonNull(uri.getLastPathSegment()));
        AlertDialog.Builder builder = new AlertDialog.Builder(PostRoomActivity.this);
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog = builder.create();
        dialog.show();
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imageURL1 = String.valueOf(urlImage);
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();

            }
        });
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete()) ;
                Uri urlImage = uriTask.getResult();
                imageURL2 = String.valueOf(urlImage);
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();

            }
        });

    }

    void onClickPushData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("roomsTest/Tro");
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
        String gender_room = "";
        if (checkboxNam.isChecked()) {
            if (!checkboxNu.isChecked()) {
                gender_room = "Nam";
            } else {
                gender_room = "Nam/Nữ";
            }
        } else {
            if (!checkboxNam.isChecked()) {
                gender_room = "Nữ";
            } else {
                gender_room = "Nam/Nữ";
            }
        }
        int status_room = 0;
        String phone = "";

        boolean isValid = true;
        if (isEmpty(edtTitleRoom)) {
            edtTitleRoom.setError("Vui lòng nhập tiêu đề bài đăng");
            isValid = false;
        } else {
            title_room = edtTitleRoom.getText().toString();
        }

        if (isEmpty(edtDeposit)) {
            edtDeposit.setError("Vui lòng nhập tiền cọc");
            isValid = false;
        } else {
            deposit_room = Long.parseLong(edtDeposit.getText().toString());
        }

        if (isEmpty(edtPrice)) {
            edtPrice.setError("Vui lòng nhập tiền cọc");
            isValid = false;
        } else {
            price_room = Long.parseLong(edtPrice.getText().toString());
        }

        int type_room = 0;
        String path = "Tro";
        if (radiobtnChungCu.isChecked() || radiobtnTro.isChecked()) {
            if (radiobtnChungCu.isChecked()) {
                path = "ChungCuMini";
                type_room = 1;
            }
        } else {
            isValid = false;
            radiobtnTro.setError("Vui lòng chọn loại phòng");
        }
        DatabaseReference myRef = database.getReference("rooms/" + path);

        if (radiobtnPhongTrong.isChecked() || radiobtnDaChoThue.isChecked()) {
            if (radiobtnDaChoThue.isChecked()) {
                status_room = 1;
            } else {
                status_room = 0;
            }
        } else {
            isValid = false;
            radiobtnTro.setError("Vui lòng chọn tình trạng phòng");
        }

        if (isEmpty(edtArea)) {
            edtArea.setError("Vui lòng nhập diện tích");
            isValid = false;
        } else {
            area_room = edtArea.getText().toString();
        }

        if (isEmpty(edtPhone)) {
            edtPhone.setError("Vui lòng nhập số điện thoại");
            isValid = false;
        } else {
            phone = edtPhone.getText().toString();
        }

        int floor = 1;
        if (isEmpty(edtFloor)) {
            edtFloor.setError("Vui lòng nhập số tầng");
            isValid = false;
        } else {
            floor = Integer.parseInt(edtFloor.getText().toString());
        }
        int person_in_room = 1;
        if (isEmpty(edtPerson)) {
            edtPerson.setError("Vui lòng nhập số người/phòng");
            isValid = false;
        } else {
            person_in_room = Integer.parseInt(edtPerson.getText().toString());
        }

        String description_room = "";
        if (isEmpty(edtDescriptionRoom)) {
            edtDescriptionRoom.setError("Vui lòng nhập số mô tả phòng chi tiết");
            isValid = false;
        } else {
            description_room = edtDescriptionRoom.getText().toString();
        }

        int park_slot = 1;
        if (isEmpty(edtPark)) {
            edtPark.setError("Vui lòng nhập số chỗ để xe trong 1 phòng");
            isValid = false;
        } else {
            park_slot = Integer.parseInt(edtPark.getText().toString());
        }

        if (!isUploadImg1 || !isUploadImg2) {
//            uploadPicture1.setError("Vui lòng upload ảnh trước khi đăng bài");
            Toast.makeText(getApplicationContext(), "Vui lòng upload ảnh trước khi đăng bài", Toast.LENGTH_SHORT);
            isValid = false;
        }


        // Them noi that
        ArrayList<FurnitureClass> furnitures = new ArrayList<>();
        if (checkbox_air_condition.isChecked()) {
            furnitures.add(new FurnitureClass(checkbox_air_condition.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-Wardrobe.svg?alt=media&token=0853bfec-58f6-4865-acb0-8892729708c8"));
        }
        if (checkbox_heater.isChecked()) {
            furnitures.add(new FurnitureClass(checkbox_heater.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-heater.svg?alt=media&token=a79c86fb-e9a6-4bd7-8d3b-5332b75de499"));
        }
        if (checkbox_curtain.isChecked()) {
            furnitures.add(new FurnitureClass(checkbox_curtain.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/ic-curtain.svg?alt=media&token=fb16bddd-da81-4a9c-bcdf-4bf2b3b13439"));
        }
        if (checkboxfridge.isChecked()) {
            furnitures.add(new FurnitureClass(checkboxfridge.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-fridge.svg?alt=media&token=88261dd0-bbb6-4bb2-83e7-cd7a19722f7b"));
        }
        if (checkboxbed.isChecked()) {
            furnitures.add(new FurnitureClass(checkboxbed.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-bed.svg?alt=media&token=8d7dd976-7514-4f97-b3f6-c90023ca6b1a"));
        }
        if (checkboxwardrobe.isChecked()) {
            furnitures.add(new FurnitureClass(checkboxwardrobe.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-Wardrobe.svg?alt=media&token=0853bfec-58f6-4865-acb0-8892729708c8"));
        }
        if (checkbox_washing_machine.isChecked()) {
            furnitures.add(new FurnitureClass(checkbox_washing_machine.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-washing-machine.svg?alt=media&token=749af34e-2276-4184-8096-16b6c11689c7"));
        }
        if (checkboxsofa.isChecked()) {
            furnitures.add(new FurnitureClass(checkboxsofa.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-sofa.svg?alt=media&token=cc1c06b4-264d-4f9c-87ff-13b1bd9ce1c1"));
        }

        ImagesRoomClass images = new ImagesRoomClass(imageURL1, imageURL2, "", "", "");

        ArrayList<Service_roomClass> services_room = new ArrayList<>();
        services_room.add(new Service_roomClass("Mạng", "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-internet.svg?alt=media&token=1bb413e5-56c5-4ef6-b010-2edb959377d0", "đ/phòng", Long.parseLong(edtInternet.getText().toString())));
        services_room.add(new Service_roomClass("Điện", "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-electric.svg?alt=media&token=f8126a05-c4fd-4cf4-bae2-c5eb52e079c4", "đ/Kwh", Long.parseLong(edtElectric.getText().toString())));
        services_room.add(new Service_roomClass("Nước", "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-water.svg?alt=media&token=ed7f99da-99fa-4398-adda-6c577608a147", "đ/m3", Long.parseLong(edtWater.getText().toString())));

        ArrayList<ExtensionRoom_class> extensions_room = new ArrayList<>();
        if (checkboxtoilet.isChecked()) {
            extensions_room.add(new ExtensionRoom_class(checkboxtoilet.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-toilet.svg?alt=media&token=8ed5205f-4de2-4d99-b31c-9009ff74c148"));
        }
        if (checkboxfloor.isChecked()) {
            extensions_room.add(new ExtensionRoom_class(checkboxfloor.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-ladder.svg?alt=media&token=f5fec867-27f0-4f1d-9f0c-0c2a84015b3c"));
        }
        if (checkbox_time_flex.isChecked()) {
            extensions_room.add(new ExtensionRoom_class(checkbox_time_flex.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-time-flex.svg?alt=media&token=6aced681-5d23-424d-ae3d-51458e1c993a"));
        }
        if (checkboxfingerprint.isChecked()) {
            extensions_room.add(new ExtensionRoom_class(checkboxfingerprint.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-finger-print.svg?alt=media&token=a83df340-63e1-4b0e-a979-2d62442ab62e"));
        }
        if (checkboxbacony.isChecked()) {
            extensions_room.add(new ExtensionRoom_class(checkboxbacony.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-ladder.svg?alt=media&token=f5fec867-27f0-4f1d-9f0c-0c2a84015b3c"));
        }
        if (checkboxpet.isChecked()) {
            extensions_room.add(new ExtensionRoom_class(checkboxpet.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-paw-pet.svg?alt=media&token=330909b5-02dd-4c87-8c73-532002fd0096"));
        }
        if (checkbox_w_owner.isChecked()) {
            extensions_room.add(new ExtensionRoom_class(checkbox_w_owner.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon%2Fic-user.svg?alt=media&token=7614a59a-cea8-4cb5-905e-a1082d378f60"));
        }
        saveImage();
        Room room = new Room(id_room, title_room, price_room, address, area_room,
                deposit_room, description_room, gender_room, park_slot,
                person_in_room, status_room, type_room, phone, images
                , furnitures, services_room, extensions_room, floor);
        myRef.child(id_room).setValue(room);
//        setValue myRef.setValue(doi tuong, new DatabaseReference.Completionlistener )
//        myRef.child("tạo id").setValue()
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}