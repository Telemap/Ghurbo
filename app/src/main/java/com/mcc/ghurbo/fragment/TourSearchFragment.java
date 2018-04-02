package com.mcc.ghurbo.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.mcc.ghurbo.R;
import com.mcc.ghurbo.api.helper.RequestTours;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.model.LocationModel;

import java.util.ArrayList;

public class TourSearchFragment extends Fragment {

    private AutoCompleteTextView actLocationTour;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> queryList;

    public TourSearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_tour_search, container, false);

        queryList = new ArrayList<>();

        actLocationTour = (AutoCompleteTextView) rootView.findViewById(R.id.act_location_tour);

        loadData();
        initListener();

        return rootView;
    }

    private void loadData() {
        adapter = new ArrayAdapter(getActivity(), android.R.layout.simple_list_item_1, queryList);
        actLocationTour.setAdapter(adapter);

        RequestTours requestTours = new RequestTours(getActivity().getApplication());
        requestTours.buildParams();
        requestTours.setResponseListener(new ResponseListener() {
            @Override
            public void onResponse(Object data) {
                if(data != null) {
                    ArrayList<LocationModel> locationModels = (ArrayList<LocationModel>) data;

                    for (LocationModel locationModel : locationModels) {
                        queryList.add(locationModel.getLocation());
                    }

                    //actLocationHotel.showDropDown();
                }
            }
        });
        requestTours.execute();
    }

    private void initListener() {
        actLocationTour.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedQuery = queryList.get(position);
                actLocationTour.setText(selectedQuery);
                if(!selectedQuery.isEmpty()) {
                    actLocationTour.setSelection(selectedQuery.length());
                }
            }
        });
    }

}

