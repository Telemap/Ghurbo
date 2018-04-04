package com.mcc.ghurbo.api.parser;



import com.mcc.ghurbo.model.TourModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TourListParser {

    public ArrayList<TourModel> getTourModels(String response) {
        ArrayList<TourModel> arrayList = new ArrayList<>();

        try {
            if (response != null && !response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        String tourId = null, tourTitle = null, location = null, thumbnailImage = null,
                                actualAdultPrice = null, adultPrice = null;
                        float tourStars = 0;

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        if (jsonObject1.has("tour_id")) {
                            tourId = jsonObject1.getString("tour_id");
                        }
                        if (jsonObject1.has("tour_title")) {
                            tourTitle = jsonObject1.getString("tour_title");
                        }
                        if (jsonObject1.has("tour_stars")) {
                            String tourStarsStr = jsonObject1.getString("tour_stars");
                            tourStars = Float.parseFloat(tourStarsStr);
                        }
                        if (jsonObject1.has("location")) {
                            location = jsonObject1.getString("location");
                        }
                        if (jsonObject1.has("thumbnail_image")) {
                            thumbnailImage = jsonObject1.getString("thumbnail_image");
                        }
                        if (jsonObject1.has("actual_tour_adult_price")) {
                            actualAdultPrice = jsonObject1.getString("actual_tour_adult_price");
                        }
                        if (jsonObject1.has("tour_adult_price")) {
                            adultPrice = jsonObject1.getString("tour_adult_price");
                        }

                        arrayList.add(new TourModel(tourId, tourTitle, tourStars, location, thumbnailImage,
                                actualAdultPrice, adultPrice));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

}
