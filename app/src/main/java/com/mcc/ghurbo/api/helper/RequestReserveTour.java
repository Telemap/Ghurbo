package com.mcc.ghurbo.api.helper;


import android.content.Context;

import com.mcc.ghurbo.api.http.BaseHttp;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.api.params.AppSecret;
import com.mcc.ghurbo.api.params.HttpParams;
import com.mcc.ghurbo.api.parser.BookingParser;

import java.util.HashMap;

public class RequestReserveTour extends BaseHttp {

    private Context mContext;
    private Object object;
    private ResponseListener responseListener;

    public RequestReserveTour(Context context) {
        super(context, HttpParams.BASE_URL + HttpParams.API_RESERVE_TOUR);
        mContext = context;
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    public void buildParams(String userId, String tourPackageId, String date,
                            String adults, String children, String infant, String additionalNotes) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(HttpParams.PARAM_SECRET_KEY, AppSecret.getAppSecretKey(mContext));
        hashMap.put(HttpParams.PARAM_USER_ID, userId);
        hashMap.put(HttpParams.PARAM_ITEM_ID, tourPackageId);
        hashMap.put(HttpParams.PARAM_DATE, date);
        hashMap.put(HttpParams.PARAM_ADULTS, adults);
        hashMap.put(HttpParams.PARAM_CHILDREN, children);
        hashMap.put(HttpParams.PARAM_INFANT, infant);
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
