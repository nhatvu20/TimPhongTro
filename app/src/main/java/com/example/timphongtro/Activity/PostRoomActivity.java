package com.example.timphongtro.Activity;

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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.timphongtro.Entity.Address;
import com.example.timphongtro.Entity.ExtensionRoom_class;
import com.example.timphongtro.Entity.ImagesRoomClass;
import com.example.timphongtro.Entity.Room;
import com.example.timphongtro.Entity.FurnitureClass;
//import com.example.timphongtro.Entity.Service_roomClass;
import com.example.timphongtro.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class PostRoomActivity extends AppCompatActivity {

    ImageView btnBack;
    EditText edtTitleRoom, edtDeposit, edtPrice, edtInternet, edtElectric, edtWater, edtArea, edtPhone, edtFloor, edtPerson, edtDescriptionRoom, edtPark, edtAddress;
    Button btn_create_room;
    RadioButton radiobtnChungCu, radiobtnTro;

    CheckBox checkboxtoilet, checkboxfloor, checkbox_time_flex, checkboxfingerprint, checkboxbacony, checkboxpet, checkbox_w_owner, checkbox_air_condition, checkbox_heater, checkbox_curtain, checkboxfridge, checkboxbed, checkboxwardrobe, checkbox_washing_machine, checkboxsofa, checkboxNam, checkboxNu;
    RadioButton radiobtnPhongTrong;
    RadioButton radiobtnDaChoThue;

    ImageView uploadPicture1, uploadPicture2;
    String imageURL1;
//    String imageURL2;
    Uri uri;
//    Uri uri2;
    Spinner spinnerCity, spinnerDistrict, spinnerWard;

    boolean isUploadImg1;
    boolean isUploadImg2;

    List<String> cities, districts, wards;

    String path;


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
//        uploadPicture2 = (ImageView) findViewById(R.id  .imageViewP2);
        isUploadImg1 = false;
//        isUploadImg2 = false;

        spinnerCity = (Spinner) findViewById(R.id.spinnerCity);
        spinnerDistrict = (Spinner) findViewById(R.id.spinnerDistrict);

        edtAddress = (EditText) findViewById(R.id.edtAddress);

        cities = new ArrayList<>();
        districts = new ArrayList<>();

        getDataForSpinnerCity();
        path = "city/HaNoi/district";
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedspinner = cities.get(position);
                if (selectedspinner.equals("Hà Nội")) {
                    path = "city/HaNoi/district";
                } else if (selectedspinner.equals("Hồ Chí Minh")) {
                    path = "city/HoChiMinh/district";
                }
                getDataForSpinnerDistrict();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        getDataForSpinnerDistrict();


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
//        ActivityResultLauncher<Intent> activityResultLauncher2 = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
//            @Override
//            public void onActivityResult(ActivityResult result) {
//                if (result.getResultCode() == Activity.RESULT_OK) {
//                    Intent data = result.getData();
//                    uri2 = data.getData();
//                    uploadPicture2.setImageURI(uri2);
//                    isUploadImg2 = true;
//                } else {
//                    Toast.makeText(PostRoomActivity.this, "No image selected", Toast.LENGTH_SHORT);
//                }
//            }
//        });
        btn_create_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveImage();
//                Toast.makeText(getApplicationContext(), "OK", Toast.LENGTH_SHORT).show();
//                onClickPushData();
                AlertDialog.Builder builder = new AlertDialog.Builder(PostRoomActivity.this);
                builder.setTitle("Xác nhận") // Thiết lập tiêu đề của Dialog
                        .setMessage("Bạn chắc chắn chứ?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
//                                if (!isUploadImg1 || !isUploadImg2) onClickPushData();
                                if (!isUploadImg1) saveImage();
                                else
                                    Toast.makeText(getApplicationContext(), "Vui lòng chọn 2 tấm ảnh", Toast.LENGTH_SHORT);
                            }
                        })
                        .setNegativeButton("Không", new DialogInterface.OnClickListener() {
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
//        uploadPicture2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent photoPicker = new Intent(Intent.ACTION_PICK);
//                photoPicker.setType("image/*");
//                activityResultLauncher2.launch(photoPicker);
//            }
//        });

    }

    public void getDataForSpinnerDistrict() {
        districts.clear();
        DatabaseReference databaseReferennceDistrict = FirebaseDatabase.getInstance().getReference();
        databaseReferennceDistrict.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnap : snapshot.getChildren()) {
                    String DistrictName = childSnap.child("name").getValue(String.class);
                    districts.add(DistrictName);
                }
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(PostRoomActivity.this, android.R.layout.simple_spinner_item, districts);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerDistrict.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void getDataForSpinnerCity() {
        cities.clear();
        DatabaseReference databaseReferennce = FirebaseDatabase.getInstance().getReference();
        path = "city";
        databaseReferennce.child(path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot childSnap : snapshot.getChildren()) {
                    String CityName = childSnap.child("name").getValue(String.class);
                    cities.add(CityName);
                }
                ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(PostRoomActivity.this, android.R.layout.simple_spinner_item, cities);
                spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinnerCity.setAdapter(spinnerAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void saveImage() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("roomImgage").child(Objects.requireNonNull(uri.getLastPathSegment()));
//        StorageReference storageReference2 = FirebaseStorage.getInstance().getReference().child("roomImgage").child(Objects.requireNonNull(uri2.getLastPathSegment()));
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
                onClickPushData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();

            }
        });
//        storageReference2.putFile(uri2).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
//            @Override
//            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
//                Task<Uri> uriTask = taskSnapshot.getStorage().getDownloadUrl();
//                while (!uriTask.isComplete()) ;
//                Uri urlImage2 = uriTask.getResult();
//                imageURL2 = String.valueOf(urlImage2);
//                dialog.dismiss();
//            }
//        }).addOnFailureListener(new OnFailureListener() {
//            @Override
//            public void onFailure(@NonNull Exception e) {
//                dialog.dismiss();
//
//            }
//        });

    }

    void onClickPushData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        DatabaseReference myRef = database.getReference("roomsTest/Tro");
        //tao doi tuong / chu y tao doi tuong phai co du ham tao co tham so khong tham so, du getter setter va co toString mac dinh cua class
        String id_room = UUID.randomUUID().toString();
        String title_room = String.valueOf(edtTitleRoom.getText());
//        long price_room = Long.parseLong(String.valueOf(edtPrice.getText()));
        long price_room = 0;
        String city = spinnerCity.getSelectedItem().toString();
        String district = spinnerDistrict.getSelectedItem().toString();
        String detail = edtAddress.getText().toString();
        String ward = "";
        String address_combine = detail + ", " + district + ", " + city;

        Address address = new Address(city, district, detail, ward, address_combine);
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
        FirebaseUser userCurrent = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference myPostRef = database.getReference("myRooms/");
        if(userCurrent != null){
            myPostRef = database.getReference("myRooms/"+userCurrent.getUid());
        }
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

//        if (!isUploadImg1 || !isUploadImg2) {
        if (!isUploadImg1) {
//            uploadPicture1.setError("Vui lòng upload ảnh trước khi đăng bài");
            Toast.makeText(getApplicationContext(), "Vui lòng upload ảnh trước khi đăng bài", Toast.LENGTH_SHORT);
            isValid = false;
        }

        int countfurnitures = 0;
        // Them noi that
        ArrayList<FurnitureClass> furnitures = new ArrayList<>();
        if (checkbox_air_condition.isChecked()) {
            countfurnitures += 1;
            furnitures.add(new FurnitureClass(checkbox_air_condition.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-air-condittion.png?alt=media&token=85d235e6-f4f4-44b1-89f4-05bed51050a6"));
        }
        if (checkbox_heater.isChecked()) {
            countfurnitures += 1;
            furnitures.add(new FurnitureClass(checkbox_heater.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-heater.png?alt=media&token=4c4871ff-ef6f-42bc-a480-3b60336b802c"));
        }
        if (checkbox_curtain.isChecked()) {
            countfurnitures += 1;
            furnitures.add(new FurnitureClass(checkbox_curtain.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-curtain.png?alt=media&token=400eb929-952b-4051-acc2-a26db74251ac"));
        }
        if (checkboxfridge.isChecked()) {
            countfurnitures += 1;
            furnitures.add(new FurnitureClass(checkboxfridge.getText().toString(),"https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-fridge.png?alt=media&token=deb2cded-5a02-464e-8e93-2672d7bc9b89"));
        }
        if (checkboxbed.isChecked()) {
            countfurnitures += 1;
            furnitures.add(new FurnitureClass(checkboxbed.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-bed.png?alt=media&token=9ed19798-ba14-4604-87d6-5f0224584f42"));
        }
        if (checkboxwardrobe.isChecked()) {
            countfurnitures += 1;
            furnitures.add(new FurnitureClass(checkboxwardrobe.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-Wardrobe.png?alt=media&token=944da04f-03dd-4b8f-b627-27f1f8f11c9a"));
        }
        if (checkbox_washing_machine.isChecked()) {
            countfurnitures += 1;
            furnitures.add(new FurnitureClass(checkbox_washing_machine.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-washing-machine.png?alt=media&token=ee166ffd-2cb6-4d76-85a8-0a587effb2af"));
        }
        if (checkboxsofa.isChecked()) {
            countfurnitures += 1;
            furnitures.add(new FurnitureClass(checkboxsofa.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-sofa.png?alt=media&token=f9bba804-271b-4740-b28c-d7d89d083d6f"));
        }

        ImagesRoomClass images = new ImagesRoomClass(imageURL1, imageURL1, imageURL1, imageURL1, "");
        int countextensions_room = 0;
        ArrayList<ExtensionRoom_class> extensions_room = new ArrayList<>();
        if (checkboxtoilet.isChecked()) {
            countextensions_room+=1;
            extensions_room.add(new ExtensionRoom_class(checkboxtoilet.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-toilet.png?alt=media&token=426b6597-5dc4-4182-887e-fbeb37d5acc0"));
        }
        if (checkboxfloor.isChecked()) {
            countextensions_room+=1;
            extensions_room.add(new ExtensionRoom_class(checkboxfloor.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-ladder.png?alt=media&token=96975838-2519-4637-87ef-1c966b0f5308"));
        }
        if (checkbox_time_flex.isChecked()) {
            countextensions_room+=1;
            extensions_room.add(new ExtensionRoom_class(checkbox_time_flex.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-time-flex.png?alt=media&token=c3d87c64-086b-43c8-b896-4d2777c2e7e5"));
        }
        if (checkboxfingerprint.isChecked()) {
            countextensions_room+=1;
            extensions_room.add(new ExtensionRoom_class(checkboxfingerprint.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-finger-print.png?alt=media&token=8dccd0ac-ff93-4d1d-9f44-6db70a315853"));
        }
        if (checkboxbacony.isChecked()) {
            countextensions_room+=1;
            extensions_room.add(new ExtensionRoom_class(checkboxbacony.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-ladder.png?alt=media&token=96975838-2519-4637-87ef-1c966b0f5308"));
        }
        if (checkboxpet.isChecked()) {
            countextensions_room+=1;
            extensions_room.add(new ExtensionRoom_class(checkboxpet.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-paw-pet.png?alt=media&token=8a649047-04d9-4421-a064-fca84b7f8f0d"));
        }
        if (checkbox_w_owner.isChecked()) {
            countextensions_room+=1;
            extensions_room.add(new ExtensionRoom_class(checkbox_w_owner.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-user.png?alt=media&token=db7d94aa-1a03-42f3-834a-4a3aec4c3866"));
        }

        if(countfurnitures == 0) {
            isValid = false;
            checkbox_air_condition.setError("Vui lòng chọn 1 món nội thất");
        }
        if (countextensions_room == 0) {
            isValid = false;
            checkboxtoilet.setError("Vui lòng chọn 1 tiện ích");
        }
//        saveImage();
        if(isValid){
            Toast.makeText(PostRoomActivity.this, "Vui lòng chọn đầy đủ các trường bao gồm ảnh", Toast.LENGTH_SHORT).show();
            String id_own_post = "";
            if (userCurrent != null) {
                id_own_post = userCurrent.getUid();
            }
            Room room = new Room(id_own_post, id_room, title_room, price_room, address, area_room, deposit_room, description_room, gender_room, park_slot,
                    person_in_room, status_room, type_room, phone, floor, images, furnitures, extensions_room,
                    Long.parseLong(edtInternet.getText().toString()), Long.parseLong(edtWater.getText().toString()), Long.parseLong(edtInternet.getText().toString()));
            myRef.child(id_room).setValue(room);
            myPostRef.child(id_room).setValue(room);
//        setValue myRef.setValue(doi tuong, new DatabaseReference.Completionlistener )
//        myRef.child("tạo id").setValue()
            Toast.makeText(PostRoomActivity.this, "Đăng bài thành công", Toast.LENGTH_SHORT).show();
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        }
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }
}