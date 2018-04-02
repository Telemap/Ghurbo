package com.mcc.ghurbo.api.helper;


import android.content.Context;
import android.util.Log;

import com.mcc.ghurbo.api.http.BaseHttp;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.api.params.AppSecret;
import com.mcc.ghurbo.api.params.HttpParams;
import com.mcc.ghurbo.api.parser.HotelLocationParser;
import com.mcc.ghurbo.model.HotelLocationModel;

import java.util.ArrayList;
import java.util.HashMap;

public class RequestHotels extends BaseHttp {

    private Context mContext;
    private Object object;
    private ResponseListener responseListener;

    public RequestHotels(Context context) {
        super(context, HttpParams.BASE_URL + HttpParams.API_HOTELS);
        mContext = context;
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    public void buildParams() {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(HttpParams.PARAM_SECRET_KEY, AppSecret.getAppSecretKey(mContext));

        post(hashMap);
    }

    @Override
    public void onBackgroundTask(String response) {
        object = new HotelLocationParser().getHotelLocationModels(response);
    }

    @Override
    public void onTaskComplete() {
        if (responseListener != null) {
            responseListener.onResponse(object);
        }
    }
}
