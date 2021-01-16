package com.example.grocerymanager;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.ContentResolver;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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

import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.HashMap;
import java.util.UUID;

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
    public Bitmap imguri2;
    private StorageTask uploadTask;

    String TAG = "MainActivity" ;

    Integer uptake = 0;

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

                AlertDialog.Builder askUser = new AlertDialog.Builder(addGrocery.this);
                askUser.setCancelable(false).setPositiveButton("Upload Picture", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        Filechooser();
                    }
                }).setNegativeButton("Take Picture", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        takeimage();
                    }
                });
                askUser.show();
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

                if (uptake == 1)
                {
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
                }
                else if (uptake == 2)
                {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    imguri2.compress(Bitmap.CompressFormat.JPEG,100,baos);

                    final String URL = UUID.randomUUID().toString();
                    StorageReference imageRef = nStorageRef.child(URL);

                    byte[] b =baos.toByteArray();
                    imageRef.putBytes(b).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            taskSnapshot.getMetadata().getReference().getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String image = String.valueOf(uri);
                                    grocery helperClass = new grocery(name,types,quantities,exp_date,image);
                                    reference.child(name).setValue(helperClass);
                                }
                            });
                        }
                    });

                }


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

    private void takeimage(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,200);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode==RESULT_OK && data!=null && data.getData()!=null)
        {
            imguri=data.getData();
            imageView.setImageURI(imguri);
            uptake = 1;
        }
        else if(requestCode == 200 && resultCode==RESULT_OK)
        {
            imguri2=(Bitmap) data.getExtras().get("data");
            imageView.setImageBitmap(imguri2);
            uptake = 2;
        }
    }
}