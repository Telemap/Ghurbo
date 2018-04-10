package com.mcc.ghurbo.model;


import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class RoomDetailsModel implements Parcelable{

    private String roomId;
    private String title;
    private String desc;
    private String maxAdults;
    private String maxChild;
    private String maxQuantity;
    private String thumbnailImage;
    private boolean breakfastInclude;
    private String price;
    private String discount;
    private String extraBeds;
    private String extrabedCharges;
    private String perNight;
    private String stay;
    private String totalPrice;
    private String actualPrice;
    private String checkin;
    private String checkout;
    private String extrabed;
    private String quantity;

    private ArrayList<String> images = null;
    private ArrayList<AmenityModel> amenities = null;

    public RoomDetailsModel(String roomId, String title, String desc,
                            String maxAdults, String maxChild, String maxQuantity,
                            String thumbnailImage,
                            boolean breakfastInclude, String price, String discount, String extraBeds,

                            String perNight, String stay, String totalPrice,
                            String actualPrice, String checkin, String checkout,
                            String extrabed, String quantity,

                            String extrabedCharges, ArrayList<String> images,
                            ArrayList<AmenityModel> amenities) {
        this.roomId = roomId;
        this.title = title;
        this.desc = desc;
        this.maxAdults = maxAdults;
        this.maxChild = maxChild;
        this.maxQuantity = maxQuantity;
        this.thumbnailImage = thumbnailImage;
        this.breakfastInclude = breakfastInclude;
        this.price = price;
        this.discount = discount;
        this.extraBeds = extraBeds;
        this.extrabedCharges = extrabedCharges;
        
        this.perNight = perNight;
        this.stay = stay;
        this.totalPrice = totalPrice;
        this.actualPrice = actualPrice;
        this.checkin = checkin;
        this.checkout = checkout;
        this.extrabed = extrabed;
        this.maxAdults = maxAdults;
        this.maxChild = maxChild;
        this.quantity = quantity;


        this.images = images;
        this.amenities = amenities;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getTitle() {
        return title;
    }

    public String getDesc() {
        return desc;
    }

    public String getMaxAdults() {
        return maxAdults;
    }

    public String getMaxChild() {
        return maxChild;
    }

    public String getMaxQuantity() {
        return maxQuantity;
    }

    public String getThumbnailImage() {
        return thumbnailImage;
    }

    public ArrayList<String> getImages() {
        return images;
    }

    public ArrayList<AmenityModel> getAmenities() {
        return amenities;
    }

    public boolean getBreakfastInclude() {
        return breakfastInclude;
    }

    public String getPrice() {
        return price;
    }

    public String getDiscount() {
        return discount;
    }

    public String getExtraBeds() {
        return extraBeds;
    }

    public String getExtrabedCharges() {
        return extrabedCharges;
    }

    public String getPerNight() {
        return perNight;
    }

    public String getStay() {
        return stay;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public String getActualPrice() {
        return actualPrice;
    }

    public String getCheckin() {
        return checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public String getExtrabed() {
        return extrabed;
    }

    public String getQuantity() {
        return quantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    protected RoomDetailsModel(Parcel in) {
        roomId = in.readString();
        title = in.readString();
        desc = in.readString();
        maxAdults = in.readString();
        maxChild = in.readString();
        maxQuantity = in.readString();
        thumbnailImage = in.readString();
        breakfastInclude = in.readByte() != 0;
        price = in.readString();
        discount = in.readString();
        extraBeds = in.readString();
        extrabedCharges = in.readString();
        perNight = in.readString();
        stay = in.readString();
        totalPrice = in.readString();
        actualPrice = in.readString();
        checkin = in.readString();
        checkout = in.readString();
        extrabed = in.readString();
        quantity = in.readString();
        images = in.createStringArrayList();
    }

    public static final Creator<RoomDetailsModel> CREATOR = new Creator<RoomDetailsModel>() {
        @Override
        public RoomDetailsModel createFromParcel(Parcel in) {
            return new RoomDetailsModel(in);
        }

        @Override
        public RoomDetailsModel[] newArray(int size) {
            return new RoomDetailsModel[size];
        }
    };

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(roomId);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeString(maxAdults);
        dest.writeString(maxChild);
        dest.writeString(maxQuantity);
        dest.writeString(thumbnailImage);
        dest.writeByte((byte) (breakfastInclude ? 1 : 0));
        dest.writeString(price);
        dest.writeString(discount);
        dest.writeString(extraBeds);
        dest.writeString(extrabedCharges);
        dest.writeString(perNight);
        dest.writeString(stay);
        dest.writeString(totalPrice);
        dest.writeString(actualPrice);
        dest.writeString(checkin);
        dest.writeString(checkout);
        dest.writeString(extrabed);
        dest.writeString(quantity);
        dest.writeStringList(images);
    }
}
