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
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcc.ghurbo.R;
import com.mcc.ghurbo.adapter.HotelAmenitiesAdapter;
import com.mcc.ghurbo.adapter.RoomListAdapter;
import com.mcc.ghurbo.adapter.TourAmenitiesAdapter;
import com.mcc.ghurbo.api.helper.RequestHotelDetails;
import com.mcc.ghurbo.api.helper.RequestTourDetails;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.listener.ItemClickListener;
import com.mcc.ghurbo.model.AmenityModel;
import com.mcc.ghurbo.model.HotelDetailsModel;
import com.mcc.ghurbo.model.RoomDetailsModel;
import com.mcc.ghurbo.model.SearchHotelModel;
import com.mcc.ghurbo.model.SearchTourModel;
import com.mcc.ghurbo.model.TourDetailsModel;
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.Utils;

import java.util.ArrayList;

public class HotelDetailsActivity extends BaseActivity {

    private SearchHotelModel searchHotelModel;

    private CollapsingToolbarLayout collapsingToolbar;
    private TextView title, location, starInfo, checkin, checkout, ratingValue;
    private ImageButton ibMap;
    private RatingBar star, rating;
    private WebView webView;
    private FloatingActionButton fabFav;
    private ImageView ivTransitionImg;

    private RecyclerView recyclerView;
    private ArrayList<AmenityModel> amenityModels;
    private HotelAmenitiesAdapter adapter;

    private RecyclerView rvRoomList;
    private ArrayList<RoomDetailsModel> roomDetailsModels;
    private RoomListAdapter roomListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();
        initView();
        initFunctionality();
        loadData();
        initListener();

    }

    private void initVariables() {
        Intent intent = getIntent();

        if (intent.hasExtra(AppConstants.BUNDLE_KEY_HOTEL_MODEL)) {
            searchHotelModel = intent.getParcelableExtra(AppConstants.BUNDLE_KEY_HOTEL_MODEL);
        }

        amenityModels = new ArrayList<>();
        roomDetailsModels = new ArrayList<>();

    }

    private void initView() {
        setContentView(R.layout.activity_hotel_details);
        initToolbar();
        enableBackButton();
        initLoader();

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        title = (TextView) findViewById(R.id.title);
        star = (RatingBar) findViewById(R.id.star);
        rating = (RatingBar) findViewById(R.id.rating);
        checkin = (TextView) findViewById(R.id.checkin);
        checkout = (TextView) findViewById(R.id.checkout);
        ratingValue = (TextView) findViewById(R.id.rating_value);
        starInfo = (TextView) findViewById(R.id.star_info);
        location = (TextView) findViewById(R.id.location);
        ibMap = (ImageButton) findViewById(R.id.ib_map);
        webView = (WebView) findViewById(R.id.web_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        fabFav = (FloatingActionButton) findViewById(R.id.fab_fav);
        ivTransitionImg = (ImageView) findViewById(R.id.iv_transition);
        rvRoomList = (RecyclerView) findViewById(R.id.rv_room_list);

    }

    private void initFunctionality() {
        invokeMessenger();
        webView.getSettings().setTextZoom(85);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setVerticalScrollBarEnabled(false);

        adapter = new HotelAmenitiesAdapter(getApplicationContext(), amenityModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(HotelDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        roomListAdapter = new RoomListAdapter(getApplicationContext(), roomDetailsModels);
        rvRoomList.setLayoutManager(new LinearLayoutManager(HotelDetailsActivity.this));
        rvRoomList.setAdapter(roomListAdapter);

        Glide.with(getApplicationContext())
                .load(searchHotelModel.getImageUrl())
                .error(R.color.placeholder)
                .into(ivTransitionImg);
    }

    private void initListener() {

        fabFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    private void loadData() {

        showLoader();
        RequestHotelDetails requestHotelDetails = new RequestHotelDetails(getApplicationContext());
        requestHotelDetails.buildParams(searchHotelModel.getHotelId(), searchHotelModel.getCheckIn(), searchHotelModel.getCheckOut());
        requestHotelDetails.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {

                HotelDetailsModel hotelDetailsModel = (HotelDetailsModel) data;
                if (hotelDetailsModel != null) {
                    hideLoader();
                    setDataToUi(hotelDetailsModel);
                } else {
                    showEmptyView();
                }
            }
        });
        requestHotelDetails.execute();
    }

    private void setDataToUi(final HotelDetailsModel hotelDetailsModel) {

        collapsingToolbar.setTitle(hotelDetailsModel.getHotelTitle());
        title.setText(hotelDetailsModel.getHotelTitle());

        String starStr = hotelDetailsModel.getHotelStars();
        if (starStr != null && !starStr.isEmpty()) {
            starInfo.setText(starStr + getString(R.string.star_hotel));
            star.setRating(Float.parseFloat(starStr));
        } else {
            starInfo.setText(starStr + getString(R.string.not_star_info));
        }

        String ratingStr = hotelDetailsModel.getHotelRatings();
        if (ratingStr != null && !ratingStr.isEmpty()) {
            ratingValue.setText(getString(R.string.rating) + ratingStr);
            rating.setRating(Float.parseFloat(ratingStr));
        } else {
            ratingValue.setText(getString(R.string.no_rating));
        }

        checkin.setText(hotelDetailsModel.getCheckInTime());
        checkout.setText(hotelDetailsModel.getCheckOutTime());

        location.setText(hotelDetailsModel.getLocation());

        ibMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.invokeMap(HotelDetailsActivity.this, hotelDetailsModel.getLatitude(), hotelDetailsModel.getLongitude());
            }
        });

        webView.loadData(hotelDetailsModel.getHotelDesc(), "text/html; charset=utf-8", "UTF-8");

        amenityModels.clear();
        amenityModels.addAll(hotelDetailsModel.getAmenities());
        if (!amenityModels.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        } else {
            recyclerView.setVisibility(View.GONE);
        }

        ivTransitionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.getInstance().invokeImages(HotelDetailsActivity.this, hotelDetailsModel.getHotelImages(), ivTransitionImg);
            }
        });

        roomDetailsModels.clear();
        roomDetailsModels.addAll(hotelDetailsModel.getRoomDetails());
        if (!roomDetailsModels.isEmpty()) {
            rvRoomList.setVisibility(View.VISIBLE);
            roomListAdapter.notifyDataSetChanged();
        } else {
            rvRoomList.setVisibility(View.GONE);
        }

        roomListAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                ImageView imageView = view.findViewById(R.id.icon);
                ActivityUtils.getInstance().invokeRoomDetailsActivity(HotelDetailsActivity.this, hotelDetailsModel.getRoomDetails().get(position), searchHotelModel, imageView);
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
