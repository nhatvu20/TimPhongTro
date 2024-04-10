package com.example.timphongtro.Activity;

import android.app.AlertDialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timphongtro.Adapter.ExtensionAdapter;
import com.example.timphongtro.Adapter.RoomAdapter;
import com.example.timphongtro.Entity.ExtensionRoom_class;
import com.example.timphongtro.Entity.Room;
import com.example.timphongtro.Entity.User;
import com.example.timphongtro.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.ArrayList;

public class UserActivity extends AppCompatActivity {

    private ImageView imageView_back;
    private TextView username, phone, email,circleImageView;
    private RecyclerView rcvUser;
    private RoomAdapter roomAdapter;
    private FirebaseDatabase database;
    private DatabaseReference postRef, userRef;
    private ArrayList<Room> roomArrayList;
    LinearLayout phoneLinear,emailLinear;
    private static final int CALL_PHONE_PERMISSION_REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        Bundle bundle = getIntent().getExtras();
        username = findViewById(R.id.username);
        imageView_back = findViewById(R.id.imageView_back);
        rcvUser = findViewById(R.id.rcvUser);
        phone = findViewById(R.id.phone);
        email = findViewById(R.id.email);
        emailLinear = findViewById(R.id.emailLinear);
        phoneLinear = findViewById(R.id.phoneLinear);
        circleImageView = findViewById(R.id.circleImageView);
        imageView_back.setColorFilter(ContextCompat.getColor(this, R.color.white));
        imageView_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        database = FirebaseDatabase.getInstance();
        roomArrayList = new ArrayList<>();

        if (bundle != null) {
            String id_own_post = bundle.getString("id_own_post");
            if(id_own_post != null) {
                userRef = database.getReference("users/" + id_own_post);

                userRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        User user = snapshot.getValue(User.class);
                        if (user != null) {
                            username.setText(user.getName());
                            phone.setText(user.getphone());
                            email.setText(user.getEmail());
                            circleImageView.setText(getFirstLetter(user.getName()));
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });

                postRef = database.getReference("rooms/");

                postRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        roomArrayList.clear();
                        if (snapshot.exists()) {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                String key = dataSnapshot.getKey();
                                if (key.equals("Tro") || key.equals("ChungCuMini")) {
                                    for (DataSnapshot childSnapshot : dataSnapshot.getChildren()) {
                                        Room room = childSnapshot.getValue(Room.class);
                                        if (room != null && room.getStatus_room() != 1) {
                                            if (id_own_post.equals(room.getId_own_post())) {
                                                roomArrayList.add(room);
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        roomAdapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
                roomAdapter = new RoomAdapter(UserActivity.this, roomArrayList);
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                layoutManager.setOrientation(RecyclerView.VERTICAL);
                rcvUser.setLayoutManager(layoutManager);
                rcvUser.setAdapter(roomAdapter);
            }

            phoneLinear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(phone.getText().length() != 0){
                        Toast.makeText(getApplicationContext(), "Lưu số điện thoại vào Clip board", Toast.LENGTH_SHORT).show();
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                        // Tạo một đối tượng ClipData để chứa văn bản cần sao chép
                        ClipData clip = ClipData.newPlainText("Label", phone.getText());

                        // Sao chép ClipData vào clipboard
                        clipboard.setPrimaryClip(clip);

                        AlertDialog.Builder builder = new AlertDialog.Builder(UserActivity.this);
                        builder.setTitle("Bạn có muốn thực hiện một cuộc gọi")
                                .setMessage("Đây là nội dung của dialog.").setPositiveButton("Có", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.CALL_PHONE)
                                                    != PackageManager.PERMISSION_GRANTED) {
                                                // Quyền gọi điện thoại chưa được cấp
                                                // Yêu cầu quyền gọi điện thoại
                                                ActivityCompat.requestPermissions(UserActivity.this,
                                                        new String[]{android.Manifest.permission.CALL_PHONE},
                                                        CALL_PHONE_PERMISSION_REQUEST_CODE);
                                            } else {
                                                // Quyền gọi điện thoại đã được cấp
                                                // Tiến hành thực hiện cuộc gọi điện thoại
//                            Toast.makeText(getApplicationContext(), roomData.getPhone(), Toast.LENGTH_SHORT).show();
                                                makePhoneCall();
                                            }
                                        }
                                    }
                                }).setNegativeButton("Không", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                    }
                                });

                        android.app.AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                    }else {
                        Toast.makeText(getApplicationContext(), "Người dùng này chưa cập nhật số điện thoại", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(getApplicationContext(), "Lưu email vào Clip board", Toast.LENGTH_SHORT).show();
                    ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);

                    // Tạo một đối tượng ClipData để chứa văn bản cần sao chép
                    ClipData clip = ClipData.newPlainText("Label", email.getText());

                    // Sao chép ClipData vào clipboard
                    clipboard.setPrimaryClip(clip);
                }
            });
        }
    }
    public static String getFirstLetter(String input) {
        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();

        if (words.length == 1) {
            // Nếu chuỗi chỉ có 1 từ, lấy chữ đầu từ đó
            result.append(words[0].charAt(0));
        } else {
            // Nếu chuỗi có nhiều từ, lấy chữ cái đầu của từ thứ 1 và 2
            for (int i = 0; i < 2; i++) {
                result.append(words[i].charAt(0));
            }
        }

        return result.toString().toUpperCase();
    }

    private void makePhoneCall() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + phone.getText()));

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }
}