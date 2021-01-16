package com.example.grocerymanager;

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

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
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
import java.util.UUID;

public class updateImageGrocery extends AppCompatActivity {

    EditText et_grocery_name;
    EditText et_type;
    EditText et_quantity;
    ImageView imageView;
    TextView et_expdate;

    Button cancel, update;

    FirebaseDatabase rootNode;
    DatabaseReference reference;

    StorageReference nStorageRef;
    public Uri imguri;
    public Bitmap imguri2;
    private StorageTask uploadTask;

    String TAG = "MainActivity";

    Integer uptake;

    DatePickerDialog.OnDateSetListener date;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_image_grocery);

        et_expdate = (TextView) findViewById(R.id.et_expdate);
        et_grocery_name = findViewById(R.id.et_grocery_name);
        et_type = findViewById(R.id.et_type);
        et_quantity = findViewById(R.id.et_quantity);
        imageView = findViewById(R.id.image_view);

        update = findViewById(R.id.et_update);
        cancel = findViewById(R.id.et_cancel);

        nStorageRef = FirebaseStorage.getInstance().getReference("Images");

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

        open();

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("grocery_list");

                String edited_name = et_grocery_name.getLayout().getText().toString();
                String edited_type = et_type.getLayout().getText().toString();
                Integer edited_quantity = Integer.valueOf(et_quantity.getLayout().getText().toString());
                String edited_date = et_expdate.getText().toString();
                String edited_image = image;

                DatabaseReference data = FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("grocery_list").child(name);
                data.removeValue();

                StorageReference deleteimage = FirebaseStorage.getInstance().getReferenceFromUrl(image);
                deleteimage.delete();

                if (uptake == 1)
                {
                    StorageReference Ref = nStorageRef.child(System.currentTimeMillis() + "," + getExtension(imguri));
                    uploadTask = Ref.putFile(imguri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String newimage = String.valueOf(uri);
                                    grocery helperClass = new grocery(edited_name, edited_type, edited_quantity, edited_date, newimage);
                                    reference.child(edited_name).setValue(helperClass);

                                    Intent nextpage = new Intent(updateImageGrocery.this, viewGrocery.class);
                                    nextpage.putExtra("grocery_name", edited_name);
                                    nextpage.putExtra("grocery_type", edited_type);
                                    nextpage.putExtra("grocery_expiry", edited_date);
                                    nextpage.putExtra("grocery_quantity", String.valueOf(edited_quantity));
                                    nextpage.putExtra("image_uri", newimage);
                                    startActivity(nextpage);
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
                                    String newimage = String.valueOf(uri);
                                    grocery helperClass = new grocery(edited_name, edited_type, edited_quantity, edited_date, newimage);
                                    reference.child(name).setValue(helperClass);

                                    Intent nextpage = new Intent(updateImageGrocery.this, viewGrocery.class);
                                    nextpage.putExtra("grocery_name", edited_name);
                                    nextpage.putExtra("grocery_type", edited_type);
                                    nextpage.putExtra("grocery_expiry", edited_date);
                                    nextpage.putExtra("grocery_quantity", String.valueOf(edited_quantity));
                                    nextpage.putExtra("image_uri", newimage);
                                    startActivity(nextpage);
                                }
                            });
                        }
                    });
                }


            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextpage = new Intent(updateImageGrocery.this, viewGrocery.class);
                nextpage.putExtra("grocery_name", name);
                nextpage.putExtra("grocery_type", type);
                nextpage.putExtra("grocery_expiry", expiry);
                nextpage.putExtra("grocery_quantity", quantity);
                nextpage.putExtra("image_uri", image);
                startActivity(nextpage);
            }
        });

        et_expdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int day = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog(updateImageGrocery.this,
                        android.R.style.Theme_Holo_Dialog_MinWidth,
                        date, year, month, day);

                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();
            }
        });

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month += 1;
                Log.d(TAG, "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                String date = day + "/" + month + "/" + year;
                et_expdate.setText(date);
            }
        };
    }

    private String getExtension(Uri uri) {
        ContentResolver cr = getContentResolver();
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType((cr.getType(uri)));
    }


    private void Filechooser() {
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, 100);
    }

    private void takeimage(){
        Intent intent=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent,200);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK && data != null && data.getData() != null) {
            imguri = data.getData();
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

    public void open(){
        AlertDialog.Builder askUser = new AlertDialog.Builder(updateImageGrocery.this);
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
}