package com.example.grocerymanager;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class tutorialStepsAdapter extends RecyclerView.Adapter<tutorialStepsAdapter.tutorialStepsViewHolder> {

    Context context;
    ArrayList<String> steps;


    public tutorialStepsAdapter(Context context, ArrayList<String> steps){
        this.context=context;
        this.steps=steps;
//        for(int i = 0; i < steps.size(); i++){
//            this.steps.add(steps.get(i));
//        }
    }


    //public popularFoodAdapter(MainActivity mainActivity, List<popularFood> popularFoodList) {
    //}

    @NonNull
    @Override
    //ada tukar line bawah ni *Public popularFoodAdapter.popularFoodViewHolder
    public tutorialStepsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.tutorial_steps_item,parent, false);
        return new tutorialStepsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull tutorialStepsAdapter.tutorialStepsViewHolder holder, int position) {

        //Picasso.get().load(getYoutubeThumbnailUrlFromVideoUrl(popularFoodList.get(position).getImageUrl())).into(holder.foodImage);

        //  holder.foodImage.setImageResource(popularFoodList.get(position).getImageUrl());
        holder.steps.setText(steps.get(position));


    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    //problem2
    public static final class tutorialStepsViewHolder extends RecyclerView.ViewHolder{

        TextView steps;
        public tutorialStepsViewHolder(@NonNull View itemView) {
            super(itemView);
            steps = itemView.findViewById(R.id.tuto_text);
        }
    }
}
