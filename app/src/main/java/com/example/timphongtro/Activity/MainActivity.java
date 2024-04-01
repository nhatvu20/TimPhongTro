package com.example.timphongtro.Activity;
 
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.app.Dialog;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


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
                replaceFragment(new NotificationFragment());
            } else if (item.getItemId() == R.id.profile) {
                if (user != null) {
                    replaceFragment(new ProfileFragment());
                }
                else {
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
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
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottom_dialog);

        LinearLayout house = dialog.findViewById(R.id.house);
        LinearLayout groupusers = dialog.findViewById(R.id.groupusers);
        LinearLayout contract = dialog.findViewById(R.id.contract);
        ImageView cancelButton = dialog.findViewById(R.id.cancelButton);

        house.setOnClickListener(v -> {
            dialog.dismiss();
            Intent search = new Intent(this,SearchActivity.class);
            startActivity(search);
        });

        groupusers.setOnClickListener(v -> {
            dialog.dismiss();
            Toast.makeText(MainActivity.this, "In Developping", Toast.LENGTH_SHORT).show();
        });

        contract.setOnClickListener(v -> {
            dialog.dismiss();
            if (user != null) {
                Intent post = new Intent(MainActivity.this, PostRoomActivity.class);
                startActivity(post);
            } else {
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
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