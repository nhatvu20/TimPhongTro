package com.example.timphongtro.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.timphongtro.Activity.UpdateInformationUserActivity;
import com.example.timphongtro.Activity.DetailRoomActivity;
import com.example.timphongtro.Activity.HistoryActivity;
import com.example.timphongtro.Activity.LoginActivity;
import com.example.timphongtro.Activity.ManagePostActivity;
import com.example.timphongtro.Activity.MyLovePostActivity;
import com.example.timphongtro.Activity.scheduleVisitRoomActivity;
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
    private TextView txtViewInfo, tvprofile;

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

        TextView btndangxuat, btnquanlyphong, btnlichhen, btnyeuthich, btnlichsu, txtviewEmail;

        txtViewInfo = view.findViewById(R.id.txtviewInfo);
        txtviewEmail = view.findViewById(R.id.txtviewEmail);

        btndangxuat = view.findViewById(R.id.btndangxuat);
        btnquanlyphong = view.findViewById(R.id.btnquanlyphong);
        btnlichhen = view.findViewById(R.id.btnlichhen);
        btnlichsu = view.findViewById(R.id.btnlichsu);
        btnyeuthich = view.findViewById(R.id.btnyeuthich);
        tvprofile = view.findViewById(R.id.tvprofile);


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

        btnlichhen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent schedule = new Intent(getActivity(), scheduleVisitRoomActivity.class);
                startActivity(schedule);
            }
        });

        txtViewInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), UpdateInformationUserActivity.class);
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


        if (mUser != null) {
            // Người dùng đã đăng nhập, bạn có thể lấy thông tin người dùng từ currentUser
            String uid = mUser.getUid();
            String email = mUser.getEmail();
            txtviewEmail.setText(email);
            userRef = database.getReference("users/" + mUser.getUid());
            userRef.child("name").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.exists()) {
                        name = snapshot.getValue(String.class);
                        if (!"".equals(name)) {
                            txtViewInfo.setText(name);
                            tvprofile.setText(getFirstLetter(name));
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        } else {
            // Người dùng chưa đăng nhập hoặc đã đăng xuất
            //Thi Chuyen qua form Login
            Intent i = new Intent(getActivity(), LoginActivity.class);
            startActivity(i);
        }


    }

    public static String getFirstLetter(String input) {
        String[] words = input.split(" ");
        StringBuilder result = new StringBuilder();

        if (words.length == 1) {
            // Nếu chuỗi chỉ có 1 từ, lấy chữ đầu từ đó
            result.append(words[0].charAt(0));
        } else {
            // Nếu chuỗi có nhiều từ, lấy chữ cái đầu của từ thứ 1 và 2
            for (int i = 0; i < 2; i++) {
                result.append(words[i].charAt(0));
            }
        }

        return result.toString().toUpperCase();
    }
}