package com.example.timphongtro.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.example.timphongtro.Adapter.ExtensionAdapter;
import com.example.timphongtro.Entity.ExtensionRoom_class;
import com.example.timphongtro.Adapter.FurnitureAdapter;
import com.example.timphongtro.Entity.FurnitureClass;
import com.example.timphongtro.Entity.Room;
import com.example.timphongtro.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

public class DetailRoomActivity extends AppCompatActivity {
    Room roomData;
    TextView textViewTitle, textViewPrice, textViewCombine_address, textViewPhone, textViewTypeRoom, textViewFloor, textViewArea, textViewDeposit, textViewPersonInRoom, textViewGender,
            textViewWater, textViewInternet, textViewElectric, textviewDescriptionRoom, textViewNameUser;
    RecyclerView recycleviewFuniture;
    RecyclerView recycleviewExtension;
    FurnitureAdapter furnitureAdapter;
    ExtensionAdapter extensionAdapter;
    ImageView imageViewBack, imageViewLove;
    Button btnCall, btnBookRoom;
    LinearLayout userPost;

    FirebaseUser user;

    CircleImageView profile_image;

    private static final int CALL_PHONE_PERMISSION_REQUEST_CODE = 1;

    private static final int codeL = 100;

    FirebaseDatabase database;
    DatabaseReference myLovePostRef;
    DatabaseReference roomRef;
    DatabaseReference userOwnPostRef;
    boolean isLove;
    Calendar myCalender;
    TextView edtTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_room);
        Bundle bundle = getIntent().getExtras();
        ImageSlider imageSlider = (ImageSlider) findViewById(R.id.ImageRoomPrd);
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();

        textViewTypeRoom = findViewById(R.id.textViewTypeRoom);
        textViewTitle = findViewById(R.id.textViewTitle);
        textViewPrice = findViewById(R.id.textViewPrice);
        textViewCombine_address = findViewById(R.id.textViewCombine_address);
        textViewPhone = findViewById(R.id.textViewPhone);
        textViewFloor = findViewById(R.id.textViewFloor);
        textViewArea = findViewById(R.id.textViewArea);
        textViewDeposit = findViewById(R.id.textViewDeposit);
        textViewPersonInRoom = findViewById(R.id.textViewPersonInRoom);
        textViewGender = findViewById(R.id.textViewGender);
        recycleviewFuniture = findViewById(R.id.recycleviewFuniture);
        recycleviewExtension = findViewById(R.id.recycleviewExtension);
        textViewWater = findViewById(R.id.textViewWater);
        textViewInternet = findViewById(R.id.textViewInternet);
        textViewElectric = findViewById(R.id.textViewElectric);
        textviewDescriptionRoom = findViewById(R.id.textviewDescriptionRoom);
        imageViewBack = findViewById(R.id.imageViewBack);
        imageViewLove = findViewById(R.id.imageViewLove);
        btnCall = findViewById(R.id.btnCall);
        btnBookRoom = findViewById(R.id.btnBookRoom);
        userPost = findViewById(R.id.userPost);
        textViewNameUser = findViewById(R.id.textViewNameUser);
        profile_image = findViewById(R.id.profile_image);

        userPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (user != null) {
//                    //Sau phai sua cho nay thanh view User
//                    Intent mypost = new Intent(DetailRoomActivity.this, ManagePostActivity.class);
//                    startActivity(mypost);
//                } else {
//                    Intent intent = new Intent(DetailRoomActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                }
            }
        });

        if (bundle != null) {
            String roomString = bundle.getString("DataRoom");
            Gson gson = new Gson();
            roomData = gson.fromJson(roomString, Room.class);
            String typeRoomStr = "";
            if (roomData.getType_room() == 0) {
                typeRoomStr = "Trọ";
            } else {
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
            textviewDescriptionRoom.setText(roomData.getDescription_room());


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

//            Drawable drawable = ContextCompat.getDrawable(DetailRoomActivity.this, R.drawable.ic_area);
//            imageViewLove.setImageDrawable(drawable);

            userOwnPostRef = database.getReference("users/" + roomData.getId_own_post());
            userOwnPostRef.child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        String name = snapshot.getValue(String.class);
                        textViewNameUser.setText(name);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

//                userOwnPostRef.child("avatar")
            myLovePostRef = null;
            if (user != null) {
                myLovePostRef = database.getReference("LovePost/" + user.getUid());

                String typeRoom = "ChungCuMini/";
                if (roomData.getType_room() == 0) {
                    typeRoom = "Tro/";
                }
                roomRef = database.getReference("rooms/" + typeRoom + roomData.getId_room());
                //check khi vao room detail
                isLove = false;
                roomRef.child("userLovePost").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        if (snapshot.exists()) {
                            if (snapshot.hasChild(user.getUid())) {
                                imageViewLove.setImageResource(R.drawable.ic_love_fill);
                            } else {
                                imageViewLove.setImageResource(R.drawable.ic_heart_thin_icon);
                            }
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                //Chua tim thi Them tim
                //day du lieu len
                imageViewLove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        isLove = true; //flag đánh dấu xem đã thực hiện xong chưa
//                        imageViewLove.setImageResource(R.drawable.ic_love_fill);
                        if (user != null) {
                            roomRef.child("userLovePost").addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot snapshot) {
                                    if (isLove) {
                                        if (snapshot.hasChild(user.getUid())) {
                                            roomRef.child("userLovePost").child(user.getUid()).removeValue();
                                            //nem room vao bang LovePost
                                            myLovePostRef.child(roomData.getId_room()).removeValue();
                                            isLove = false;
                                        } else {
                                            roomRef.child("userLovePost").child(user.getUid()).setValue(true);
                                            //nem room vao bang LovePost
                                            myLovePostRef.child(roomData.getId_room()).setValue(roomData);
                                            isLove = false;
                                        }
                                        //nem id hien tai vao bang room
                                    }
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError error) {

                                }
                            });
                        }
                    }
                });
            } else {
                imageViewLove.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent login = new Intent(DetailRoomActivity.this, LoginActivity.class);
                        startActivity(login);
                        Toast.makeText(DetailRoomActivity.this,"Bạn phải đăng nhập để sử dụng chức năng này",Toast.LENGTH_SHORT).show();
                    }
                });
            }


            btnCall.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE)
                                != PackageManager.PERMISSION_GRANTED) {
                            // Quyền gọi điện thoại chưa được cấp
                            // Yêu cầu quyền gọi điện thoại
                            ActivityCompat.requestPermissions(DetailRoomActivity.this,
                                    new String[]{android.Manifest.permission.CALL_PHONE},
                                    CALL_PHONE_PERMISSION_REQUEST_CODE);
                        } else {
                            // Quyền gọi điện thoại đã được cấp
                            // Tiến hành thực hiện cuộc gọi điện thoại
                            Toast.makeText(getApplicationContext(), roomData.getPhone(), Toast.LENGTH_SHORT).show();
                            makePhoneCall();
                        }
                    }
                }
            });

            btnBookRoom.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (user != null) {
                        showBottomDialog();
                    }else {
                        Intent login = new Intent(DetailRoomActivity.this, LoginActivity.class);
                        startActivity(login);
                        Toast.makeText(DetailRoomActivity.this,"Bạn phải đăng nhập để sử dụng chức năng này",Toast.LENGTH_SHORT).show();
                    }
                }
            });

            textViewPhone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Lưu vào Clip board", Toast.LENGTH_SHORT).show();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                    // Tạo một đối tượng ClipData để chứa văn bản cần sao chép
                    ClipData clip = ClipData.newPlainText("Label", textViewPhone.getText());

                    // Sao chép ClipData vào clipboard
                    clipboard.setPrimaryClip(clip);
                }
            });

//            textViewCombine_address.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getApplicationContext(),"Nhảy sang map",Toast.LENGTH_SHORT).show();
//                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(roomData.getAddress().getAddress_combine()));
//
//                    // Tạo Intent để chuyển tới Google Maps
//                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                    mapIntent.setPackage("com.google.android.apps.maps");
//
//                    // Kiểm tra xem ứng dụng Google Maps đã được cài đặt hay chưa
//                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
//                        // Mở Google Maps
//                        startActivity(mapIntent);
//                    }
//                }
//            });

            imageViewBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Intent main = new Intent(DetailRoomActivity.this, MainActivity.class);
//                    startActivity(main);
                    finish();
                }
            });

        }

    }

    private void makePhoneCall() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + roomData.getPhone()));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void showBottomDialog() {

        final BottomSheetDialog dialog = new BottomSheetDialog(DetailRoomActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setContentView(R.layout.dialog_book_room);

        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        cancelButton.setOnClickListener(v -> dialog.dismiss());


        edtTime = dialog.findViewById(R.id.edtTime);
        myCalender = Calendar.getInstance();
        DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalender.set(Calendar.YEAR, year);
                myCalender.set(Calendar.MONTH, month);
                myCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();
            }
        };

        edtTime.setOnClickListener(v -> {
            new DatePickerDialog(DetailRoomActivity.this, date, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
        });

//        edtTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                dialog.dismiss();
//                new DatePickerDialog(DetailRoomActivity.this, date, myCalender.get(Calendar.YEAR), myCalender.get(Calendar.MONTH), myCalender.get(Calendar.DAY_OF_MONTH)).show();
//            }
//        });

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCancelable(true);
    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy EEEE";
        SimpleDateFormat dateFormat = new SimpleDateFormat(myFormat, Locale.US);
        edtTime.setText(dateFormat.format(myCalender.getTime()));
    }

}