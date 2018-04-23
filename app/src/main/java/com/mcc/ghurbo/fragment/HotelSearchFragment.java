package com.mcc.ghurbo.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.DatePickerUtils;
import com.mcc.ghurbo.utility.Utils;

import java.util.ArrayList;

public class HotelSearchFragment extends Fragment {

    private AutoCompleteTextView actLocationHotel;

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

    private static final String
            LOCATION_LIST_KEY = "location_list",
            SEARCH_MODEL_KEY = "search_model"
    ;

    public HotelSearchFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState == null) {
            locationModels = new ArrayList<>();
            loadData();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_hotel_search, container, false);

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


        initListener();

        return rootView;
    }

    private void loadData() {

        RequestHotelLocations requestHotelLocations = new RequestHotelLocations(getActivity().getApplication());
        requestHotelLocations.buildParams();
        requestHotelLocations.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {
                if(data != null) {
                    locationModels.clear();
                    showView((ArrayList<LocationModel>) data, null);
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

        SearchHotelModel searchHotelModel = getInputs();
        String location = actLocationHotel.getText().toString();

        if(location.isEmpty() || searchHotelModel.getCheckIn().isEmpty() || searchHotelModel.getCheckOut().isEmpty() || searchHotelModel.getAdult().isEmpty()) {
            Utils.showToast(getActivity().getApplicationContext(), getString(R.string.input_validation));
        } else {
            ActivityUtils.getInstance().invokeHotelListActivity(getActivity(), searchHotelModel);
        }
    }

    private void showView(ArrayList<LocationModel> locationModels, SearchHotelModel searchHotelModel) {

        queryList = new ArrayList<>();
        for (LocationModel locationModel : locationModels) {
            queryList.add(locationModel.getLocation());
        }
        this.locationModels = locationModels;

        Activity activity = getActivity();
        if(activity != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, queryList);
            actLocationHotel.setAdapter(adapter);
        }

        if (!queryList.isEmpty()) {
            pbLocation.setVisibility(View.GONE);
            ibSelectLocation.setVisibility(View.VISIBLE);
        }

        if(searchHotelModel != null) {
            selectedLocationId = searchHotelModel.getLocationId();
            tvCheckIn.setText(searchHotelModel.getCheckIn());
            tvCheckOut.setText(searchHotelModel.getCheckOut());
            tvAdult.setText(searchHotelModel.getAdult());
            tvChild.setText(searchHotelModel.getChild());
            tvRooms.setText(searchHotelModel.getRooms());
        }

    }

    private SearchHotelModel getInputs() {

        String checkIn = tvCheckIn.getText().toString();
        String checkOut = tvCheckOut.getText().toString();
        String adult = tvAdult.getText().toString();
        String child = tvChild.getText().toString();
        String rooms = tvRooms.getText().toString();

        return new SearchHotelModel(selectedLocationId, checkIn, checkOut, adult, child, rooms);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(LOCATION_LIST_KEY, locationModels);
        outState.putParcelable(SEARCH_MODEL_KEY, getInputs());
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if(savedInstanceState != null) {
            ArrayList<LocationModel> restoredList = savedInstanceState.getParcelableArrayList(LOCATION_LIST_KEY);
            SearchHotelModel searchHotelModel = savedInstanceState.getParcelable(SEARCH_MODEL_KEY);
            showView(restoredList, searchHotelModel);
        }
    }


}

