package com.mcc.ghurbo.fragment;


import android.app.DatePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.api.helper.RequestHotels;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.model.LocationModel;
import com.mcc.ghurbo.utility.DatePickerUtils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

public class HotelSearchFragment extends Fragment {

    private AutoCompleteTextView actLocationHotel;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> queryList;

    private ImageButton ibSelectLocation, minusAdult, plusAdult, minusChild,
            plusChild, minusRooms, plusRooms;
    private ProgressBar pbLocation;
    private LinearLayout llCheckIn;
    private LinearLayout llCheckOut;
    private TextView tvCheckIn, tvCheckOut, tvAdult, tvChild, tvRooms;

    public HotelSearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_hotel_search, container, false);

        queryList = new ArrayList<>();

        actLocationHotel = (AutoCompleteTextView) rootView.findViewById(R.id.act_location_hotel);
        ibSelectLocation = (ImageButton) rootView.findViewById(R.id.ib_select_location);
        pbLocation = (ProgressBar) rootView.findViewById(R.id.pb_location);
        llCheckIn = (LinearLayout) rootView.findViewById(R.id.ll_check_in);
        llCheckOut = (LinearLayout) rootView.findViewById(R.id.ll_check_out);
        tvCheckIn = (TextView) rootView.findViewById(R.id.tv_check_in);
        tvCheckOut = (TextView) rootView.findViewById(R.id.tv_check_out);

        minusAdult = (ImageButton) rootView.findViewById(R.id.minus_adult);
        tvAdult = (TextView) rootView.findViewById(R.id.tv_adult);
        plusAdult = (ImageButton) rootView.findViewById(R.id.plus_adult);
        minusChild = (ImageButton) rootView.findViewById(R.id.minus_child);
        tvChild = (TextView) rootView.findViewById(R.id.tv_child);
        plusChild = (ImageButton) rootView.findViewById(R.id.plus_child);
        minusRooms = (ImageButton) rootView.findViewById(R.id.minus_rooms);
        tvRooms = (TextView) rootView.findViewById(R.id.tv_rooms);
        plusRooms = (ImageButton) rootView.findViewById(R.id.plus_rooms);

        loadData();
        initListener();

        return rootView;
    }

    private void loadData() {
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, queryList);
        actLocationHotel.setAdapter(adapter);

        RequestHotels requestHotels = new RequestHotels(getActivity().getApplication());
        requestHotels.buildParams();
        requestHotels.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {
                if(data != null) {
                    ArrayList<LocationModel> locationModels = (ArrayList<LocationModel>) data;

                    for (LocationModel locationModel : locationModels) {
                        queryList.add(locationModel.getLocation());
                    }

                    if (!queryList.isEmpty()) {
                        pbLocation.setVisibility(View.GONE);
                        ibSelectLocation.setVisibility(View.VISIBLE);
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
                if(!selectedQuery.isEmpty()) {
                    actLocationHotel.setSelection(selectedQuery.length());
                }
            }
        });

        ibSelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actLocationHotel.showDropDown();
            }
        });

        llCheckIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerUtils(getActivity(), new DatePickerUtils.DateListener() {
                    @Override
                    public void onDateSet(String finalDate) {
                        tvCheckIn.setText(finalDate);
                    }
                });
            }
        });

        llCheckOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerUtils(getActivity(), new DatePickerUtils.DateListener() {
                    @Override
                    public void onDateSet(String finalDate) {
                        tvCheckOut.setText(finalDate);
                    }
                });
            }
        });

        minusAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrement(tvAdult);
            }
        });

        plusAdult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment(tvAdult);
            }
        });

        minusChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrement(tvChild);
            }
        });

        plusChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment(tvChild);
            }
        });

        minusRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                decrement(tvRooms);
            }
        });

        plusRooms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                increment(tvRooms);
            }
        });

    }

    private void increment(TextView textView) {
        String string = textView.getText().toString();
        if(string.isEmpty()) {
            textView.setText("1");
        } else {
            int count = Integer.valueOf(string);
            count++;
            textView.setText(String.valueOf(count));
        }
    }

    private void decrement(TextView textView) {
        String string = textView.getText().toString();
        if(!string.isEmpty()) {
            int count = Integer.valueOf(string);
            if(count > 0) {
                count--;
                textView.setText(String.valueOf(count));
            }
        }
    }



}

