package com.mcc.ghurbo.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.data.preference.AppPreference;
import com.mcc.ghurbo.data.preference.PrefKey;
import com.mcc.ghurbo.fragment.HotelSearchFragment;
import com.mcc.ghurbo.fragment.TourSearchFragment;
import com.mcc.ghurbo.utility.ActivityUtils;

public class MainActivity extends BaseActivity {

    private CardView btnSearchHotel, btnSearchTour;
    private LinearLayout searchHotelPanel, searchTourPanel;
    private ImageView hotelIcon, tourIcon;
    private TextView tvHotel, tvTour;

    private String searchRequest; // from widget

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         *
         * TODO 2: Registration page landscape view
         * TODO 3: Homepage landscape design
         * TODO 5: Send bas64 with profile data
         * TODO 7: Server nor returning user image path
         *
         *
         * DONE 1: Implement profile image page after login
         * DONE 4: Progress loader in login
         * DONE 6: Tour and hotel favorite option
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
        }

        if(action != null && action.equals(AppConstants.BUNDLE_TOUR_SEARCH)) {
            searchRequest = AppConstants.BUNDLE_TOUR_SEARCH;
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
    }

    private void initFunctionality(Bundle savedInstanceState) {
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

    }

    private void initHotelSearch() {
        searchHotelPanel.setBackgroundResource(R.color.appColor);
        searchTourPanel.setBackgroundResource(R.color.white);
        hotelIcon.setImageResource(R.drawable.ic_hotel_white);
        tourIcon.setImageResource(R.drawable.ic_tour_color);
        tvHotel.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));
        tvTour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.appColor));

        refreshFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.input_fragment, new HotelSearchFragment(), "hotelFragment").commit();
    }

    private void initTourSearch() {
        searchHotelPanel.setBackgroundResource(R.color.white);
        searchTourPanel.setBackgroundResource(R.color.appColor);
        hotelIcon.setImageResource(R.drawable.ic_hotel_color);
        tourIcon.setImageResource(R.drawable.ic_tour_white);
        tvHotel.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.appColor));
        tvTour.setTextColor(ContextCompat.getColor(getApplicationContext(), R.color.white));

        refreshFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.input_fragment, new TourSearchFragment(), "tourFragment").commit();
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

    @Override
    protected void onResume() {
        super.onResume();

        boolean isLoggedIn = AppPreference.getInstance(getApplicationContext()).getBoolean(PrefKey.LOGIN);
        boolean isSkipped = AppPreference.getInstance(getApplicationContext()).getBoolean(PrefKey.SKIPPED);

        if (!isLoggedIn && !isSkipped) {
            ActivityUtils.getInstance().invokeActivity(MainActivity.this, SplashActivity.class, true);
        }
    }
}
