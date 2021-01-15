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
import com.example.grocerymanager.BreakfastFood;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BreakfastFoodAdapter extends RecyclerView.Adapter<BreakfastFoodAdapter.BreakfastFoodViewHolder> {

    Context context;
    List<BreakfastFood> breakfastFoodList;

    public BreakfastFoodAdapter(Context context, List<BreakfastFood>breakfastFoodList){
        this.context=context;
        this.breakfastFoodList=breakfastFoodList;
    }

    //public popularFoodAdapter(MainActivity mainActivity, List<popularFood> popularFoodList) {
    //}

    @NonNull
    @Override
    //ada tukar line bawah ni *Public popularFoodAdapter.popularFoodViewHolder
    public BreakfastFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.breakfast_food_row_item,parent, false);
        return new BreakfastFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.example.grocerymanager.BreakfastFoodAdapter.BreakfastFoodViewHolder holder, final int position) {
        Picasso.get().load(breakfastFoodList.get(position).getImageUrl()).into(holder.foodImage);

      //  holder.foodImage.setImageResource(popularFoodList.get(position).getImageUrl());
        holder.name.setText(breakfastFoodList.get(position).getName());
        holder.rating.setText(breakfastFoodList.get(position).getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailsRecipe.class);
                i.putExtra("id_recipe", breakfastFoodList.get(position).getId());
                i.putExtra("food_pic", breakfastFoodList.get(position).getImageUrl());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return breakfastFoodList.size();
    }

    //problem2
    public static final class BreakfastFoodViewHolder extends RecyclerView.ViewHolder{

        ImageView foodImage;
        TextView rating,name;
        public BreakfastFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage=itemView.findViewById(R.id.imageView2);
            name= itemView.findViewById(R.id.recipe_name);
            rating= itemView.findViewById(R.id.recipe_rating);
        }
    }
}
