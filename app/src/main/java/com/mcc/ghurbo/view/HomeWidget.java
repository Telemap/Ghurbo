package com.mcc.ghurbo.view;


import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.activity.LoginActivity;
import com.mcc.ghurbo.activity.MainActivity;
import com.mcc.ghurbo.data.constant.AppConstants;

public class HomeWidget  extends AppWidgetProvider {

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                int appWidgetId) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.home_switch);

        Intent hotelIntent = new Intent(context, MainActivity.class);
        hotelIntent.setAction(AppConstants.BUNDLE_HOTEL_SEARCH);
        hotelIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        hotelIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, hotelIntent, 0);
        views.setOnClickPendingIntent(R.id.search_hotel_panel, pendingIntent);

        Intent tourIntent = new Intent(context, MainActivity.class);
        tourIntent.setAction(AppConstants.BUNDLE_TOUR_SEARCH);
        tourIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        tourIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent tourPendingIntent = PendingIntent.getActivity(context, 0, tourIntent, 0);
        views.setOnClickPendingIntent(R.id.search_tour_panel, tourPendingIntent);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }

}

