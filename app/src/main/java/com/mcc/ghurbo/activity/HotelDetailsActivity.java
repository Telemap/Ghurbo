package com.mcc.ghurbo.activity;


import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import com.bumptech.glide.Priority;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.mcc.ghurbo.R;
import com.mcc.ghurbo.adapter.HotelAmenitiesAdapter;
import com.mcc.ghurbo.adapter.RoomListAdapter;
import com.mcc.ghurbo.adapter.TourAmenitiesAdapter;
import com.mcc.ghurbo.api.helper.RequestAddToFav;
import com.mcc.ghurbo.api.helper.RequestHotelDetails;
import com.mcc.ghurbo.api.helper.RequestTourDetails;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.data.preference.AppPreference;
import com.mcc.ghurbo.data.preference.PrefKey;
import com.mcc.ghurbo.listener.ItemClickListener;
import com.mcc.ghurbo.model.AmenityModel;
import com.mcc.ghurbo.model.FavoriteModel;
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
    private HotelDetailsModel hotelDetailsModel;

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

    private static final String DATA_KEY = "data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();
        initView();
        initFunctionality();
        loadData(savedInstanceState);
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
                .placeholder(R.color.placeholder)
                .priority(Priority.IMMEDIATE)
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .dontAnimate()
                .into(ivTransitionImg);
    }

    private void initListener() {

        fabFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeFav();
            }
        });

    }

    private void loadData(Bundle savedInstanceState) {

        if(savedInstanceState == null) {
            String userId = AppPreference.getInstance(getApplicationContext()).getString(PrefKey.USER_ID);

            showLoader();
            RequestHotelDetails requestHotelDetails = new RequestHotelDetails(getApplicationContext());
            requestHotelDetails.buildParams(userId, searchHotelModel.getHotelId(), searchHotelModel.getCheckIn(), searchHotelModel.getCheckOut());
            requestHotelDetails.setResponseListener(new ResponseListener() {
                @Override
                public void onResponse(Object data) {

                    HotelDetailsModel model = (HotelDetailsModel) data;
                    if (model != null) {
                        hotelDetailsModel = model;
                        setDataToUi();
                    } else {
                        showEmptyView();
                    }
                }
            });
            requestHotelDetails.execute();
        }
    }

    private void setDataToUi() {

        hideLoader();

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

                searchHotelModel.setAddress(hotelDetailsModel.getLocation());
                searchHotelModel.setLatitude(hotelDetailsModel.getLatitude());
                searchHotelModel.setLongitude(hotelDetailsModel.getLongitude());
                searchHotelModel.setPhoneNumber(hotelDetailsModel.getPhoneNumber());

                ImageView imageView = view.findViewById(R.id.icon);
                ActivityUtils.getInstance().invokeRoomDetailsActivity(HotelDetailsActivity.this, hotelDetailsModel.getRoomDetails().get(position), searchHotelModel, imageView);
            }
        });

        setFavStatus(hotelDetailsModel.isFavorite());

    }


    private void makeFav() {
        boolean isLoggedIn = AppPreference.getInstance(getApplicationContext()).getBoolean(PrefKey.LOGIN);

        if (isLoggedIn) {
            if (hotelDetailsModel != null) {
                String userId = AppPreference.getInstance(getApplicationContext()).getString(PrefKey.USER_ID);
                RequestAddToFav requestAddToFav = new RequestAddToFav(getApplicationContext());
                requestAddToFav.buildParams(userId, AppConstants.TYPE_HOTELS, hotelDetailsModel.getHotelId());
                requestAddToFav.execute();

                if (hotelDetailsModel.isFavorite()) {
                    removeFromFavorite(hotelDetailsModel.getHotelId());
                    hotelDetailsModel.setFavorite(false);
                } else {
                    addToFavorite(hotelDetailsModel.getHotelId(), hotelDetailsModel.getHotelTitle(),
                            hotelDetailsModel.getThumbnailImage(), "", hotelDetailsModel.getLocation(),
                            AppConstants.TYPE_HOTELS, "", searchHotelModel.getAdult(), searchHotelModel.getChild(),
                            searchHotelModel.getCheckIn(), searchHotelModel.getCheckOut(), searchHotelModel.getRooms());
                    hotelDetailsModel.setFavorite(true);
                }
                setFavStatus(hotelDetailsModel.isFavorite());
            }
        } else {
            Snackbar snackbar = Snackbar.make(fabFav, getString(R.string.login_msg), Snackbar.LENGTH_LONG);
            snackbar.setAction(getString(R.string.login), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtils.getInstance().invokeLoginForFavActivity(HotelDetailsActivity.this);
                }
            });
            snackbar.show();
        }
    }

    private void setFavStatus(boolean isFav) {
        if (isFav) {
            fabFav.setImageResource(R.drawable.ic_fav_marked);
        } else {
            fabFav.setImageResource(R.drawable.ic_fav_unmarked);
        }
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

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelable(DATA_KEY, hotelDetailsModel);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if(state != null) {
            hotelDetailsModel = state.getParcelable(DATA_KEY);
            setDataToUi();

        }
    }
}
