package com.example.timphongtro;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import com.example.timphongtro.HomePage.MainActivity;

public class SplashScreen extends AppCompatActivity {

//    public static int SPLASH_TIMER = 3000;

//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);

 //       setContentView(R.layout.activity_splash_screen);

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                Intent intent = new Intent(SplashScreen.this, MainActivity.class);
//                startActivity(intent);
//                finish();
//            }
//        }, SPLASH_TIMER);

//    }



    private EditText txtSDT;
    private Button btnDangky, btnDangnhap;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();
        txtSDT = (EditText) findViewById(R.id.txtSDT);
        btnDangky = (Button) findViewById(R.id.btnDangky);
        btnDangnhap = (Button) findViewById(R.id.btnDangnhap);

        btnDangnhap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Dangnhap();
            }

            private void Dangnhap() {
                String txtSDTedit;
                txtSDTedit = txtSDT.getText().toString();
                if (TextUtils.isEmpty(txtSDTedit)) {
                    Toast.makeText(getApplicationContext(), "Vui long nhap so dien thoai.", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithCustomToken(txtSDTedit).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Dang nhap thanh cong", LENGTH_SHORT).show();
                            Intent i = new Intent(SplashScreen.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Dang nhap khong thanh cong", LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dangky();
            }

            private void Dangky() {
                Intent i = new Intent(SplashScreen.this, RegisterActivity.class);
                startActivity(i);
            }
        });
    }
}