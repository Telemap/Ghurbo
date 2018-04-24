package com.mcc.ghurbo.utility;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class PricingUtils {

    public static float getHotelPrice(String ratePrice, String totalRooms, int totalDays) {
        float rate = 1;
        if (ratePrice != null && !ratePrice.isEmpty()) {
            rate = Float.parseFloat(ratePrice);
        }

        float roomCount = 1;
        if (totalRooms != null && !totalRooms.isEmpty()) {
            roomCount = Float.parseFloat(totalRooms);
        }

        return rate * roomCount * totalDays;
    }

    public static float getTourPrice(String adultPrice, String childPrice,
                                     String totalAdult, String totalChild) {
        float rateAdult = 0;
        if (adultPrice != null && !adultPrice.isEmpty()) {
            rateAdult = Float.parseFloat(adultPrice);
        }

        float rateChild = 0;
        if (adultPrice != null && !adultPrice.isEmpty()) {
            rateChild = Float.parseFloat(childPrice);
        }

        float adultCount = 0;
        if (totalAdult != null && !totalAdult.isEmpty()) {
            adultCount = Float.parseFloat(totalAdult);
        }

        float childCount = 0;
        if (totalChild != null && !totalChild.isEmpty()) {
            childCount = Float.parseFloat(totalChild);
        }

        return (rateAdult * adultCount) + (rateChild * childCount);
    }

    public static int getDays(String checkIn, String checkOut) {
        try {
            Date checkInDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(checkIn);
            Date checkOutDate = new SimpleDateFormat("yyyy-MM-dd", Locale.US).parse(checkOut);
            long diff = checkOutDate.getTime() - checkInDate.getTime();
            return (int) (diff / (1000 * 60 * 60 * 24));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 1;
    }

}
