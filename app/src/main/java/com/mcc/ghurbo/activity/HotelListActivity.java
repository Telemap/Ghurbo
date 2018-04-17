package com.mcc.ghurbo.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.adapter.HotelListAdapter;
import com.mcc.ghurbo.api.helper.RequestHotels;
import com.mcc.ghurbo.api.helper.RequestTours;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.listener.ItemClickListener;
import com.mcc.ghurbo.model.HotelModel;
import com.mcc.ghurbo.model.SearchHotelModel;
import com.mcc.ghurbo.model.TourModel;
import com.mcc.ghurbo.utility.ActivityUtils;

import java.util.ArrayList;

public class HotelListActivity extends BaseActivity{

    private SearchHotelModel searchHotelModel;
    private RecyclerView recyclerView;
    private HotelListAdapter adapter;
    private ArrayList<HotelModel> arrayList;
    private LinearLayoutManager mLayoutManager;

    private ProgressBar pbLoadMore;

    private int page = 1;
    private boolean loading = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();
        initView();
        initFunctionality();
        loadData(page);
        initListener();

    }

    private void initVariables() {
        Intent intent = getIntent();

        if(intent.hasExtra(AppConstants.BUNDLE_KEY_HOTEL_MODEL)) {
            searchHotelModel = intent.getParcelableExtra(AppConstants.BUNDLE_KEY_HOTEL_MODEL);
        }

        arrayList = new ArrayList<>();
    }

    private void initView() {
        setContentView(R.layout.activity_list);
        initToolbar();
        enableBackButton();
        initLoader();

        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        pbLoadMore = (ProgressBar) findViewById(R.id.pb_load_more);
    }

    private void initFunctionality() {
        mLayoutManager = new LinearLayoutManager(HotelListActivity.this);
        adapter = new HotelListAdapter(getApplicationContext(), arrayList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);

        invokeMessenger();
    }

    private void initListener() {
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position, View v) {

                String hotelName = arrayList.get(position).getHotelTitle();
                String hotelId = arrayList.get(position).getHotelId();
                searchHotelModel.setHotelName(hotelName);
                searchHotelModel.setHotelId(hotelId);

                ImageView imageView = v.findViewById(R.id.icon);
                String imageUrl = arrayList.get(position).getThumbnailImage();
                searchHotelModel.setImageUrl(imageUrl);

                ActivityUtils.getInstance().invokeHotelDetailsActivity(HotelListActivity.this, searchHotelModel, imageView);

            }
        });

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    onScrolledMore();
                }
            }
        });
    }

    private void onScrolledMore() {
        int visibleItemCount, totalItemCount, pastVisibleItems;
        visibleItemCount = mLayoutManager.getChildCount();
        totalItemCount = mLayoutManager.getItemCount();
        pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();

        if (loading) {
            if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
                loading = false;
                pbLoadMore.setVisibility(View.VISIBLE);
                page = page + 1;
                loadData(page);
            }
        }
    }

    private void loadData(int page) {

        if(page == 1) {
            showLoader();
        }
        pbLoadMore.setVisibility(View.VISIBLE);

        RequestHotels requestHotels = new RequestHotels(getApplicationContext());
        requestHotels.buildParams(searchHotelModel.getLocationId(), page);
        requestHotels.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {

                if (data != null) {
                    arrayList.addAll((ArrayList<HotelModel>) data);
                    if (!arrayList.isEmpty()) {
                        hideLoader();
                        adapter.notifyDataSetChanged();
                    } else {
                        showEmptyView();
                    }
                } else {
                    showEmptyView();
                }
                pbLoadMore.setVisibility(View.GONE);
                loading = true;

            }
        });
        requestHotels.execute();
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
