package com.mcc.ghurbo.activity;


import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.adapter.MyBookingAdapter;
import com.mcc.ghurbo.api.helper.RequestMyBooking;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.data.preference.AppPreference;
import com.mcc.ghurbo.data.preference.PrefKey;
import com.mcc.ghurbo.listener.ItemClickListener;
import com.mcc.ghurbo.model.MyBookingModel;
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.PricingUtils;

import java.util.ArrayList;

public class MyBookingsActivity extends BaseActivity {

    private TextView infoText;
    private RecyclerView recyclerView;
    private MyBookingAdapter myBookingAdapter;
    private GridLayoutManager mLayoutManager;
    private ArrayList<MyBookingModel> arrayList;

    private final String LIST_STATE_KEY = "list_state";
    private final String LIST_DATA_KEY = "list_data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVar();
        initView();
        initFunctionality();
        initListeners();
        loadData(savedInstanceState);
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
        setToolbarTitle(getString(R.string.my_bookings));

        infoText = (TextView) findViewById(R.id.info_text);
        recyclerView = (RecyclerView) findViewById(R.id.recycler_view);

    }

    private void initFunctionality() {
        myBookingAdapter = new MyBookingAdapter(getApplicationContext(), arrayList);
        mLayoutManager = new GridLayoutManager(MyBookingsActivity.this, getResources().getInteger(R.integer.booking_column));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(myBookingAdapter);
    }

    private void initListeners() {
        myBookingAdapter.setItemClickListener(new ItemClickListener() {
            @Override
            public void onItemClick(int position, View view) {
                ActivityUtils.getInstance().invokeBookingDetailsActivity(MyBookingsActivity.this, arrayList.get(position), true);
            }
        });
    }

    private void loadData(Bundle savedInstanceState) {

        if(savedInstanceState == null) {
            showLoader();
            String userId = AppPreference.getInstance(getApplicationContext()).getString(PrefKey.USER_ID);
            RequestMyBooking requestMyBooking = new RequestMyBooking(getApplicationContext());
            requestMyBooking.buildParams(userId);
            requestMyBooking.setResponseListener(new ResponseListener() {
                @Override
                public void onResponse(Object data) {
                    hideLoader();
                    if (data != null) {
                        ArrayList<MyBookingModel> responseData = (ArrayList<MyBookingModel>) data;
                        if (!responseData.isEmpty()) {

                            //TODO: Optimize pricing, get total price from server

                            for (MyBookingModel myBookingModel : responseData) {
                                if (myBookingModel.getType().equals(AppConstants.TYPE_HOTELS)) {
                                    int nights = 1;
                                    if (myBookingModel.getNights() != null) {
                                        nights = Integer.parseInt(myBookingModel.getNights());
                                    }
                                    float totalPrice = PricingUtils.getHotelPrice(myBookingModel.getPrice(), myBookingModel.getRoomNumber(), nights);
                                    myBookingModel.setTotalPrice(String.valueOf(totalPrice));
                                } else if (myBookingModel.getType().equals(AppConstants.TYPE_TOURS)) {
                                    float totalPrice = PricingUtils.getTourPrice(
                                            myBookingModel.getPrice(),
                                            myBookingModel.getPrice(), // TODO: get child rate from server for tour
                                            myBookingModel.getAdults(),
                                            myBookingModel.getChild()
                                    );
                                    myBookingModel.setTotalPrice(String.valueOf(totalPrice));
                                }
                            }

                            loadListData(responseData);
                        } else {
                            infoText.setText(getString(R.string.no_booking));
                            showEmptyView();
                        }
                    } else {
                        infoText.setText(getString(R.string.no_booking));
                        showEmptyView();
                    }
                }
            });
            requestMyBooking.execute();
        }
    }

    private void loadListData(ArrayList<MyBookingModel> myBookingModels) {
        hideLoader();
        arrayList.clear();
        arrayList.addAll(myBookingModels);
        myBookingAdapter.notifyDataSetChanged();

        if(arrayList.isEmpty()) {
            showEmptyView();
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

    @Override
    protected void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putParcelable(LIST_STATE_KEY, mLayoutManager.onSaveInstanceState());
        state.putParcelableArrayList(LIST_DATA_KEY, arrayList);
    }

    @Override
    protected void onRestoreInstanceState(Bundle state) {
        super.onRestoreInstanceState(state);
        if(state != null) {
            ArrayList<MyBookingModel> restoredList = state.getParcelableArrayList(LIST_DATA_KEY);
            loadListData(restoredList);

            mLayoutManager.onRestoreInstanceState(state.getParcelable(LIST_STATE_KEY));
        }
    }
}
