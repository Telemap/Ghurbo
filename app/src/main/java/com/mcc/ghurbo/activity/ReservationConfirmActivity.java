package com.mcc.ghurbo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.api.helper.RequestReserveHotel;
import com.mcc.ghurbo.api.helper.RequestReserveTour;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.data.preference.AppPreference;
import com.mcc.ghurbo.data.preference.PrefKey;
import com.mcc.ghurbo.model.SearchHotelModel;
import com.mcc.ghurbo.model.SearchTourModel;

public class ReservationConfirmActivity extends BaseActivity {

    private SearchTourModel searchTourModel;
    private SearchHotelModel searchHotelModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVar();
        initView();
        initFunctionality();
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
        setContentView(R.layout.activity_reservation_confirm);
        initToolbar();
        enableBackButton();
        initLoader();

    }

    private void initFunctionality() {

        showLoader();

        String userId = AppPreference.getInstance(getApplicationContext()).getString(PrefKey.USER_ID);
        // reserve hotel
        if(searchHotelModel != null) {
            String notes = getString(R.string.concat_note) + searchHotelModel.getNotes() + getString(R.string.concat_coupon) + searchHotelModel.getCouponCode();

            RequestReserveHotel requestReserveHotel = new RequestReserveHotel(getApplicationContext());
            requestReserveHotel.buildParams(userId,
                    searchHotelModel.getHotelId(),
                    searchHotelModel.getRoomId(),
                    searchHotelModel.getCheckIn(),
                    searchHotelModel.getCheckOut(),
                    searchHotelModel.getRooms(),
                    searchHotelModel.getAdult(),
                    searchHotelModel.getChild(),
                    notes
                    );
            requestReserveHotel.setResponseListener(new ResponseListener() {
                @Override
                public void onResponse(Object data) {


                    hideLoader();
                }
            });
            requestReserveHotel.execute();
        }

        // reserve tour
        if(searchTourModel != null) {
            String notes = getString(R.string.concat_note) + searchTourModel.getNotes() + getString(R.string.concat_coupon) + searchTourModel.getCouponCode();
            RequestReserveTour requestReserveTour =  new RequestReserveTour(getApplicationContext());
            requestReserveTour.buildParams(
                    userId,
                    searchTourModel.getTourPackageId(),
                    searchTourModel.getDate(),
                    searchTourModel.getAdult(),
                    searchTourModel.getChild(),
                    searchTourModel.getChild(),
                    notes

            );
            requestReserveTour.setResponseListener(new ResponseListener() {
                @Override
                public void onResponse(Object data) {


                    hideLoader();
                }
            });
            requestReserveTour.execute();
        }

    }

    private void initListeners() {

    }

}
