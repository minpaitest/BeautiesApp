package com.android.beautiesapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.android.beautiesapp.R;
import com.android.beautiesapp.data.model.Product;
import com.android.beautiesapp.util.FirebaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ProductListActivity extends AppCompatActivity {

        private ArrayList<Product> products;
        private FirebaseDatabase mFirebaseDatabase;
        private DatabaseReference mDatabaseReference;
        private ChildEventListener mChildListener;

        @Override
        protected void onCreate(Bundle savedInstanceState){
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_product_list);
            //FirebaseUtil.openFbReference("Products",this);
            FirebaseUtil.openFbReference("Products");

            RecyclerView rvProducts = (RecyclerView) findViewById(R.id.rvFinalPage);
            final ProductAdapter adapter = new ProductAdapter();
            rvProducts.setAdapter(adapter);
            LinearLayoutManager productLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
            rvProducts.setLayoutManager(productLayoutManager);
        }

        public boolean onCreateOptionsMenu(Menu menu){

            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.productlist_activity_menu, menu);
            return true;
        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

            switch(item.getItemId()){

                case R.id.insert_menu:
                    Intent intent = new Intent(this, ProductActivity.class);
                    startActivity(intent);
                    return true;
                case R.id.wishlist_menu:
                    Intent intent2 = new Intent(this, ShoppingCartActivity.class);
                    startActivity(intent2);
                    return true;


            }
        return super.onOptionsItemSelected(item);
    }


}
