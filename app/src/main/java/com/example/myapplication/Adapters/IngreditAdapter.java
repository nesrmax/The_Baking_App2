package com.example.myapplication.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.R;

import java.util.ArrayList;

public class IngreditAdapter extends RecyclerView.Adapter<IngreditAdapter.MovieHolder> {
    private Context context;
    private ArrayList<String> the_ingred_list;

    public IngreditAdapter(Context context, ArrayList<String> itemlist) {
        this.context = context;
        this.the_ingred_list = itemlist;
    }

    static class MovieHolder extends RecyclerView.ViewHolder {
        TextView name_of_item;
        ImageView imageView;

        MovieHolder(View itemView) {
            super(itemView);
            name_of_item = itemView.findViewById(R.id.back_Button);
            imageView = itemView.findViewById(R.id.row);
            imageView.setVisibility(View.VISIBLE);


        }
    }

    @NonNull
    @Override
    public IngreditAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        return new IngreditAdapter.MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull IngreditAdapter.MovieHolder holder, final int position) {
        holder.name_of_item.setText(the_ingred_list.get(position));
    }

    @Override
    public int getItemCount() {
        return the_ingred_list.size();
    }

}




