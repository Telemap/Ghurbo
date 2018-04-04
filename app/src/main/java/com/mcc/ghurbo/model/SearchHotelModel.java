package com.mcc.ghurbo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class SearchHotelModel implements Parcelable{

    private String id;
    private String location;
    private String checkIn;
    private String checkOut;
    private int adult;
    private int child;
    private int rooms;

    public SearchHotelModel(String location, String checkIn, String checkOut, int adult, int child, int rooms) {
        this.location = location;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.adult = adult;
        this.child = child;
        this.rooms = rooms;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getAdult() {
        return adult;
    }

    public int getChild() {
        return child;
    }

    public int getRooms() {
        return rooms;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.id);
        dest.writeString(this.location);
        dest.writeString(this.checkIn);
        dest.writeString(this.checkOut);
        dest.writeInt(this.adult);
        dest.writeInt(this.child);
        dest.writeInt(this.rooms);
    }

    protected SearchHotelModel(Parcel in) {
        this.id = in.readString();
        this.location = in.readString();
        this.checkIn = in.readString();
        this.checkOut = in.readString();
        this.adult = in.readInt();
        this.child = in.readInt();
        this.rooms = in.readInt();
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
