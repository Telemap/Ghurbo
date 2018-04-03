package com.mcc.ghurbo.activity;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
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
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.Utils;

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
        if(getSupportActionBar() != null) {
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


        } else if (id == R.id.action_reservations) {

        }

        else if (id == R.id.action_share) {
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

        if(mToolbar != null) {
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
                    .error(R.drawable.ic_profile)
                    .into(imageView);
        }
    }

}