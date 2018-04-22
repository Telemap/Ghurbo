package com.mcc.ghurbo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchTourModel implements Parcelable {

    private String locationId;
    private String type;
    private String date;
    private String adult = "0";
    private String child = "0";

    // added as extra
    private String tourPackageId; // for booking
    private String tourPackageName; // for booking
    private String rateAdult; // for booking
    private String rateChild; // for booking
    private String notes; // for booking
    private String couponCode; // for booking
    private String imageUrl; // for transition
    private String address; // for booking
    private String longitude; // for booking
    private String latitude; // for booking
    private String phoneNumber; // for booking

    public SearchTourModel(String locationId, String type, String date, String adult, String child) {
        this.locationId = locationId;
        this.type = type;
        this.date = date;
        this.adult = adult;
        this.child = child;
    }

    public SearchTourModel(String date, String adult, String child) {
        this.date = date;
        this.adult = adult;
        this.child = child;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getAdult() {
        return adult;
    }

    public String getChild() {
        return child;
    }

    public String getType() {
        return type;
    }

    public String getDate() {
        return date;
    }

    public String getTourPackageId() {
        return tourPackageId;
    }

    public void setTourPackageId(String tourPackageId) {
        this.tourPackageId = tourPackageId;
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

    public String getTourPackageName() {
        return tourPackageName;
    }

    public void setTourPackageName(String tourPackageName) {
        this.tourPackageName = tourPackageName;
    }

    public String getRateAdult() {
        return rateAdult;
    }

    public void setRateAdult(String rateAdult) {
        this.rateAdult = rateAdult;
    }

    public String getRateChild() {
        return rateChild;
    }

    public void setRateChild(String rateChild) {
        this.rateChild = rateChild;
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
        dest.writeString(this.type);
        dest.writeString(this.date);
        dest.writeString(this.adult);
        dest.writeString(this.child);
        dest.writeString(this.tourPackageId);
        dest.writeString(this.imageUrl);
        dest.writeString(this.notes);
        dest.writeString(this.couponCode);
        dest.writeString(this.tourPackageName);
        dest.writeString(this.rateAdult);
        dest.writeString(this.rateChild);
        dest.writeString(this.address);
        dest.writeString(this.longitude);
        dest.writeString(this.latitude);
        dest.writeString(this.phoneNumber);
    }

    protected SearchTourModel(Parcel in) {
        this.locationId = in.readString();
        this.type = in.readString();
        this.date = in.readString();
        this.adult = in.readString();
        this.child = in.readString();
        this.tourPackageId = in.readString();
        this.imageUrl = in.readString();
        this.notes = in.readString();
        this.couponCode = in.readString();
        this.tourPackageName = in.readString();
        this.rateAdult = in.readString();
        this.rateChild = in.readString();
        this.address = in.readString();
        this.longitude = in.readString();
        this.latitude = in.readString();
        this.phoneNumber = in.readString();
    }

    public static final Parcelable.Creator<SearchTourModel> CREATOR = new Parcelable.Creator<SearchTourModel>() {
        public SearchTourModel createFromParcel(Parcel source) {
            return new SearchTourModel(source);
        }

        public SearchTourModel[] newArray(int size) {
            return new SearchTourModel[size];
        }
    };


}
