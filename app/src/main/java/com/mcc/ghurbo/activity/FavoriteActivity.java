package com.mcc.ghurbo.activity;


import android.app.LoaderManager;
import android.content.CursorLoader;
import android.content.Loader;
import android.database.Cursor;
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
import com.mcc.ghurbo.data.sqlite.DbConstants;
import com.mcc.ghurbo.listener.ItemClickListener;
import com.mcc.ghurbo.model.FavoriteModel;
import com.mcc.ghurbo.model.SearchHotelModel;
import com.mcc.ghurbo.model.SearchTourModel;
import com.mcc.ghurbo.utility.ActivityUtils;

import java.util.ArrayList;

public class FavoriteActivity extends BaseActivity implements LoaderManager.LoaderCallbacks<Cursor>{

    private TextView infoText;
    private RecyclerView recyclerView;
    private FavoriteAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    private ArrayList<FavoriteModel> arrayList;

    private Bundle savedInstanceState;
    private final String LIST_STATE_KEY = "list_state";
    private final String LIST_DATA_KEY = "list_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        // TODO: Sync fav list with server data
        initVar();
        initView();
        initFunctionality(savedInstanceState);
        initListeners();
        invokeMessenger();

    }

    private void initVar() {
        arrayList = new ArrayList<>();
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

    private void initFunctionality(Bundle savedInstanceState) {
        adapter = new FavoriteAdapter(FavoriteActivity.this, arrayList);
        linearLayoutManager = new LinearLayoutManager(FavoriteActivity.this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        this.savedInstanceState = savedInstanceState;

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

        if(savedInstanceState == null) {
            getLoaderManager().destroyLoader(0);
            getLoaderManager().initLoader(0, null, this);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        String[] projection = {
                DbConstants._ID,
                DbConstants.COLUMN_ID,
                DbConstants.COLUMN_TITLE,
                DbConstants.COLUMN_IMAGE,
                DbConstants.COLUMN_PRICE,
                DbConstants.COLUMN_LOCATION,
                DbConstants.COLUMN_TYPE,
                DbConstants.COLUMN_DATE,
                DbConstants.COLUMN_ADULT,
                DbConstants.COLUMN_CHILD,
                DbConstants.COLUMN_CHECK_IN,
                DbConstants.COLUMN_CHECK_OUT,
                DbConstants.COLUMN_ROOMS,
        };

        return new CursorLoader(this, DbConstants.FAV_CONTENT_URI, projection, null, null, DbConstants._ID + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor c) {
        ArrayList<FavoriteModel> favoriteModels = new ArrayList<>();
        if (c != null && c.getCount() > 0) {

            if (c.moveToFirst()) {
                do {
                    favoriteModels.add(

                            new FavoriteModel(
                                    c.getString(c.getColumnIndexOrThrow(DbConstants.COLUMN_ID)),
                                    c.getString(c.getColumnIndexOrThrow(DbConstants.COLUMN_TITLE)),
                                    c.getString(c.getColumnIndexOrThrow(DbConstants.COLUMN_IMAGE)),
                                    c.getString(c.getColumnIndexOrThrow(DbConstants.COLUMN_PRICE)),
                                    c.getString(c.getColumnIndexOrThrow(DbConstants.COLUMN_LOCATION)),
                                    c.getString(c.getColumnIndexOrThrow(DbConstants.COLUMN_TYPE)),
                                    c.getString(c.getColumnIndexOrThrow(DbConstants.COLUMN_DATE)),
                                    c.getString(c.getColumnIndexOrThrow(DbConstants.COLUMN_ADULT)),
                                    c.getString(c.getColumnIndexOrThrow(DbConstants.COLUMN_CHILD)),
                                    c.getString(c.getColumnIndexOrThrow(DbConstants.COLUMN_CHECK_IN)),
                                    c.getString(c.getColumnIndexOrThrow(DbConstants.COLUMN_CHECK_OUT)),
                                    c.getString(c.getColumnIndexOrThrow(DbConstants.COLUMN_ROOMS))
                            )
                    );
                } while (c.moveToNext());
            }

            c.close();
        }
        loadListData(favoriteModels);
    }

    private void loadListData(ArrayList<FavoriteModel> arrayList) {
        this.arrayList.clear();
        this.arrayList.addAll(arrayList);
        if (this.arrayList.isEmpty()) {
            showEmptyView();
        } else {
            hideLoader();
        }
        if(adapter != null) {
            adapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelable(LIST_STATE_KEY, linearLayoutManager.onSaveInstanceState());
        state.putParcelableArrayList(LIST_DATA_KEY, arrayList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if(state != null) {
            ArrayList<FavoriteModel> restoredList = state.getParcelableArrayList(LIST_DATA_KEY);
            loadListData(restoredList);

            linearLayoutManager.onRestoreInstanceState(state.getParcelable(LIST_STATE_KEY));
        }
    }
}
