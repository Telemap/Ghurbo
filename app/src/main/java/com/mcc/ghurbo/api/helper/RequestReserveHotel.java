package com.mcc.ghurbo.api.helper;


import android.content.Context;

import com.mcc.ghurbo.api.http.BaseHttp;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.api.params.AppSecret;
import com.mcc.ghurbo.api.params.HttpParams;
import com.mcc.ghurbo.api.parser.TourListParser;

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

    public void buildParams(String userId, String itemid, String subItemId,
                            String checkIn, String checkOut, String roomCount,
                            String adults, String children, String additionalNotes) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(HttpParams.PARAM_SECRET_KEY, AppSecret.getAppSecretKey(mContext));
        hashMap.put(HttpParams.PARAM_USER_ID, userId);
        hashMap.put(HttpParams.PARAM_ITEM_ID, itemid);
        hashMap.put(HttpParams.PARAM_SUBITEM_ID, subItemId);
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
        object = new TourListParser().getTourModels(response);
    }

    @Override
    public void onTaskComplete() {
        if (responseListener != null) {
            responseListener.onResponse(object);
        }
    }
}
