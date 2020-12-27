package com.android.beautiesapp.activities;

import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.beautiesapp.R;
import com.android.beautiesapp.data.model.Product;
import com.android.beautiesapp.util.FirebaseUtil;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference mDatabaseReference;
    private ChildEventListener mChildListener;
    private static final int PICTURE_RESULT = 42;
    private FirebaseAuth mAuth;
    public static final String TAG = "";
    EditText tvProductName ;
    EditText tvProductDesc ;
    EditText tvProductPrice ;
    ImageView imageView;
    Product product;


    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        mFirebaseDatabase = FirebaseDatabase.getInstance();
        mDatabaseReference = mFirebaseDatabase.getReference().child("Products");

        //mAuth = FirebaseAuth.getInstance();
        tvProductName = (EditText)findViewById(R.id.tvProductName);
        tvProductDesc = (EditText)findViewById(R.id.txtProductDesc);
        tvProductPrice = (EditText)findViewById(R.id.txtPrice);
        imageView = (ImageView) findViewById(R.id.productImage);

        Intent intent = getIntent();
        Product product = (Product) intent.getSerializableExtra("Product");

        if(product == null){
            product = new Product();
        }

        this.product = product;
        tvProductName.setText(product.getName());
        tvProductDesc.setText(product.getDescription());
        tvProductPrice.setText(product.getPrice()+"");

        showImage(product.getPic());

        Button btnImage = findViewById(R.id.btnImage);
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("image/jpeg");
                intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
                startActivityForResult(intent.createChooser(intent,
                        "Insert Picture"), PICTURE_RESULT);
            }
        });


    }

    public boolean onCreateOptionsMenu(Menu menu){

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.save_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch(item.getItemId()){

            case R.id.save_menu:
                saveProduct();
                Toast.makeText(this,"Product saved",Toast.LENGTH_LONG).show();
                clean();
                backToList();
                return true;
            case R.id.delete_menu:
                deleteProduct();
                Toast.makeText(this,"Product Deleted",Toast.LENGTH_LONG).show();
                backToList();
            default :
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean doStringMatch(String password, String password1) {

        return password.equals(password1);
    }

    private boolean isEmpty(String email) {
        return email.equals("");
    }

    private void saveProduct(){

        product.setName(tvProductName.getText().toString());
        product.setDescription(tvProductDesc.getText().toString());
        product.setPrice(Double.parseDouble(tvProductPrice.getText().toString()));

        if(product.getId()==null){
            mDatabaseReference.push().setValue(product);
        }else{
            mDatabaseReference.child(product.getId()).setValue(product);
        }

    }

    private void clean(){

        tvProductName.setText("");
        tvProductDesc.setText("");
        tvProductPrice.setText("");
    }

    private void backToList(){

        Intent intent =  new Intent(this, ProductListActivity.class);
        startActivity(intent);

    }

    private void deleteProduct(){
        if(product == null){
            Toast.makeText(this, "Plese save the product before deleting.", Toast.LENGTH_LONG).show();
            return;
        }

        mDatabaseReference.child(product.getId()).removeValue();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //if (requestCode == PICTURE_RESULT && resultCode == RESULT_OK) {
        if (requestCode == PICTURE_RESULT) {
            Uri imageUri = data.getData();

            StorageReference ref = FirebaseUtil.mStorageRef.child(imageUri.getLastPathSegment());
            Log.d( " gettingref...", ref.toString());

            String temp = ref.getDownloadUrl().toString();

            Log.d( " path ", temp);

            //UploadTask uploadTask = ref.putFile(file);


            ref.putFile(imageUri).addOnSuccessListener(this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //String url = taskSnapshot.getUploadSessionUri().toString();
                    /*String url = taskSnapshot.getStorage().getDownloadUrl().toString();
                    product.setPic(url);
                    showImage(url);*/

                    String url = taskSnapshot.getUploadSessionUri().toString();
                    String pictureName = taskSnapshot.getStorage().getPath();

                    String temp = taskSnapshot.getStorage().getDownloadUrl().toString();

                    Log.d(" storageRef ...." ,FirebaseUtil.mStorageRef.child("Products").toString());

                    String route = FirebaseUtil.mStorageRef.child("/"+pictureName+".jpeg").toString();

                    Log.d(" route ..." , route);

                    String newUrl = FirebaseUtil.mStorageRef.child("/"+pictureName+".jpeg").getDownloadUrl().toString();

                    Log.d(" new url ..." , newUrl);
                    Log.d("temp ..." , temp);
                    product.setPic(url);
                    showImage(url);
                }


            });
        }
    }

    private void showImage(String url){


        StorageReference ref = FirebaseUtil.mStorageRef.child("20.jpeg");

        Log.d("ref " , ref.toString());

        url = ref.getDownloadUrl().toString();

        if(url  != null && url.isEmpty()== false){

            int width = Resources.getSystem().getDisplayMetrics().widthPixels;
            Picasso.with(this)
                    .load(url)
                    .resize(50,50)
                    .centerCrop()
                    .into(imageView);
            //Picasso.get().load(url).fit().centerInside().into(imageView);
        }

    }
}
