package com.example.timphongtro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.timphongtro.Fragment.HomeFragment;
import com.example.timphongtro.Fragment.NotificationFragment;
import com.example.timphongtro.Fragment.ProfileFragment;
import com.example.timphongtro.Fragment.ServiceFragment;
import com.example.timphongtro.R;
import com.example.timphongtro.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;


public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        //Sử dụng ViewBinding để tối ưu về lượng code cho thanh bottom nav chuyển tab
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.service) {
                replaceFragment(new ServiceFragment());
            } else if (item.getItemId() == R.id.notification) {
                if (user != null) {
                    replaceFragment(new NotificationFragment());
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this, "Vui lòng đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
                }
            } else if (item.getItemId() == R.id.profile) {
//                if (user != null) {
//                    boolean isGoogleSignIn = user.getProviderData().stream()
//                            .anyMatch(userInfo -> userInfo.getProviderId().equals(GoogleAuthProvider.PROVIDER_ID));
//
//                    boolean isFirebaseSignIn = user.getProviderData().stream()
//                            .anyMatch(userInfo -> userInfo.getProviderId().equals(EmailAuthProvider.PROVIDER_ID));
//
//                    if (isGoogleSignIn) {
//                        // Người dùng đã đăng nhập bằng Google
//                        // Do something khi người dùng đăng nhập bằng Google
//                        replaceFragment(new ProfileFragment());
//                    } else if (isFirebaseSignIn) {
//                        // Người dùng đã đăng nhập bằng Firebase Authentication
//                        // Do something khi người dùng đăng nhập bằng Firebase Authentication
//                        replaceFragment(new ProfileFragment());
//                    } else {
//                        // Trường hợp khác, không phải đăng nhập bằng Google hoặc Firebase Authentication
//                    }
//                } else {
//                    // Người dùng chưa đăng nhập
//                    // Do something khi người dùng chưa đăng nhập
//                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                    startActivity(intent);
//                    Toast.makeText(MainActivity.this,"Vui lòng đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
//                }
                GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
                String id_user = user != null ? user.getUid() : (account != null ? account.getId() : "");

                if (user != null || account != null) {
                    replaceFragment(new ProfileFragment());
                }
                else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Toast.makeText(MainActivity.this,"Vui lòng đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        });

        binding.fab.setOnClickListener(v -> {
            showBottomDialog();
        });
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(binding.frameLayout.getId(), fragment);
        fragmentTransaction.commit();
    }

    //Hiển thị khay dưới khi bấm dấu cộng
    private void showBottomDialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_dialog);

        LinearLayout house = dialog.findViewById(R.id.house);
        LinearLayout groupusers = dialog.findViewById(R.id.pickImgAlbum);
        LinearLayout contract = dialog.findViewById(R.id.contract);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        house.setOnClickListener(v -> {
            dialog.dismiss();
            Intent search = new Intent(this, SearchActivity.class);
            startActivity(search);
        });

        groupusers.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "In Developping", Toast.LENGTH_SHORT).show();
        });

        contract.setOnClickListener(v -> {
            dialog.dismiss();
            if (user != null) {
                Intent post = new Intent(this, PostRoomActivity.class);
                startActivity(post);
            } else {
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
                Toast.makeText(MainActivity.this, "Bạn phải đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
            }
        });

        cancelButton.setOnClickListener(v -> dialog.dismiss());

        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);
        dialog.setCancelable(true);
    }
}