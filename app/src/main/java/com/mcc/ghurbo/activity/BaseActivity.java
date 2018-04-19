package com.mcc.ghurbo.activity;


import android.content.ContentValues;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mcc.ghurbo.R;
import com.mcc.ghurbo.data.preference.AppPreference;
import com.mcc.ghurbo.data.preference.PrefKey;
import com.mcc.ghurbo.data.sqlite.DbConstants;
import com.mcc.ghurbo.model.FavoriteModel;
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.Utils;

import java.util.ArrayList;

public class BaseActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar mToolbar;
    private DrawerLayout mDrawerLayout;
    private NavigationView mNavigationView;

    private LinearLayout loadingView, noDataView;

    public void initToolbar() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
    }

    public void enableBackButton() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }
    }

    public Toolbar getToolbar() {
        if (mToolbar == null) {
            mToolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(mToolbar);
        }
        return mToolbar;
    }

    public void setToolbarTitle(String title) {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle(title);
        }
    }

    public void initDrawer() {

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        mNavigationView = (NavigationView) findViewById(R.id.navigationView);
        mNavigationView.setNavigationItemSelectedListener(this);
        mNavigationView.setItemIconTintList(null);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        toggle.syncState();

    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_favorites) {
            ActivityUtils.getInstance().invokeActivity(BaseActivity.this, FavoriteActivity.class, false);
        } else if (id == R.id.action_reservations) {
            ActivityUtils.getInstance().invokeActivity(BaseActivity.this, MyBookingsActivity.class, false);
        } else if (id == R.id.action_share) {
            Utils.shareApp(BaseActivity.this);
        } else if (id == R.id.action_rate_app) {
            Utils.rateThisApp(BaseActivity.this);
        } else if (id == R.id.action_settings) {
            ActivityUtils.getInstance().invokeActivity(BaseActivity.this, SettingsActivity.class, false);
        }

        if (mDrawerLayout != null && mDrawerLayout.isDrawerOpen(GravityCompat.START)) {
            mDrawerLayout.closeDrawer(GravityCompat.START);
        }
        return true;
    }


    public void initLoader() {
        loadingView = (LinearLayout) findViewById(R.id.loadingView);
        noDataView = (LinearLayout) findViewById(R.id.noDataView);
    }

    public void showLoader() {
        if (loadingView != null) {
            loadingView.setVisibility(View.VISIBLE);
        }

        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }

    public void hideLoader() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (noDataView != null) {
            noDataView.setVisibility(View.GONE);
        }
    }

    public void showEmptyView() {
        if (loadingView != null) {
            loadingView.setVisibility(View.GONE);
        }
        if (noDataView != null) {
            noDataView.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (mToolbar != null) {
            Utils.noInternetWarning(mToolbar, getApplicationContext());
        }
    }

    public void loadProfileData() {
        View headerView = mNavigationView.getHeaderView(0);
        TextView tvUsername = (TextView) headerView.findViewById(R.id.tv_username);
        ImageView imageView = (ImageView) headerView.findViewById(R.id.profile_image);

        String name = AppPreference.getInstance(getApplicationContext()).getString(PrefKey.NAME);
        String profilePic = AppPreference.getInstance(getApplicationContext()).getString(PrefKey.PROFILE_PIC);
        boolean skipped = AppPreference.getInstance(getApplicationContext()).getBoolean(PrefKey.SKIPPED);

        if (skipped) {
            tvUsername.setText(getString(R.string.guest_user));
        } else {
            tvUsername.setText(name);
        }

        if (profilePic != null) {

            Glide.with(getApplicationContext())
                    .load(profilePic)
                    .crossFade()
                    .error(R.drawable.ic_profile)
                    .into(imageView);

        }
    }

    public void invokeMessenger() {
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.invokeWeb(BaseActivity.this, getString(R.string.ghurbo_messenger));
            }
        });
    }

    public void addToFavorite(String id, String title,
                              String image, String price,
                              String location, String type,
                              String date, String adult,
                              String child, String checkIn,
                              String checkOut, String rooms
    ) {

        ContentValues values = new ContentValues();
        values.put(DbConstants.COLUMN_ID, id);
        values.put(DbConstants.COLUMN_TITLE, title);
        values.put(DbConstants.COLUMN_IMAGE, image);
        values.put(DbConstants.COLUMN_PRICE, price);
        values.put(DbConstants.COLUMN_LOCATION, location);
        values.put(DbConstants.COLUMN_TYPE, type);
        values.put(DbConstants.COLUMN_DATE, date);
        values.put(DbConstants.COLUMN_ADULT, adult);
        values.put(DbConstants.COLUMN_CHILD, child);
        values.put(DbConstants.COLUMN_CHECK_IN, checkIn);
        values.put(DbConstants.COLUMN_CHECK_OUT, checkOut);
        values.put(DbConstants.COLUMN_ROOMS, rooms);


        getContentResolver().insert(DbConstants.CONTENT_URI, values);
    }


    public boolean isFavorite(String id, String type) {

        String[] projection = {
                DbConstants._ID,
                DbConstants.COLUMN_ID
        };
        String selection = DbConstants.COLUMN_ID + " =? AND " + DbConstants.COLUMN_TYPE + " =?";
        String[] selectionArgs = {id, type};

        Cursor c = getContentResolver().query(DbConstants.CONTENT_URI,
                projection,
                selection,
                selectionArgs,
                null);

        if (c != null && c.getCount() > 0) {
            c.close();
            return true;
        }
        return false;
    }

    public ArrayList<FavoriteModel> getFavoriteList() {

        ArrayList<FavoriteModel> arrayList = new ArrayList<>();

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

        Cursor c = getContentResolver().query(DbConstants.CONTENT_URI,
                projection,
                null,
                null,
                null);

        if (c != null && c.getCount() > 0) {

            if (c.moveToFirst()) {
                do {
                    arrayList.add(

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
        return arrayList;
    }

    public void removeFromFavorite(String id) {
        String selection = DbConstants.COLUMN_ID + "=?";
        String[] selectionArgs = {String.valueOf(id)};
        getContentResolver().delete(DbConstants.CONTENT_URI, selection, selectionArgs);
    }
}