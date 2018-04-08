package com.mcc.ghurbo.api.helper;


import android.content.Context;
import android.util.Log;

import com.mcc.ghurbo.api.http.BaseHttp;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.api.params.AppSecret;
import com.mcc.ghurbo.api.params.HttpParams;
import com.mcc.ghurbo.api.parser.TourDetailsParser;
import com.mcc.ghurbo.api.parser.TourListParser;

import java.util.HashMap;

public class RequestTourDetails extends BaseHttp {

    private Context mContext;
    private Object object;
    private ResponseListener responseListener;

    public RequestTourDetails(Context context) {
        super(context, HttpParams.BASE_URL + HttpParams.API_TOUR_DETAILS);
        mContext = context;
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    public void buildParams(String id) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(HttpParams.PARAM_SECRET_KEY, AppSecret.getAppSecretKey(mContext));
        hashMap.put(HttpParams.PARAM_TOUR_ID, id);

        post(hashMap);
    }

    @Override
    public void onBackgroundTask(String response) {
        object = new TourDetailsParser().getTourDetailsModel(response);
    }

    @Override
    public void onTaskComplete() {
        if (responseListener != null) {
            responseListener.onResponse(object);
        }
    }
}
