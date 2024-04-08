package com.example.timphongtro.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.timphongtro.Entity.Service;
import com.example.timphongtro.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    private ArrayList<Service> cartList;
    private Context context;
    private FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
    private FirebaseUser user = firebaseAuth.getCurrentUser();
    private String userID = user.getUid();
    private DatabaseReference cartRef = FirebaseDatabase.getInstance().getReference("Cart/" + userID);

    public CartAdapter(Context context, ArrayList<Service> cartList) {
        this.context = context;
        this.cartList = cartList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_view_holder, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartAdapter.CartViewHolder holder, int position) {
        Service service = cartList.get(position);
        holder.name.setText(service.getTitle());
        DecimalFormat decimalFormat = new DecimalFormat("#,###.###");
        decimalFormat.setDecimalSeparatorAlwaysShown(false);
        holder.price.setText(decimalFormat.format(service.getPrice()) + " VNĐ");
        holder.amount.setText(String.valueOf(service.getAmount()));
        Glide.with(context).load(service.getImg1()).centerCrop().into(holder.image);

        holder.btn_remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                removeCartItem(service);
            }
        });
        holder.minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentAmount = service.getAmount();
                if (currentAmount > 1) {
                    int updatedAmount = currentAmount - 1;
                    service.setAmount(updatedAmount);
                    notifyDataSetChanged();
                    updateCartItem(service);
                } else {
                    removeCartItem(service);
                }
            }
        });

        holder.plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentAmount = service.getAmount();
                int updatedAmount = currentAmount + 1;
                service.setAmount(updatedAmount);
                notifyDataSetChanged();
                updateCartItem(service);
            }
        });
    }

    private void removeCartItem(Service service) {
        cartRef.orderByChild("title").equalTo(service.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().removeValue();
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    private void updateCartItem(Service service) {
        cartRef.orderByChild("title").equalTo(service.getTitle()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    snapshot.getRef().child("amount").setValue(service.getAmount());
                    break;
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });
    }

    @Override
    public int getItemCount() {
        return cartList.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {

        TextView name, price, plus, minus;
        ImageView image;
        Button btn_remove;
        EditText amount;

        public CartViewHolder(View itemview) {
            super(itemview);
            plus = itemview.findViewById(R.id.textView_plus);
            minus = itemview.findViewById(R.id.textView_minus);
            name = itemview.findViewById(R.id.Cartname);
            price = itemview.findViewById(R.id.Price);
            image = itemview.findViewById(R.id.Cartimage);
            btn_remove = itemview.findViewById(R.id.button_remove);
            amount = itemview.findViewById(R.id.editText_amount);
        }
    }
}
