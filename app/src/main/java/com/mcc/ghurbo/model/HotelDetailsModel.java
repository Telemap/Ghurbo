package com.mcc.ghurbo.model;


import java.util.ArrayList;

public class HotelDetailsModel {

    private String hotelId;
    private String hotelTitle;
    private String hotelDesc;
    private String hotelStars;
    private Object hotelRatings;
    private String location;
    private String latitude;
    private String longitude;
    private String checkInTime;
    private String checkOutTime;
    private String thumbnailImage;
    private String module;
    private ArrayList<RoomDetailsModel> roomDetails = null;
    private ArrayList<String> hotelImages = null;
    private ArrayList<AmenityModel> amenities = null;

    public HotelDetailsModel(String hotelId, String hotelTitle, String hotelDesc,
                             String hotelStars, Object hotelRatings, String location,
                             String latitude, String longitude,
                             String checkInTime, String checkOutTime, String thumbnailImage,
                             String module, ArrayList<String> hotelImages, ArrayList<AmenityModel> amenities, ArrayList<RoomDetailsModel> roomDetails) {
        this.hotelId = hotelId;
        this.hotelTitle = hotelTitle;
        this.hotelDesc = hotelDesc;
        this.hotelStars = hotelStars;
        this.hotelRatings = hotelRatings;
        this.location = location;
        this.latitude = latitude;
        this.longitude = longitude;
        this.amenities = amenities;
        this.checkInTime = checkInTime;
        this.checkOutTime = checkOutTime;
        this.thumbnailImage = thumbnailImage;
        this.module = module;
        this.hotelImages = hotelImages;
        this.roomDetails = roomDetails;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getHotelTitle() {
        return hotelTitle;
    }

    public String getHotelDesc() {
        return hotelDesc;
    }

    public String getHotelStars() {
        return hotelStars;
    }

    public Object getHotelRatings() {
        return hotelRatings;
    }

    public String getLocation() {
        return location;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public ArrayList<AmenityModel> getAmenities() {
        return amenities;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public String getModule() {
        return module;
    }

    public ArrayList<String> getHotelImages() {
        return hotelImages;
    }

    public ArrayList<RoomDetailsModel> getRoomDetails() {
        return roomDetails;
    }
}
