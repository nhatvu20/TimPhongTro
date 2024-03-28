package com.example.timphongtro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
 
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.example.timphongtro.Entity.ExtensionRoom_class;
import com.example.timphongtro.R;

import java.util.ArrayList; 

public class ExtensionAdapter extends RecyclerView.Adapter<ExtensionAdapter.ViewHolderExtesion>{
    Context context;
    ArrayList<ExtensionRoom_class> list;

    public ExtensionAdapter(Context context, ArrayList<ExtensionRoom_class> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ExtensionAdapter.ViewHolderExtesion onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.extentions_view_holder,parent,false);
        return new ViewHolderExtesion(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ExtensionAdapter.ViewHolderExtesion holder, int position) {
        holder.titleExtension.setText(list.get(holder.getAdapterPosition()).getName());
        if(!"".equals(list.get(holder.getAdapterPosition()).getImg())){
            Glide.with(context)
                    .load(list.get(holder.getAdapterPosition()).getImg())
                    .apply(new RequestOptions()
                            .centerCrop()
                            .diskCacheStrategy(DiskCacheStrategy.ALL)) // để lưu ảnh trong bộ nhớ cache.
                    .into(holder.imageViewExtension);
        }
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
            imageViewExtension = itemView.findViewById(R.id.imageViewExtension);
            titleExtension = itemView.findViewById(R.id.titleExtension);
        }
    }
}
