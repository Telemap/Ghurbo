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
import com.mcc.ghurbo.api.helper.RequestTourLocations;
import com.mcc.ghurbo.api.helper.RequestTourType;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.model.LocationModel;
import com.mcc.ghurbo.model.SearchTourModel;
import com.mcc.ghurbo.utility.ActivityUtils;
import com.mcc.ghurbo.utility.DatePickerUtils;
import com.mcc.ghurbo.utility.Utils;

import java.util.ArrayList;

public class TourSearchFragment extends Fragment {

    private AutoCompleteTextView actLocationTour, actType;

    private ArrayList<String> queryList, typeList;
    private ArrayList<LocationModel> locationModels;

    private ImageButton ibSelectLocation, minusAdult, plusAdult, minusChild,
            plusChild, ibSelectType;
    private ProgressBar pbLocation, pbType;
    private LinearLayout llDate;
    private TextView tvDate, tvAdult, tvChild;
    private Button btnSearch;

    private String selectedTourId;

    private static final String
            LOCATION_LIST_KEY = "location_list",
            SEARCH_MODEL_KEY = "search_model"
    ;

    public TourSearchFragment() {

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

        View rootView = inflater.inflate(R.layout.fragment_tour_search, container, false);

        typeList = new ArrayList<>();

        actLocationTour = (AutoCompleteTextView) rootView.findViewById(R.id.act_location_tour);
        actType = (AutoCompleteTextView) rootView.findViewById(R.id.act_type);

        ibSelectLocation = (ImageButton) rootView.findViewById(R.id.ib_select_location);
        ibSelectType = (ImageButton) rootView.findViewById(R.id.ib_select_type);
        pbLocation = (ProgressBar) rootView.findViewById(R.id.pb_location);
        pbType = (ProgressBar) rootView.findViewById(R.id.pb_type);
        llDate = (LinearLayout) rootView.findViewById(R.id.ll_date);
        tvDate = (TextView) rootView.findViewById(R.id.tv_date);

        minusAdult = (ImageButton) rootView.findViewById(R.id.minus_adult);
        tvAdult = (TextView) rootView.findViewById(R.id.tv_adult);
        plusAdult = (ImageButton) rootView.findViewById(R.id.plus_adult);
        minusChild = (ImageButton) rootView.findViewById(R.id.minus_child);
        tvChild = (TextView) rootView.findViewById(R.id.tv_child);
        plusChild = (ImageButton) rootView.findViewById(R.id.plus_child);

        btnSearch = (Button) rootView.findViewById(R.id.btn_search);

        setDefaults();
        initListener();

        return rootView;
    }

    private void loadData() {

        RequestTourLocations requestTourLocations = new RequestTourLocations(getActivity().getApplication());
        requestTourLocations.buildParams();
        requestTourLocations.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {
                if(data != null) {
                    locationModels.clear();
                    showView((ArrayList<LocationModel>) data, null);
                }
            }
        });
        requestTourLocations.execute();

    }

    private void initListener() {

        actLocationTour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                String selectedText = actLocationTour.getText().toString();
                int index = queryList.indexOf(selectedText);
                selectedTourId = locationModels.get(index).getId();

                loadTourType(selectedTourId);
            }
        });


        ibSelectLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actLocationTour.showDropDown();
            }
        });

        ibSelectType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedTourId != null && !selectedTourId.isEmpty()) {
                    actType.showDropDown();
                } else {
                    Utils.showToast(getActivity().getApplicationContext(), getString(R.string.select_tour));
                }
            }
        });

        llDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerUtils(getActivity(), new DatePickerUtils.DateListener() {
                    @Override
                    public void onDateSet(String finalDate) {
                        tvDate.setText(finalDate);
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

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAndInvokeSearch();
            }
        });

    }

    private void loadTourType(String locationId) {

        ArrayAdapter<String> typeAdapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, typeList);
        actType.setAdapter(typeAdapter);

        pbType.setVisibility(View.VISIBLE);
        ibSelectType.setVisibility(View.INVISIBLE);

        RequestTourType requestTourType = new RequestTourType(getActivity().getApplicationContext());
        requestTourType.buildParams(locationId);
        requestTourType.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {

                if(data != null) {
                    typeList.clear();

                    typeList.addAll((ArrayList<String>) data);

                    if (!typeList.isEmpty()) {
                        ibSelectType.setVisibility(View.VISIBLE);
                    } else {
                        Utils.showToast(getActivity().getApplicationContext(), getString(R.string.unavailable));
                    }
                    pbType.setVisibility(View.GONE);
                }

            }
        });
        requestTourType.execute();
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
        SearchTourModel searchTourModel = getInputs();
        String location = actLocationTour.getText().toString();
        if(location.isEmpty() || searchTourModel.getType().isEmpty() || searchTourModel.getDate().isEmpty() || searchTourModel.getAdult().isEmpty()) {
            Utils.showToast(getActivity().getApplicationContext(), getString(R.string.input_validation));
        } else {
            ActivityUtils.getInstance().invokeTourListActivity(getActivity(), searchTourModel);
        }
    }

    private void showView(ArrayList<LocationModel> locationModels, SearchTourModel searchTourModel) {
        queryList = new ArrayList<>();
        for (LocationModel locationModel : locationModels) {
            queryList.add(locationModel.getLocation());
        }
        this.locationModels = locationModels;
        Activity activity = getActivity();
        if(activity != null) {
            ArrayAdapter<String> adapter = new ArrayAdapter(activity, android.R.layout.simple_list_item_1, queryList);
            actLocationTour.setAdapter(adapter);
        }

        if (!queryList.isEmpty()) {
            pbLocation.setVisibility(View.GONE);
            ibSelectLocation.setVisibility(View.VISIBLE);
        }

        if(searchTourModel != null) {
            selectedTourId = searchTourModel.getTourPackageId();
            actType.setText(searchTourModel.getType());
            tvDate.setText(searchTourModel.getDate());
            tvAdult.setText(searchTourModel.getAdult());
            tvChild.setText(searchTourModel.getChild());
        }
    }

    private SearchTourModel getInputs() {
        String type = actType.getText().toString();
        String date = tvDate.getText().toString();
        String adult = tvAdult.getText().toString();
        String child = tvChild.getText().toString();
        return new SearchTourModel(selectedTourId, type, date, adult, child);
    }

    private void setDefaults() {
        tvDate.setText(Utils.getToday());
        tvAdult.setText("2");
        tvChild.setText("0");
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
            SearchTourModel searchTourModel = savedInstanceState.getParcelable(SEARCH_MODEL_KEY);
            showView(restoredList, searchTourModel);
        }
    }
}

