package com.example.myapplication.Widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.example.myapplication.Adapters.MainAdapter;
import com.example.myapplication.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class NewAppWidget extends AppWidgetProvider {
   public static String x="ffff";
   static String word ;
    static JSONArray the_ingerdient_List;
    {

        try {
            the_ingerdient_List = new JSONArray(MainAdapter.item_pos);
            word="";
            for (int i = 0; i < the_ingerdient_List.length(); i++) {
                JSONObject jsonObject = the_ingerdient_List.getJSONObject(i);
                int quantity = jsonObject.getInt("quantity");
                String measure = jsonObject.getString("measure");
                String ingredient = jsonObject.getString("ingredient");

                word  += quantity+" "+measure+"\n"+ingredient+"\n";
            }
        } catch (JSONException e1) {
            e1.printStackTrace();
        }
    }

   static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                               int appWidgetId){

       CharSequence widgetText = context.getString(R.string.appwidget_text);
       RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.new_app_widget);
       views.setTextViewText(R.id.appwidget_text, word);

       Intent intentUpdate = new Intent(context, NewAppWidget.class);
       intentUpdate.setAction(AppWidgetManager.ACTION_APPWIDGET_UPDATE);


       int[] idArray = new int[]{appWidgetId};
       intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray);


       PendingIntent pendingUpdate = PendingIntent.getBroadcast(
               context, appWidgetId, intentUpdate,
               PendingIntent.FLAG_UPDATE_CURRENT);
       views.setOnClickPendingIntent(R.id.appwidget_text, pendingUpdate);
       appWidgetManager.updateAppWidget(appWidgetId, views);
   }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
                updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
    }

    @Override
    public void onDisabled(Context context) {
    }
}

