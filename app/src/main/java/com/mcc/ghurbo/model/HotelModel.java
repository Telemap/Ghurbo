package com.mcc.ghurbo.model;

public class HotelModel {

    private String hotelId;
    private String hotelTitle;
    private float hotelStars;
    private String location;
    private String hotelDescription;
    private String thumbnailImage;
    private String basicPrice;

    public HotelModel(String hotelId, String hotelTitle, float hotelStars, String location, String hotelDescription, String thumbnailImage,
                      String basicPrice) {
        this.hotelId = hotelId;
        this.hotelTitle = hotelTitle;
        this.hotelStars = hotelStars;
        this.location = location;
        this.hotelDescription = hotelDescription;
        this.thumbnailImage = thumbnailImage;
        this.basicPrice = basicPrice;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getHotelTitle() {
        return hotelTitle;
    }

    public float getHotelStars() {
        return hotelStars;
    }

    public String getLocation() {
        return location;
    }

    public String getHotelDescription() {
        return hotelDescription;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public String getBasicPrice() {
        return basicPrice;
    }

}
