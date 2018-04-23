package com.mcc.ghurbo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.data.preference.AppPreference;
import com.mcc.ghurbo.data.preference.PrefKey;
import com.mcc.ghurbo.fragment.HotelSearchFragment;
import com.mcc.ghurbo.fragment.TourSearchFragment;
import com.mcc.ghurbo.model.NotificationModel;
import com.mcc.ghurbo.model.TourModel;
import com.mcc.ghurbo.utility.ActivityUtils;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private CardView btnSearchHotel, btnSearchTour;
    private LinearLayout searchHotelPanel, searchTourPanel;
    private ImageView hotelIcon, tourIcon;
    private TextView tvHotel, tvTour;
    private RelativeLayout notificationView;
    private TextView notificationCount, toolbarTitle;

    private String searchRequest; // from widget

    private static final String SELECTION_KEY = "selection_key";
    private static final int SELECTED_HOTEL = 1, SELECTED_TOUR = 2;
    private int currentSelected = SELECTED_HOTEL;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         *
         * TODO 5: Send bas64 with profile data
         * TODO 13: Remove empty data from server
         *
         *
         * DONE 1: Implement profile image page after login
         * DONE 4: Progress loader in login
         * DONE 6: Tour and hotel favorite option
         * DONE 8: Integrate push notification
         * DONE 13: Implement loader to load data in a activity
         * DONE 12: Dynamic keystore management
         * DONE 11: Write initialRelease gradle task
         * DONE 10: RTL
         * DONE 14: Push notification issue in Oreo
         * DONE 9: Handle activity rotation
         * DONE 2: Registration page landscape view
         * DONE 3: Homepage landscape design
         * DONE 7: Server not returning user image path
         *
         * Note to reviewer:
         *
         *  I have developed a travel assistant app as my Capstone Project. This app allows to search
         *  hotel and tour package in my local area. User will be able to search and book hotel or
         *  tour package through the app.
         *
         *  Few important notes:
         *  For optimum case, all location may not contain hotel or tour package
         *
         *  For best output follow the following path:
         *
         *  Search Hotel:
         *  Location - Cox's Bazar, Chittagong Division, Bangladesh
         *  Check In - Current Date
         *  Check Out - One or two day ahead from current date
         *  Adult - Bigger than zero
         *  Child - Can be empty
         *  Rooms - Bigger than zero
         *
         *  Search Tour:
         *  Location - Sundarbans, Bangladesh
         *  Type - Holidays
         *  Date - Current Date (or any)
         *  Adult - Bigger than zero
         *  Child - Can be empty
         *
         *
         *  I have implemented firebase cloud messaging to implement push notification feature. I
         *  have written a php script to send push from server.
         *
         *  Here is push dashboard: https://mccltd.info/projects/ghurbo/push/index.php
         *
         */

        initVars();
        initView();
        initFunctionality(savedInstanceState);
        initListener();
    }

    private void initVars() {
        Intent intent = getIntent();

        String action = intent.getAction();
        if(action != null && action.equals(AppConstants.BUNDLE_HOTEL_SEARCH)) {
            searchRequest = AppConstants.BUNDLE_HOTEL_SEARCH;
            intent.setAction(null);
        }

        if(action != null && action.equals(AppConstants.BUNDLE_TOUR_SEARCH)) {
            searchRequest = AppConstants.BUNDLE_TOUR_SEARCH;
            intent.setAction(null);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        initToolbar();
        initDrawer();

        btnSearchHotel = (CardView) findViewById(R.id.btn_search_hotel);
        btnSearchTour = (CardView) findViewById(R.id.btn_search_tour);
        searchHotelPanel = (LinearLayout) findViewById(R.id.search_hotel_panel);
        searchTourPanel = (LinearLayout) findViewById(R.id.search_tour_panel);
        hotelIcon = (ImageView) findViewById(R.id.hotel_icon);
        tourIcon = (ImageView) findViewById(R.id.tour_icon);
        tvHotel = (TextView) findViewById(R.id.tv_hotel);
        tvTour = (TextView) findViewById(R.id.tv_tour);
        notificationView = (RelativeLayout) findViewById(R.id.notification_view);
        notificationCount = (TextView) findViewById(R.id.notification_count);
        toolbarTitle = (TextView) findViewById(R.id.toolbar_title);
    }

    private void initFunctionality(Bundle savedInstanceState) {

        toolbarTitle.setText(getString(R.string.app_name));

        loadProfileData();

        if(searchRequest != null && searchRequest.equals(AppConstants.BUNDLE_HOTEL_SEARCH)) {
            initHotelSearch();
        } else if (searchRequest != null && searchRequest.equals(AppConstants.BUNDLE_TOUR_SEARCH)) {
            initTourSearch();
        } else {
            initFragment(savedInstanceState);
        }
    }

    private void initListener() {

        invokeMessenger();

        btnSearchHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initHotelSearch();
            }
        });

        btnSearchTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                initTourSearch();
            }
        });

        notificationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityUtils.getInstance().invokeActivity(MainActivity.this, NotificationListActivity.class, false);
            }
        });
    }

    private void initHotelSearch() {
        showButtonSelection(SELECTED_HOTEL);
        refreshFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.input_fragment, new HotelSearchFragment(), "hotelFragment").commit();
    }

    private void initTourSearch() {
        showButtonSelection(SELECTED_TOUR);
        refreshFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.input_fragment, new TourSearchFragment(), "tourFragment").commit();
    }

    private void showButtonSelection(int current) {
        currentSelected = current;
        if(current == SELECTED_HOTEL) {
            searchHotelPanel.setBackgroundResource(R.color.appColor);
            searchTourPanel.setBackgroundResource(R.color.white);
            hotelIcon.setImageResource(R.drawable.ic_hotel_white);
            tourIcon.setImageResource(R.drawable.ic_tour_color);
            tvHotel.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
            tvTour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.appColor));
        } else if (current == SELECTED_TOUR) {
            searchHotelPanel.setBackgroundResource(R.color.white);
            searchTourPanel.setBackgroundResource(R.color.appColor);
            hotelIcon.setImageResource(R.drawable.ic_hotel_color);
            tourIcon.setImageResource(R.drawable.ic_tour_white);
            tvHotel.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.appColor));
            tvTour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        }
    }


    private void initFragment(Bundle savedInstanceState) {
        if(savedInstanceState == null) {
            refreshFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.input_fragment, new HotelSearchFragment(), "hotelFragment").commit();
        }
    }

    private void refreshFragment() {
        Fragment hotel = getSupportFragmentManager().findFragmentByTag("hotelFragment");
        if (hotel != null) {
            getSupportFragmentManager().beginTransaction().remove(hotel).commit();
        }

        Fragment tour = getSupportFragmentManager().findFragmentByTag("tourFragment");
        if (tour != null) {
            getSupportFragmentManager().beginTransaction().remove(tour).commit();
        }
    }

    public void initNotification() {
        notificationCount.setVisibility(View.INVISIBLE);
        int count = getUnseenNotificationCount();

        if (count > 0) {
            notificationCount.setVisibility(View.VISIBLE);
            notificationCount.setText(String.valueOf(count));
        } else {
            notificationCount.setVisibility(View.INVISIBLE);
        }

    }

    private BroadcastReceiver newNotificationReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            initNotification();
        }
    };

    @Override
    protected void onResume() {
        super.onResume();

        boolean isLoggedIn = AppPreference.getInstance(getApplicationContext()).getBoolean(PrefKey.LOGIN);
        boolean isSkipped = AppPreference.getInstance(getApplicationContext()).getBoolean(PrefKey.SKIPPED);

        if (!isLoggedIn && !isSkipped) {
            ActivityUtils.getInstance().invokeActivity(MainActivity.this, SplashActivity.class, true);
        }

        IntentFilter intentFilter = new IntentFilter(AppConstants.NEW_NOTIFICATION);
        LocalBroadcastManager.getInstance(this).registerReceiver(newNotificationReceiver, intentFilter);

        initNotification();
    }

    @Override
    protected void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(newNotificationReceiver);
    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putInt(SELECTION_KEY, currentSelected);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if(state != null) {
            currentSelected = state.getInt(SELECTION_KEY);
            showButtonSelection(currentSelected);
        }
    }


}
