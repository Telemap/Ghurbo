package com.mcc.ghurbo.model;

public class AmenityModel {

    private String title;
    private String icon;

    public AmenityModel(String title, String icon) {
        this.title = title;
        this.icon = icon;
    }

    public String getTitle() {
        return title;
    }

    public String getIcon() {
        return icon;
    }
}
