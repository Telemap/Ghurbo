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
import com.mcc.ghurbo.api.helper.RequestHotels;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.model.LocationModel;

import java.util.ArrayList;

public class HotelSearchFragment extends Fragment {

    private AutoCompleteTextView actLocationHotel;

    private ArrayAdapter<String> adapter;
    private ArrayList<String> queryList;

    public HotelSearchFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_hotel_search, container, false);

        queryList = new ArrayList<>();

        actLocationHotel = (AutoCompleteTextView) rootView.findViewById(R.id.act_location_hotel);

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

                    //actLocationHotel.showDropDown();
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
    }

}

