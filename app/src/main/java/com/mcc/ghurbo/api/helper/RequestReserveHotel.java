package com.mcc.ghurbo.api.helper;


import android.content.Context;

import com.mcc.ghurbo.api.http.BaseHttp;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.api.params.AppSecret;
import com.mcc.ghurbo.api.params.HttpParams;
import com.mcc.ghurbo.api.parser.BookingParser;

import java.util.HashMap;

public class RequestReserveHotel extends BaseHttp {

    private Context mContext;
    private Object object;
    private ResponseListener responseListener;

    public RequestReserveHotel(Context context) {
        super(context, HttpParams.BASE_URL + HttpParams.API_RESERVE_HOTEL);
        mContext = context;
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    public void buildParams(String userId, String hotelId, String roomId,
                            String checkIn, String checkOut, String roomCount,
                            String adults, String children, String additionalNotes) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(HttpParams.PARAM_SECRET_KEY, AppSecret.getAppSecretKey(mContext));
        hashMap.put(HttpParams.PARAM_USER_ID, userId);
        hashMap.put(HttpParams.PARAM_ITEM_ID, hotelId);
        hashMap.put(HttpParams.PARAM_SUBITEM_ID, roomId);
        hashMap.put(HttpParams.PARAM_CHECK_IN, checkIn);
        hashMap.put(HttpParams.PARAM_CHECK_OUT, checkOut);
        hashMap.put(HttpParams.PARAM_ROOM_COUNT, roomCount);
        hashMap.put(HttpParams.PARAM_ADULTS, adults);
        hashMap.put(HttpParams.PARAM_CHILDREN, children);
        hashMap.put(HttpParams.PARAM_NOTES, additionalNotes);

        post(hashMap);
    }

    @Override
    public void onBackgroundTask(String response) {
        object = new BookingParser().getBookingInformation(response);
    }

    @Override
    public void onTaskComplete() {
        if (responseListener != null) {
            responseListener.onResponse(object);
        }
    }
}
