package com.mcc.ghurbo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.data.preference.AppPreference;
import com.mcc.ghurbo.data.preference.PrefKey;
import com.mcc.ghurbo.model.SearchHotelModel;
import com.mcc.ghurbo.model.SearchTourModel;
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.Utils;

public class ReservationNoteActivity extends BaseActivity {

    private SearchTourModel searchTourModel;
    private SearchHotelModel searchHotelModel;

    private EditText etNote, etCoupon;
    private Button btnConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Add a form here to re check date, adult, child and number of rooms

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

                if (searchHotelModel != null) {
                    searchHotelModel.setNotes(etNote.getText().toString());
                    searchHotelModel.setCouponCode(etCoupon.getText().toString());
                }

                if (searchTourModel != null) {
                    searchTourModel.setNotes(etNote.getText().toString());
                    searchTourModel.setCouponCode(etCoupon.getText().toString());
                }

                boolean isLogin = AppPreference.getInstance(getApplicationContext()).getBoolean(PrefKey.LOGIN);
                if(isLogin) {
                    ActivityUtils.getInstance().invokeReserveConfirmActivity(ReservationNoteActivity.this, searchTourModel, searchHotelModel);
                } else {
                    Utils.showToast(getApplicationContext(), getString(R.string.login_message));
                    ActivityUtils.getInstance().invokeLoginActivity(ReservationNoteActivity.this, searchTourModel, searchHotelModel);
                }
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home){
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
