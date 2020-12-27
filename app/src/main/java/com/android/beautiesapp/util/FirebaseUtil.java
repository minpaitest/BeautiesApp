package com.android.beautiesapp.util;

import com.android.beautiesapp.data.model.Product;
import com.android.beautiesapp.data.model.ProductCart;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

//import com.firebase.ui.auth.AuthUI;

public class FirebaseUtil {

    public static FirebaseDatabase mFirebaseDatabase;
    public static DatabaseReference mDatabaseReference;
    private static FirebaseUtil firebaseUtil;
    public static FirebaseStorage mStorage;
    public static StorageReference mStorageRef;
    public static ArrayList<Product> products;
    public static ArrayList<ProductCart> productCarts;
    private static final int RC_SIGN_IN = 123;
    private FirebaseUtil(){};
    public static boolean isAdmin;


    public static void openFbReference(String ref) {
        if (firebaseUtil == null) {
            firebaseUtil = new FirebaseUtil();
            mFirebaseDatabase = FirebaseDatabase.getInstance();

            connectStorage();

        }

        products = new ArrayList<Product>();
        productCarts = new ArrayList<ProductCart>();
        mDatabaseReference = mFirebaseDatabase.getReference().child(ref);
    }


    public static void connectStorage() {
        mStorage = FirebaseStorage.getInstance();
        mStorageRef = mStorage.getReference().child("products_pictures");
    }
}
