package com.mcc.ghurbo.api.parser;


import com.mcc.ghurbo.model.MyBookingModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MyBookingsParser {

    public ArrayList<MyBookingModel> getMyBookingModels(String response) {

        ArrayList<MyBookingModel> arrayList = new ArrayList<>();

        try {
            if (response != null && !response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                    JSONArray jsonData = jsonObject.getJSONArray("data");

                    for(int i = 0; i < jsonData.length(); i++) {

                        JSONObject userData = jsonData.getJSONObject(i);

                        String bookingId = null, hotelId = null,
                                hotelName = null, roomId = null,
                                roomName = null, roomNumber = null,
                                type = null, expiry = null, userId = null,
                                status = null, price = null, checkin = null,
                                checkout = null, nights = null, adults = null,
                                child = null, location = null, photo = null,
                                phone = null, stars = null, days = null,
                                tourId = null, tourName = null, latitude = null,
                        longitude = null;

                        if (userData.has("booking_id")) {
                            bookingId = userData.getString("booking_id");
                        }

                        if (userData.has("hotel_id")) {
                            hotelId = userData.getString("hotel_id");
                        }

                        if (userData.has("hotel_name")) {
                            hotelName = userData.getString("hotel_name");
                        }

                        if (userData.has("room_id")) {
                            roomId = userData.getString("room_id");
                        }

                        if (userData.has("room_name")) {
                            roomName = userData.getString("room_name");
                        }

                        if (userData.has("room_number")) {
                            roomNumber = userData.getString("room_number");
                        }

                        if (userData.has("type")) {
                            type = userData.getString("type");
                        }

                        if (userData.has("expiry")) {
                            expiry = userData.getString("expiry");
                        }

                        if (userData.has("user_id")) {
                            userId = userData.getString("user_id");
                        }

                        if (userData.has("status")) {
                            status = userData.getString("status");
                        }

                        if (userData.has("price")) {
                            price = userData.getString("price");
                        }

                        if (userData.has("checkin")) {
                            checkin = userData.getString("checkin");
                        }

                        if (userData.has("checkout")) {
                            checkout = userData.getString("checkout");
                        }

                        if (userData.has("days")) {
                            days = userData.getString("days");
                        }

                        if (userData.has("nights")) {
                            nights = userData.getString("nights");
                        }

                        if (userData.has("adults")) {
                            adults = userData.getString("adults");
                        }

                        if (userData.has("child")) {
                            child = userData.getString("child");
                        }

                        if (userData.has("tour_id")) {
                            tourId = userData.getString("tour_id");
                        }

                        if (userData.has("tour_name")) {
                            tourName = userData.getString("tour_name");
                        }

                        if (userData.has("location")) {
                            location = userData.getString("location");
                        }

                        if (userData.has("photo")) {
                            photo = userData.getString("photo");
                        }

                        if (userData.has("phone")) {
                            phone = userData.getString("phone");
                        }

                        if (userData.has("stars")) {
                            stars = userData.getString("stars");
                        }

                        if (userData.has("latitude")) {
                            latitude = userData.getString("latitude");
                        }

                        if (userData.has("longitude")) {
                            longitude = userData.getString("longitude");
                        }

                        MyBookingModel myBookingModel = new MyBookingModel(bookingId, hotelId,
                                hotelName, roomId,
                                roomName, roomNumber,
                                type,
                                status, price, checkin,
                                checkout, nights, adults,
                                child, location, photo,
                                phone, stars, days,
                                tourId, tourName, latitude,
                                longitude);

                        arrayList.add(myBookingModel);
                    }

                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return arrayList;
    }

}
