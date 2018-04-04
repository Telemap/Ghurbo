package com.mcc.ghurbo.api.parser;



import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TourTypeParser {

    public ArrayList<String> getTourTypeModels(String response) {
        ArrayList<String> arrayList = new ArrayList<>();

        try {
            if (response != null && !response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);
                if(jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for(int i = 0; i < jsonArray.length(); i++) {
                        String type = null;

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        if(jsonObject1.has("type")) {
                            type = jsonObject1.getString("type");
                        }
                        arrayList.add(type);
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

}
