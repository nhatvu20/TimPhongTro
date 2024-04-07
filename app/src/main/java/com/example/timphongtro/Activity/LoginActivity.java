package com.example.timphongtro.Activity;

import static android.widget.Toast.LENGTH_SHORT;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timphongtro.Fragment.HomeFragment;
import com.example.timphongtro.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class LoginActivity extends AppCompatActivity {
    private EditText txtemail;
    private EditText txtpassword;
    private Button btnDangnhap;
    private FirebaseAuth mAuth;
    private ImageView imgGoogleSignin;
    private TextView textviewDangky;
    private TextView txtViewForgotPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        txtemail = (EditText) findViewById(R.id.txtemail);
        txtpassword = (EditText) findViewById(R.id.txtpassword);
        textviewDangky = (TextView) findViewById(R.id.textviewDangky);
        btnDangnhap = (Button) findViewById(R.id.btnDangnhap);
        txtViewForgotPassword = (TextView) findViewById(R.id.txtViewForgotPassword);
        imgGoogleSignin = (ImageView) findViewById(R.id.imgGoogleSignin);
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
                            Intent i = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(i);
                            btnDangnhap.setEnabled(false);
                        } else {
                            Toast.makeText(getApplicationContext(), "Đăng nhập không thành công", LENGTH_SHORT).show();
                        }
                    }
                });
            }

        });
        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail().build();
        GoogleSignInClient googleSignInClient = GoogleSignIn.getClient(this,googleSignInOptions);
        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        //Checking if user already signed in
        if(googleSignInAccount != null){


            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
            @Override
            public void onActivityResult(ActivityResult result) {

                //đăng nhập tài khoản sau khi người dùng chọn tài khoản từ hộp thoại tài khoản google
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(result.getData());
                handleSignInTask(task);
            }
        });

        imgGoogleSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                activityResultLauncher.launch(signInIntent);
            }
        });
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
        txtViewForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(LoginActivity.this, ForgotPasswordActivity.class);
                startActivity(i);
            }
        });

    }

    public void handleSignInTask(Task<GoogleSignInAccount> task) {
        try {
            GoogleSignInAccount account = task.getResult(ApiException.class);
            Intent i = new Intent(LoginActivity.this, MainActivity.class);
 //         final String getFullName = account.getDisplayName();
//            final String getEmail = account.getEmail();
            startActivity(i);
            finish();

        } catch (ApiException e) {
            e.printStackTrace();
            Toast.makeText(getApplicationContext(), "In Developing", LENGTH_SHORT).show();
        }
    }

}