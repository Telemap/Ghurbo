package com.mcc.ghurbo.model;


import android.os.Parcel;
import android.os.Parcelable;

public class MyBookingModel implements Parcelable{

    private String bookingId;
    private String hotelId;
    private String hotelName;
    private String roomId;
    private String roomName;
    private String roomNumber;
    private String type;
    private String status;
    private String price;
    private String checkin;
    private String checkout;
    private String nights;
    private String adults;
    private String child;
    private String location;
    private String photo;
    private String phone;
    private String stars;
    private String days;
    private String tourId;
    private String tourName;
    private String latitude;
    private String longitude;

    // extra
    private String totalPrice;

    public MyBookingModel(String bookingId, String hotelId,
                          String hotelName, String roomId,
                          String roomName, String roomNumber,
                          String type,
                          String status, String price, String checkin,
                          String checkout, String nights, String adults,
                          String child, String location, String photo,
                          String phone, String stars, String days,
                          String tourId, String tourName, String latitude,
                          String longitude
    ) {
        this.bookingId = bookingId;
        this.hotelId = hotelId;
        this.hotelName = hotelName;
        this.roomId = roomId;
        this.roomName = roomName;
        this.roomNumber = roomNumber;
        this.type = type;
        this.status = status;
        this.price = price;
        this.checkin = checkin;
        this.checkout = checkout;
        this.nights = nights;
        this.adults = adults;
        this.child = child;
        this.location = location;
        this.photo = photo;
        this.phone = phone;
        this.stars = stars;
        this.days = days;
        this.tourId = tourId;
        this.tourName = tourName;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public String getBookingId() {
        return bookingId;
    }

    public String getHotelId() {
        return hotelId;
    }

    public String getHotelName() {
        return hotelName;
    }

    public String getRoomId() {
        return roomId;
    }

    public String getRoomName() {
        return roomName;
    }

    public String getRoomNumber() {
        return roomNumber;
    }

    public String getType() {
        return type;
    }

    public String getStatus() {
        return status;
    }

    public String getPrice() {
        return price;
    }

    public String getCheckin() {
        return checkin;
    }

    public String getCheckout() {
        return checkout;
    }

    public String getNights() {
        return nights;
    }

    public String getAdults() {
        return adults;
    }

    public String getChild() {
        return child;
    }

    public String getLocation() {
        return location;
    }

    public String getPhoto() {
        return photo;
    }

    public String getPhone() {
        return phone;
    }

    public String getStars() {
        return stars;
    }

    public String getDays() {
        return days;
    }

    public String getTourId() {
        return tourId;
    }

    public String getTourName() {
        return tourName;
    }

    public String getLatitude() {
        return latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    protected MyBookingModel(Parcel in) {
        bookingId = in.readString();
        hotelId = in.readString();
        hotelName = in.readString();
        roomId = in.readString();
        roomName = in.readString();
        roomNumber = in.readString();
        type = in.readString();
        status = in.readString();
        price = in.readString();
        checkin = in.readString();
        checkout = in.readString();
        nights = in.readString();
        adults = in.readString();
        child = in.readString();
        location = in.readString();
        photo = in.readString();
        phone = in.readString();
        stars = in.readString();
        days = in.readString();
        tourId = in.readString();
        tourName = in.readString();
        latitude = in.readString();
        longitude = in.readString();
        totalPrice = in.readString();
    }

    public static final Creator<MyBookingModel> CREATOR = new Creator<MyBookingModel>() {
        @Override
        public MyBookingModel createFromParcel(Parcel in) {
            return new MyBookingModel(in);
        }

        @Override
        public MyBookingModel[] newArray(int size) {
            return new MyBookingModel[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(bookingId);
        parcel.writeString(hotelId);
        parcel.writeString(hotelName);
        parcel.writeString(roomId);
        parcel.writeString(roomName);
        parcel.writeString(roomNumber);
        parcel.writeString(type);
        parcel.writeString(status);
        parcel.writeString(price);
        parcel.writeString(checkin);
        parcel.writeString(checkout);
        parcel.writeString(nights);
        parcel.writeString(adults);
        parcel.writeString(child);
        parcel.writeString(location);
        parcel.writeString(photo);
        parcel.writeString(phone);
        parcel.writeString(stars);
        parcel.writeString(days);
        parcel.writeString(tourId);
        parcel.writeString(tourName);
        parcel.writeString(latitude);
        parcel.writeString(longitude);
        parcel.writeString(totalPrice);
    }
}
