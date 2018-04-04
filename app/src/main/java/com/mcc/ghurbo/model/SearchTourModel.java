package com.mcc.ghurbo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchTourModel implements Parcelable {

    private String id;
    private String location;
    private String type;
    private String date;
    private int adult;
    private int child;

    public SearchTourModel(String id, String location, String type, String date, int adult, int child) {
        this.id = id;
        this.location = location;
        this.type = type;
        this.date = date;
        this.adult = adult;
        this.child = child;
    }

    public String getId() {
        return id;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.location);
        dest.writeString(this.type);
        dest.writeString(this.date);
        dest.writeInt(this.adult);
        dest.writeInt(this.child);
    }

    protected SearchTourModel(Parcel in) {
        this.id = in.readString();
        this.location = in.readString();
        this.type = in.readString();
        this.date = in.readString();
        this.adult = in.readInt();
        this.child = in.readInt();
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
