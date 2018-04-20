package com.mcc.ghurbo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.bumptech.glide.Glide;
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
                    bookNow();
                } else {
                    Utils.showToast(getApplicationContext(), getString(R.string.login_message));
                    ActivityUtils.getInstance().invokeLoginActivity(ReservationNoteActivity.this, searchTourModel, searchHotelModel);
                }
            }
        });
    }


    private void bookNow() {


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

                    if(data != null) {
                        String bookingNo = (String) data;
                        MyBookingModel myBookingModel = new MyBookingModel(
                                bookingNo,
                                searchHotelModel.getHotelId(),
                                searchHotelModel.getHotelName(),
                                searchHotelModel.getRoomId(),

                                String hotelName, String roomId,
                                String roomName, String roomNumber,
                                String type, String expiry, String userId,
                                String status, String price, String checkin,
                                String checkout, String nights, String adults,
                                String child, String location, String photo,
                                String phone, String stars, String days,
                                String tourId, String tourName, String latitude,
                                String longitude
                        );

                        hotelName.setText(searchHotelModel.getHotelName());

                        tvBookingNumber.setText(getString(R.string.booking_no) + bookingNo);

                        checkInOutPanel.setVisibility(View.VISIBLE);
                        checkin.setText(searchHotelModel.getCheckIn());
                        checkout.setText(searchHotelModel.getCheckOut());

                        datePanel.setVisibility(View.GONE);

                        tvRoomName.setText(searchHotelModel.getRoomName());

                        adultRatePanel.setVisibility(View.GONE);
                        adultCountPanel.setVisibility(View.GONE);
                        childRatePanel.setVisibility(View.GONE);
                        childCountPanel.setVisibility(View.GONE);

                        tvRate.setText(searchHotelModel.getRate() +" "+ AppConstants.CURRENCY);
                        tvTotalAdult.setText(searchHotelModel.getAdult());
                        tvTotalChild.setText(searchHotelModel.getChild());

                        roomsView.setVisibility(View.VISIBLE);
                        tvRooms.setText(searchHotelModel.getRooms());

                        tvLocation.setText(searchHotelModel.getAddress());
                        tvCall.setText(searchHotelModel.getPhoneNumber());

                        String price = searchHotelModel.getRate();
                        float rate = 0;
                        if (price != null) {
                            rate = Float.parseFloat(price);
                        }

                        String totalRooms = searchHotelModel.getRooms();
                        float roomCount = 0;
                        if (totalRooms != null) {
                            roomCount = Float.parseFloat(totalRooms);
                        }

                        float totalPrice = rate * roomCount;

                        tvTotal.setText(String.valueOf(totalPrice));

                        Glide.with(getApplicationContext())
                                .load(searchHotelModel.getImageUrl())
                                .error(R.color.placeholder)
                                .placeholder(R.color.placeholder)
                                .into(ivRoom);

                        callTo = searchHotelModel.getPhoneNumber();

                        if(callTo == null) {
                            btnCall.setVisibility(View.GONE);
                        }

                        ActivityUtils.getInstance().invokeBookingDetailsActivity(ReservationNoteActivity.this, arrayList.get(position), true);

                    } else {
                        Utils.showToast(getApplicationContext(), getString(R.string.booking_failed));
                    }
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

                    if(data != null) {
                        hotelName.setText(searchTourModel.getTourPackageName());
                        String bookingNo = (String) data;
                        tvBookingNumber.setText(getString(R.string.booking_no) + bookingNo);

                        checkInOutPanel.setVisibility(View.GONE);
                        datePanel.setVisibility(View.VISIBLE);
                        date.setText(searchTourModel.getDate());

                        roomInfoView.setVisibility(View.GONE);

                        tvRateAdult.setText(searchTourModel.getRateAdult() +" "+ AppConstants.CURRENCY);
                        tvRateChild.setText(searchTourModel.getRateChild() +" "+ AppConstants.CURRENCY);
                        tvTotalAdult.setText(searchTourModel.getAdult());
                        tvTotalChild.setText(searchTourModel.getChild());
                        roomsView.setVisibility(View.GONE);

                        tvLocation.setText(searchTourModel.getAddress());
                        tvCall.setText(searchTourModel.getPhoneNumber());

                        String adultPrice = searchTourModel.getRateAdult();
                        float rateAdult = 0;
                        if (adultPrice != null) {
                            rateAdult = Float.parseFloat(adultPrice);
                        }

                        String childPrice = searchTourModel.getRateChild();
                        float rateChild = 0;
                        if (adultPrice != null) {
                            rateChild = Float.parseFloat(childPrice);
                        }

                        String totalAdult = searchTourModel.getAdult();
                        float adultCount = 0;
                        if (totalAdult != null) {
                            adultCount = Float.parseFloat(totalAdult);
                        }

                        String totalChild = searchTourModel.getChild();
                        float childCount = 0;
                        if (totalChild != null) {
                            childCount = Float.parseFloat(totalChild);
                        }

                        float totalPrice = (rateAdult * adultCount) + (rateChild * childCount); // TODO: Optimize pricing

                        tvTotal.setText(String.valueOf(totalPrice));

                        ratePanel.setVisibility(View.GONE);

                        callTo = searchTourModel.getPhoneNumber();

                        if(callTo == null) {
                            btnCall.setVisibility(View.GONE);
                        }

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
