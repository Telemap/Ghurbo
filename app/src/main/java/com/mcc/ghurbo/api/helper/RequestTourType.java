package com.mcc.ghurbo.api.helper;


import android.content.Context;

import com.mcc.ghurbo.api.http.BaseHttp;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.api.params.AppSecret;
import com.mcc.ghurbo.api.params.HttpParams;
import com.mcc.ghurbo.api.parser.TourTypeParser;

import java.util.HashMap;

public class RequestTourType extends BaseHttp {

    private Context mContext;
    private Object object;
    private ResponseListener responseListener;

    public RequestTourType(Context context) {
        super(context, HttpParams.BASE_URL + HttpParams.API_TOUR_TYPE);
        mContext = context;
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    public void buildParams(String locationId) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(HttpParams.PARAM_SECRET_KEY, AppSecret.getAppSecretKey(mContext));
        hashMap.put(HttpParams.PARAM_LOCATION_ID, locationId);

        post(hashMap);
    }

    @Override
    public void onBackgroundTask(String response) {
        object = new TourTypeParser().getTourTypeModels(response);
    }

    @Override
    public void onTaskComplete() {
        if (responseListener != null) {
            responseListener.onResponse(object);
        }
    }
}
