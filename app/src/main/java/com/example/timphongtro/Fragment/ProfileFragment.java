package com.example.timphongtro.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.timphongtro.Activity.InformationActivity;
import com.example.timphongtro.Activity.LoginActivity;
import com.example.timphongtro.R;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.jar.Attributes;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    FirebaseDatabase database;
    private String name;
    private TextView txtViewDangxuat;

    DatabaseReference userRef;
    private TextView txtViewInfo;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_profile, container, false);
       
}

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();
        Spinner spinner;
        

        TextView txtViewInfo = view.findViewById(R.id.txtviewInfo);

        TextView txtViewDangxuat = view.findViewById(R.id.dangxuat);
        txtViewDangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
            }
        });

        txtViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), InformationActivity.class);
                startActivity(i);
            }
        });
        database = FirebaseDatabase.getInstance();
        userRef = database.getReference("users/"+mUser.getUid());
        userRef.child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    name = snapshot.getValue(String.class);
                    txtViewInfo.setText(name);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });



        FirebaseUser currentUser = mAuth.getCurrentUser();
        if (currentUser != null) {
            // Người dùng đã đăng nhập, bạn có thể lấy thông tin người dùng từ currentUser
            String uid = currentUser.getUid();
            String email = currentUser.getEmail();
       //     String name =
            //Chuyen qua chuc nang do
            //Neu dang la Form Ca nhan thi se cho truong Email nao vao textView de Hien thi

        } else {
            // Người dùng chưa đăng nhập hoặc đã đăng xuất
            //Thi Chuyen qua form Login
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
        }

        
    }
}