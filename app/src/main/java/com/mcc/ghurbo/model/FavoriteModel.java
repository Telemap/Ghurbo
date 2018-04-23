package com.mcc.ghurbo.model;


import android.os.Parcel;
import android.os.Parcelable;

public class FavoriteModel implements Parcelable{

    private String id;
    private String title;
    private String image;
    private String price;
    private String location;
    private String type;

    // for tour
    private String tourDate;

    // for hotel
    private String hotelCheckIn;
    private String hotelCheckOut;
    private String hotelRooms;

    // common
    private String adult;
    private String child;

    public FavoriteModel(String id, String title, String image, String price,
                         String location, String type, String tourDate,
                         String adult, String child, String hotelCheckIn,
                         String hotelCheckOut, String hotelRooms) {
        this.id = id;
        this.title = title;
        this.image = image;
        this.price = price;
        this.location = location;
        this.type = type;
        this.tourDate = tourDate;
        this.adult = adult;
        this.child = child;
        this.hotelCheckIn = hotelCheckIn;
        this.hotelCheckOut = hotelCheckOut;
        this.hotelRooms = hotelRooms;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }

    public String getPrice() {
        return price;
    }

    public String getLocation() {
        return location;
    }

    public String getType() {
        return type;
    }

    public String getTourDate() {
        return tourDate;
    }

    public String getHotelCheckIn() {
        return hotelCheckIn;
    }

    public String getHotelCheckOut() {
        return hotelCheckOut;
    }

    public String getHotelRooms() {
        return hotelRooms;
    }

    public String getAdult() {
        return adult;
    }

    public String getChild() {
        return child;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(title);
        parcel.writeString(image);
        parcel.writeString(price);
        parcel.writeString(location);
        parcel.writeString(type);
        parcel.writeString(tourDate);
        parcel.writeString(hotelCheckIn);
        parcel.writeString(hotelCheckOut);
        parcel.writeString(hotelRooms);
        parcel.writeString(adult);
        parcel.writeString(child);
    }

    protected FavoriteModel(Parcel in) {
        id = in.readString();
        title = in.readString();
        image = in.readString();
        price = in.readString();
        location = in.readString();
        type = in.readString();
        tourDate = in.readString();
        hotelCheckIn = in.readString();
        hotelCheckOut = in.readString();
        hotelRooms = in.readString();
        adult = in.readString();
        child = in.readString();
    }

    public static final Creator<FavoriteModel> CREATOR = new Creator<FavoriteModel>() {
        @Override
        public FavoriteModel createFromParcel(Parcel in) {
            return new FavoriteModel(in);
        }

        @Override
        public FavoriteModel[] newArray(int size) {
            return new FavoriteModel[size];
        }
    };
}
