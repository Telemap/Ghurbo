package com.mcc.ghurbo.model;

public class TourModel {

    private String tourId;
    private String tourTitle;
    private float tourStars;
    private String location;
    private String thumbnailImage;
    private String actualAdultPrice;
    private String adultPrice;

    public TourModel(String tourId, String tourTitle, float tourStars, String location, String thumbnailImage,
                     String actualAdultPrice, String adultPrice) {
        this.tourId = tourId;
        this.tourTitle = tourTitle;
        this.tourStars = tourStars;
        this.location = location;
        this.thumbnailImage = thumbnailImage;
        this.actualAdultPrice = actualAdultPrice;
        this.adultPrice = adultPrice;
    }

    public String getTourId() {
        return tourId;
    }

    public String getTourTitle() {
        return tourTitle;
    }

    public float getTourStars() {
        return tourStars;
    }

    public String getLocation() {
        return location;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public String getActualAdultPrice() {
        return actualAdultPrice;
    }

    public String getAdultPrice() {
        return adultPrice;
    }


}
