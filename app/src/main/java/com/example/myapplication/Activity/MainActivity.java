package com.example.myapplication.Activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.myapplication.Adapters.MainAdapter;
import com.example.myapplication.Models.Model;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.http.GET;


public class MainActivity extends AppCompatActivity {

    public static Context context;

    public static Bundle bundle;

    public static ArrayList<Model> ArrayList_of_model = new ArrayList<Model>();
    public RecyclerView The_Main_List;
    public RecyclerView.LayoutManager lay_Manager;
    MainAdapter The_Main_Adapter;

    private String name;
    String image;
    public static boolean Tablet;
    JSONArray array_of_steps;
    JSONArray array_of_ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        bundle = savedInstanceState;
        Tablet = getResources().getBoolean(R.bool.Tablet);
        The_Main_List = findViewById(R.id.mainlist);


        if (Tablet) {
            lay_Manager = new GridLayoutManager(getApplicationContext(), 2);


        } else {
            lay_Manager = new LinearLayoutManager(getApplicationContext(), LinearLayout.VERTICAL, false);
        }

        The_Main_List.setLayoutManager(lay_Manager);
        The_Main_List.setHasFixedSize(true);

        if (savedInstanceState == null) {
            GetJson();
        } else {
            GetJson();
            Toast.makeText(getApplicationContext(), "savv!=null", Toast.LENGTH_SHORT).show();
        }


    }


    public interface GetData {
        @GET("baking.json")
        Call<ResponseBody> get();
    }


    void GetJson() {
        ArrayList_of_model.clear();

        Retrofit retrofit = new Retrofit.Builder().baseUrl(getApplicationContext().getString(R.string.BaseUrl)).build();
        GetData getData = retrofit.create(GetData.class);
        Call<ResponseBody> call = getData.get();
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    JSONArray jsonArray = new JSONArray(response.body().string());
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        name = jsonObject.optString("name");
                        image = jsonObject.optString("image");
                        array_of_ingredients = jsonObject.getJSONArray("ingredients");
                        array_of_steps = jsonObject.getJSONArray("steps");

                        ArrayList_of_model.add(new Model(name, array_of_ingredients, array_of_steps, image));
                    }
                    The_Main_Adapter = new MainAdapter(getApplicationContext(), ArrayList_of_model);
                    The_Main_List.setAdapter(The_Main_Adapter);
                    //-------------------------------------------------------------------

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

}
