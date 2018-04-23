package com.mcc.ghurbo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class LocationModel implements Parcelable{

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


    protected LocationModel(Parcel in) {
        id = in.readString();
        location = in.readString();
    }

    public static final Creator<LocationModel> CREATOR = new Creator<LocationModel>() {
        @Override
        public LocationModel createFromParcel(Parcel in) {
            return new LocationModel(in);
        }

        @Override
        public LocationModel[] newArray(int size) {
            return new LocationModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(location);
    }
}
