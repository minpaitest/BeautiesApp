package com.android.beautiesapp.activities;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.beautiesapp.R;
import com.android.beautiesapp.data.model.Product;
import com.android.beautiesapp.data.model.ProductCart;
import com.android.beautiesapp.util.FirebaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.ShoppingCartViewHolder>{

    ArrayList<ProductCart> productCarts;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;
    private ImageView imageProduct;

    public ShoppingCartAdapter(){

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
    public ShoppingCartViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        Context context = viewGroup.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_cart_row,viewGroup,false);

        return new ShoppingCartViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartViewHolder shoppingCartViewHolder, int i) {

        final ProductCart productCart = productCarts.get(i);
        shoppingCartViewHolder.bind(productCart);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;

        shoppingCartViewHolder.buttonRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                deleteFromCart(productCart);
                Intent intent = new Intent(view.getContext(), ShoppingCartActivity.class);
                view.getContext().startActivity(intent);
                Toast.makeText(view.getContext(), "Removed from cart.", Toast.LENGTH_LONG).show();

            }
        });

        shoppingCartViewHolder.buttonIncrease.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {

               /* TextView tvQty = (TextView) view.findViewById(R.id.tvQuantity);
                productCart.setQty(tvQty.getText());
                product.setDescription(tvProductDesc.getText().toString());
                product.setPrice(Double.parseDouble(tvProductPrice.getText().toString()));*/
                productCart.setQty(productCart.getQty()+1);
                productCart.setSubTotal(productCart.getQty()*productCart.getPrice());
                updateQty(productCart);
                Intent intent = new Intent(view.getContext(), ShoppingCartActivity.class);
                view.getContext().startActivity(intent);
                Toast.makeText(view.getContext(), "Quantity is increased.", Toast.LENGTH_LONG).show();
            }
        });

        shoppingCartViewHolder.buttonDecrease.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int temp = productCart.getQty();
                if(temp>1){

                    productCart.setQty(productCart.getQty()-1);
                    productCart.setSubTotal(productCart.getQty()*productCart.getPrice());
                    updateQty(productCart);
                    Intent intent = new Intent(view.getContext(), ShoppingCartActivity.class);
                    view.getContext().startActivity(intent);
                    Toast.makeText(view.getContext(), "Quantity is decreased.", Toast.LENGTH_LONG).show();
                }else{

                    Toast.makeText(view.getContext(), "Quantity is not allowed to decrease.", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return productCarts.size();
    }

    public class ShoppingCartViewHolder extends  RecyclerView.ViewHolder{

        TextView tvName;
        TextView tvPrice;
        TextView tvQty;
        TextView tvSubTotal;
        Button buttonRemove;
        Button buttonIncrease;
        Button buttonDecrease;


        public ShoppingCartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = (TextView) itemView.findViewById(R.id.tvProductName);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            tvQty = (TextView) itemView.findViewById(R.id.tvQuantity);
            tvSubTotal = (TextView) itemView.findViewById(R.id.tvSubTotal);
            buttonRemove = (Button) itemView.findViewById(R.id.btnRemove);
            buttonIncrease = (Button) itemView.findViewById(R.id.btnIncrease);
            buttonDecrease = (Button) itemView.findViewById(R.id.btnDecrease);
        }

        public void bind(ProductCart productCart){
            tvName.setText(productCart.getName());
            String temp = productCart.getPrice() +"";
            tvPrice.setText(temp);
            tvQty.setText(productCart.getQty()+"");
            tvSubTotal.setText(productCart.getQty()*productCart.getPrice() +"");

        }

    }

    private void deleteFromCart(ProductCart pc){

        mDatabaseReference.child(pc.getId()).removeValue();

    }

    private void updateQty(ProductCart pc){

        mDatabaseReference.child(pc.getId()).setValue(pc);

    }

}
