package com.example.timphongtro.Fragment;
 
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.timphongtro.R;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment { 

    private Button btnDangxuat;
    private FirebaseAuth mAuth;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
//        // Inflate the layout for this fragment

        return inflater.inflate(R.layout.fragment_profile, container, false);
    }


}