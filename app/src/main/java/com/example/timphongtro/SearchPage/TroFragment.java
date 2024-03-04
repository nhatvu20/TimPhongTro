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
import android.widget.SearchView;

import com.example.timphongtro.Database.RoomAdapter;
import com.example.timphongtro.Database.RoomViewHolderData;
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
    RoomAdapter troAdapter;
    ArrayList<RoomViewHolderData> trolist;
    SearchView searchView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tro, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        searchView = view.findViewById(R.id.search_tro);
        searchView.clearFocus();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                searchList(newText);
                return true;
            }
        });
        trorecyclerView = view.findViewById(R.id.trorecyclerview);
        trorecyclerView.setHasFixedSize(true);
        trodatabase = FirebaseDatabase.getInstance().getReference("rooms/Tro");
        trorecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL,false));
        trolist = new ArrayList<>();
        troAdapter = new RoomAdapter(getContext(), trolist);
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
    public void searchList(String text){
        ArrayList<RoomViewHolderData> searchList = new ArrayList<>();
        for (RoomViewHolderData roomViewHolderData: trolist){
            if (roomViewHolderData.getTitle_room().toLowerCase().contains(text.toLowerCase())){
                searchList.add(roomViewHolderData);
            }
        }
        troAdapter.searchDataList(searchList);
    }
}