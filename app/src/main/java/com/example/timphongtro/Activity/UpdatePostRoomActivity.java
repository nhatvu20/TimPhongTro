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
import android.widget.Toast;

import com.bumptech.glide.Glide;
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
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UpdatePostRoomActivity extends AppCompatActivity {

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
    boolean isUploadImg1;
    boolean isUploadImg2;

    List<String> cities, districts, wards;

    String path;
    ArrayList<FurnitureClass> furnitures;

    ArrayList<ExtensionRoom_class> extensions_room;
    Address address;

    Room roomData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_post_room);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String roomString = bundle.getString("DataRoom");
            Gson gson = new Gson();
            roomData = gson.fromJson(roomString, Room.class);
            address = roomData.getAddress();
        }
        initView();

        cities = new ArrayList<>();
        districts = new ArrayList<>();

        path = "city/HaNoi/district";


        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent main = new Intent(UpdatePostRoomActivity.this, MainActivity.class);
//                startActivity(main);
                finish();
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
                    //Không có else vì đã có sẵn 1 bức ảnh
//                    isUploadImg1 = false;
//                    Toast.makeText(UpdatePostRoomActivity.this, "No image selected", Toast.LENGTH_LONG).show();
                }
            }
        });
        btn_create_room.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePostRoomActivity.this);
                builder.setTitle("Xác nhận") // Thiết lập tiêu đề của Dialog
                        .setMessage("Bạn có muốn đăng bài không?")
                        .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                if (isUploadImg1) {
                                    Uri uriTmp = uri;
                                    if (containsSubstring(uriTmp.toString(), "https")) {
                                        imageURL1 = uri.toString();
                                        onClickPushData();
                                    } else {
                                        saveImage();
                                    }
                                } else
                                    Toast.makeText(getApplicationContext(), "Vui lòng chọn 1 tấm ảnh", Toast.LENGTH_LONG).show();
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
    }

    public void saveImage() {
        StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("roomImgage").child(Objects.requireNonNull(uri.getLastPathSegment()));
        AlertDialog.Builder builder = new AlertDialog.Builder(UpdatePostRoomActivity.this);
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
    }

    void onClickPushData() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        String title_room = String.valueOf(edtTitleRoom.getText());

        String id_room = "";
        String city = "";
        String district = "";
        String detail = "";
        String ward = "";
        String address_combine = detail + ", " + district + ", " + city;
        if (roomData != null) {
            id_room = roomData.getId_room();
            city = address.getCity();
            district = address.getDistrict();
            detail = address.getDetail();
            ward = "";
            address_combine = detail + ", " + district + ", " + city;
        }

        if ("".equals(detail)) {
            address = new Address(city, district);
        } else {
            address = new Address(city, district, detail, ward, address_combine);
        }
        String area_room = edtArea.getText().toString();
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

        boolean isValid = true;
        if (isEmpty(edtTitleRoom)) {
            edtTitleRoom.setError("Vui lòng nhập tiêu đề bài đăng");
            isValid = false;
        } else {
            title_room = edtTitleRoom.getText().toString();
        }

        long deposit_room = 0;
        if (isEmpty(edtDeposit)) {
            edtDeposit.setError("Vui lòng nhập tiền cọc");
            isValid = false;
        } else {
            deposit_room = Long.parseLong(edtDeposit.getText().toString());
        }

        long price_room = 0;
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

        int status_room = 0;
        if (radiobtnPhongTrong.isChecked() || radiobtnDaChoThue.isChecked()) {
            if (radiobtnDaChoThue.isChecked()) {
                status_room = 1;
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
        String phone = "";
        if (isEmpty(edtPhone)) {
            edtPhone.setError("Vui lòng nhập số điện thoại");
            isValid = false;
        } else {
            String regex = "^\\d{10}$";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(edtPhone.getText().toString());
            if (matcher.matches()) {
                phone = edtPhone.getText().toString();
            } else {
                edtPhone.setError("Vui lòng nhập đúng định dạng số điện thoại");
                isValid = false;
            }

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

        if(isEmpty(edtElectric) || isEmpty(edtInternet) || isEmpty(edtWater)){
            isValid = false;
            if (isEmpty(edtInternet)) {
                edtInternet.setError("Vui lòng nhập giá Internet");
            }
            if(isEmpty(edtElectric)) {
                edtElectric.setError("Vui lòng nhập giá điện");
            }
            if(isEmpty(edtWater)) {
                edtWater.setError("Vui lòng nhập giá nước");
            }
        }

        // Them noi that
        furnitures = new ArrayList<>();
        handleDataFurniture();
        //Them tien ich
        extensions_room = new ArrayList<>();
        handleDataExtensions();


        ImagesRoomClass images = new ImagesRoomClass(imageURL1, imageURL1, imageURL1, imageURL1, "");
//        saveImage();

        if (furnitures.isEmpty()) {
            isValid = false;
            checkbox_air_condition.setError("Vui lòng chọn 1 món nội thất");
        }
        if (extensions_room.isEmpty()) {
            isValid = false;
            checkboxtoilet.setError("Vui lòng chọn 1 tiện ích");
        }
        String id_own_post = "";
        if (userCurrent != null) {
            id_own_post = userCurrent.getUid();
        }
        if (isValid) {
            Room room = new Room(id_own_post, id_room, title_room, price_room, address, area_room, deposit_room, description_room, gender_room, park_slot,
                    person_in_room, status_room, type_room, phone, floor, images, furnitures, extensions_room,
                    Long.parseLong(edtInternet.getText().toString()), Long.parseLong(edtWater.getText().toString()), Long.parseLong(edtInternet.getText().toString()));

            //Xu ly cho firebase
            myRef.child(id_room).setValue(room);
            Toast.makeText(UpdatePostRoomActivity.this, "Cập nhật thông tin phòng thành công", Toast.LENGTH_SHORT).show();
            Intent main = new Intent(this, MainActivity.class);
            startActivity(main);
        } else {
            Toast.makeText(UpdatePostRoomActivity.this, "Vui lòng nhập đầy đủ các trường dữ liệu", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleDataFurniture() {
        if (checkbox_air_condition.isChecked()) {
            furnitures.add(new FurnitureClass("checkbox_air_condition", checkbox_air_condition.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-air-condittion.png?alt=media&token=85d235e6-f4f4-44b1-89f4-05bed51050a6"));
        }
        if (checkbox_heater.isChecked()) {
            furnitures.add(new FurnitureClass("checkbox_heater", checkbox_heater.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-heater.png?alt=media&token=4c4871ff-ef6f-42bc-a480-3b60336b802c"));
        }
        if (checkbox_curtain.isChecked()) {
            furnitures.add(new FurnitureClass("checkbox_curtain", checkbox_curtain.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-curtain.png?alt=media&token=400eb929-952b-4051-acc2-a26db74251ac"));
        }
        if (checkboxfridge.isChecked()) {
            furnitures.add(new FurnitureClass("checkboxfridge", checkboxfridge.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-fridge.png?alt=media&token=deb2cded-5a02-464e-8e93-2672d7bc9b89"));
        }
        if (checkboxbed.isChecked()) {
            furnitures.add(new FurnitureClass("checkboxbed", checkboxbed.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-bed.png?alt=media&token=9ed19798-ba14-4604-87d6-5f0224584f42"));
        }
        if (checkboxwardrobe.isChecked()) {
            furnitures.add(new FurnitureClass("checkboxwardrobe", checkboxwardrobe.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-Wardrobe.png?alt=media&token=944da04f-03dd-4b8f-b627-27f1f8f11c9a"));
        }
        if (checkbox_washing_machine.isChecked()) {
            furnitures.add(new FurnitureClass("checkbox_washing_machine", checkbox_washing_machine.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-washing-machine.png?alt=media&token=ee166ffd-2cb6-4d76-85a8-0a587effb2af"));
        }
        if (checkboxsofa.isChecked()) {
            furnitures.add(new FurnitureClass("checkboxsofa", checkboxsofa.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-sofa.png?alt=media&token=f9bba804-271b-4740-b28c-d7d89d083d6f"));
        }
    }

    private void handleDataExtensions() {
        if (checkboxtoilet.isChecked()) {
            extensions_room.add(new ExtensionRoom_class("checkboxtoilet", checkboxtoilet.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-toilet.png?alt=media&token=426b6597-5dc4-4182-887e-fbeb37d5acc0"));
        }
        if (checkboxfloor.isChecked()) {
            extensions_room.add(new ExtensionRoom_class("checkboxfloor", checkboxfloor.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-ladder.png?alt=media&token=96975838-2519-4637-87ef-1c966b0f5308"));
        }
        if (checkbox_time_flex.isChecked()) {
            extensions_room.add(new ExtensionRoom_class("checkbox_time_flex", checkbox_time_flex.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-time-flex.png?alt=media&token=c3d87c64-086b-43c8-b896-4d2777c2e7e5"));
        }
        if (checkboxfingerprint.isChecked()) {
            extensions_room.add(new ExtensionRoom_class("checkboxfingerprint", checkboxfingerprint.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-finger-print.png?alt=media&token=8dccd0ac-ff93-4d1d-9f44-6db70a315853"));
        }
        if (checkboxbacony.isChecked()) {
            extensions_room.add(new ExtensionRoom_class("checkboxbacony", checkboxbacony.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-ladder.png?alt=media&token=96975838-2519-4637-87ef-1c966b0f5308"));
        }
        if (checkboxpet.isChecked()) {
            extensions_room.add(new ExtensionRoom_class("checkboxpet", checkboxpet.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-paw-pet.png?alt=media&token=8a649047-04d9-4421-a064-fca84b7f8f0d"));
        }
        if (checkbox_w_owner.isChecked()) {
            extensions_room.add(new ExtensionRoom_class("checkbox_w_owner", checkbox_w_owner.getText().toString(), "https://firebasestorage.googleapis.com/v0/b/my-application-67ef3.appspot.com/o/icon_png%2Fic-user.png?alt=media&token=db7d94aa-1a03-42f3-834a-4a3aec4c3866"));
        }
    }

    boolean isEmpty(EditText text) {
        CharSequence str = text.getText().toString();
        return TextUtils.isEmpty(str);
    }

    public static boolean containsSubstring(String mainString, String substring) {
        return mainString.contains(substring);
    }

    void initView() {
        btnBack = (ImageView) findViewById(R.id.btnBack);

        edtTitleRoom = (EditText) findViewById(R.id.edtTitleRoom);
        edtPrice = (EditText) findViewById(R.id.edtPrice);
        edtDeposit = (EditText) findViewById(R.id.edtDeposit);

        edtInternet = (EditText) findViewById(R.id.edtInternet);
        edtElectric = (EditText) findViewById(R.id.edtElectric);
        edtWater = (EditText) findViewById(R.id.edtWater);

        radiobtnChungCu = (RadioButton) findViewById(R.id.radiobtnChungCu);
        radiobtnTro = (RadioButton) findViewById(R.id.radiobtnTro);

        edtArea = (EditText) findViewById(R.id.edtArea);
        edtPhone = (EditText) findViewById(R.id.edtPhone);
        edtFloor = (EditText) findViewById(R.id.edtFloor);
        edtPerson = (EditText) findViewById(R.id.edtPerson);
        edtDescriptionRoom = (EditText) findViewById(R.id.edtDescriptionRoom);
        edtPark = (EditText) findViewById(R.id.edtPark);

        checkboxtoilet = (CheckBox) findViewById(R.id.checkboxtoilet);
        checkboxfloor = (CheckBox) findViewById(R.id.checkboxfloor);
        checkbox_time_flex = (CheckBox) findViewById(R.id.checkbox_time_flex);
        checkboxfingerprint = (CheckBox) findViewById(R.id.checkboxfingerprint);
        checkboxbacony = (CheckBox) findViewById(R.id.checkboxbacony);
        checkboxpet = (CheckBox) findViewById(R.id.checkboxpet);
        checkbox_w_owner = (CheckBox) findViewById(R.id.checkbox_w_owner);

        checkbox_air_condition = (CheckBox) findViewById(R.id.checkbox_air_condition);
        checkbox_heater = (CheckBox) findViewById(R.id.checkbox_heater);
        checkbox_curtain = (CheckBox) findViewById(R.id.checkbox_curtain);
        checkboxfridge = (CheckBox) findViewById(R.id.checkboxfridge);
        checkboxbed = (CheckBox) findViewById(R.id.checkboxbed);
        checkboxwardrobe = (CheckBox) findViewById(R.id.checkboxwardrobe);
        checkbox_washing_machine = (CheckBox) findViewById(R.id.checkbox_washing_machine);
        checkboxsofa = (CheckBox) findViewById(R.id.checkboxsofa);

        checkboxNam = (CheckBox) findViewById(R.id.checkboxNam);
        checkboxNu = (CheckBox) findViewById(R.id.checkboxNu);

        radiobtnPhongTrong = (RadioButton) findViewById(R.id.radiobtnPhongTrong);
        radiobtnDaChoThue = (RadioButton) findViewById(R.id.radiobtnDaChoThue);

        btn_create_room = (Button) findViewById(R.id.btn_create_room);

        uploadPicture1 = (ImageView) findViewById(R.id.imageViewP1);

        edtAddress = (EditText) findViewById(R.id.edtAddress);

        initDataForUpdate();
    }

    private void initDataForUpdate() {
        if (roomData != null) {
            edtTitleRoom.setText(roomData.getTitle_room());
            edtAddress.setText(address.getAddress_combine());
            edtPrice.setText(String.valueOf(roomData.getPrice_room()));
            edtDeposit.setText(String.valueOf(roomData.getDeposit_room()));

            uri = Uri.parse(roomData.getImages().getImg1());
            Glide.with(this)
                    .load(uri)
                    .into(uploadPicture1);

            isUploadImg1 = true;

            checkboxNam.setChecked(false);
            checkboxNu.setChecked(false);
            if ("Nam/Nữ".equals(roomData.getGender_room())) {
                checkboxNam.setChecked(true);
                checkboxNu.setChecked(true);
            } else if ("Nam".equals(roomData.getGender_room())) {
                checkboxNam.setChecked(true);
            } else {
                checkboxNu.setChecked(true);
            }

            if (roomData.getType_room() == 0) {
                radiobtnTro.setChecked(true);
            } else {
                radiobtnChungCu.setChecked(true);
            }

            edtArea.setText(roomData.getArea_room());
            edtPhone.setText(roomData.getPhone());
            edtFloor.setText(String.valueOf(roomData.getFloor()));
            edtPerson.setText(String.valueOf(roomData.getPerson_in_room()));
            edtDescriptionRoom.setText(roomData.getDescription_room());
            edtPark.setText(String.valueOf(roomData.getPark_slot()));

            furnitures = roomData.getFurniture();
            extensions_room = roomData.getExtension_room();

            for (FurnitureClass furniture : furnitures) {
                if (furniture != null) {
                    if ("checkbox_air_condition".equals(furniture.getId())) {
                        checkbox_air_condition.setChecked(true);
                    }
                    if ("checkbox_heater".equals(furniture.getId())) {
                        checkbox_heater.setChecked(true);
                    }
                    if ("checkbox_curtain".equals(furniture.getId())) {
                        checkbox_curtain.setChecked(true);
                    }
                    if ("checkboxfridge".equals(furniture.getId())) {
                        checkboxfridge.setChecked(true);
                    }
                    if ("checkboxbed".equals(furniture.getId())) {
                        checkboxbed.setChecked(true);
                    }
                    if ("checkboxwardrobe".equals(furniture.getId())) {
                        checkboxwardrobe.setChecked(true);
                    }
                    if ("checkbox_washing_machine".equals(furniture.getId())) {
                        checkbox_washing_machine.setChecked(true);
                    }
                    if ("checkboxsofa".equals(furniture.getId())) {
                        checkboxsofa.setChecked(true);
                    }
                }
            }

            for (ExtensionRoom_class extensionRoom : extensions_room) {
                if (extensionRoom != null) {
                    if ("checkboxtoilet".equals(extensionRoom.getId())) {
                        checkboxtoilet.setChecked(true);
                    }
                    if ("checkboxfloor".equals(extensionRoom.getId())) {
                        checkboxfloor.setChecked(true);
                    }
                    if ("checkbox_time_flex".equals(extensionRoom.getId())) {
                        checkbox_time_flex.setChecked(true);
                    }
                    if ("checkboxfingerprint".equals(extensionRoom.getId())) {
                        checkboxfingerprint.setChecked(true);
                    }
                    if ("checkboxbacony".equals(extensionRoom.getId())) {
                        checkboxbacony.setChecked(true);
                    }
                    if ("checkboxpet".equals(extensionRoom.getId())) {
                        checkboxpet.setChecked(true);
                    }
                    if ("checkbox_w_owner".equals(extensionRoom.getId())) {
                        checkbox_w_owner.setChecked(true);
                    }
                }
            }

            if (roomData.getStatus_room() == 0) {
                radiobtnPhongTrong.setChecked(true);
            } else {
                radiobtnDaChoThue.setChecked(true);
            }

            edtInternet.setText(String.valueOf(roomData.getPrice_internet()));
            edtElectric.setText(String.valueOf(roomData.getPrice_electric()));
            edtWater.setText(String.valueOf(roomData.getPrice_water()));
        }
    }
}