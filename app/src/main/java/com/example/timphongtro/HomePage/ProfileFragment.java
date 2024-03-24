package com.example.timphongtro.HomePage;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Spinner;

import com.example.timphongtro.LoginActivity;
import com.example.timphongtro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ProfileFragment extends Fragment {

    private Button btnDangxuat;
    private FirebaseAuth mAuth;

    private FirebaseUser mUser;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment

        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        Spinner spinner;

        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);

        Button btnDangxuat = rootView.findViewById(R.id.btnDangxuat);

//        btnDangxuat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logout();
//            }

//            private void logout() {
//                SharedPreferences preferences = requireContext().getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = preferences.edit();
//                editor.clear();
//                editor.apply();
//
//                // Redirect to login screen or desired destination
//                Intent i = new Intent(ProfileFragment.,HomeFragment.class);
//            }
//        });
//
        return inflater.inflate(R.layout.fragment_profile, container, false);


//        FirebaseUser currentUser = mAuth.getCurrentUser();
//        if (currentUser != null) {
//            // Người dùng đã đăng nhập, bạn có thể lấy thông tin người dùng từ currentUser
//            String uid = currentUser.getUid();
//            String email = currentUser.getEmail();
//            //Chuyen qua chuc nang do
//            //Neu dang la Form Ca nhan thi se cho truong Email nao vao textView de Hien thi
//
//        } else {
//            // Người dùng chưa đăng nhập hoặc đã đăng xuất
//            //Thi Chuyen qua form Login
//            Intent i = new Intent(getActivity(), LoginActivity.class);
//            startActivity(i);
//        }
    }


}