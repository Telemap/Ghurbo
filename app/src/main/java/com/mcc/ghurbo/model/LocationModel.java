package com.mcc.ghurbo.model;

/**
 * Created by Ashiq on 4/2/2018.
 */

public class LocationModel {

    private String hotelId;
    private String location;

    public LocationModel(String hotelId, String location) {
        this.hotelId = hotelId;
        this.location = location;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getLocation() {
        return location;
    }
}
