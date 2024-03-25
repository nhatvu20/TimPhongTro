package com.example.timphongtro.Activity;

import static android.widget.Toast.LENGTH_SHORT;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timphongtro.Fragment.ForgotPasswordActivity;
import com.example.timphongtro.Activity.MainActivity;
import com.example.timphongtro.R;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.BeginSignInResult;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private EditText txtemail;
    private EditText txtpassword;
    private Button btnDangnhap;
    private FirebaseAuth mAuth;

    private FirebaseUser mUser;
    private TextView textviewDangky;

    private TextView textviewQuenMK;
    private LinearLayout layout_forgotpassword;

    private Button btnGGSignin;
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;

    private static final int REQ_ONE_TAP = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        txtemail = (EditText) findViewById(R.id.txtemail);
        txtpassword = (EditText) findViewById(R.id.txtpassword);
        textviewDangky = (TextView) findViewById(R.id.textviewDangky);
        btnDangnhap = (Button) findViewById(R.id.btnDangnhap);
        textviewQuenMK = (TextView) findViewById(R.id.textviewQuenMK);

        layout_forgotpassword = (LinearLayout) findViewById(R.id.layout_forgotpassword);
//        btnGGSignin = (Button) findViewById(R.id.btnGGSignin);


//        oneTapClient = Identity.getSignInClient(this);
//        signInRequest = BeginSignInRequest.builder()
//                        .setPasswordRequestOptions(BeginSignInRequest.PasswordRequestOptions.builder()
//                                .setSupported(true)
//                                .build())
//                                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
//                                        .setSupported(true)
//                                      //  .setServerClientId(getString(R.string.default_web_client_id))
//                                        .setFilterByAuthorizedAccounts(false)
//                                        .build())
//                .setAutoSelectEnabled(true)
//                .build();
//        public void btnGGSignin(View view){
//            oneTapClient.beginSignIn(signInRequest).addOnSuccessListener(this, new OnSuccessListener<BeginSignInResult>() {
//                @Override
//                public void onSuccess(BeginSignInResult beginSignInResult) {
//                    try {
//                        startIntentSenderForResult(beginSignInResult.getPendingIntent().getIntentSender(),REQ_ONE_TAP,null,0,0,0);
//
//                    }
//                    catch(IntentSender.SendIntentException e) {
//                        Log.e(TAG,"Couldn't start One Tap UI: "+e.getLocalizedMessage());
//                    }
//                }
//            })
//                    .addOnFailureListener(this, new OnFailureListener() {
//                        @Override
//                        public void onFailure(@NonNull Exception e) {
//                            Log.d(TAG,e.getLocalizedMessage());
//                        }
//                    });
//        }

        btnDangnhap.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Dangnhap();
            }

            private void Dangnhap() {
                String email;
                String password;
                email = txtemail.getText().toString();
                password = txtpassword.getText().toString();
                if (TextUtils.isEmpty(email)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập Email.", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (TextUtils.isEmpty(password)) {
                    Toast.makeText(getApplicationContext(), "Vui lòng nhập Mật khẩu.", Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Đăng nhập thành công", LENGTH_SHORT).show();
                            btnDangnhap.setEnabled(false);
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                        } else {
                            Toast.makeText(getApplicationContext(), "Đăng nhập không thành công", LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
//        btnGGSignin
        textviewDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dangky();
            }

            private void Dangky() {
                Intent i = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        textviewQuenMK.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
            }

        });

    }

}