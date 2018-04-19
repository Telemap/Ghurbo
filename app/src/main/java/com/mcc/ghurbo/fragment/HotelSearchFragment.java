package com.mcc.ghurbo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.api.helper.RequestHotelLocations;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.model.LocationModel;
import com.mcc.ghurbo.model.SearchHotelModel;
import com.mcc.ghurbo.model.SearchTourModel;
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.DatePickerUtils;
import com.mcc.ghurbo.utility.Utils;

import java.util.ArrayList;

public class HotelSearchFragment extends Fragment {

    private AutoCompleteTextView actLocationHotel;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> queryList;
    private ArrayList<LocationModel> locationModels;

    private ImageButton ibSelectLocation, minusAdult, plusAdult, minusChild,
            plusChild, minusRooms, plusRooms;
    private ProgressBar pbLocation;
    private LinearLayout llCheckIn;
    private LinearLayout llCheckOut;
    private TextView tvCheckIn, tvCheckOut, tvAdult, tvChild, tvRooms;
    private Button btnSearch;

    private String selectedLocationId;

    public HotelSearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_hotel_search, container, false);

        queryList = new ArrayList<>();
        locationModels = new ArrayList<>();

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
        btnSearch = (Button) rootView.findViewById(R.id.btn_search);

        loadData();
        initListener();

        return rootView;
    }

    private void loadData() {
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, queryList);
        actLocationHotel.setAdapter(adapter);

        RequestHotelLocations requestHotelLocations = new RequestHotelLocations(getActivity().getApplication());
        requestHotelLocations.buildParams();
        requestHotelLocations.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {
                if(data != null) {
                    locationModels.clear();
                    queryList.clear();
                    locationModels = (ArrayList<LocationModel>) data;

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
        requestHotelLocations.execute();
    }

    private void initListener() {
        actLocationHotel.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedText = actLocationHotel.getText().toString();
                int index = queryList.indexOf(selectedText);
                selectedLocationId = locationModels.get(index).getId();
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

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndInvokeSearch();
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

    private void validateAndInvokeSearch() {
        String location = actLocationHotel.getText().toString();
        String checkIn = tvCheckIn.getText().toString();
        String checkOut = tvCheckOut.getText().toString();
        String adult = tvAdult.getText().toString();
        String child = tvChild.getText().toString();
        String rooms = tvRooms.getText().toString();

        if(location.isEmpty() || checkIn.isEmpty() || checkOut.isEmpty() || adult.isEmpty()) {
            Utils.showToast(getActivity().getApplicationContext(), getString(R.string.input_validation));
        } else {

            if(adult.isEmpty()) {
                adult = "0";
            }
            if(child.isEmpty()) {
                child = "0";
            }
            if(rooms.isEmpty()) {
                rooms = "0";
            }
            ActivityUtils.getInstance().invokeHotelListActivity(getActivity(), new SearchHotelModel(selectedLocationId, checkIn, checkOut, adult, child, rooms));
        }
    }



}

