package com.example.grocerymanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.util.Calendar;
import java.util.HashMap;

public class addGrocery extends AppCompatActivity {
    EditText grocery_name;
    EditText type;
    EditText quantity;
    Button store;
    ImageView imageView;
    TextView expdate;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    StorageReference nStorageRef;
    public Uri imguri;
    private StorageTask uploadTask;

    String TAG = "MainActivity" ;

    DatePickerDialog.OnDateSetListener date;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_grocery);

        expdate = (TextView) findViewById(R.id.et_expdate);
        grocery_name = findViewById(R.id.et_grocery_name);
        type = findViewById(R.id.et_type);
        quantity=findViewById(R.id.et_quantity);
        store = findViewById(R.id.et_store);
        imageView = findViewById(R.id.image_view);

        nStorageRef= FirebaseStorage.getInstance().getReference("Images");

        imageView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Filechooser();
            }
        });

        expdate.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(addGrocery.this,
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
                expdate.setText(date);
            }
        };

        store.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View view)
            {
                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("grocery_list");

                String name = grocery_name.getLayout().getText().toString();
                String types = type.getLayout().getText().toString();
                Integer quantities = Integer.valueOf(quantity.getLayout().getText().toString());
                String exp_date = expdate.getText().toString();

                StorageReference Ref=nStorageRef.child(System.currentTimeMillis()+"," + getExtension(imguri));
                uploadTask=Ref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri uri) {
                                String image = String.valueOf(uri);
                                grocery helperClass = new grocery(name,types,quantities,exp_date,image);
                                reference.child(name).setValue(helperClass);
                            }
                        });
                    }
                });

                Intent nextpage = new Intent(addGrocery.this,listGrocery.class);
                startActivity(nextpage);
            }
        });
    }

    private String getExtension(Uri uri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType((cr.getType(uri)));
    }


    private void Filechooser(){
        Intent intent=new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,100);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imguri=data.getData();
            imageView.setImageURI(imguri);
        }
    }
}