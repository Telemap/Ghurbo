package com.mcc.ghurbo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class AmenityModel implements Parcelable{

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

    protected AmenityModel(Parcel in) {
        title = in.readString();
        icon = in.readString();
    }

    public static final Creator<AmenityModel> CREATOR = new Creator<AmenityModel>() {
        @Override
        public AmenityModel createFromParcel(Parcel in) {
            return new AmenityModel(in);
        }

        @Override
        public AmenityModel[] newArray(int size) {
            return new AmenityModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(icon);
    }
}
