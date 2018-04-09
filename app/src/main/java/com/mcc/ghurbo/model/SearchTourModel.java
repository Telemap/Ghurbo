package com.mcc.ghurbo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchTourModel implements Parcelable {

    private String locationId;
    private String location;
    private String type;
    private String date;
    private int adult;
    private int child;

    // added as extra
    private String tourPackageId; // for booking
    private String imageUrl; // for transition

    public SearchTourModel(String locationId, String location, String type, String date, int adult, int child) {
        this.locationId = locationId;
        this.location = location;
        this.type = type;
        this.date = date;
        this.adult = adult;
        this.child = child;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getLocation() {
        return location;
    }

    public int getAdult() {
        return adult;
    }

    public int getChild() {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.locationId);
        dest.writeString(this.location);
        dest.writeString(this.type);
        dest.writeString(this.date);
        dest.writeInt(this.adult);
        dest.writeInt(this.child);
        dest.writeString(this.tourPackageId);
        dest.writeString(this.imageUrl);
    }

    protected SearchTourModel(Parcel in) {
        this.locationId = in.readString();
        this.location = in.readString();
        this.type = in.readString();
        this.date = in.readString();
        this.adult = in.readInt();
        this.child = in.readInt();
        this.tourPackageId = in.readString();
        this.imageUrl = in.readString();
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
