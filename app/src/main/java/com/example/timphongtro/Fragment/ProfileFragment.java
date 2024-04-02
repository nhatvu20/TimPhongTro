package com.example.timphongtro.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.timphongtro.Activity.DetailRoomActivity;
import com.example.timphongtro.Activity.HistoryActivity;
import com.example.timphongtro.Activity.InformationActivity;
import com.example.timphongtro.Activity.LoginActivity;
import com.example.timphongtro.Activity.ManagePostActivity;
import com.example.timphongtro.Activity.MyLovePostActivity;
import com.example.timphongtro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfileFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    FirebaseDatabase database;
    private String name;
    DatabaseReference userRef;
    private TextView txtViewInfo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        mUser = FirebaseAuth.getInstance().getCurrentUser();

        TextView btndangxuat, btnquanlyphong, btnlichhen, btnyeuthich, btnlichsu;

        txtViewInfo = view.findViewById(R.id.txtviewInfo);

        btndangxuat = view.findViewById(R.id.btndangxuat);
        btnquanlyphong = view.findViewById(R.id.btnquanlyphong);
        btnlichhen = view.findViewById(R.id.btnlichhen);
        btnlichsu = view.findViewById(R.id.btnlichsu);
        btnyeuthich = view.findViewById(R.id.btnyeuthich);


        btnlichsu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HistoryActivity.class);
                startActivity(intent);
            }
        });
        btndangxuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Intent i = new Intent(getActivity(), LoginActivity.class);
                startActivity(i);
                getActivity().finish();
            }
        });

        btnquanlyphong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mUser != null) {
                    //Sau phai sua cho nay thanh view User
                    Intent mypost = new Intent(getActivity(), ManagePostActivity.class);
                    startActivity(mypost);
                } else {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            }
        });

        btnyeuthich.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lovepost = new Intent(getActivity(), MyLovePostActivity.class);
                startActivity(lovepost);
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
        userRef = database.getReference("users/" + mUser.getUid());
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