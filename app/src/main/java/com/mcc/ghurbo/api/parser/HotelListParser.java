package com.mcc.ghurbo.api.parser;



import com.mcc.ghurbo.model.HotelModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HotelListParser {

    public ArrayList<HotelModel> getHotelModels(String response) {
        ArrayList<HotelModel> arrayList = new ArrayList<>();

        try {
            if (response != null && !response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {

                        String hotelId = null, hotelTitle = null, location = null, hotelDescription = null, thumbnailImage = null,
                                basicPrice = null;
                        float hotelStars = 0;

                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);

                        if (jsonObject1.has("hotel_id")) {
                            hotelId = jsonObject1.getString("hotel_id");
                        }
                        if (jsonObject1.has("hotel_title")) {
                            hotelTitle = jsonObject1.getString("hotel_title");
                        }
                        if (jsonObject1.has("hotel_stars")) {
                            String tourStarsStr = jsonObject1.getString("hotel_stars");
                            hotelStars = Float.parseFloat(tourStarsStr);
                        }
                        if (jsonObject1.has("location")) {
                            location = jsonObject1.getString("location");
                        }
                        if (jsonObject1.has("hotel_desc")) {
                            hotelDescription = jsonObject1.getString("hotel_desc");
                        }
                        if (jsonObject1.has("thumbnail_image")) {
                            thumbnailImage = jsonObject1.getString("thumbnail_image");
                        }
                        if (jsonObject1.has("basic_price")) {
                            basicPrice = jsonObject1.getString("basic_price");
                        }

                        arrayList.add(new HotelModel(hotelId, hotelTitle, hotelStars, location, hotelDescription,
                                thumbnailImage, basicPrice));
                    }
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return arrayList;
    }

}
