package com.example.timphongtro.Activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.example.timphongtro.Entity.User;
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

import java.util.Objects;

public class UpdateInformationUserActivity extends AppCompatActivity {

    FirebaseDatabase database;
    private FirebaseUser mUser;
    DatabaseReference userRef;
    private String name, email, phone;
    private EditText txtname, txtemail, txtphone;
    private ImageView imageViewBack;
    User user;
    String imageURL;
    Uri uri;
    private Button btnCapnhat;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_updateinformationuser);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        txtname = (EditText) findViewById(R.id.txtname);
        txtemail = (EditText) findViewById(R.id.txtemail);
        imageViewBack = (ImageView) findViewById(R.id.imageViewBack);
        btnCapnhat = (Button) findViewById(R.id.btnCapnhat);
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        txtphone = (EditText) findViewById(R.id.txtphone);
        imageViewBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users/" + mUser.getUid());

        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    user = snapshot.getValue(User.class);
                    if(user!=null) {
                        email = user.getEmail();
                        phone = user.getphone();
                        name = user.getName();
                        txtname.setText(name);
                        txtemail.setText(email);
                        txtphone.setText(phone);
                        txtemail.setEnabled(false);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
        btnCapnhat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                uploadData();
            }
        });
    }

    private void uploadData() {
        User user = new User(email, mUser.getUid(), txtname.getText().toString(), txtphone.getText().toString());
        DatabaseReference databaseRef = FirebaseDatabase.getInstance().getReference("users/" + mUser.getUid());
        databaseRef.setValue(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        // Cập nhật thành công
                        Toast.makeText(getApplicationContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Có lỗi xảy ra khi cập nhật
                    }
                });
    }
}