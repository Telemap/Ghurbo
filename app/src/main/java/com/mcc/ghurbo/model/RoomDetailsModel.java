package com.mcc.ghurbo.model;


import java.util.ArrayList;

public class RoomDetailsModel {

    private String roomId;
    private String title;
    private String desc;
    private String maxAdults;
    private String maxChild;
    private Integer maxQuantity;
    private String thumbnailImage;
    private String breakfastInclude;
    private Integer price;
    private Integer aprice;
    private Integer aprice1;
    private Integer discount;
    private String extraBeds;
    private Integer extrabedCharges;

    private String roomID;
    private Integer perNight;
    private Integer stay;
    private Integer totalPrice;
    private Integer actualPrice;
    private String checkin;
    private String checkout;
    private Integer extrabed;
    private String quantity;

    private ArrayList<String> images = null;
    private ArrayList<AmenityModel> amenities = null;

    public RoomDetailsModel(String roomId, String title, String desc,
                            String maxAdults, String maxChild, Integer maxQuantity,
                            String thumbnailImage,
                            String breakfastInclude, Integer price, Integer aprice,
                            Integer aprice1, Integer discount, String extraBeds,

                            String roomID, Integer perNight, Integer stay, Integer totalPrice,
                            Integer actualPrice, String checkin, String checkout,
                            Integer extrabed, String quantity,

                            Integer extrabedCharges, ArrayList<String> images,
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
        this.aprice = aprice;
        this.aprice1 = aprice1;
        this.discount = discount;
        this.extraBeds = extraBeds;
        this.extrabedCharges = extrabedCharges;


        this.roomID = roomID;
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

    public Integer getMaxQuantity() {
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

    public String getBreakfastInclude() {
        return breakfastInclude;
    }

    public Integer getPrice() {
        return price;
    }

    public Integer getAprice() {
        return aprice;
    }

    public Integer getAprice1() {
        return aprice1;
    }

    public Integer getDiscount() {
        return discount;
    }

    public String getExtraBeds() {
        return extraBeds;
    }

    public Integer getExtrabedCharges() {
        return extrabedCharges;
    }




    public String getRoomID() {
        return roomID;
    }

    public Integer getPerNight() {
        return perNight;
    }

    public Integer getStay() {
        return stay;
    }

    public Integer getTotalPrice() {
        return totalPrice;
    }

    public Integer getActualPrice() {
        return actualPrice;
    }

    public String getCheckin() {
        return checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public Integer getExtrabed() {
        return extrabed;
    }

    public String getQuantity() {
        return quantity;
    }
}
