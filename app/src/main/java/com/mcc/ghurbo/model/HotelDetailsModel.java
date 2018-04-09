package com.mcc.ghurbo.model;


import java.util.ArrayList;

public class HotelDetailsModel {

    private String tourId;
    private String tourTitle;
    private String tourDesc;
    private String tourStars;
    private String latitude;
    private String longitude;
    private String location;
    private String maxAdults;
    private String maxChild;
    private String maxInfant;
    private String adultPrice;
    private String childPrice;
    private String infantPrice;
    private String adultStatus;
    private String childStatus;
    private String infantStatus;
    private String tourDays;
    private String tourNights;
    private String thumbnailImage;
    private ArrayList<String> allPhotos = null;
    private ArrayList<AmenityModel> amenities = null;

    public HotelDetailsModel(String tourId, String tourTitle,
                             String tourDesc, String tourStars,
                             String latitude, String longitude,
                             String location, String maxAdults,
                             String maxChild, String maxInfant,
                             String adultPrice, String childPrice,
                             String infantPrice, String adultStatus,
                             String childStatus, String infantStatus,
                             String tourDays, String tourNights,
                             String thumbnailImage, ArrayList<String> allPhotos,
                             ArrayList<AmenityModel> amenities) {
        this.tourId = tourId;
        this.tourTitle = tourTitle;
        this.tourDesc = tourDesc;
        this.tourStars = tourStars;
        this.latitude = latitude;
        this.longitude = longitude;
        this.location = location;
        this.maxAdults = maxAdults;
        this.maxChild = maxChild;
        this.maxInfant = maxInfant;
        this.adultPrice = adultPrice;
        this.childPrice = childPrice;
        this.infantPrice = infantPrice;
        this.adultStatus = adultStatus;
        this.childStatus = childStatus;
        this.infantStatus = infantStatus;
        this.tourDays = tourDays;
        this.tourNights = tourNights;
        this.thumbnailImage = thumbnailImage;
        this.allPhotos = allPhotos;
        this.amenities = amenities;
    }

    public String getTourId() {
        return tourId;
    }

    public String getTourTitle() {
        return tourTitle;
    }

    public String getTourDesc() {
        return tourDesc;
    }

    public String getTourStars() {
        return tourStars;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getLocation() {
        return location;
    }

    public String getMaxAdults() {
        return maxAdults;
    }

    public void setMaxAdults(String maxAdults) {
        this.maxAdults = maxAdults;
    }

    public String getMaxChild() {
        return maxChild;
    }

    public String getMaxInfant() {
        return maxInfant;
    }

    public String getAdultPrice() {
        return adultPrice;
    }

    public String getChildPrice() {
        return childPrice;
    }

    public String getInfantPrice() {
        return infantPrice;
    }

    public String getAdultStatus() {
        return adultStatus;
    }

    public String getChildStatus() {
        return childStatus;
    }

    public String getInfantStatus() {
        return infantStatus;
    }

    public String getTourDays() {
        return tourDays;
    }

    public String getTourNights() {
        return tourNights;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public ArrayList<String> getAllPhotos() {
        return allPhotos;
    }

    public ArrayList<AmenityModel> getAmenities() {
        return amenities;
    }

}
