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
import com.android.beautiesapp.data.model.ProductCart;
import com.android.beautiesapp.util.FirebaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ShoppingCartActivity extends AppCompatActivity {

    private ArrayList<ProductCart> productCarts;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);
        //FirebaseUtil.openFbReference("Products",this);
        FirebaseUtil.openFbReference("ProductsCarts");

        RecyclerView rvProductsInCart = (RecyclerView) findViewById(R.id.rvFinalPage);
        final ShoppingCartAdapter adapter = new ShoppingCartAdapter();
        rvProductsInCart.setAdapter(adapter);
        LinearLayoutManager productLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rvProductsInCart.setLayoutManager(productLayoutManager);
    }

    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.shopping_cart, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.checkout:
                Intent intent = new Intent(this, CheckOutActivity.class);
                startActivity(intent);
                return true;
            /*case R.id.productList:
                Intent productIntent = new Intent(this, ProductListActivity.class);
                startActivity(productIntent);
                return true;*/

        }
        return super.onOptionsItemSelected(item);
    }

}
