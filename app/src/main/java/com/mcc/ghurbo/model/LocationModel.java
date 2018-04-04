package com.mcc.ghurbo.model;

public class LocationModel {

    private String id;
    private String location;

    public LocationModel(String id, String location) {
        this.id = id;
        this.location = location;
    }

    public String getId() {
        return id;
    }

    public String getLocation() {
        return location;
    }
}
