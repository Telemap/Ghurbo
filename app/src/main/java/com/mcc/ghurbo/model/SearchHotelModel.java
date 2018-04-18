package com.mcc.ghurbo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchHotelModel implements Parcelable{

    private String locationId;
    private String location;
    private String checkIn;
    private String checkOut;
    private String adult;
    private String child;
    private String rooms;

    // added as extra
    private String hotelId; // for booking
    private String hotelName; // for booking
    private String roomName; // for booking
    private String rate; // for booking
    private String roomId; // for booking
    private String notes; // for booking
    private String couponCode; // for booking
    private String imageUrl; // for booking
    private String address; // for booking
    private String longitude; // for booking
    private String latitude; // for booking
    private String phoneNumber; // for booking

    public SearchHotelModel(String locationId, String location, String checkIn, String checkOut, String adult, String child, String rooms) {
        this.locationId = locationId;
        this.location = location;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.adult = adult;
        this.child = child;
        this.rooms = rooms;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getLocation() {
        return location;
    }

    public String getCheckIn() {
        return checkIn;
    }

    public String getCheckOut() {
        return checkOut;
    }

    public String getAdult() {
        return adult;
    }

    public String getChild() {
        return child;
    }

    public String getRooms() {
        return rooms;
    }

    public String getHotelId() {
        return hotelId;
    }

    public void setHotelId(String hotelId) {
        this.hotelId = hotelId;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getCouponCode() {
        return couponCode;
    }

    public void setCouponCode(String couponCode) {
        this.couponCode = couponCode;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public void setHotelName(String hotelName) {
        this.hotelName = hotelName;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.locationId);
        dest.writeString(this.location);
        dest.writeString(this.checkIn);
        dest.writeString(this.checkOut);
        dest.writeString(this.adult);
        dest.writeString(this.child);
        dest.writeString(this.rooms);
        dest.writeString(this.hotelId);
        dest.writeString(this.imageUrl);
        dest.writeString(this.notes);
        dest.writeString(this.couponCode);
        dest.writeString(this.roomId);
        dest.writeString(this.hotelName);
        dest.writeString(this.rate);
        dest.writeString(this.roomName);
        dest.writeString(this.address);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
        dest.writeString(this.phoneNumber);
    }

    protected SearchHotelModel(Parcel in) {
        this.locationId = in.readString();
        this.location = in.readString();
        this.checkIn = in.readString();
        this.checkOut = in.readString();
        this.adult = in.readString();
        this.child = in.readString();
        this.rooms = in.readString();
        this.hotelId = in.readString();
        this.imageUrl = in.readString();
        this.notes = in.readString();
        this.couponCode = in.readString();
        this.roomId = in.readString();
        this.hotelName = in.readString();
        this.rate = in.readString();
        this.roomName = in.readString();
        this.address = in.readString();
        this.longitude = in.readString();
        this.latitude = in.readString();
        this.phoneNumber = in.readString();
    }

    public static final Parcelable.Creator<SearchHotelModel> CREATOR = new Parcelable.Creator<SearchHotelModel>() {
        public SearchHotelModel createFromParcel(Parcel source) {
            return new SearchHotelModel(source);
        }

        public SearchHotelModel[] newArray(int size) {
            return new SearchHotelModel[size];
        }
    };

}
