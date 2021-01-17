package com.example.grocerymanager;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.database.annotations.NotNull;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import java.util.ArrayList;

public class TutorialSteps extends AppCompatActivity {

    private RecyclerView tutorialRecycler;
    private tutorialStepsAdapter TutorialStepsAdapter;
    private ArrayList<String> Steps;
    private String vID, vTitle, vDesc;
    private YouTubePlayerView videoView;
    private TextView title, desc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tutorial_steps);
        videoView = findViewById(R.id.videoView);
        title = findViewById(R.id.videoTitle);
        //desc = findViewById(R.id.desc);
        getLifecycle().addObserver(videoView);
        if (getIntent().hasExtra("image_url") && getIntent().hasExtra("youtube_link") && getIntent().hasExtra("title") && getIntent().hasExtra("short_desc")) {
            Steps = getIntent().getStringArrayListExtra("image_url");
            vID = getIntent().getStringExtra("youtube_link");
            vTitle = getIntent().getStringExtra("title");
            //vDesc = getIntent().getStringExtra("short_desc");
            title.setText(vTitle);
            //desc.setText(vDesc);
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