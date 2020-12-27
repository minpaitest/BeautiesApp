package com.android.beautiesapp.activities;

import android.content.Context;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.beautiesapp.R;
import com.android.beautiesapp.data.model.ProductCart;
import com.android.beautiesapp.util.FirebaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CheckOutAdapter extends RecyclerView.Adapter<CheckOutAdapter.CheckoutViewHolder>{

    ArrayList<ProductCart> productCarts;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;
    private ImageView imageProduct;

    double total = 0.0;

    public CheckOutAdapter(){

        FirebaseUtil.openFbReference("ProductsCarts");
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        productCarts = FirebaseUtil.productCarts;

        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ProductCart productCart = dataSnapshot.getValue(ProductCart.class);
                Log.d("ProductCart " , productCart.getName());
                productCart.setId(dataSnapshot.getKey());
                productCarts.add(productCart);
                notifyItemInserted(productCarts.size()-1);

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        };
        mDatabaseReference.addChildEventListener(mChildListener);
    }

    @NonNull
    @Override
    public CheckoutViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_checkout_row,viewGroup,false);

        return new CheckoutViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CheckoutViewHolder checkoutViewHolder, int i) {

        final ProductCart productCart = productCarts.get(i);
        checkoutViewHolder.bind(productCart);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

    }

    @Override
    public int getItemCount() {
        return productCarts.size();
    }

    public class CheckoutViewHolder extends  RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvPrice;
        TextView tvQty;
        TextView tvSubTotal;
        TextView textViewTotalPrice;


        public CheckoutViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvQty = (TextView) itemView.findViewById(R.id.tv_qty);
            tvSubTotal = (TextView) itemView.findViewById(R.id.tvSubTotal);
            textViewTotalPrice = (TextView) itemView.findViewById(R.id.tvTotalPrice);
        }

        public void bind(ProductCart productCart){
            tvName.setText(productCart.getName());
            double price = productCart.getPrice() ;
            tvPrice.setText(price + "");
            int qty = productCart.getQty();
            tvQty.setText(qty+"");
            double subTotal= productCart.getQty()*productCart.getPrice();
            tvSubTotal.setText(subTotal +"");
            total += subTotal;
            double tempTotal = total;
            //textViewTotalPrice.setText(subTotal  +"");

        }

    }

}
