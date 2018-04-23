package com.mcc.ghurbo.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class HotelDetailsModel implements Parcelable{

    private String hotelId;
    private String hotelTitle;
    private String hotelDesc;
    private String hotelStars;
    private String hotelRatings;
    private String location;
    private String latitude;
    private String longitude;
    private String checkInTime;
    private String checkOutTime;
    private String thumbnailImage;
    private String module;
    private String phoneNumber;
    private boolean isFavorite;
    private ArrayList<RoomDetailsModel> roomDetails = null;
    private ArrayList<String> hotelImages = null;
    private ArrayList<AmenityModel> amenities = null;

    public HotelDetailsModel(String hotelId, String hotelTitle, String hotelDesc,
                             String hotelStars, String hotelRatings, String location,
                             String latitude, String longitude,
                             String checkInTime, String checkOutTime, String thumbnailImage,
                             String module, String phoneNumber, boolean isFavorite, ArrayList<String> hotelImages,
                             ArrayList<AmenityModel> amenities, ArrayList<RoomDetailsModel> roomDetails) {
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
        this.phoneNumber = phoneNumber;
        this.isFavorite = isFavorite;
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

    public String getHotelRatings() {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public boolean isFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(hotelId);
        dest.writeString(hotelTitle);
        dest.writeString(hotelDesc);
        dest.writeString(hotelStars);
        dest.writeString(hotelRatings);
        dest.writeString(location);
        dest.writeString(latitude);
        dest.writeString(longitude);
        dest.writeString(checkInTime);
        dest.writeString(checkOutTime);
        dest.writeString(thumbnailImage);
        dest.writeString(module);
        dest.writeString(phoneNumber);
        dest.writeByte((byte) (isFavorite ? 1 : 0));
        dest.writeTypedList(roomDetails);
        dest.writeStringList(hotelImages);
        dest.writeTypedList(amenities);
    }

    protected HotelDetailsModel(Parcel in) {
        hotelId = in.readString();
        hotelTitle = in.readString();
        hotelDesc = in.readString();
        hotelStars = in.readString();
        hotelRatings = in.readString();
        location = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        checkInTime = in.readString();
        checkOutTime = in.readString();
        thumbnailImage = in.readString();
        module = in.readString();
        phoneNumber = in.readString();
        isFavorite = in.readByte() != 0;
        roomDetails = in.createTypedArrayList(RoomDetailsModel.CREATOR);
        hotelImages = in.createStringArrayList();
        amenities = in.createTypedArrayList(AmenityModel.CREATOR);
    }

    public static final Creator<HotelDetailsModel> CREATOR = new Creator<HotelDetailsModel>() {
        @Override
        public HotelDetailsModel createFromParcel(Parcel in) {
            return new HotelDetailsModel(in);
        }

        @Override
        public HotelDetailsModel[] newArray(int size) {
            return new HotelDetailsModel[size];
        }
    };
}
