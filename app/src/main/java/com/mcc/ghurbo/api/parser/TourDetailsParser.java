package com.mcc.ghurbo.api.parser;

import com.mcc.ghurbo.model.AmenityModel;
import com.mcc.ghurbo.model.TourDetailsModel;
import com.mcc.ghurbo.model.TourModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class TourDetailsParser {

    public TourDetailsModel getTourDetailsModel(String response) {

        try {
            if (response != null && !response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

                    String tourId = null, tourTitle = null,
                            tourDesc = null, tourStars = null,
                            latitude = null, longitude = null,
                            location = null, maxAdults = null,
                            maxChild = null, maxInfant = null,
                            adultPrice = null, childPrice = null,
                            infantPrice = null, adultStatus = null,
                            childStatus = null, infantStatus = null,
                            tourDays = null, tourNights = null,
                            thumbnailImage = null;

                    ArrayList<String> allPhotos = null;
                    ArrayList<AmenityModel> amenities = null;

                    JSONObject jsonData = jsonObject.getJSONObject("data");

                    if (jsonData.has("tour_id")) {
                        tourId = jsonData.getString("tour_id");
                    }

                    if (jsonData.has("tour_title")) {
                        tourTitle = jsonData.getString("tour_title");
                    }

                    if (jsonData.has("tour_desc")) {
                        tourDesc = jsonData.getString("tour_desc");
                    }

                    if (jsonData.has("tour_stars")) {
                        tourStars = jsonData.getString("tour_stars");
                    }

                    if (jsonData.has("latitude")) {
                        latitude = jsonData.getString("latitude");
                    }

                    if (jsonData.has("longitude")) {
                        longitude = jsonData.getString("longitude");
                    }

                    if (jsonData.has("location")) {
                        location = jsonData.getString("location");
                    }

                    if (jsonData.has("max_adults")) {
                        maxAdults = jsonData.getString("max_adults");
                    }

                    if (jsonData.has("max_child")) {
                        maxChild = jsonData.getString("max_child");
                    }

                    if (jsonData.has("max_infant")) {
                        maxInfant = jsonData.getString("max_infant");
                    }

                    if (jsonData.has("adult_price")) {
                        adultPrice = jsonData.getString("adult_price");
                    }

                    if (jsonData.has("child_price")) {
                        childPrice = jsonData.getString("child_price");
                    }

                    if (jsonData.has("infant_price")) {
                        infantPrice = jsonData.getString("infant_price");
                    }

                    if (jsonData.has("adult_status")) {
                        adultStatus = jsonData.getString("adult_status");
                    }

                    if (jsonData.has("child_status")) {
                        childStatus = jsonData.getString("child_status");
                    }

                    if (jsonData.has("infant_status")) {
                        infantStatus = jsonData.getString("infant_status");
                    }

                    if (jsonData.has("tour_days")) {
                        tourDays = jsonData.getString("tour_days");
                    }

                    if (jsonData.has("tour_nights")) {
                        tourNights = jsonData.getString("tour_nights");
                    }

                    if (jsonData.has("thumbnail_image")) {
                        thumbnailImage = jsonData.getString("thumbnail_image");
                    }

                    if (jsonData.has("all_photos")) {
                        JSONArray photos = jsonData.getJSONArray("all_photos");
                        allPhotos = getPhotos(photos);

                    }

                    if (jsonData.has("amenities")) {
                        JSONArray amenitiesData = jsonData.getJSONArray("amenities");
                        amenities = getAmenityModels(amenitiesData);

                    }

                    return new TourDetailsModel(tourId, tourTitle,
                            tourDesc, tourStars,
                            latitude, longitude,
                            location, maxAdults,
                            maxChild, maxInfant,
                            adultPrice, childPrice,
                            infantPrice, adultStatus,
                            childStatus, infantStatus,
                            tourDays, tourNights,
                            thumbnailImage, allPhotos,
                            amenities);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    private ArrayList<String> getPhotos(JSONArray photos) {
        ArrayList<String> arrayList = new ArrayList<>();
        try {
            for (int p = 0; p < photos.length(); p++) {
                JSONObject photoObject = photos.getJSONObject(p);
                String photo = null;
                if (photoObject.has("photo")) {
                    photo = photoObject.getString("photo");
                }
                arrayList.add(photo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }

    private ArrayList<AmenityModel> getAmenityModels(JSONArray amenitiesData) {
        ArrayList<AmenityModel> arrayList = new ArrayList<>();
        try {
            for (int am = 0; am < amenitiesData.length(); am++) {
                JSONObject amenityObject = amenitiesData.getJSONObject(am);
                String title = null, icon = null;
                if (amenityObject.has("amenitie_name")) {
                    title = amenityObject.getString("amenitie_name");
                }
                if (amenityObject.has("amenitie_icon")) {
                    icon = amenityObject.getString("amenitie_icon");
                }
                arrayList.add(new AmenityModel(title, icon));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
