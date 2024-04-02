package com.example.timphongtro.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.timphongtro.Activity.CartActivity;
import com.example.timphongtro.Activity.LoginActivity;
import com.example.timphongtro.Activity.ServiceActivity;
import com.example.timphongtro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class ServiceFragment extends Fragment {

    private LinearLayout chothuenoithat, tuvanthietkephong, suachuadiennuoc, giatla, doibinhnuoc, doibinhga;
    private ImageView button_cart;

    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_service, container, false);
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        chothuenoithat = view.findViewById(R.id.chothuenoithat);
        tuvanthietkephong = view.findViewById(R.id.tuvanthietkephong);
        suachuadiennuoc = view.findViewById(R.id.suachuadiennuoc);
        giatla = view.findViewById(R.id.giatla);
        doibinhnuoc = view.findViewById(R.id.doibinhnuoc);
        doibinhga = view.findViewById(R.id.doibinhga);
        button_cart = view.findViewById(R.id.button_cart);
        button_cart.setColorFilter(ContextCompat.getColor(getContext(), R.color.white));

        button_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                if (user != null){
                    intent = new Intent(getContext(), CartActivity.class);
                } else {
                    intent = new Intent(getContext(), LoginActivity.class);
                }
                startActivity(intent);

            }
        });

        chothuenoithat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openServiceActivity("chothuenoithat");
            }
        });

        tuvanthietkephong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openServiceActivity("tuvanthietkephong");
            }
        });

        suachuadiennuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openServiceActivity("suachuadiennuoc");
            }
        });
        giatla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openServiceActivity("giatla");
            }
        });

        doibinhnuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openServiceActivity("doibinhnuoc");
            }
        });

        doibinhga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openServiceActivity("doibinhga");
            }
        });
    }

    private void openServiceActivity(String item) {
        Intent intent = new Intent(getContext(), ServiceActivity.class);
        intent.putExtra("item", item);
        startActivity(intent);
    }
}