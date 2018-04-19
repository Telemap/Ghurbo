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
import com.mcc.ghurbo.R;
import com.mcc.ghurbo.adapter.TourAmenitiesAdapter;
import com.mcc.ghurbo.api.helper.RequestAddToFav;
import com.mcc.ghurbo.api.helper.RequestTourDetails;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.data.preference.AppPreference;
import com.mcc.ghurbo.data.preference.PrefKey;
import com.mcc.ghurbo.model.AmenityModel;
import com.mcc.ghurbo.model.SearchTourModel;
import com.mcc.ghurbo.model.TourDetailsModel;
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.Utils;

import java.util.ArrayList;

public class TourDetailsActivity extends BaseActivity {

    private SearchTourModel searchTourModel;
    private TourDetailsModel tourDetailsModel;

    private CollapsingToolbarLayout collapsingToolbar;
    private TextView title, adultPrice, adultMax, childPrice, childMax,
            tvDuration, location;
    private ImageButton ibMap;
    private RatingBar rating;
    private WebView webView;
    private Button btnBookNow;
    private FloatingActionButton fabFav;
    private ImageView ivTransitionImg;

    private RecyclerView recyclerView;
    private ArrayList<AmenityModel> amenityModels;
    private TourAmenitiesAdapter adapter;

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

        if (intent.hasExtra(AppConstants.BUNDLE_KEY_TOUR_MODEL)) {
            searchTourModel = intent.getParcelableExtra(AppConstants.BUNDLE_KEY_TOUR_MODEL);
        }

        amenityModels = new ArrayList<>();

    }

    private void initView() {
        setContentView(R.layout.activity_tour_details);
        initToolbar();
        enableBackButton();
        initLoader();

        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        title = (TextView) findViewById(R.id.title);
        rating = (RatingBar) findViewById(R.id.rating);
        adultPrice = (TextView) findViewById(R.id.adult_price);
        adultMax = (TextView) findViewById(R.id.adult_max);
        childPrice = (TextView) findViewById(R.id.child_price);
        childMax = (TextView) findViewById(R.id.child_max);
        tvDuration = (TextView) findViewById(R.id.tv_duration);
        location = (TextView) findViewById(R.id.location);
        ibMap = (ImageButton) findViewById(R.id.ib_map);
        webView = (WebView) findViewById(R.id.web_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        btnBookNow = (Button) findViewById(R.id.btn_book_now);
        fabFav = (FloatingActionButton) findViewById(R.id.fab_fav);
        ivTransitionImg = (ImageView) findViewById(R.id.iv_transition);

    }

    private void initFunctionality() {
        invokeMessenger();
        webView.getSettings().setTextZoom(85);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setVerticalScrollBarEnabled(false);

        adapter = new TourAmenitiesAdapter(getApplicationContext(), amenityModels);
        recyclerView.setLayoutManager(new LinearLayoutManager(TourDetailsActivity.this, LinearLayoutManager.HORIZONTAL, false));
        recyclerView.setAdapter(adapter);

        Glide.with(getApplicationContext())
                .load(searchTourModel.getImageUrl())
                .error(R.color.placeholder)
                .placeholder(R.color.placeholder)
                .into(ivTransitionImg);
    }

    private void initListener() {
        btnBookNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.getInstance().invokeReserveNoteActivity(TourDetailsActivity.this, searchTourModel, null);
            }
        });

        fabFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makeFav();
            }
        });
    }

    private void loadData() {

        String userId = AppPreference.getInstance(getApplicationContext()).getString(PrefKey.USER_ID);

        showLoader();
        RequestTourDetails requestTourDetails = new RequestTourDetails(getApplicationContext());
        requestTourDetails.buildParams(userId, searchTourModel.getTourPackageId());
        requestTourDetails.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {

                TourDetailsModel model = (TourDetailsModel) data;
                if (model != null) {
                    hideLoader();
                    tourDetailsModel = model;
                    setDataToUi();
                } else {
                    showEmptyView();
                }
            }
        });
        requestTourDetails.execute();
    }

    private void setDataToUi() {

        collapsingToolbar.setTitle(tourDetailsModel.getTourTitle());
        title.setText(tourDetailsModel.getTourTitle());

        String ratingStr = tourDetailsModel.getTourStars();
        if (ratingStr != null && !ratingStr.isEmpty()) {
            rating.setRating(Float.parseFloat(ratingStr));
        } else {
            rating.setVisibility(View.GONE);
        }

        adultPrice.setText(tourDetailsModel.getAdultPrice() + getString(R.string.currency));
        adultMax.setText(tourDetailsModel.getMaxAdults() + getString(R.string.person_max));

        String childStatus = tourDetailsModel.getChildStatus();
        if (childStatus != null && childStatus.equals("1")) {
            childPrice.setText(tourDetailsModel.getChildPrice() + getString(R.string.currency));
            childMax.setText(tourDetailsModel.getMaxChild() + getString(R.string.person_max));
        } else {
            childPrice.setText(getString(R.string.na));
        }

        tvDuration.setText(tourDetailsModel.getTourDays() + getString(R.string.days) + " " + tourDetailsModel.getTourNights() + getString(R.string.nights));
        location.setText(tourDetailsModel.getLocation());

        ibMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Utils.invokeMap(TourDetailsActivity.this, tourDetailsModel.getLatitude(), tourDetailsModel.getLongitude());
            }
        });

        webView.loadData(tourDetailsModel.getTourDesc(), "text/html; charset=utf-8", "UTF-8");

        amenityModels.clear();
        amenityModels.addAll(tourDetailsModel.getAmenities());
        if (!amenityModels.isEmpty()) {
            recyclerView.setVisibility(View.VISIBLE);
            adapter.notifyDataSetChanged();
        } else {
            recyclerView.setVisibility(View.GONE);
        }

        ivTransitionImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.getInstance().invokeImages(TourDetailsActivity.this, tourDetailsModel.getAllPhotos(), ivTransitionImg);
            }
        });

        setFavStatus(tourDetailsModel.isFavorite());

        // set pricing
        searchTourModel.setRateAdult(tourDetailsModel.getAdultPrice());
        searchTourModel.setRateChild(tourDetailsModel.getChildPrice());
        searchTourModel.setAddress(tourDetailsModel.getLocation());
        searchTourModel.setLatitude(tourDetailsModel.getLatitude());
        searchTourModel.setLongitude(tourDetailsModel.getLongitude());
        searchTourModel.setPhoneNumber(tourDetailsModel.getPhoneNumber());
    }

    private void makeFav() {
        boolean isLoggedIn = AppPreference.getInstance(getApplicationContext()).getBoolean(PrefKey.LOGIN);

        if (isLoggedIn) {
            if (tourDetailsModel != null) {
                String userId = AppPreference.getInstance(getApplicationContext()).getString(PrefKey.USER_ID);
                RequestAddToFav requestAddToFav = new RequestAddToFav(getApplicationContext());
                requestAddToFav.buildParams(userId, AppConstants.TYPE_TOURS, searchTourModel.getTourPackageId());
                requestAddToFav.execute();

                if (tourDetailsModel.isFavorite()) {
                    removeFromFavorite(searchTourModel.getTourPackageId());
                    tourDetailsModel.setFavorite(false);
                } else {
                    addToFavorite(tourDetailsModel.getTourId(), tourDetailsModel.getTourTitle(),
                            tourDetailsModel.getThumbnailImage(), tourDetailsModel.getAdultPrice(),
                            tourDetailsModel.getLocation(), AppConstants.TYPE_TOURS, searchTourModel.getDate(),
                            searchTourModel.getAdult(), searchTourModel.getChild(), "", "", "");
                    tourDetailsModel.setFavorite(true);
                }
                setFavStatus(tourDetailsModel.isFavorite());
            }
        } else {
            Snackbar snackbar = Snackbar.make(fabFav, getString(R.string.login_msg), Snackbar.LENGTH_LONG);
            snackbar.setAction(getString(R.string.login), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtils.getInstance().invokeLoginForFavActivity(TourDetailsActivity.this);
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
}
