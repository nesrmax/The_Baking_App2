package com.example.myapplication.Fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.myapplication.Adapters.StepAdapter;
import com.example.myapplication.Models.Model;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TheFragment extends android.support.v4.app.Fragment {
    JSONArray The_list_Step;
    ArrayList<Model> array_list_step = new ArrayList<Model>();
    static LinearLayoutManager lay_Manager;
    static StepAdapter stepAdapter;

    public TheFragment() {
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.detail, container, false);
        RecyclerView mStepsList = view.findViewById(R.id.step_recycler);
        lay_Manager = new LinearLayoutManager(getActivity(), LinearLayout.VERTICAL, false);

        mStepsList.setLayoutManager(lay_Manager);
        mStepsList.setHasFixedSize(true);
        try {
            The_list_Step = new JSONArray(this.getArguments().getString("Ste"));
            for (int x = 0; x < The_list_Step.length(); x++) {
                JSONObject object = The_list_Step.getJSONObject(x);
                int id = object.optInt("id");
                String shortDescription = object.optString("shortDescription");
                String description = object.optString("description");
                String videoURL = object.optString("videoURL");
                if (videoURL == null) {
                    videoURL = "";
                }

                String thumbnailURL = object.optString("thumbnailURL");
                if (thumbnailURL == null) {
                    thumbnailURL = "";
                }
                array_list_step.add(new Model(shortDescription, description, videoURL, thumbnailURL));
            }


            stepAdapter = new StepAdapter(getActivity(), array_list_step);
            mStepsList.setAdapter(stepAdapter);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return view;
    }
}