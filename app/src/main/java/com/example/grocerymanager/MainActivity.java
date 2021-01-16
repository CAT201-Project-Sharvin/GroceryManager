package com.example.grocerymanager;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.IpSecManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    RecyclerView cardsRecycler;
    RecyclerView expiryRecycler;
    tutorialCardsAdapter TutorialCardAdapter;
    expGroceryAdapter ExpGroceryAdapter;
    List<Tutorials> tutorialsList;
    List<grocery> expiredItems;
    grocery GroceryItems;
    ArrayList<String> Steps;
    String stepsURL [] ={"how-to-beat-egg-whites", "how-to-cook-basmati-rice",
            "how-to-cook-pasta", "how-to-cook-salmon", "how-to-fry-a-steak",
            "how-to-peel-and-deveine-a-prawn", "how-to-poach-an-egg",
            "how-to-prepare-an-avocado", "how-to-remove-tomato-skin",
            "how-to-zest-a-lemon"};
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    View headerView;
    DatabaseReference ref;
    FirebaseAuth fAuth;
    ImageView registerBtn, addBtn, scanBtn, deleteBtn, viewBtn, recipeBtn;
    TextView userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = findViewById(R.id.drawer_toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawerLayout);

        navigationView = findViewById(R.id.navi_view);
        headerView = navigationView.getHeaderView(0);
        addBtn = findViewById(R.id.addBtn);
        viewBtn = findViewById(R.id.viewBtn);
        recipeBtn = findViewById(R.id.recipeBtn);
        userName = headerView.findViewById(R.id.userName);
        fAuth = FirebaseAuth.getInstance();
        ref = FirebaseDatabase.getInstance().getReference(fAuth.getCurrentUser().getUid());

       ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Log.d("username", snapshot.child("userName").getValue().toString());
                String name = snapshot.child("userName").getValue().toString();
                userName.setText(name);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                R.string.openNavDrawer,
                R.string.closeNavDrawer
        );

        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        loadExpItems();
        loadCards();



        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), addGrocery.class));
            }
        });

        viewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), listGrocery.class));
            }
        });

        recipeBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), Recipe.class));
            }
        });
    }

    public void btnClick(View view) {
        FirebaseAuth.getInstance().signOut();
        startActivity(new Intent(getApplicationContext(), Login.class));
    }

    public void loadCards(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    tutorialsList = new ArrayList<>();
                    for(int i = 0; i < 10; i++) {
                        String url = "https://spoonacular.com/academy/" + stepsURL[i];
                        Log.d("URLs", url);
                        org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
                        //String title = doc.select("div.academyLesson").select("h1").text();
                        Elements data = doc.select("div.academyLesson");
                        String articleTitle = data.select("h1").text();
                        Log.d("Title", articleTitle);
                        String shortDesc = data.select("div.row g").select("ol.steps").select("li").text();
                        Elements steps = data.select("div.column.postBody").select("ol.steps");
                        String imageUrl = data.select("div.column.postBody").select("iframe").attr("src").toString();

                        int numSteps = steps.size();
                        int totalSteps = (steps.select("li")).size();
                        Log.d("Number of items", String.valueOf(numSteps));
                        Log.d("total steps", String.valueOf(totalSteps));
                        Steps = new ArrayList<>();
                        for (int j = 0; j < totalSteps; j++) {
                            Steps.add(steps.select("li").eq(j).text());
                            Log.d("Steps", steps.select("li").eq(j).text());
                        }
                        tutorialsList.add(new Tutorials(articleTitle, shortDesc, imageUrl, Steps));

                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            setCardsRecycler(tutorialsList);
                        }
                    });
                    //adapter.notifyDataSetChanged();

                } catch (IOException e) {
                    e.printStackTrace();
                    Log.d("items", "hello");
                }
            }
        }){}.start();
    }

    public void loadExpItems(){
        DatabaseReference ref= FirebaseDatabase.getInstance().getReference(FirebaseAuth.getInstance().getCurrentUser().getUid()).child("grocery_list");
        ref.addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SimpleDateFormat dateStructure = new SimpleDateFormat("dd/MM/yyyy");

                new Thread(new Runnable() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void run() {
                        try {
                            expiredItems = new ArrayList<>();
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                                final grocery gro_list = dataSnapshot.getValue(grocery.class);
                                LocalDate date = LocalDate.now();
                                String cDate = date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
                                String oDate = gro_list.getDate();
                                Date dateObj1 = dateStructure.parse(cDate);
                                Date dateObj2 = dateStructure.parse(oDate);
                                long diff = dateObj2.getTime() - dateObj1.getTime();
                                int diffDays = (int) (diff/(24 * 60 * 60 * 1000));
                                if (diffDays<= 3) {
                                    String txt = gro_list.getName();
                                    String ty = gro_list.getType();
                                    String quan = String.valueOf(gro_list.getQuantity());
                                    String exp = gro_list.getDate();
                                    String img = gro_list.getImageUri();
                                    //GroceryItems = new grocery(txt, ty, Integer.valueOf(quan), exp, img);
                                    expiredItems.add(gro_list);
                                } else {
                                    continue;
                                }

                            }

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        if (!expiredItems.isEmpty()) {
                                            setExpiryRecycler(expiredItems);
                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        } catch (NumberFormatException | ParseException e) {
                            e.printStackTrace();
                        }
                    }
                }){}.start();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
    public void setCardsRecycler(List<Tutorials> tutorialsList){
        cardsRecycler = findViewById(R.id.cards_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        cardsRecycler.setLayoutManager(layoutManager);
        TutorialCardAdapter = new tutorialCardsAdapter(this,tutorialsList);
        cardsRecycler.setAdapter(TutorialCardAdapter);
    }

    public void setExpiryRecycler(List<grocery> expiredItems){
        expiryRecycler = findViewById(R.id.expiry_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        expiryRecycler.setLayoutManager(layoutManager);
        ExpGroceryAdapter = new expGroceryAdapter(this,expiredItems);
        expiryRecycler.setAdapter(ExpGroceryAdapter);
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

       switch(item.getItemId()){
           case R.id.adding_items:
               startActivity(new Intent(getApplicationContext(), addGrocery.class));
               break;
           case R.id.viewing_items:
               startActivity(new Intent(getApplicationContext(), listGrocery.class));
               break;
           case R.id.log_out:
               FirebaseAuth.getInstance().signOut();
               startActivity(new Intent(getApplicationContext(), Login.class));
               break;

       }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}




