package com.mcc.ghurbo.api.parser;



import com.mcc.ghurbo.model.LocationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class LocationParser {

    public ArrayList<LocationModel> getHotelLocationModels(String response) {
        ArrayList<LocationModel> arrayList = new ArrayList<>();

        try {
            if (response != null && !response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i = 0; i < jsonArray.length(); i++) {
                        String id = null, location = null;

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        if(jsonObject1.has("slug")) {
                            id = jsonObject1.getString("slug");
                        }

                        if(jsonObject1.has("location")) {
                            location = jsonObject1.getString("location");
                        }
                        arrayList.add(new LocationModel(id, location));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

}
