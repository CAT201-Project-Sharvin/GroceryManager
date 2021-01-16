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

import com.squareup.picasso.Picasso;

import java.util.List;

public class expGroceryAdapter extends RecyclerView.Adapter<expGroceryAdapter.expGroceryViewHolder>{

    Context context;
    List<grocery> groceryList;

    public expGroceryAdapter(Context context,List<grocery>groceryList){
        this.context=context;
        this.groceryList=groceryList;
    }

    @NonNull
    @Override
    public expGroceryAdapter.expGroceryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.expired_items,parent, false);
        return new expGroceryAdapter.expGroceryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull expGroceryAdapter.expGroceryViewHolder holder, final int position) {
        Picasso.get().load(groceryList.get(position).getImageUri()).into(holder.expImage);
        holder.expName.setText(groceryList.get(position).getName());
        holder.expDay.setText(groceryList.get(position).getDate());

    }

    @Override
    public int getItemCount() {
        return groceryList.size();
    }


    public static final class expGroceryViewHolder extends RecyclerView.ViewHolder{

        ImageView expImage;
        TextView expName,expDay;
        public expGroceryViewHolder(@NonNull View itemView) {
            super(itemView);

            expImage=itemView.findViewById(R.id.exp_image);
            expName = itemView.findViewById(R.id.exp_item_name);
            expDay = itemView.findViewById(R.id.exp_item_day);
        }
    }
}
