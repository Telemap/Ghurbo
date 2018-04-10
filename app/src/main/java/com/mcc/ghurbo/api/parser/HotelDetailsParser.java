package com.mcc.ghurbo.api.parser;

import com.mcc.ghurbo.model.AmenityModel;
import com.mcc.ghurbo.model.HotelDetailsModel;
import com.mcc.ghurbo.model.RoomDetailsModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HotelDetailsParser {

    public HotelDetailsModel getHotelDetailsModel(String response) {

        try {
            if (response != null && !response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {

                    String hotelId = null, hotelTitle = null, hotelDesc = null,
                            hotelStars = null, hotelRatings = null, location = null,
                            latitude = null, longitude = null,
                            checkInTime = null, checkOutTime = null, thumbnailImage = null,
                            module = null;

                    ArrayList<String> hotelImages = null;
                    ArrayList<AmenityModel> amenities = null;
                    ArrayList<RoomDetailsModel> roomDetails = null;

                    JSONObject jsonData = jsonObject.getJSONObject("data");


                    if (jsonData.has("hotel_id")) {
                        hotelId = jsonData.getString("hotel_id");
                    }

                    if (jsonData.has("hotel_title")) {
                        hotelTitle = jsonData.getString("hotel_title");
                    }

                    if (jsonData.has("hotel_desc")) {
                        hotelDesc = jsonData.getString("hotel_desc");
                    }

                    if (jsonData.has("hotel_stars")) {
                        hotelStars = jsonData.getString("hotel_stars");
                    }

                    if (jsonData.has("hotel_ratings")) {
                        hotelRatings = jsonData.getString("hotel_ratings");
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

                    if (jsonData.has("check_in_time")) {
                        checkInTime = jsonData.getString("check_in_time");
                    }

                    if (jsonData.has("check_out_time")) {
                        checkOutTime = jsonData.getString("check_out_time");
                    }

                    if (jsonData.has("thumbnail_image")) {
                        thumbnailImage = jsonData.getString("thumbnail_image");
                    }

                    if (jsonData.has("module")) {
                        module = jsonData.getString("module");
                    }

                    if (jsonData.has("hotel_images")) {
                        JSONArray photos = jsonData.getJSONArray("hotel_images");
                        hotelImages = getPhotos(photos);
                    }

                    if (jsonData.has("amenities")) {
                        JSONArray amenitiesData = jsonData.getJSONArray("amenities");
                        amenities = getAmenityModels(amenitiesData);
                    }

                    if (jsonData.has("room_details")) {
                        JSONArray infoData = jsonData.getJSONArray("room_details");
                        roomDetails = getRoomDetailsModels(infoData);
                    }

                    return new HotelDetailsModel(hotelId, hotelTitle, hotelDesc,
                            hotelStars, hotelRatings, location,
                            latitude, longitude,
                            checkInTime, checkOutTime, thumbnailImage,
                            module, hotelImages, amenities,
                            roomDetails);
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

    private ArrayList<RoomDetailsModel> getRoomDetailsModels(JSONArray roomDetailsData) {
        ArrayList<RoomDetailsModel> arrayList = new ArrayList<>();
        try {
            for (int am = 0; am < roomDetailsData.length(); am++) {
                JSONObject roomDetailsObject = roomDetailsData.getJSONObject(am);

                String roomId = null, title = null, desc = null,
                        maxAdults = null, maxChild = null, maxQuantity = null,
                        thumbnailImage = null, price = null, discount = null,
                        extraBeds = null, perNight = null, stay = null, totalPrice = null,
                        actualPrice = null, checkin = null, checkout = null, extrabed = null,
                        quantity = null, extrabedCharges = null;
                boolean breakfastInclude = false;
                ArrayList<String> images = null;
                ArrayList<AmenityModel> amenities = null;


                if (roomDetailsObject.has("room_id")) {
                    roomId = roomDetailsObject.getString("room_id");
                }

                if (roomDetailsObject.has("title")) {
                    title = roomDetailsObject.getString("title");
                }
                if (roomDetailsObject.has("desc")) {
                    desc = roomDetailsObject.getString("desc");
                }
                if (roomDetailsObject.has("maxAdults")) {
                    maxAdults = roomDetailsObject.getString("maxAdults");
                }
                if (roomDetailsObject.has("maxChild")) {
                    maxChild = roomDetailsObject.getString("maxChild");
                }
                if (roomDetailsObject.has("maxQuantity")) {
                    maxQuantity = roomDetailsObject.getString("maxQuantity");
                }
                if (roomDetailsObject.has("thumbnail_image")) {
                    thumbnailImage = roomDetailsObject.getString("thumbnail_image");
                }
                if (roomDetailsObject.has("breakfast_include")) {
                    breakfastInclude = roomDetailsObject.getBoolean("breakfast_include");
                }
                if (roomDetailsObject.has("price")) {
                    price = roomDetailsObject.getString("price");
                }
                if (roomDetailsObject.has("discount")) {
                    discount = roomDetailsObject.getString("discount");
                }
                if (roomDetailsObject.has("extraBeds")) {
                    extraBeds = roomDetailsObject.getString("extraBeds");
                }
                if (roomDetailsObject.has("extrabedCharges")) {
                    extrabedCharges = roomDetailsObject.getString("extrabedCharges");
                }

                if (roomDetailsObject.has("Info")) {
                    JSONObject infoObject = roomDetailsObject.getJSONObject("Info");

                    if (infoObject.has("perNight")) {
                        perNight = infoObject.getString("perNight");
                    }

                    if (infoObject.has("stay")) {
                        stay = infoObject.getString("stay");
                    }

                    if (infoObject.has("totalPrice")) {
                        totalPrice = infoObject.getString("totalPrice");
                    }

                    if (infoObject.has("actualPrice")) {
                        actualPrice = infoObject.getString("actualPrice");
                    }

                    if (infoObject.has("checkin")) {
                        checkin = infoObject.getString("checkin");
                    }

                    if (infoObject.has("checkout")) {
                        checkout = infoObject.getString("checkout");
                    }

                    if (infoObject.has("extrabed")) {
                        extrabed = infoObject.getString("extrabed");
                    }

                    if (infoObject.has("maxAdults")) {
                        maxAdults = infoObject.getString("maxAdults");
                    }

                    if (infoObject.has("maxChild")) {
                        maxChild = infoObject.getString("maxChild");
                    }

                    if (infoObject.has("quantity")) {
                        quantity = infoObject.getString("quantity");
                    }

                }

                if (roomDetailsObject.has("images")) {
                    JSONArray photos = roomDetailsObject.getJSONArray("images");
                    images = getPhotos(photos);
                }

                if (roomDetailsObject.has("amenities")) {
                    JSONArray amenitiesData = roomDetailsObject.getJSONArray("amenities");
                    amenities = getAmenityModels(amenitiesData);
                }

                arrayList.add(new RoomDetailsModel(roomId, title, desc,
                        maxAdults, maxChild, maxQuantity,
                        thumbnailImage, breakfastInclude, price, discount, extraBeds,
                        perNight, stay, totalPrice, actualPrice, checkin, checkout,
                        extrabed, quantity, extrabedCharges, images, amenities));

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return arrayList;
    }
}
