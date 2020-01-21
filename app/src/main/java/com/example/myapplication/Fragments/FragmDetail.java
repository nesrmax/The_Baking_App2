package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.myapplication.Adapters.IngreditAdapter;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FragmDetail extends Fragment {

    ArrayList<String> inger_list = new ArrayList<String>();

    JSONArray ingerdient_List;


    public FragmDetail() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detail, container, false);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);


        RecyclerView minList_gredit = view.findViewById(R.id.ingerd_recycler);
        minList_gredit.setLayoutManager(layoutManager);
        minList_gredit.setHasFixedSize(true);


        try {


            if (this.getArguments() != null) {
                ingerdient_List = new JSONArray(this.getArguments().getString("Ing"));

            }

            for (int i = 0; i < ingerdient_List.length(); i++) {
                JSONObject jsonObject = ingerdient_List.getJSONObject(i);
                int quantity = jsonObject.optInt("quantity");
                String measure = jsonObject.optString("measure");
                String ingredient = jsonObject.optString("ingredient");
                String word = "";
                word = quantity + " " + measure + "\n" + ingredient;
                inger_list.add(word);

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }


        IngreditAdapter ingred_Adapter = new IngreditAdapter(getActivity(), inger_list);
        minList_gredit.setAdapter(ingred_Adapter);

        return view;

    }
}
