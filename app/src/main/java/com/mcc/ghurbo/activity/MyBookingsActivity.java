package com.mcc.ghurbo.activity;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.api.helper.RequestMyBooking;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.data.preference.AppPreference;
import com.mcc.ghurbo.data.preference.PrefKey;
import com.mcc.ghurbo.utility.ActivityUtils;

public class MyBookingsActivity extends BaseActivity {

    private TextView infoText;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVar();
        initView();
        initListeners();
        loadData();
        invokeMessenger();

    }

    private void initVar() {

    }

    private void initView() {
        setContentView(R.layout.activity_list);
        initToolbar();
        enableBackButton();

        infoText = (TextView) findViewById(R.id.info_text);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }

    private void initListeners() {

    }

    private void loadData() {
        String userId = AppPreference.getInstance(getApplicationContext()).getString(PrefKey.USER_ID);
        RequestMyBooking requestMyBooking = new RequestMyBooking(getApplicationContext());
        requestMyBooking.buildParams(userId);
        requestMyBooking.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {

            }
        });
        requestMyBooking.execute();
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

    @Override
    protected void onResume() {
        super.onResume();

        boolean isLoggedIn = AppPreference.getInstance(getApplicationContext()).getBoolean(PrefKey.LOGIN);
        if (!isLoggedIn) {
            Snackbar snackbar = Snackbar.make(infoText, getString(R.string.login_msg), Snackbar.LENGTH_LONG);
            snackbar.setAction(getString(R.string.login), new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtils.getInstance().invokeLoginForFavActivity(MyBookingsActivity.this);
                }
            });
            snackbar.show();
        }
    }
}
