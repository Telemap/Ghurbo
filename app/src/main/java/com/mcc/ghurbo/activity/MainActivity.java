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

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.api.helper.RequestHotels;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.fragment.HotelSearchFragment;
import com.mcc.ghurbo.fragment.TourSearchFragment;
import com.mcc.ghurbo.model.LocationModel;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private Button btnSearchHotel, btnSearchTour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /**
         * TODO 1: Implement profile image page after login
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

        btnSearchHotel = (Button) findViewById(R.id.btn_search_hotel);
        btnSearchTour = (Button) findViewById(R.id.btn_search_tour);
    }

    private void initFunctionality() {
        loadProfileData();
    }

    private void initListener(final Bundle savedInstanceState) {
        initFragment(savedInstanceState);

        btnSearchHotel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refreshFragment();
                getSupportFragmentManager().beginTransaction().add(R.id.input_fragment, new HotelSearchFragment(), "hotelFragment").commit();
            }
        });

        btnSearchTour.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
