package com.example.grocerymanager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class listGrocery extends AppCompatActivity {

    ListView listView;
    TextView link;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_grocery);

        listView = findViewById(R.id.text1);

        final ArrayList<String> name = new ArrayList<>();
        final ArrayList<String> type = new ArrayList<>();
        final ArrayList<String> expiry = new ArrayList<>();
        final ArrayList<String> quantity = new ArrayList<>();
        final ArrayList<String> image = new ArrayList<>();

        final ArrayAdapter adapter = new ArrayAdapter<String>(this,R.layout.list_item,name);
        listView.setAdapter(adapter);

        DatabaseReference ref= FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("grocery_list");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                name.clear();
                type.clear(); expiry.clear();quantity.clear();image.clear();
                for(DataSnapshot dataSnapshot:snapshot.getChildren()){
                    final grocery gro_list=dataSnapshot.getValue(grocery.class);
                    String txt = gro_list.getName();
                    String ty = gro_list.getType();
                    String quan = String.valueOf(gro_list.getQuantity());
                    String exp = gro_list.getDate();
                    String img = gro_list.getImageUri();
                    name.add(txt);
                    type.add(ty);
                    expiry.add(exp);
                    quantity.add(quan);
                    image.add(img);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override

            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent nextpage = new Intent(listGrocery.this,viewGrocery.class);
                nextpage.putExtra("grocery_name", name.get(i));
                nextpage.putExtra("grocery_type", type.get(i));
                nextpage.putExtra("grocery_expiry", expiry.get(i));
                nextpage.putExtra("grocery_quantity", quantity.get(i));
                nextpage.putExtra("image_uri",image.get(i));
                startActivity(nextpage);
            }

        });
    }
}