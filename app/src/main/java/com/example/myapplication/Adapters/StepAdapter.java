package com.example.myapplication.Adapters;

import android.app.Activity;
import android.app.FragmentTransaction;
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
import com.example.myapplication.Fragments.FregmShowDetail;
import com.example.myapplication.Activity.MainActivity;
import com.example.myapplication.Models.Model;
import com.example.myapplication.R;
import com.example.myapplication.Activity.ShowActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class StepAdapter extends RecyclerView.Adapter<StepAdapter.MovieHolder> {
    private Context context;
    private ArrayList<Model> list;
    public static ArrayList<Model> arrayList = new ArrayList<>();
    public static int position = 0;

    public StepAdapter(Context context, ArrayList<Model> list) {
        this.context = context;
        this.list = list;
        arrayList = list;

    }

    static class MovieHolder extends RecyclerView.ViewHolder {
        LinearLayout linearLayout;
        TextView the_item_name;
        ImageView imageView;

        MovieHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.row);
            the_item_name = itemView.findViewById(R.id.back_Button);
            linearLayout = itemView.findViewById(R.id.lin_layout);
            linearLayout.setOrientation(LinearLayout.VERTICAL);

        }
    }

    @NonNull
    @Override
    public StepAdapter.MovieHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.row, parent, false);
        return new StepAdapter.MovieHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull StepAdapter.MovieHolder holder, final int position) {
        holder.the_item_name.setText(list.get(position).getShort_Desc());
        if (!list.get(position).getThumb_URL().isEmpty()) {
            holder.imageView.setVisibility(View.VISIBLE);
            Picasso.with(context).load(list.get(position).getThumb_URL()).into(holder.imageView);
        } else {
            holder.imageView.setVisibility(View.INVISIBLE);
        }


        holder.the_item_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!MainActivity.Tablet) {
                    Intent intent = new Intent(context, ShowActivity.class);
                    StepAdapter.position = position;
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("des", list.get(position).getDescription());
                    intent.putExtra("Vid", list.get(position).getVideo_URL());
                    intent.putExtra("Th", list.get(position).getThumb_URL());
                    context.startActivity(intent);
                } else if (MainActivity.Tablet && DetailActivity.Port) {
                    Intent intent = new Intent(context, ShowActivity.class);
                    StepAdapter.position = position;
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("des", list.get(position).getDescription());
                    intent.putExtra("Vid", list.get(position).getVideo_URL());
                    intent.putExtra("Th", list.get(position).getThumb_URL());
                    context.startActivity(intent);
                } else {

                    StepAdapter.position = position;
                    FregmShowDetail fregmShowDetail = new FregmShowDetail();
                    android.app.FragmentManager manager = ((Activity) context).getFragmentManager();
                    FragmentTransaction transaction = manager.beginTransaction();
                    transaction.replace(R.id.ShowContainer, fregmShowDetail);
                    transaction.commit();

                }


            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


}



