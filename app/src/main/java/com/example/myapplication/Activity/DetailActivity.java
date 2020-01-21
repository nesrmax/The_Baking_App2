package com.example.myapplication.Activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.NavUtils;
import android.support.v4.app.TaskStackBuilder;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.myapplication.Fragments.FragmDetail;
import com.example.myapplication.Fragments.TheFragment;
import com.example.myapplication.Fragments.FregmShowDetail;
import com.example.myapplication.R;

public class DetailActivity extends AppCompatActivity {


    public static boolean Port;

    public static Context context;


    @SuppressLint({"NewApi", "RestrictedApi"})
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        context = getApplicationContext();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        Port = getResources().getBoolean(R.bool.Port_Of_Tablet);


        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        Bundle bund_1 = new Bundle();
        Bundle bund_2 = new Bundle();
        bund_1.putString("Ing", bundle.getString("In"));
        bund_2.putString("Ste", bundle.getString("St"));
        FragmDetail fragment = new FragmDetail();
        TheFragment theFragment = new TheFragment();
        FregmShowDetail fregmShowDetail = new FregmShowDetail();
        fragment.setArguments(bund_1);
        theFragment.setArguments(bund_2);

        android.support.v4.app.FragmentManager fragmentManager = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.Cont_Of_Ingrd, fragment);

        android.support.v4.app.FragmentManager fragmentManagerr = getSupportFragmentManager();
        android.support.v4.app.FragmentTransaction fragmentTransactionn = fragmentManagerr.beginTransaction();
        fragmentTransactionn.add(R.id.Cont_Of_Sep, theFragment);

        //------------------------for Rotation --------------------------------------------
        if (savedInstanceState == null) {
            fragmentTransaction.commit();
            fragmentTransactionn.commit();
        } else {
            fragmentTransaction.remove(fragment);
            fragmentTransactionn.remove(theFragment);
            fragmentTransaction.commit();
            fragmentTransactionn.commit();

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                Intent Intent_Of_Up = NavUtils.getParentActivityIntent(this);
                if (NavUtils.shouldUpRecreateTask(this, Intent_Of_Up)) {
                    TaskStackBuilder.create(this)
                            .addNextIntentWithParentStack(Intent_Of_Up)
                            .startActivities();
                } else {
                    NavUtils.navigateUpTo(this, Intent_Of_Up);
                }
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
