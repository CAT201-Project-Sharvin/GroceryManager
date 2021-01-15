package com.example.grocerymanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.jetbrains.annotations.NotNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;

public class DetailsRecipe extends AppCompatActivity {
    private Button button;
    String recipeIngredients = "https://api.spoonacular.com/recipes/637876/ingredientWidget.json?apiKey=fde780bf140e4dbbaefd90ab69e3d459";
    String recipeIngredients1 = "https://api.spoonacular.com/recipes/";
    String recipeIngredients2 = "/ingredientWidget.json?apiKey=fde780bf140e4dbbaefd90ab69e3d459";
    String recipeSummary = "https://api.spoonacular.com/recipes/637876/summary?apiKey=fde780bf140e4dbbaefd90ab69e3d459";
    String recipeSummary1 = "https://api.spoonacular.com/recipes/";
    String recipeSummary2 = "/summary?apiKey=fde780bf140e4dbbaefd90ab69e3d459";
    String recipeInstructions = "https://api.spoonacular.com/recipes/637876/analyzedInstructions?apiKey=fde780bf140e4dbbaefd90ab69e3d459";
    String recipeInstruction1 = "https://api.spoonacular.com/recipes/";
    String recipeInstruction2 = "/analyzedInstructions?apiKey=fde780bf140e4dbbaefd90ab69e3d459";
    OkHttpClient client = new OkHttpClient();
    String unit, nama, namaBahan, id, summary,namaBarang,cara,pic;
    int number;
    Double value;
    TextView title,summaryContent,ingridientContent,instructionContent;
    ImageView food_pic;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        title = findViewById(R.id.nama_recipe);
        food_pic = findViewById(R.id.main_image);
        summaryContent = findViewById(R.id.summary_recipe);
        ingridientContent = findViewById(R.id.ingridient_recipe);
        instructionContent = findViewById(R.id.instrction_recipe);


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_recipe);

        button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();

            }
        });

        Intent intent = getIntent();
        id = intent.getStringExtra("id_recipe");
        pic = intent.getStringExtra("food_pic");
        Log.v("intent","Intent successful");

        String fullApiRecipeIngredients = recipeIngredients1 + id + recipeIngredients2;
        try {
            run(fullApiRecipeIngredients);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void run(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Log.v("Recipe Ingredient ","Entering run successful");


        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Context context = getApplicationContext();
                CharSequence text = "Cari API lain siot";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    String responseString = responseBody.string();
                    Log.v("Recipe Ingredient ","Entering OnResponse successful");

                    //JSON parsing
                    try {
                        JSONObject balas = new JSONObject(responseString);
                        JSONArray ingredients = balas.getJSONArray("ingredients");
                        Log.v("Recipe Ingredient ","Entering Parsing Recipe Ingredients");

                        for (int i = 0; i < ingredients.length(); i++) {
                            JSONObject singleIngredient = ingredients.getJSONObject(i);
                            namaBahan = singleIngredient.getString("name");
                            JSONObject amount = singleIngredient.getJSONObject("amount");
                            JSONObject calcMalaysia = amount.getJSONObject("metric");
                            value = calcMalaysia.getDouble("value");
                            unit = calcMalaysia.getString("unit");
                            Log.v("Recipe Ingredient ","Parsing  successful");


                        }

                        String fullApiRecipeSummary = recipeSummary1 + id + recipeSummary2;
                        run1(fullApiRecipeSummary);

                    } catch (JSONException e) {
                        e.printStackTrace();
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void run1(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Context context = getApplicationContext();
                CharSequence text = "Cari API lain siot";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    String responseString = responseBody.string();
                    //JSON parsing
                    try {
                        JSONObject balas = new JSONObject(responseString);
                        nama = balas.getString("title");
                        summary = balas.getString("summary");

                        String fullApiRecipeInstruction = recipeInstruction1 + id + recipeInstruction2;
                        run2(fullApiRecipeInstruction);

                    } catch (JSONException e) {
                        e.printStackTrace();
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

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    void run2(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();
                Context context = getApplicationContext();
                CharSequence text = "Cari API lain siot";
                int duration = Toast.LENGTH_SHORT;

                Toast toast = Toast.makeText(context, text, duration);
                toast.show();
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                try (ResponseBody responseBody = response.body()) {
                    String responseString = responseBody.string();
                    //JSON parsing
                    try {
                        JSONArray balas = new JSONArray(responseString);
                        JSONObject balasObject = balas.getJSONObject(0);
                        JSONArray steps = balasObject.getJSONArray("steps");

                        for (int i = 0; i < steps.length(); i++) {
                            JSONObject singleStep = steps.getJSONObject(i);
                            number = singleStep.getInt("number");
                            cara = singleStep.getString("step");
                            JSONArray barang = singleStep.getJSONArray("equipment");
                            JSONObject barangObject = barang.getJSONObject(0);
                            namaBarang = barangObject.getString("name");

                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                title.setText(nama);
                                Picasso.get().load(pic).into(food_pic);
                                summaryContent.setText(summary);
                                ingridientContent.setText(namaBahan);
                                instructionContent.setText(cara);

                            }
                        });

                    } catch (JSONException e) {
                        e.printStackTrace();
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