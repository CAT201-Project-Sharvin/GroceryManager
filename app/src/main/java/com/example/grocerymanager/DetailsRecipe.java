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
    //String recipeIngredients2 = "/ingredientWidget.json?apiKey=fde780bf140e4dbbaefd90ab69e3d459";
    String recipeIngredients2 = "/ingredientWidget.json?apiKey=b07a52c682e244109a54eb58858235d3";
    String recipeSummary = "https://api.spoonacular.com/recipes/637876/summary?apiKey=fde780bf140e4dbbaefd90ab69e3d459";
    String recipeSummary1 = "https://api.spoonacular.com/recipes/";
    //String recipeSummary2 = "/summary?apiKey=fde780bf140e4dbbaefd90ab69e3d459";
    String recipeSummary2 = "/summary?apiKey=b07a52c682e244109a54eb58858235d3";
    String recipeInstructions = "https://api.spoonacular.com/recipes/637876/analyzedInstructions?apiKey=fde780bf140e4dbbaefd90ab69e3d459";
    String recipeInstruction1 = "https://api.spoonacular.com/recipes/";
    //String recipeInstruction2 = "/analyzedInstructions?apiKey=fde780bf140e4dbbaefd90ab69e3d459";
    String recipeInstruction2 = "/analyzedInstructions?apiKey=b07a52c682e244109a54eb58858235d3";

    OkHttpClient client = new OkHttpClient();
    String unit, nama, namaBahan, id, summary,namaBarang,cara,pic;
    int number;
    Double value;
    TextView title,summaryContent,ingridientContent,instructionContent;
    ImageView food_pic;
    String caraIncrement, bahanIncrement;




    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_recipe);

        Log.v("Details Activity","Entered");
        title = (TextView) findViewById(R.id.nama_recipe);
        food_pic = (ImageView) findViewById(R.id.main_image);

        summaryContent = (TextView) findViewById(R.id.summary_recipe);
        ingridientContent = (TextView) findViewById(R.id.ingridient_recipe);
        instructionContent = (TextView) findViewById(R.id.instrction_recipe);

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
            Log.v("fullApi","Successful");
            run(fullApiRecipeIngredients);
            Log.v("fullApi","run full api successful");

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
                            bahanIncrement = bahanIncrement+namaBahan + "\n";


                        }

                        String fullApiRecipeSummary = recipeSummary1 + id + recipeSummary2;
                        run1(fullApiRecipeSummary);
                        Log.v("Recipe Ingredient ","called run1");


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
        Log.v("Recipe Summary ","Requested  Recipe Summary URL");

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
                        Log.v("Recipe Summary ",nama);
                        Log.v("Recipe Summary",summary);



                        String fullApiRecipeInstruction = recipeInstruction1 + id + recipeInstruction2;
                        run2(fullApiRecipeInstruction);
                        Log.v("Recipe Summary ","called run2");


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
        Log.v("Recipe Instrcution ","Entered successful run2");


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
                        Log.v("Recipe Instrcution ","Entered parsing successful run2");

                        JSONArray balas = new JSONArray(responseString);
                        Log.v("Recipe Instrcution","Balas OK");
                        JSONObject balasObject = balas.getJSONObject(0);
                        Log.v("Recipe Instrcution","BalasObject OK");
                        JSONArray steps = balasObject.getJSONArray("steps");
                        Log.v("Recipe Instrcution", "Steps OK");

                        for (int i = 0; i < steps.length(); i++) {
                            JSONObject singleStep = steps.getJSONObject(i);
                            Log.v("Recipe Instrcution", "SingleSteps OK");
                            number = singleStep.getInt("number");
                            Log.v("Recipe Instrcution", String.valueOf(number));
                            cara = singleStep.getString("step");
                            Log.v("Recipe Instrcution", cara.toString());
                            JSONArray barang = singleStep.getJSONArray("equipment");
                            Log.v("Recipe Instrcution", barang.toString());

                            //JSONObject barangObject = barang.getJSONObject(0);
                            Log.v("Recipe Instrcution", "BarangObject Barang  OK");
                            //namaBarang = barangObject.getString("name");
                            Log.v("Recipe Instrcution", "BarangObject namaBarang OK");

                            Log.v("Recipe Instrcution ", String.valueOf(number));
                            Log.v("Recipe Instrcution",cara);
                            // Log.v("Recipe Instrcution",namaBarang);
                            Log.v("Recipe Instrcution", String.valueOf(steps.length()));
                            caraIncrement = caraIncrement+cara+ "\n";
                        }

                        Log.v("UI Thread ","Entering UI Thread ");
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                title.setText(nama);
                                Picasso.get().load(pic).into(food_pic);
                                Log.v("UI Thread ","Entering 5 on UI Thread Successful");
                                Log.v("UI Thread ", summary);
                                //summaryContent.setText("summary test");
                                //summaryContent.setText(summary);
                                // Log.v("UI Thread ","Summary on UI Thread Successful");
                                ingridientContent.setText(bahanIncrement);
                                Log.v("UI Thread ","namaBahan on UI Thread Successful");
                                instructionContent.setText(caraIncrement);
                                Log.v("UI Thread ","cara on UI Thread Successful");

                                Log.v("UI Thread ","Run on UI Thread Successful");

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




