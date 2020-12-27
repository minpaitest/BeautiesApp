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

import com.android.beautiesapp.R;
import com.android.beautiesapp.data.model.Product;
import com.android.beautiesapp.data.model.ProductCart;
import com.android.beautiesapp.util.FirebaseUtil;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductAdapter  extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder>{

    ArrayList<Product> products;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;
    private ImageView imageProduct;
    private FirebaseAuth mAuth;


    public ProductAdapter(){

        FirebaseUtil.openFbReference("Products");
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        products = FirebaseUtil.products;

        mChildListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                Product product = dataSnapshot.getValue(Product.class);
                Log.d("Product " , product.getName());
                product.setId(dataSnapshot.getKey());
                products.add(product);
                notifyItemInserted(products.size()-1);

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
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        Context context = viewGroup.getContext();
        View itemView = LayoutInflater.from(context).inflate(R.layout.rv_row,viewGroup,false);

        return new ProductViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final ProductViewHolder productViewHolder, int i) {

        final Product product = products.get(i);
        productViewHolder.bind(product);
        int width = Resources.getSystem().getDisplayMetrics().widthPixels;
       //Picasso.get().load(imageUrl).fit().centerInside().into(holder.imageView);

        productViewHolder.buttonAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //int position = i;
                //Log.d("Click",String.valueOf(i));
                //Product selectedProduct = products.get(position);
                ProductCart productCart = new ProductCart();
                productCart.setName(product.getName());
                productCart.setPrice(product.getPrice());
                productCart.setQty(1);
                productCart.setSubTotal(1*product.getPrice());
                productCart.setTotal(1*product.getPrice());
                saveProduct(productCart);

                Intent intent = new Intent(view.getContext(), ShoppingCartActivity.class);
                //intent.putExtra ("Product",product);
                view.getContext().startActivity(intent);
            }
        });

    }

    private void saveProduct(ProductCart pc){

        FirebaseUtil.openFbReference("ProductsCarts");
        mFirebaseDatabase = FirebaseUtil.mFirebaseDatabase;
        mDatabaseReference = FirebaseUtil.mDatabaseReference;
        mDatabaseReference.push().setValue(pc);

    }


    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ProductViewHolder extends  RecyclerView.ViewHolder implements  View.OnClickListener {

        TextView tvProductId;
        TextView tvTitle;
        TextView tvDescription;
        TextView tvPrice;
        Button buttonAddToCart;


        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = (TextView) itemView.findViewById(R.id.tvProductName);
            tvDescription = (TextView) itemView.findViewById(R.id.tvProductDesc);
            tvPrice = (TextView) itemView.findViewById(R.id.tvPrice);
            imageProduct = (ImageView) itemView.findViewById(R.id.prdImage);
            buttonAddToCart = (Button) itemView.findViewById(R.id.btnAddToCart);
            itemView.setOnClickListener(this);
        }

        public void bind(Product product){
            tvTitle.setText(product.getName());
            tvDescription.setText(product.getDescription());
            String temp = product.getPrice() +"";
            tvPrice.setText(temp);
            showImage(product.getPic());

        }

        @Override
        public void onClick(View view) {

            int position = getAdapterPosition();
            Log.d("Click",String.valueOf(position));
            Product selectedProduct = products.get(position);
            Intent intent = new Intent(view.getContext(), ProductActivity.class);
            intent.putExtra ("Product",selectedProduct);
            view.getContext().startActivity(intent);

        }

        private void showImage(String url) {

            if (url != null && url.isEmpty() == false) {

                int width = Resources.getSystem().getDisplayMetrics().widthPixels;
                Picasso.with(imageProduct.getContext())
                        .load(url)
                        .resize(50, 50)
                        .centerCrop()
                        .into(imageProduct);
                //Picasso.get().load(url).fit().centerInside().into(imageProduct);
                /*Picasso.with(imageProduct.getContext())
                        .load(url)
                        .fit()
                        .centerCrop()
                        .into(imageProduct);*/

            }
        }

    }
}
