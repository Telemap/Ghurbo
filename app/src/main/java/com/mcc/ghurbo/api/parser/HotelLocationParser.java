package com.mcc.ghurbo.api.parser;



import com.mcc.ghurbo.model.HotelLocationModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HotelLocationParser {

    public ArrayList<HotelLocationModel> getHotelLocationModels(String response) {
        ArrayList<HotelLocationModel> arrayList = new ArrayList<>();

        try {
            if (response != null && !response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i = 0; i < jsonArray.length(); i++) {
                        int id = -1;
                        String location = null;

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                        if(jsonObject1.has("slug")) {
                            id = jsonObject1.getInt("slug");
                        }

                        if(jsonObject1.has("location")) {
                            location = jsonObject1.getString("location");
                        }
                        arrayList.add(new HotelLocationModel(id, location));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

}
