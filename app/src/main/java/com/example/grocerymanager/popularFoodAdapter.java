package com.example.grocerymanager;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grocerymanager.DetailsRecipe;
import com.example.grocerymanager.R;
import com.example.grocerymanager.PopularFoods;
import com.squareup.picasso.Picasso;

import java.util.List;

public class popularFoodAdapter extends RecyclerView.Adapter<popularFoodAdapter.popularFoodViewHolder> {

    Context context;
    List<PopularFoods> popularFoodList;

    public popularFoodAdapter(Context context,List<PopularFoods>popularFoodList){
        this.context=context;
        this.popularFoodList=popularFoodList;
    }

    //public popularFoodAdapter(MainActivity mainActivity, List<popularFood> popularFoodList) {
    //}

    @NonNull
    @Override
    //ada tukar line bawah ni *Public popularFoodAdapter.popularFoodViewHolder
    public popularFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.popular_food_row_item,parent, false);
        return new popularFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull popularFoodAdapter.popularFoodViewHolder holder, final int position) {
        Picasso.get().load(popularFoodList.get(position).getImageUrl()).into(holder.foodImage);

        //  holder.foodImage.setImageResource(popularFoodList.get(position).getImageUrl());
        holder.name.setText(popularFoodList.get(position).getName());
        holder.rating.setText(popularFoodList.get(position).getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailsRecipe.class);
                i.putExtra("id_recipe", popularFoodList.get(position).getId());
                i.putExtra("food_pic", popularFoodList.get(position).getImageUrl());
                context.startActivity(i);

            }
        });

    }

    @Override
    public int getItemCount() {
        return popularFoodList.size();
    }

    //problem2
    public static final class popularFoodViewHolder extends RecyclerView.ViewHolder{

        ImageView foodImage;
        TextView rating,name;
        public popularFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage=itemView.findViewById(R.id.imageView2);
            name= itemView.findViewById(R.id.recipe_name);
            rating= itemView.findViewById(R.id.recipe_rating);
        }
    }
}
