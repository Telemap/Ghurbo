package com.mcc.ghurbo.activity;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.adapter.FavoriteAdapter;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.data.preference.AppPreference;
import com.mcc.ghurbo.data.preference.PrefKey;
import com.mcc.ghurbo.listener.ItemClickListener;
import com.mcc.ghurbo.model.FavoriteModel;
import com.mcc.ghurbo.model.SearchHotelModel;
import com.mcc.ghurbo.model.SearchTourModel;
import com.mcc.ghurbo.utility.ActivityUtils;

import java.util.ArrayList;

public class FavoriteActivity extends BaseActivity {

    private TextView infoText;
    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private ArrayList<FavoriteModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // TODO: Sync fav list with server data
        initVar();
        initView();
        initFunctionality();
        initListeners();
        invokeMessenger();

    }

    private void initVar() {
        arrayList = getFavoriteList();
    }

    private void initView() {
        setContentView(R.layout.activity_list);
        initToolbar();
        enableBackButton();
        initLoader();
        setToolbarTitle(getString(R.string.my_favorites));

        infoText = (TextView) findViewById(R.id.info_text);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }

    private void initFunctionality() {
        adapter = new FavoriteAdapter(FavoriteActivity.this, arrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(FavoriteActivity.this));
        recyclerView.setAdapter(adapter);


        if (arrayList.isEmpty()) {
            showEmptyView();
        } else {
            hideLoader();
        }
    }

    private void initListeners() {
        adapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                invokeDetails(arrayList.get(position), view);
            }
        });
    }

    private void invokeDetails(FavoriteModel favoriteModel, View v) {

        String type = favoriteModel.getType();

        if (type != null && type.equals(AppConstants.TYPE_TOURS)) {
            SearchTourModel searchTourModel = new SearchTourModel(favoriteModel.getTourDate(), favoriteModel.getAdult(),
                    favoriteModel.getChild());
            searchTourModel.setTourPackageName(favoriteModel.getTitle());
            searchTourModel.setTourPackageId(favoriteModel.getId());
            searchTourModel.setImageUrl(favoriteModel.getImage());

            ImageView imageView = v.findViewById(R.id.icon);
            ActivityUtils.getInstance().invokeTourDetailsActivity(FavoriteActivity.this, searchTourModel, imageView);
        } else if (type != null && type.equals(AppConstants.TYPE_HOTELS)) {
            SearchHotelModel searchHotelModel = new SearchHotelModel(favoriteModel.getHotelCheckIn(),
                    favoriteModel.getHotelCheckOut(), favoriteModel.getAdult(), favoriteModel.getChild(),
                    favoriteModel.getHotelRooms());
            searchHotelModel.setHotelName(favoriteModel.getTitle());
            searchHotelModel.setHotelId(favoriteModel.getId());
            searchHotelModel.setImageUrl(favoriteModel.getImage());

            ImageView imageView = v.findViewById(R.id.icon);
            ActivityUtils.getInstance().invokeHotelDetailsActivity(FavoriteActivity.this, searchHotelModel, imageView);
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

    @Override
    protected void onResume() {
        super.onResume();

        boolean isLoggedIn = AppPreference.getInstance(getApplicationContext()).getBoolean(PrefKey.LOGIN);
        if (!isLoggedIn) {
            infoText.setText(getString(R.string.fav_login_msg));
            Snackbar snackbar = Snackbar.make(infoText, getString(R.string.login_msg), Snackbar.LENGTH_LONG);
            snackbar.setAction(getString(R.string.login), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtils.getInstance().invokeLoginForFavActivity(FavoriteActivity.this);
                }
            });
            snackbar.show();
        }
    }
}
