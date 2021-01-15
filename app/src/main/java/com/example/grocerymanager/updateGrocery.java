package com.example.grocerymanager;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;

public class updateGrocery extends AppCompatActivity {

    EditText et_grocery_name;
    EditText et_type;
    EditText et_quantity;
    ImageView imageView;
    TextView et_expdate;

    Button cancel,update;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    String TAG = "MainActivity";
    DatePickerDialog.OnDateSetListener date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_grocery);

        et_expdate = (TextView) findViewById(R.id.et_expdate);
        et_grocery_name = findViewById(R.id.et_grocery_name);
        et_type = findViewById(R.id.et_type);
        et_quantity=findViewById(R.id.et_quantity);
        imageView = findViewById(R.id.image_view);

        update = findViewById(R.id.et_update);
        cancel = findViewById(R.id.et_cancel);


        final String image = getIntent().getStringExtra("image_uri");
        final String name = getIntent().getStringExtra("grocery_name");
        final String type = getIntent().getStringExtra("grocery_type");
        final String expiry = getIntent().getStringExtra("grocery_expiry");
        final String quantity = getIntent().getStringExtra("grocery_quantity");

        Glide.with(this).load(image).into(imageView);
        et_grocery_name.setText(name);
        et_type.setText(type);
        et_quantity.setText(quantity);
        et_expdate.setText(expiry);

        update.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view)
            {
                DatabaseReference data = FirebaseDatabase.getInstance().getReference("grocery_list").child(name);
                data.removeValue();



                String edited_name = et_grocery_name.getLayout().getText().toString();
                String edited_type = et_type.getLayout().getText().toString();
                Integer edited_quantity = Integer.valueOf(et_quantity.getLayout().getText().toString());
                String edited_date = et_expdate.getText().toString();

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("grocery_list");

                grocery helperClass = new grocery(edited_name,edited_type,edited_quantity,edited_date,image);
                reference.child(name).setValue(helperClass);

                Intent nextpage = new Intent(updateGrocery.this,viewGrocery.class);
                nextpage.putExtra("grocery_name", edited_name);
                nextpage.putExtra("grocery_type", edited_type);
                nextpage.putExtra("grocery_expiry", edited_date);
                nextpage.putExtra("grocery_quantity", edited_quantity);
                nextpage.putExtra("image_uri",image);
                startActivity(nextpage);
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view)
            {
                Intent nextpage = new Intent(updateGrocery.this,viewGrocery.class);
                nextpage.putExtra("grocery_name", name);
                nextpage.putExtra("grocery_type", type);
                nextpage.putExtra("grocery_expiry", expiry);
                nextpage.putExtra("grocery_quantity", quantity);
                nextpage.putExtra("image_uri",image);
                startActivity(nextpage);
            }
        });

        et_expdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(updateGrocery.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        date, year,month,day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        date=new DatePickerDialog.OnDateSetListener(){
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day){
                month += 1;
                Log.d(TAG,"onDateSet: mm/dd/yyy: "+ month + "/" + day + "/" +year);
                String date = day + "/" + month + "/" + year;
                et_expdate.setText(date);
            }
        };
    }
}