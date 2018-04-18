package com.mcc.ghurbo.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcc.ghurbo.R;
import com.mcc.ghurbo.adapter.TourAmenitiesAdapter;
import com.mcc.ghurbo.api.helper.RequestTourDetails;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.model.AmenityModel;
import com.mcc.ghurbo.model.RoomDetailsModel;
import com.mcc.ghurbo.model.SearchHotelModel;
import com.mcc.ghurbo.model.SearchTourModel;
import com.mcc.ghurbo.model.TourDetailsModel;
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.Utils;

import java.util.ArrayList;

public class RoomDetailsActivity extends BaseActivity {

    private RoomDetailsModel roomDetailsModel;
    private SearchHotelModel searchHotelModel;

    private CollapsingToolbarLayout collapsingToolbar;
    private TextView title, price, perNight, discountPercent, discountSubtitle,
            finalPrice, extraPrices, capacity;
    private WebView webView;
    private Button btnBookNow;
    private ImageView ivTransitionImg;
    private LinearLayout breakfast;

    private RecyclerView recyclerView;
    private ArrayList<AmenityModel> amenityModels;
    private TourAmenitiesAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();
        initView();
        initFunctionality();
        setDataToUi();
        initListener();

    }

    private void initVariables() {
        Intent intent = getIntent();

        if (intent.hasExtra(AppConstants.BUNDLE_ROOM_DETAILS)) {
            roomDetailsModel = intent.getParcelableExtra(AppConstants.BUNDLE_ROOM_DETAILS);
        }

        if (intent.hasExtra(AppConstants.BUNDLE_KEY_HOTEL_MODEL)) {
            searchHotelModel = intent.getParcelableExtra(AppConstants.BUNDLE_KEY_HOTEL_MODEL);
        }

        amenityModels = new ArrayList<>();

    }

    private void initView() {
        setContentView(R.layout.activity_room_details);
        initToolbar();
        enableBackButton();

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        title = (TextView) findViewById(R.id.title);
        capacity = (TextView) findViewById(R.id.capacity);
        price = (TextView) findViewById(R.id.price);
        perNight = (TextView) findViewById(R.id.per_night);
        discountPercent = (TextView) findViewById(R.id.discount_percent);
        discountSubtitle = (TextView) findViewById(R.id.discount_subtitle);
        finalPrice = (TextView) findViewById(R.id.final_price);
        extraPrices = (TextView) findViewById(R.id.extra_prices);
        breakfast = (LinearLayout) findViewById(R.id.breakfast);
        webView = (WebView) findViewById(R.id.web_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnBookNow = (Button) findViewById(R.id.btn_book_now);
        ivTransitionImg = (ImageView) findViewById(R.id.iv_transition);

    }

    private void initFunctionality() {
        invokeMessenger();
        webView.getSettings().setTextZoom(85);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setVerticalScrollBarEnabled(false);

        adapter = new TourAmenitiesAdapter(getApplicationContext(), amenityModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(RoomDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        Glide.with(getApplicationContext())
                .load(roomDetailsModel.getThumbnailImage())
                .error(R.color.placeholder)
                .placeholder(R.color.placeholder)
                .into(ivTransitionImg);
    }

    private void initListener() {
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchHotelModel.setRate(roomDetailsModel.getTotalPrice());
                searchHotelModel.setRoomId(roomDetailsModel.getRoomId());
                searchHotelModel.setRoomName(roomDetailsModel.getTitle());
                ActivityUtils.getInstance().invokeReserveNoteActivity(RoomDetailsActivity.this, null, searchHotelModel);
            }
        });

    }

    private void setDataToUi() {

        collapsingToolbar.setTitle(roomDetailsModel.getTitle());
        title.setText(roomDetailsModel.getTitle());
        capacity.setText(
                getString(R.string.capacity) +
                        roomDetailsModel.getMaxAdults() +
                        getString(R.string.adult) +
                        getString(R.string.and) +
                        roomDetailsModel.getMaxChild() +
                        getString(R.string.child) +
                        getString(R.string.max) +
                        roomDetailsModel.getMaxQuantity() +
                        getString(R.string.total));

        price.setText(AppConstants.CURRENCY + roomDetailsModel.getPrice());
        perNight.setText(AppConstants.CURRENCY + roomDetailsModel.getPerNight()+ getString(R.string.per_night));
        discountPercent.setText(roomDetailsModel.getDiscount()+ getString(R.string.percent));
        discountSubtitle.setText("");
        finalPrice.setText(AppConstants.CURRENCY + roomDetailsModel.getTotalPrice());

        if(roomDetailsModel.getExtraBeds() != null && !roomDetailsModel.getExtraBeds().equals("0")) {
            extraPrices.setText(AppConstants.CURRENCY + roomDetailsModel.getExtrabedCharges() + getString(R.string.extra_bed) + getString(R.string.max) + roomDetailsModel.getExtraBeds() + getString(R.string.total));
        }

        if(roomDetailsModel.getBreakfastInclude()) {
            breakfast.setVisibility(View.VISIBLE);
        } else {
            breakfast.setVisibility(View.GONE);
        }

        webView.loadData(roomDetailsModel.getDesc(), "text/html; charset=utf-8", "UTF-8");

        amenityModels.clear();
        amenityModels.addAll(roomDetailsModel.getAmenities());
        if(!amenityModels.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        } else {
            recyclerView.setVisibility(View.GONE);
        }

        ivTransitionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.getInstance().invokeImages(RoomDetailsActivity.this, roomDetailsModel.getImages(), ivTransitionImg);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            supportFinishAfterTransition();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
