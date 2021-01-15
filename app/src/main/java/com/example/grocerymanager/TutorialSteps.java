package com.example.grocerymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.google.firebase.database.annotations.NotNull;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class TutorialSteps extends AppCompatActivity {

    RecyclerView tutorialRecycler;
    tutorialStepsAdapter TutorialStepsAdapter;
    ArrayList<String> Steps;
    String vID;
    YouTubePlayerView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_steps);
        videoView = findViewById(R.id.videoView);
        getLifecycle().addObserver(videoView);
        if (getIntent().hasExtra("image_url") && getIntent().hasExtra("youtube_link")) {
            Steps = getIntent().getStringArrayListExtra("image_url");
            vID = getIntent().getStringExtra("youtube_link");
            Log.d("link", vID);
            Log.d("StepsTuto", String.valueOf(Steps.size()));
            videoView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                @Override
                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                    super.onReady(youTubePlayer);
                    youTubePlayer.loadVideo(vID, 0);
                }
            });
            //setImage(imageUrl, imageName);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    setPopularRecycler(Steps);
                }
            });

        }
    }

    private void setPopularRecycler(ArrayList<String> Steps) {
        tutorialRecycler=findViewById(R.id.tutorial_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL,false);
        tutorialRecycler.setLayoutManager(layoutManager);
        //problem3
        //PopularFoodAdapter= new popularFoodAdapter(this,popularFoodList);
        TutorialStepsAdapter = new tutorialStepsAdapter(this, Steps);
        tutorialRecycler.setAdapter(TutorialStepsAdapter);

    }
}