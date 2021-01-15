package com.example.grocerymanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
public class Recipe extends AppCompatActivity {

    RecyclerView popularRecycler, breakfastRecycler, lunchRecycler, dinnerRecycler;
    popularFoodAdapter PopularFoodAdapter;
    BreakfastFoodAdapter breakfastFoodAdapter;
    LunchFoodAdapter lunchFoodAdapter;
    DinnerFoodAdapter dinnerFoodAdapter;
    String searchCharacter = "chicken";
    String breakfastMain = "https://api.spoonacular.com/recipes/complexSearch?query=meat&type=breakfast&number=10&apiKey=fde780bf140e4dbbaefd90ab69e3d459";
    String randomRecipe = "https://api.spoonacular.com/recipes/complexSearch?query=";
    String apiKey = "&apiKey=fde780bf140e4dbbaefd90ab69e3d459";
    String type, query, number;
    List<PopularFoods> popularFoodList;
    List<BreakfastFood> breakfastFoodList;
    List<LunchFood> lunchFoodList;
    List<DinnerFood> dinnerFoodList;


    protected void parsePopular(JSONArray result) throws JSONException {

        popularFoodList = new ArrayList<>();
        for (int i = 0; i < result.length(); i++) {
            Log.v("tittle", "settlejjjjjj");
            JSONObject recipe = result.getJSONObject(i);
            String tittle = recipe.getString("title");
            Log.v("tittle", tittle);
            String imageLink = recipe.getString("image");
            String id = recipe.getString("id");
            Log.v("image", imageLink);
            popularFoodList.add(new PopularFoods(tittle, "4.57", imageLink, id));
        }
    }

    protected void parseBreakfast(JSONArray result) throws JSONException{
        breakfastFoodList = new ArrayList<>();
        for (int i = 0; i < result.length(); i++) {
            Log.v("tittle", "settlejjjjjj");
            JSONObject recipe = result.getJSONObject(i);
            String tittle = recipe.getString("title");
            Log.v("tittle", tittle);
            String imageLink = recipe.getString("image");
            Log.v("image", imageLink);
            String id = recipe.getString("id");
            Log.v("breakfast", "Lepas ke belum");
            breakfastFoodList.add(new BreakfastFood(tittle, "4.5", imageLink, id));
            //breakfastFoodList.add(new BreakfastFood("title","2.0",imageLink));
            Log.v("breakfast", "Lepas dah add breakfast");
        }
    }

    protected void parseLunch(JSONArray result) throws JSONException{
        lunchFoodList = new ArrayList<>();
        for (int i = 0; i <  result.length(); i++) {
            Log.v("tittle", "masuk lunch");
            JSONObject recipe = result.getJSONObject(i);
            String tittle = recipe.getString("title");
            Log.v("tittle", tittle);
            String imageLink = recipe.getString("image");
            Log.v("image", imageLink);
            String id = recipe.getString("id");
            Log.v("breakfast", "Lepas ke belum");
            lunchFoodList.add(new LunchFood(tittle, "4.5", imageLink,id));
            //breakfastFoodList.add(new BreakfastFood("title","2.0",imageLink));
            Log.v("breakfast", "Lepas dah add lunch");
        }

    }

    protected  void parseDinner(JSONArray result) throws JSONException{
        dinnerFoodList = new ArrayList<>();
        for (int i = 0; i <result.length(); i++) {
            Log.v("tittle", "masuk dinner");
            JSONObject recipe = result.getJSONObject(i);
            String tittle = recipe.getString("title");
            Log.v("tittle", tittle);
            String imageLink = recipe.getString("image");
            Log.v("image", imageLink);
            String id = recipe.getString("id");
            Log.v("breakfast", "Lepas ke belum");
            dinnerFoodList.add(new DinnerFood(tittle, "4.5", imageLink,id));
            //breakfastFoodList.add(new BreakfastFood("title","2.0",imageLink));
            Log.v("breakfast", "Lepas dah add dinner");
        }
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);

        popularFoodList = new ArrayList<>();
        breakfastFoodList = new ArrayList<>();
        lunchFoodList = new ArrayList<>();
        dinnerFoodList = new ArrayList<>();



        Log.v("tittle", "toyaaaaa");

        query="meat";
        type="popular";
        number="10";
        String fullApi = randomRecipe +query+"&type="+type+"&number="+number+ apiKey;
        //popular food
        try {
            requestPopular(fullApi);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //problem 1 minute 23:09
    private void setPopularRecycler(List<PopularFoods> popularFoodList) {
        popularRecycler = findViewById(R.id.popular_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        popularRecycler.setLayoutManager(layoutManager);
        //problem3
        PopularFoodAdapter = new popularFoodAdapter(this, popularFoodList);
        popularRecycler.setAdapter(PopularFoodAdapter);

    }

    private void setBreakfastRecycler(List<BreakfastFood> breakfastFoodList) {
        breakfastRecycler = findViewById(R.id.breakfast_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        breakfastRecycler.setLayoutManager(layoutManager);
        //problem3
        breakfastFoodAdapter = new BreakfastFoodAdapter(this, breakfastFoodList);
        breakfastRecycler.setAdapter(breakfastFoodAdapter);

    }

    private void setLunchRecycler(List<LunchFood> lunchFoodList) {
        lunchRecycler = findViewById(R.id.lunch_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        lunchRecycler.setLayoutManager(layoutManager);
        //problem3
        lunchFoodAdapter = new LunchFoodAdapter(this, lunchFoodList);
        lunchRecycler.setAdapter(lunchFoodAdapter);
        int size = lunchFoodList.size();
        int lol = size;


    }

    private void setDinnerRecycler(List<DinnerFood> dinnerFoodList) {
        dinnerRecycler = findViewById(R.id.dinner_recycler);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        dinnerRecycler.setLayoutManager(layoutManager);
        //problem3
        dinnerFoodAdapter = new DinnerFoodAdapter(this, dinnerFoodList);
        dinnerRecycler.setAdapter(dinnerFoodAdapter);
    }

    //Internet Access Permission
    private final OkHttpClient client = new OkHttpClient();

    public void requestPopular(final String fullApi) throws Exception {

        Request request = new Request.Builder()
                .url(fullApi)
                .build();

        client.newCall(request).enqueue(new Callback() {
            //Respone if fail requet
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Context context = getApplicationContext();
                CharSequence text = "Cari API lain siot";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            //Response ada tapi tak semsstinya content tu ada (error404)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("tittle", "MAsuk blum");

                try (ResponseBody responseBody = response.body()) {
                    {
                        Log.v("tittle", "settle");
                        String responseString = responseBody.string();
                        //JSON parsing
                        try {
                            JSONObject reader = new JSONObject(responseString);
                            JSONArray result = reader.getJSONArray("results");
                            Log.v("response", responseString);
                            parsePopular(result);
                            query="cereal";
                            type="breakfast";
                            number="3";
                            String fullApi = randomRecipe +query+"&type="+type+"&number="+number+ apiKey;
                            try {
                                requestBreakfast(fullApi);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                }
            }
        });
    }

    public void requestBreakfast(final String fullApi) throws Exception {

        Request request = new Request.Builder()
                .url(fullApi)
                .build();

        client.newCall(request).enqueue(new Callback() {
            //Respone if fail requet
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Context context = getApplicationContext();
                CharSequence text = "Cari API lain siot";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            //Response ada tapi tak semsstinya content tu ada (error404)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("tittle", "MAsuk blum");

                try (ResponseBody responseBody = response.body()) {
                    {
                        Log.v("tittle", "settle");
                        String responseString = responseBody.string();
                        //JSON parsing
                        try {
                            JSONObject reader = new JSONObject(responseString);
                            JSONArray result = reader.getJSONArray("results");
                            Log.v("response", responseString);
                            parseBreakfast(result);
                            query="meat";
                            type="lunch";
                            number="3";
                            String fullApi = randomRecipe +query+"&type="+type+"&number="+number+ apiKey;
                            try {
                                requestLunch(fullApi);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                }
            }
        });
    }

    public void requestLunch(final String fullApi) throws Exception {

        Request request = new Request.Builder()
                .url(fullApi)
                .build();

        client.newCall(request).enqueue(new Callback() {
            //Respone if fail requet
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Context context = getApplicationContext();
                CharSequence text = "Cari API lain siot";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            //Response ada tapi tak semsstinya content tu ada (error404)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("tittle", "MAsuk blum");

                try (ResponseBody responseBody = response.body()) {
                    {
                        Log.v("tittle", "settle");
                        String responseString = responseBody.string();
                        //JSON parsing
                        try {
                            JSONObject reader = new JSONObject(responseString);
                            JSONArray result = reader.getJSONArray("results");
                            Log.v("response", responseString);

                            parseLunch(result);

                            query="chicken";
                            type="dinner";
                            number="3";
                            String fullApi = randomRecipe +query+"&type="+type+"&number="+number+ apiKey;


                            try {
                                requestDinner(fullApi);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                }
            }
        });
    }

    public void requestDinner(String fullApi) throws Exception {

        Request request = new Request.Builder()
                .url(fullApi)
                .build();

        client.newCall(request).enqueue(new Callback() {
            //Respone if fail requet
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
                Context context = getApplicationContext();
                CharSequence text = "Cari API lain siot";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            //Response ada tapi tak semsstinya content tu ada (error404)
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("tittle", "MAsuk blum");

                try (ResponseBody responseBody = response.body()) {
                    {
                        Log.v("tittle", "settle");
                        String responseString = responseBody.string();
                        //JSON parsing
                        try {
                            JSONObject reader = new JSONObject(responseString);
                            JSONArray result = reader.getJSONArray("results");
                            Log.v("response", responseString);

                            parseDinner(result);

                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    setDinnerRecycler(dinnerFoodList);
                                    setBreakfastRecycler(breakfastFoodList);
                                    setLunchRecycler(lunchFoodList);
                                    setPopularRecycler(popularFoodList);
                                }
                            });

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    if (!response.isSuccessful())
                        throw new IOException("Unexpected code " + response);

                    Headers responseHeaders = response.headers();
                    for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                        System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                    }

                }
            }
        });
    }

}