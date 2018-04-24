package com.mcc.ghurbo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcc.ghurbo.R;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.model.MyBookingModel;
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.BitmapEmailUtils;
import com.mcc.ghurbo.utility.PermissionUtils;
import com.mcc.ghurbo.utility.PrintUtils;
import com.mcc.ghurbo.utility.Utils;

public class BookingDetailsActivity extends BaseActivity {

    private MyBookingModel myBookingModel;

    private TextView hotelName, tvBookingNumber, checkin, tvLocation,
            checkout, tvRoomName, tvTotalAdult, tvCall, tvTotalChild, date, tvTotal, tvRooms;
    private RelativeLayout btnLocation, btnCall, roomInfoView;
    private LinearLayout roomsView, checkInOutPanel, datePanel, parentView;
    private Button btnEmail, btnPrint;
    private ImageView ivRoom;

    private String callTo;
    private boolean fromHistory;

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

        if (intent.hasExtra(AppConstants.BUNDLE_BOOKING_DETAILS)) {
            myBookingModel = intent.getParcelableExtra(AppConstants.BUNDLE_BOOKING_DETAILS);
        }

        if (intent.hasExtra(AppConstants.BUNDLE_FROM_HISTORY)) {
            fromHistory = intent.getBooleanExtra(AppConstants.BUNDLE_FROM_HISTORY, false);
        }

    }

    private void initView() {
        setContentView(R.layout.activity_booking_details);
        initToolbar();
        enableBackButton();
        initLoader();
        hideLoader();

        hotelName = (TextView) findViewById(R.id.hotel_name);
        tvBookingNumber = (TextView) findViewById(R.id.tv_booking_number);
        checkin = (TextView) findViewById(R.id.checkin);
        checkout = (TextView) findViewById(R.id.checkout);
        tvRoomName = (TextView) findViewById(R.id.tv_room_name);
        tvTotalAdult = (TextView) findViewById(R.id.tv_total_adult);
        tvTotalChild = (TextView) findViewById(R.id.tv_total_child);
        tvTotal = (TextView) findViewById(R.id.tv_total);
        tvRooms = (TextView) findViewById(R.id.tv_rooms);
        date = (TextView) findViewById(R.id.date);
        tvLocation = (TextView) findViewById(R.id.tv_location);
        tvCall = (TextView) findViewById(R.id.tv_call);

        btnLocation = (RelativeLayout) findViewById(R.id.btn_location);
        btnCall = (RelativeLayout) findViewById(R.id.btn_call);
        roomInfoView = (RelativeLayout) findViewById(R.id.room_info_view);

        roomsView = (LinearLayout) findViewById(R.id.rooms_view);
        checkInOutPanel = (LinearLayout) findViewById(R.id.check_in_out_panel);
        datePanel = (LinearLayout) findViewById(R.id.date_panel);

        btnEmail = (Button) findViewById(R.id.btn_email);
        btnPrint = (Button) findViewById(R.id.btn_print);

        ivRoom = (ImageView) findViewById(R.id.iv_room);
        parentView = (LinearLayout) findViewById(R.id.parent_view);

    }

    private void initFunctionality() {

        String title = null;
        if (myBookingModel.getType().equals(AppConstants.TYPE_HOTELS)) {
            title = myBookingModel.getHotelName();

            Glide.with(getApplicationContext())
                    .load(myBookingModel.getPhoto())
                    .error(R.color.placeholder)
                    .placeholder(R.color.placeholder)
                    .into(ivRoom);
            tvRoomName.setText(myBookingModel.getRoomName());
            tvRooms.setText(myBookingModel.getRoomNumber());

            checkInOutPanel.setVisibility(View.VISIBLE);
            datePanel.setVisibility(View.GONE);
            roomsView.setVisibility(View.VISIBLE);
            roomInfoView.setVisibility(View.VISIBLE);
        } else if (myBookingModel.getType().equals(AppConstants.TYPE_TOURS)) {
            title = myBookingModel.getTourName();

            checkInOutPanel.setVisibility(View.GONE);
            datePanel.setVisibility(View.VISIBLE);
            roomsView.setVisibility(View.GONE);
            roomInfoView.setVisibility(View.GONE);
        }
        hotelName.setText(title);
        tvBookingNumber.setText(getString(R.string.booking_no) + myBookingModel.getBookingId());
        checkin.setText(myBookingModel.getCheckin());
        checkout.setText(myBookingModel.getCheckout());
        date.setText(myBookingModel.getCheckin());

        tvTotalAdult.setText(myBookingModel.getAdults());
        tvTotalChild.setText(myBookingModel.getChild());
        tvLocation.setText(myBookingModel.getLocation());
        tvTotal.setText(myBookingModel.getTotalPrice());

        callTo = myBookingModel.getPhone();
        if (callTo == null) {
            btnCall.setVisibility(View.GONE);
        }
        tvCall.setText(callTo);
    }

    private void initListeners() {
        btnEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                emailInvoice();
            }
        });

        btnPrint.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                printInvoice();
            }
        });

        btnLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (myBookingModel != null) {
                    Utils.invokeMap(BookingDetailsActivity.this, myBookingModel.getLatitude(), myBookingModel.getLongitude());
                } else {
                    Utils.invokeMap(BookingDetailsActivity.this, myBookingModel.getLatitude(), myBookingModel.getLongitude());
                }
            }
        });

        btnCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.makePhoneCall(BookingDetailsActivity.this, callTo);
            }
        });
    }

    private void printInvoice() {
        btnEmail.setVisibility(View.INVISIBLE);
        btnPrint.setVisibility(View.INVISIBLE);
        PrintUtils printUtils = new PrintUtils(parentView, BookingDetailsActivity.this, getString(R.string.ghurbo_print));
        printUtils.setTaskListener(new PrintUtils.TaskListener() {
            @Override
            public void onTaskComplete() {
                btnEmail.setVisibility(View.VISIBLE);
                btnPrint.setVisibility(View.VISIBLE);
            }
        });
        printUtils.execute();
    }

    private void emailInvoice() {
        if (PermissionUtils.isPermissionGranted(BookingDetailsActivity.this, PermissionUtils.SD_WRITE_PERMISSIONS, PermissionUtils.REQUEST_WRITE_STORAGE)) {
            btnEmail.setVisibility(View.INVISIBLE);
            btnPrint.setVisibility(View.INVISIBLE);
            BitmapEmailUtils bitmapEmailUtils = new BitmapEmailUtils(parentView, BookingDetailsActivity.this, getString(R.string.ghurbo_print));
            bitmapEmailUtils.setTaskListener(new BitmapEmailUtils.TaskListener() {
                @Override
                public void onTaskComplete() {
                    btnEmail.setVisibility(View.VISIBLE);
                    btnPrint.setVisibility(View.VISIBLE);
                }
            });
            bitmapEmailUtils.execute();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (PermissionUtils.isPermissionResultGranted(grantResults)) {
            if (requestCode == PermissionUtils.REQUEST_CALL) {
                Utils.makePhoneCall(BookingDetailsActivity.this, callTo);
            } else if (requestCode == PermissionUtils.REQUEST_WRITE_STORAGE) {
                emailInvoice();
            }
        } else {
            Utils.showToast(BookingDetailsActivity.this, getString(R.string.permission_not_granted));
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(fromHistory) {
            finish();
        } else {
            ActivityUtils.getInstance().invokeActivity(BookingDetailsActivity.this, MainActivity.class, true);
        }
    }

}