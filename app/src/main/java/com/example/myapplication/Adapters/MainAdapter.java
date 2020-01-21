package com.example.myapplication.Adapters;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.myapplication.Activity.DetailActivity;
import com.example.myapplication.Models.Model;
import com.example.myapplication.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;


public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MovieHolder> {

    private ArrayList<Model> the_list_item;
    public static String item_pos = "";
    private Context context;

    public MainAdapter(Context context, ArrayList<Model> the_list_item) {
        this.context = context;
        this.the_list_item = the_list_item;

    }

    static class MovieHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        ImageView imageView;
        TextView the_item_name;

        MovieHolder(View item_view) {
            super(item_view);
            linearLayout = item_view.findViewById(R.id.lin_layout);
            linearLayout.setOrientation(LinearLayout.VERTICAL);
            imageView = item_view.findViewById(R.id.row);
            the_item_name = item_view.findViewById(R.id.back_Button);
        }
    }

    @NonNull
    @Override
    public MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        return new MovieHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieHolder holder, final int position) {

        if (!the_list_item.get(position).getImage().isEmpty()) {
            Picasso.with(context).load(the_list_item.get(position).getImage()).into(holder.imageView);
        }


        holder.the_item_name.setText(the_list_item.get(position).getName());
        holder.the_item_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, DetailActivity.class);
                item_pos = the_list_item.get(position).getJsonArray1().toString();
                intent.putExtra("In", the_list_item.get(position).getJsonArray1().toString());
                intent.putExtra("St", the_list_item.get(position).getJsonArray2().toString());
                //***************************//
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);


            }
        });

    }

    @Override
    public int getItemCount() {
        return the_list_item.size();
    }

}


