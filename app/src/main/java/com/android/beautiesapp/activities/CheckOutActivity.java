package com.android.beautiesapp.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;

import com.android.beautiesapp.R;
import com.android.beautiesapp.data.model.ProductCart;
import com.android.beautiesapp.util.FirebaseUtil;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CheckOutActivity extends AppCompatActivity {

    private ArrayList<ProductCart> productCarts;
    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chekcout);
        //FirebaseUtil.openFbReference("Products",this);
        FirebaseUtil.openFbReference("ProductsCarts");

        RecyclerView rvProducts = (RecyclerView) findViewById(R.id.rvFinalPage);
        final CheckOutAdapter adapter = new CheckOutAdapter();
        rvProducts.setAdapter(adapter);
        LinearLayoutManager checkoutLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false);
        rvProducts.setLayoutManager(checkoutLayoutManager);
    }


}
