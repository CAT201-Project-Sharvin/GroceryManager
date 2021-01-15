package com.example.grocerymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    RecyclerView cardsRecycler;
    tutorialCardsAdapter TutorialCardAdapter;
    List<Tutorials> tutorialsList;
    ArrayList<String> Steps;
    String stepsURL [] ={"how-to-beat-egg-whites", "how-to-cook-basmati-rice",
            "how-to-cook-pasta", "how-to-cook-salmon", "how-to-fry-a-steak",
            "how-to-peel-and-deveine-a-prawn", "how-to-poach-an-egg",
            "how-to-prepare-an-avocado", "how-to-remove-tomato-skin",
            "how-to-zest-a-lemon"};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        loadCards();
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
                        //Log.d("Title", title);
                        //popularFoodList = new ArrayList<>();
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

    public void setCardsRecycler(List<Tutorials> tutorialsList){
        cardsRecycler = findViewById(R.id.cards_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL,false);
        cardsRecycler.setLayoutManager(layoutManager);
        //problem3
        TutorialCardAdapter = new tutorialCardsAdapter(this,tutorialsList);
        cardsRecycler.setAdapter(TutorialCardAdapter);
    }
}




