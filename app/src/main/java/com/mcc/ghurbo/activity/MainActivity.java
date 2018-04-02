package com.mcc.ghurbo.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.api.helper.RequestHotels;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.model.HotelLocationModel;
import com.mcc.ghurbo.utility.Utils;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private AutoCompleteTextView actLocationHotel;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> queryList, filterList;

    long previousTime = 0, delayTime = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initVariables();
        initView();
        initFunctionality();
        initListener();
    }

    private void initVariables() {
        queryList = new ArrayList<>();
        filterList = new ArrayList<>();
    }

    private void initView() {
        setContentView(R.layout.activity_main);
        actLocationHotel = (AutoCompleteTextView) findViewById(R.id.act_location_hotel);
        initToolbar();
        initDrawer();
    }

    private void initFunctionality() {
        adapter = new ArrayAdapter(MainActivity.this, android.R.layout.simple_list_item_1, filterList);
        actLocationHotel.setAdapter(adapter);

        RequestHotels requestHotels = new RequestHotels(getApplicationContext());
        requestHotels.buildParams();
        requestHotels.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {
                if(data != null) {
                    ArrayList<HotelLocationModel> hotelLocationModels = (ArrayList<HotelLocationModel>) data;

                    for(HotelLocationModel hotelLocationModel : hotelLocationModels) {
                        queryList.add(hotelLocationModel.getLocation());
                        filterList.add(hotelLocationModel.getLocation());
                    }
                }
            }
        });
        requestHotels.execute();
    }

    private void initListener() {
        actLocationHotel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedQuery = queryList.get(position);
                actLocationHotel.setText(selectedQuery);
            }
        });

        actLocationHotel.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                long currentTime  = System.currentTimeMillis();
                if((currentTime - previousTime) > delayTime) {
                    previousTime = System.currentTimeMillis();

                    String text = s.toString();
                    if (text.length() > 2) {

                        filterList.clear();
                        for(String string : queryList) {
                            if(string.contains(text)) {
                                filterList.add(string);
                            }
                        }
                        adapter.notifyDataSetChanged();
                        actLocationHotel.showDropDown();
                    }

                }
            }
        });
    }


}
