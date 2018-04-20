package com.mcc.ghurbo.activity;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
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
import com.mcc.ghurbo.model.MyBookingModel;
import com.mcc.ghurbo.model.SearchHotelModel;
import com.mcc.ghurbo.model.SearchTourModel;
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.DialogUtils;
import com.mcc.ghurbo.utility.PricingUtils;
import com.mcc.ghurbo.utility.Utils;

public class BookNowActivity extends BaseActivity {

    private SearchTourModel searchTourModel;
    private SearchHotelModel searchHotelModel;

    private EditText etNote, etCoupon;
    private Button btnConfirm;

    private boolean fromLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO: Add a form here to re check date, adult, child and number of rooms

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

        if (intent.hasExtra(AppConstants.BUNDLE_FROM_LOGIN)) {
            fromLogin = intent.getBooleanExtra(AppConstants.BUNDLE_FROM_LOGIN, false);
        }

    }

    private void initView() {
        setContentView(R.layout.activity_book_now);
        initToolbar();
        enableBackButton();

        etNote = (EditText) findViewById(R.id.et_note);
        etCoupon = (EditText) findViewById(R.id.et_coupon);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);
    }

    private void initFunctionality() {
        if(fromLogin) {
            bookNow();
        }
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
                    bookNow();
                } else {
                    Utils.showToast(getApplicationContext(), getString(R.string.login_message));
                    ActivityUtils.getInstance().invokeLoginActivity(BookNowActivity.this, searchTourModel, searchHotelModel);
                }
            }
        });
    }


    private void bookNow() {

        final ProgressDialog progressDialog = DialogUtils.showProgressDialog(BookNowActivity.this, getString(R.string.loading), false);

        String userId = AppPreference.getInstance(getApplicationContext()).getString(PrefKey.USER_ID);
        // reserve hotel
        if (searchHotelModel != null) {
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

                    if (data != null) {
                        String bookingNo = (String) data;


                        int days = PricingUtils.getDays(searchHotelModel.getCheckIn(), searchHotelModel.getCheckOut());
                        float totalPrice = PricingUtils.getHotelPrice(searchHotelModel.getRate(), searchHotelModel.getRooms(), days);

                        // TODO: get payment status from booking API
                        // TODO: get total days/nights from booking API to calculate price
                        // TODO: get star from booking API
                        // TODO: get price from booking API

                        MyBookingModel myBookingModel = new MyBookingModel(
                                bookingNo,
                                searchHotelModel.getHotelId(),
                                searchHotelModel.getHotelName(),
                                searchHotelModel.getRoomId(),
                                searchHotelModel.getRoomName(),
                                searchHotelModel.getRooms(),
                                AppConstants.TYPE_HOTELS,
                                "", searchHotelModel.getRate(),
                                searchHotelModel.getCheckIn(),
                                searchHotelModel.getCheckOut(),
                                "", searchHotelModel.getAdult(),
                                searchHotelModel.getChild(), searchHotelModel.getAddress(),
                                searchHotelModel.getImageUrl(),
                                searchHotelModel.getPhoneNumber(),
                                "", "", "", "", searchHotelModel.getLatitude(),
                                searchHotelModel.getLongitude()
                        );
                        myBookingModel.setTotalPrice(String.valueOf(totalPrice));

                        if(progressDialog != null) {
                            DialogUtils.dismissProgressDialog(progressDialog);
                        }

                        ActivityUtils.getInstance().invokeBookingDetailsActivity(BookNowActivity.this, myBookingModel, false);

                    } else {
                        Utils.showToast(getApplicationContext(), getString(R.string.booking_failed));
                    }
                }
            });
            requestReserveHotel.execute();
        }

        // reserve tour
        if (searchTourModel != null) {
            String notes = getString(R.string.concat_note) + searchTourModel.getNotes() + getString(R.string.concat_coupon) + searchTourModel.getCouponCode();
            RequestReserveTour requestReserveTour = new RequestReserveTour(getApplicationContext());
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

                    if (data != null) {
                        String bookingNo = (String) data;

                        float totalPrice = PricingUtils.getTourPrice(
                                searchTourModel.getRateAdult(),
                                searchTourModel.getRateChild(),
                                searchTourModel.getAdult(),
                                searchTourModel.getChild()
                        );

                        // TODO: get price from booking API

                        MyBookingModel myBookingModel = new MyBookingModel(
                                bookingNo,
                                "",
                                "",
                                "",
                                "",
                                "",
                                AppConstants.TYPE_TOURS,
                                "", searchTourModel.getRateAdult(),
                                searchTourModel.getDate(),
                                "",
                                "", searchTourModel.getAdult(),
                                searchTourModel.getChild(), searchTourModel.getAddress(),
                                searchTourModel.getImageUrl(),
                                searchTourModel.getPhoneNumber(),
                                "", "", searchTourModel.getTourPackageId(),
                                searchTourModel.getTourPackageName(), searchTourModel.getLatitude(),
                                searchTourModel.getLongitude()
                        );
                        myBookingModel.setTotalPrice(String.valueOf(totalPrice));

                        if(progressDialog != null) {
                            DialogUtils.dismissProgressDialog(progressDialog);
                        }

                        ActivityUtils.getInstance().invokeBookingDetailsActivity(BookNowActivity.this, myBookingModel, false);

                    } else {
                        Utils.showToast(getApplicationContext(), getString(R.string.booking_failed));
                    }

                }
            });
            requestReserveTour.execute();
        }
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
