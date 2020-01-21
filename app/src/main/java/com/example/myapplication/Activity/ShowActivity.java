package com.example.myapplication.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.myapplication.Fragments.FregmShowDetail;
import com.example.myapplication.R;


public class ShowActivity extends AppCompatActivity {
    android.app.FragmentManager fragmentManager =getFragmentManager();
    android.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    FregmShowDetail mshowDetailFregm;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_fragment);
        if (savedInstanceState != null) {
            mshowDetailFregm = (FregmShowDetail) getFragmentManager().findFragmentByTag("gg");


        } else {
            mshowDetailFregm = new FregmShowDetail();
            fragmentTransaction.add(R.id.show_freg, mshowDetailFregm);
            fragmentTransaction.commit();
        }

    }

}





