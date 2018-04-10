package com.mcc.ghurbo.api.helper;


import android.content.Context;

import com.mcc.ghurbo.api.http.BaseHttp;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.api.params.AppSecret;
import com.mcc.ghurbo.api.params.HttpParams;
import com.mcc.ghurbo.api.parser.HotelDetailsParser;
import com.mcc.ghurbo.api.parser.TourDetailsParser;

import java.util.HashMap;

public class RequestHotelDetails extends BaseHttp {

    private Context mContext;
    private Object object;
    private ResponseListener responseListener;

    public RequestHotelDetails(Context context) {
        super(context, HttpParams.BASE_URL + HttpParams.API_HOTEL_DETAILS);
        mContext = context;
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    public void buildParams(String id, String checkIn, String checkOut) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(HttpParams.PARAM_SECRET_KEY, AppSecret.getAppSecretKey(mContext));
        hashMap.put(HttpParams.PARAM_HOTEL_ID, id);
        hashMap.put(HttpParams.PARAM_CHECK_IN, checkIn);
        hashMap.put(HttpParams.PARAM_CHECK_OUT, checkOut);

        post(hashMap);
    }

    @Override
    public void onBackgroundTask(String response) {
        object = new HotelDetailsParser().getHotelDetailsModel(response);
    }

    @Override
    public void onTaskComplete() {
        if (responseListener != null) {
            responseListener.onResponse(object);
        }
    }
}
