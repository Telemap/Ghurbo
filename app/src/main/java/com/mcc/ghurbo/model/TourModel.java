package com.mcc.ghurbo.model;

import android.os.Parcel;
import android.os.Parcelable;

public class TourModel implements Parcelable{

    private String tourId;
    private String tourTitle;
    private float tourStars;
    private String location;
    private String thumbnailImage;
    private String actualAdultPrice;
    private String adultPrice;

    public TourModel(String tourId, String tourTitle, float tourStars, String location, String thumbnailImage,
                     String actualAdultPrice, String adultPrice) {
        this.tourId = tourId;
        this.tourTitle = tourTitle;
        this.tourStars = tourStars;
        this.location = location;
        this.thumbnailImage = thumbnailImage;
        this.actualAdultPrice = actualAdultPrice;
        this.adultPrice = adultPrice;
    }

    public String getTourId() {
        return tourId;
    }

    public String getTourTitle() {
        return tourTitle;
    }

    public float getTourStars() {
        return tourStars;
    }

    public String getLocation() {
        return location;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public String getActualAdultPrice() {
        return actualAdultPrice;
    }

    public String getAdultPrice() {
        return adultPrice;
    }


    protected TourModel(Parcel in) {
        tourId = in.readString();
        tourTitle = in.readString();
        tourStars = in.readFloat();
        location = in.readString();
        thumbnailImage = in.readString();
        actualAdultPrice = in.readString();
        adultPrice = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(tourId);
        dest.writeString(tourTitle);
        dest.writeFloat(tourStars);
        dest.writeString(location);
        dest.writeString(thumbnailImage);
        dest.writeString(actualAdultPrice);
        dest.writeString(adultPrice);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TourModel> CREATOR = new Creator<TourModel>() {
        @Override
        public TourModel createFromParcel(Parcel in) {
            return new TourModel(in);
        }

        @Override
        public TourModel[] newArray(int size) {
            return new TourModel[size];
        }
    };

}
