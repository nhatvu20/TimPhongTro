package com.example.timphongtro.Database;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.timphongtro.R;

import java.util.ArrayList;

public class ExtensionAdapter extends RecyclerView.Adapter<ExtensionAdapter.ViewHolderExtesion>{
    Context context;
    ArrayList<ExtensionRoom_class> list;
    @NonNull
    @Override
    public ExtensionAdapter.ViewHolderExtesion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.extentions_view_holder,parent,false);
        return new ViewHolderExtesion(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtensionAdapter.ViewHolderExtesion holder, int position) {

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolderExtesion extends RecyclerView.ViewHolder {
        ImageView imageViewExtension;
        TextView titleExtension;

        public ViewHolderExtesion(@NonNull View itemView) {
            super(itemView);

        }
    }
}
