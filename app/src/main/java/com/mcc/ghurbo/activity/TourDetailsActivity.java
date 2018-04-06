package com.mcc.ghurbo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.adapter.TourListAdapter;
import com.mcc.ghurbo.api.helper.RequestTourDetails;
import com.mcc.ghurbo.api.helper.RequestTours;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.listener.ItemClickListener;
import com.mcc.ghurbo.model.SearchTourModel;
import com.mcc.ghurbo.model.TourModel;

import java.util.ArrayList;

public class TourDetailsActivity extends BaseActivity{

    private SearchTourModel searchTourModel;

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

        if(intent.hasExtra(AppConstants.BUNDLE_KEY_TOUR_MODEL)) {
            searchTourModel = intent.getParcelableExtra(AppConstants.BUNDLE_KEY_TOUR_MODEL);
        }

    }

    private void initView() {
        setContentView(R.layout.activity_tour_details);
        initToolbar();
        enableBackButton();
        initLoader();

    }

    private void initFunctionality() {

    }

    private void initListener() {

    }



    private void loadData() {
        RequestTourDetails requestTourDetails = new RequestTourDetails(getApplicationContext());
        requestTourDetails.buildParams(searchTourModel.getTourPackageId());
        requestTourDetails.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {

            }
        });
        requestTourDetails.execute();
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
