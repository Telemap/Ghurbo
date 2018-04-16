package com.mcc.ghurbo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.model.SearchHotelModel;
import com.mcc.ghurbo.model.SearchTourModel;

public class ReservationNoteActivity extends BaseActivity {


    private SearchTourModel searchTourModel;
    private SearchHotelModel searchHotelModel;

    private EditText etNote, etCoupon;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVar();
        initView();
        initListeners();
        invokeMessenger();

    }

    private void initVar() {
        Intent intent = getIntent();

        if (intent.hasExtra(AppConstants.BUNDLE_KEY_HOTEL_MODEL)) {
            searchHotelModel = intent.getParcelableExtra(AppConstants.BUNDLE_KEY_HOTEL_MODEL);
        }

        if (intent.hasExtra(AppConstants.BUNDLE_KEY_TOUR_MODEL)) {
            searchTourModel = intent.getParcelableExtra(AppConstants.BUNDLE_KEY_TOUR_MODEL);
        }
    }

    private void initView() {
        setContentView(R.layout.activity_reservation_note);
        initToolbar();
        enableBackButton();

        etNote = (EditText) findViewById(R.id.et_note);
        etCoupon = (EditText) findViewById(R.id.et_coupon);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);

    }

    private void initListeners() {
        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
