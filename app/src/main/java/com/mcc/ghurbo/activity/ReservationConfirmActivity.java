package com.mcc.ghurbo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
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

    private TextView hotelName, tvBookingNumber, checkin,
            checkout, tvRoomName, tvRateAdult, tvTotalAdult,
            tvRateChild, tvTotalChild, date, tvTotal, tvRooms, tvRate, infoText;
    private RelativeLayout btnLocation, btnCall, roomInfoView;
    private LinearLayout roomsView, checkInOutPanel, datePanel, adultRatePanel,
            adultCountPanel, childRatePanel, childCountPanel, ratePanel;
    private Button btnEmail, btnPrint;
    private ImageView ivRoom;

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

        hotelName = (TextView) findViewById(R.id.hotel_name);
        tvBookingNumber = (TextView) findViewById(R.id.tv_booking_number);
        checkin = (TextView) findViewById(R.id.checkin);
        checkout = (TextView) findViewById(R.id.checkout);
        tvRoomName = (TextView) findViewById(R.id.tv_room_name);
        tvRateAdult = (TextView) findViewById(R.id.tv_rate_adult);
        tvTotalAdult = (TextView) findViewById(R.id.tv_total_adult);
        tvRateChild = (TextView) findViewById(R.id.tv_rate_child);
        tvTotalChild = (TextView) findViewById(R.id.tv_total_child);
        tvTotal = (TextView) findViewById(R.id.tv_total);
        tvRooms = (TextView) findViewById(R.id.tv_rooms);
        tvRate = (TextView) findViewById(R.id.tv_rate);
        date = (TextView) findViewById(R.id.date);
        infoText = (TextView) findViewById(R.id.info_text);

        btnLocation = (RelativeLayout) findViewById(R.id.btn_location);
        btnCall = (RelativeLayout) findViewById(R.id.btn_call);
        roomInfoView = (RelativeLayout) findViewById(R.id.room_info_view);

        roomsView = (LinearLayout) findViewById(R.id.rooms_view);
        checkInOutPanel = (LinearLayout) findViewById(R.id.check_in_out_panel);
        datePanel = (LinearLayout) findViewById(R.id.date_panel);
        ratePanel = (LinearLayout) findViewById(R.id.rate_panel);
        adultRatePanel = (LinearLayout) findViewById(R.id.adult_rate_panel);
        adultCountPanel = (LinearLayout) findViewById(R.id.adult_count_panel);
        childRatePanel = (LinearLayout) findViewById(R.id.child_rate_panel);
        childCountPanel = (LinearLayout) findViewById(R.id.child_count_panel);

        btnEmail = (Button) findViewById(R.id.btn_email);
        btnPrint = (Button) findViewById(R.id.btn_print);

        ivRoom = (ImageView) findViewById(R.id.iv_room);

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

                    if(data != null) {
                        hotelName.setText(searchHotelModel.getHotelName());
                        String bookingNo = (String) data;
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

                    } else {
                        showEmptyView();
                        infoText.setText(getString(R.string.booking_failed));
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

                    hideLoader();

                    if(data != null) {
                        hotelName.setText(searchTourModel.getTourPackageName());
                        String bookingNo = (String) data;
                        tvBookingNumber.setText(getString(R.string.booking_no) + bookingNo);

                        checkInOutPanel.setVisibility(View.GONE);
                        datePanel.setVisibility(View.VISIBLE);
                        date.setText(searchTourModel.getDate());

                        roomInfoView.setVisibility(View.GONE);

                        tvRateAdult.setText(searchTourModel.getRateAdult() + AppConstants.CURRENCY);
                        tvRateChild.setText(searchTourModel.getRateChild() + AppConstants.CURRENCY);
                        tvTotalAdult.setText(searchTourModel.getAdult());
                        tvTotalChild.setText(searchTourModel.getChild());
                        roomsView.setVisibility(View.GONE);

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

                    } else {
                        showEmptyView();
                        infoText.setText(getString(R.string.booking_failed));
                    }

                }
            });
            requestReserveTour.execute();
        }

    }

    private void initListeners() {
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

}
