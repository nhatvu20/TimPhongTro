package com.example.timphongtro.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.timphongtro.BroadcastReceiver.NetworkChangeReceiver;
import com.example.timphongtro.Fragment.HomeFragment;
import com.example.timphongtro.Fragment.NotificationFragment;
import com.example.timphongtro.Fragment.ProfileFragment;
import com.example.timphongtro.Fragment.ServiceFragment;
import com.example.timphongtro.R;
import com.example.timphongtro.databinding.ActivityMainBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class MainActivity extends AppCompatActivity {
    NetworkChangeReceiver networkChangeReceiver;
    private boolean isReceiverRegistered = false;
    private ActivityMainBinding binding;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user;
    GoogleSignInAccount account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        networkChangeReceiver = new NetworkChangeReceiver();
        account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        user = firebaseAuth.getCurrentUser();
        //Sử dụng ViewBinding để tối ưu về lượng code cho thanh bottom nav chuyển tab
        replaceFragment(new HomeFragment());
        binding.bottomNavigationView.setBackground(null);

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if (item.getItemId() == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (item.getItemId() == R.id.service) {
                replaceFragment(new ServiceFragment());
            } else if (item.getItemId() == R.id.notification) {
                if (user != null || account != null) {
                    replaceFragment(new NotificationFragment());
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
//                    Toast.makeText(MainActivity.this, "Vui lòng đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
                }
            } else if (item.getItemId() == R.id.profile) {
                if (user != null || account != null) {
                    replaceFragment(new ProfileFragment());
                } else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
//                    Toast.makeText(MainActivity.this,"Vui lòng đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
                }
            }
            return true;
        });

        binding.fab.setOnClickListener(v -> {
            showBottomDialog();
        });
    }

    @Override
    protected void onResume() {

        super.onResume();
        // Đăng ký BroadcastReceiver khi Activity được hiển thị
        if (!isReceiverRegistered) {
            registerReceiver(networkChangeReceiver, new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION));
            isReceiverRegistered = true;
        }
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
            if (user != null || account != null) {
                Intent post = new Intent(this, PostRoomActivity.class);
                startActivity(post);
            } else {
                Intent login = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(login);
//                Toast.makeText(MainActivity.this, "Bạn phải đăng nhập để sử dụng chức năng này", Toast.LENGTH_SHORT).show();
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