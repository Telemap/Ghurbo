package com.mcc.ghurbo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class HotelModel implements Parcelable{

    private String hotelId;
    private String hotelTitle;
    private float hotelStars;
    private String location;
    private String hotelDescription;
    private String thumbnailImage;
    private String basicPrice;

    public HotelModel(String hotelId, String hotelTitle, float hotelStars, String location, String hotelDescription, String thumbnailImage,
                      String basicPrice) {
        this.hotelId = hotelId;
        this.hotelTitle = hotelTitle;
        this.hotelStars = hotelStars;
        this.location = location;
        this.hotelDescription = hotelDescription;
        this.thumbnailImage = thumbnailImage;
        this.basicPrice = basicPrice;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getHotelTitle() {
        return hotelTitle;
    }

    public float getHotelStars() {
        return hotelStars;
    }

    public String getLocation() {
        return location;
    }

    public String getHotelDescription() {
        return hotelDescription;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public String getBasicPrice() {
        return basicPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(hotelId);
        parcel.writeString(hotelTitle);
        parcel.writeFloat(hotelStars);
        parcel.writeString(location);
        parcel.writeString(hotelDescription);
        parcel.writeString(thumbnailImage);
        parcel.writeString(basicPrice);
    }

    protected HotelModel(Parcel in) {
        hotelId = in.readString();
        hotelTitle = in.readString();
        hotelStars = in.readFloat();
        location = in.readString();
        hotelDescription = in.readString();
        thumbnailImage = in.readString();
        basicPrice = in.readString();
    }

    public static final Creator<HotelModel> CREATOR = new Creator<HotelModel>() {
        @Override
        public HotelModel createFromParcel(Parcel in) {
            return new HotelModel(in);
        }

        @Override
        public HotelModel[] newArray(int size) {
            return new HotelModel[size];
        }
    };
}
