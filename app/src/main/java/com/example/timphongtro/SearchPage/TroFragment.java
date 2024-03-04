package com.example.timphongtro.SearchPage;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.timphongtro.Database.RoomViewHolderData;
import com.example.timphongtro.Database.TroAdapter;
import com.example.timphongtro.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class TroFragment extends Fragment {

    RecyclerView trorecyclerView;
    DatabaseReference trodatabase;
    TroAdapter troAdapter;
    ArrayList<RoomViewHolderData> trolist;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        trorecyclerView = view.findViewById(R.id.trorecyclerview);
        trorecyclerView.setHasFixedSize(true);
        trodatabase = FirebaseDatabase.getInstance().getReference("rooms/Tro");
        trorecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        trolist = new ArrayList<>();
        troAdapter = new TroAdapter(getContext(), trolist);
        trorecyclerView.setAdapter(troAdapter);
        fetchtrorecyclerviewdatabase();
    }

    private void fetchtrorecyclerviewdatabase() {
        trodatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    RoomViewHolderData roomViewHolderData = dataSnapshot.getValue(RoomViewHolderData.class);
                    trolist.add(roomViewHolderData);
                }
                troAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}