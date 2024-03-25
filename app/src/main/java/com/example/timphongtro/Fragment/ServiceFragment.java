package com.example.timphongtro.Fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.timphongtro.R;

public class ServiceFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_service, container, false);
    }

    public interface ServiceItemClickedListener{
        void ServiceItemClicked(String servicename);
    }

    private ServiceItemClickedListener serviceItemClickedListener;
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        LinearLayout chothuenoithat, tuvanthietkephong, suachuadiennuoc, giatla, doibinhnuoc, doibinhga;

        chothuenoithat = view.findViewById(R.id.chothuenoithat);
        tuvanthietkephong = view.findViewById(R.id.tuvanthietkephong);
        suachuadiennuoc = view.findViewById(R.id.suachuadiennuoc);
        giatla = view.findViewById(R.id.giatla);
        doibinhnuoc = view.findViewById(R.id.doibinhnuoc);
        doibinhga = view.findViewById(R.id.doibinhga);

        chothuenoithat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceItemClickedListener != null) {
                    serviceItemClickedListener.ServiceItemClicked("chothuenoithat");
                }
            }
        });
        tuvanthietkephong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceItemClickedListener != null) {
                    serviceItemClickedListener.ServiceItemClicked("chothuenoithat");
                }
            }
        });
        suachuadiennuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceItemClickedListener != null) {
                    serviceItemClickedListener.ServiceItemClicked("chothuenoithat");
                }
            }
        });
        giatla.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceItemClickedListener != null) {
                    serviceItemClickedListener.ServiceItemClicked("chothuenoithat");
                }
            }
        });
        doibinhnuoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceItemClickedListener != null) {
                    serviceItemClickedListener.ServiceItemClicked("chothuenoithat");
                }
            }
        });
        doibinhga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (serviceItemClickedListener != null) {
                    serviceItemClickedListener.ServiceItemClicked("chothuenoithat");
                }
            }
        });
    }
}