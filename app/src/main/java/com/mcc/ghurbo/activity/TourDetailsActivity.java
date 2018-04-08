package com.mcc.ghurbo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.adapter.MultiImageAdapter;
import com.mcc.ghurbo.adapter.TourListAdapter;
import com.mcc.ghurbo.api.helper.RequestTourDetails;
import com.mcc.ghurbo.api.helper.RequestTours;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.listener.ItemClickListener;
import com.mcc.ghurbo.model.SearchTourModel;
import com.mcc.ghurbo.model.TourDetailsModel;
import com.mcc.ghurbo.model.TourModel;
import com.mcc.ghurbo.utility.ActivityUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;

import me.relex.circleindicator.CircleIndicator;

public class TourDetailsActivity extends BaseActivity {

    private SearchTourModel searchTourModel;

    private CollapsingToolbarLayout collapsingToolbar;
    private ViewPager mPager;
    private TextView title, adultPrice, adultMax, childPrice, childMax, infantPrice, infantMax;
    private RatingBar rating;

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

    }

    private void initView() {
        setContentView(R.layout.activity_tour_details);
        initToolbar();
        enableBackButton();
        initLoader();

        mPager = (ViewPager) findViewById(R.id.pager);
        collapsingToolbar = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        title = (TextView) findViewById(R.id.title);
        rating = (RatingBar) findViewById(R.id.rating);
        adultPrice = (TextView) findViewById(R.id.adult_price);
        adultMax = (TextView) findViewById(R.id.adult_max);
        childPrice = (TextView) findViewById(R.id.child_price);
        childMax = (TextView) findViewById(R.id.child_max);
        infantPrice = (TextView) findViewById(R.id.infant_price);
        infantMax = (TextView) findViewById(R.id.infant_max);

    }

    private void initFunctionality() {
        invokeMessenger();
    }

    private void initListener() {

    }

    private void loadData() {

        showLoader();
        RequestTourDetails requestTourDetails = new RequestTourDetails(getApplicationContext());
        requestTourDetails.buildParams(searchTourModel.getTourPackageId());
        requestTourDetails.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {

                TourDetailsModel tourDetailsModel = (TourDetailsModel) data;
                if (tourDetailsModel != null) {
                    hideLoader();
                    setDataToUi(tourDetailsModel);
                } else {
                    showEmptyView();
                }
            }
        });
        requestTourDetails.execute();
    }

    private void setDataToUi(TourDetailsModel tourDetailsModel) {
        loadPhotos(tourDetailsModel.getAllPhotos());
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
        if(childStatus != null && childStatus.equals("1")) {
            childPrice.setText(tourDetailsModel.getChildPrice() + getString(R.string.currency));
            childMax.setText(tourDetailsModel.getMaxChild() + getString(R.string.person_max));
        } else {
            childPrice.setText(getString(R.string.na));
        }

        String infantStatus = tourDetailsModel.getInfantStatus();
        if(infantStatus != null && infantStatus.equals("1")) {
            infantPrice.setText(tourDetailsModel.getInfantPrice() + getString(R.string.currency));
            infantMax.setText(tourDetailsModel.getMaxInfant() + getString(R.string.person_max));
        } else {
            infantPrice.setText(getString(R.string.na));
        }
    }

    private void loadPhotos(final ArrayList<String> images) {

        if (images != null && !images.isEmpty()) {
            MultiImageAdapter multiImageAdapter = new MultiImageAdapter(getApplicationContext(), images);
            mPager.setAdapter(multiImageAdapter);
            CircleIndicator indicator = (CircleIndicator) findViewById(R.id.sliderIndicator);
            indicator.setViewPager(mPager);

            multiImageAdapter.setItemClickListener(new ItemClickListener() {
                @Override
                public void onItemClick(int position, View view) {
                    ActivityUtils.getInstance().invokeImages(TourDetailsActivity.this, images);
                }
            });
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
