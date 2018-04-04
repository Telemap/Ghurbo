package com.mcc.ghurbo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.api.helper.RequestHotels;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.fragment.HotelSearchFragment;
import com.mcc.ghurbo.fragment.TourSearchFragment;
import com.mcc.ghurbo.model.LocationModel;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private RelativeLayout btnSearchHotel, btnSearchTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * TODO 1: Implement profile image page after login
         * TODO 2: Registration page landscape view
         * TODO 3: Homepage landscape design
         *
         */

        initVariables();
        initView();
        initFunctionality();
        initListener(savedInstanceState);
    }

    private void initVariables() {

    }

    private void initView() {
        setContentView(R.layout.activity_main);
        initToolbar();
        initDrawer();

        btnSearchHotel = (RelativeLayout) findViewById(R.id.btn_search_hotel);
        btnSearchTour = (RelativeLayout) findViewById(R.id.btn_search_tour);
    }

    private void initFunctionality() {
        loadProfileData();
    }

    private void initListener(final Bundle savedInstanceState) {
        initFragment(savedInstanceState);

        btnSearchHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnSearchHotel.setBackgroundResource(R.drawable.bg_hotel_tour_selected);
                btnSearchTour.setBackgroundResource(R.drawable.bg_hotel_tour_normal);

                refreshFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.input_fragment, new HotelSearchFragment(), "hotelFragment").commit();
            }
        });

        btnSearchTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                btnSearchHotel.setBackgroundResource(R.drawable.bg_hotel_tour_normal);
                btnSearchTour.setBackgroundResource(R.drawable.bg_hotel_tour_selected);

                refreshFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.input_fragment, new TourSearchFragment(), "tourFragment").commit();
            }
        });

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




}
