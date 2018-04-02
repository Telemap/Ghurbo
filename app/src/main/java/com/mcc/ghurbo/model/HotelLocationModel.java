package com.mcc.ghurbo.model;

/**
 * Created by Ashiq on 4/2/2018.
 */

public class HotelLocationModel {

    private int hotelId;
    private String location;

    public HotelLocationModel(int hotelId, String location) {
        this.hotelId = hotelId;
        this.location = location;
    }

    public int getHotelId() {
        return hotelId;
    }

    public String getLocation() {
        return location;
    }
}
