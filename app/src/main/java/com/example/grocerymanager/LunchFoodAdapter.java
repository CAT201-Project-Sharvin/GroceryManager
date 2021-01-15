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
import com.example.grocerymanager.LunchFood;
import com.squareup.picasso.Picasso;

import java.util.List;

public class LunchFoodAdapter extends RecyclerView.Adapter<LunchFoodAdapter.LunchFoodViewHolder> {

    Context context;
    List<LunchFood> lunchFoodList;

    public LunchFoodAdapter(Context context, List<LunchFood> lunchFoodList){
        this.context=context;
        this.lunchFoodList=lunchFoodList;
    }

    //public popularFoodAdapter(MainActivity mainActivity, List<popularFood> popularFoodList) {
    //}

    @NonNull
    @Override
    //ada tukar line bawah ni *Public popularFoodAdapter.popularFoodViewHolder
    public LunchFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.lunch_food_row_item,parent, false);
        return new LunchFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(com.example.grocerymanager.LunchFoodAdapter.LunchFoodViewHolder holder, final int position) {
        Picasso.get().load(lunchFoodList.get(position).getImageUrl()).into(holder.foodImage);

      //  holder.foodImage.setImageResource(popularFoodList.get(position).getImageUrl());
        holder.name.setText(lunchFoodList.get(position).getName());
        holder.rating.setText(lunchFoodList.get(position).getRating());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, DetailsRecipe.class);
                i.putExtra("id_recipe", lunchFoodList.get(position).getId());
                i.putExtra("food_pic", lunchFoodList.get(position).getImageUrl());
                context.startActivity(i);
            }
        });

    }

    @Override
    public int getItemCount() {
        return lunchFoodList.size();
    }

    //problem2
    public static final class LunchFoodViewHolder extends RecyclerView.ViewHolder{

        ImageView foodImage;
        TextView rating,name;
        public LunchFoodViewHolder(@NonNull View itemView) {
            super(itemView);

            foodImage=itemView.findViewById(R.id.imageView2);
            name= itemView.findViewById(R.id.recipe_name);
            rating= itemView.findViewById(R.id.recipe_rating);
        }
    }
}
