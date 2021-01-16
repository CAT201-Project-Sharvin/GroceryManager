package com.example.grocerymanager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class viewGrocery extends AppCompatActivity {

    Button update,delete;
    TextView name1,type1,quantity1,date1;
    ImageView groceryImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_grocery);

        name1 = findViewById(R.id.ViewName);
        type1 = findViewById(R.id.ViewType);
        quantity1 = findViewById(R.id.ViewQuantity);
        date1 = findViewById(R.id.ViewDate);
        update = findViewById(R.id.update);
        delete=findViewById(R.id.delete);
        groceryImage=findViewById(R.id.image_view);

        final String image = getIntent().getStringExtra("image_uri");
        final String name = getIntent().getStringExtra("grocery_name");
        final String type = getIntent().getStringExtra("grocery_type");
        final String expiry = getIntent().getStringExtra("grocery_expiry");
        final String quantity = getIntent().getStringExtra("grocery_quantity");

        name1.setText(name);
        type1.setText(type);
        quantity1.setText(quantity);
        date1.setText(expiry);
        Glide.with(this).load(image).into(groceryImage);

        update.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view)
            {
               Intent nextpage = new Intent(viewGrocery.this,updateGrocery.class);
                nextpage.putExtra("grocery_name", name);
                nextpage.putExtra("grocery_type", type);
                nextpage.putExtra("grocery_expiry", expiry);
                nextpage.putExtra("grocery_quantity", quantity);
                nextpage.putExtra("image_uri",image);
               startActivity(nextpage);
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view)
            {
                DatabaseReference data = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("grocery_list").child(name);
                data.removeValue();

                StorageReference deleteimage = FirebaseStorage.getInstance().getReferenceFromUrl(image);
                deleteimage.delete();

                Intent nextpage = new Intent(viewGrocery.this,listGrocery.class);
                startActivity(nextpage);
            }
        });
    }
}