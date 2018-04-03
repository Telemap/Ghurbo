package com.mcc.ghurbo.api.helper;

import android.content.Context;
import android.util.Log;

import com.mcc.ghurbo.api.http.BaseHttp;
import com.mcc.ghurbo.api.http.ResponseListener;
import com.mcc.ghurbo.api.params.AppSecret;
import com.mcc.ghurbo.api.params.HttpParams;
import com.mcc.ghurbo.api.parser.UserParser;

import java.util.HashMap;

public class RequestLogin extends BaseHttp {

    private Context mContext;
    private Object object;
    private ResponseListener responseListener;

    public RequestLogin(Context context) {
        super(context, HttpParams.BASE_URL + HttpParams.API_LOGIN);
        mContext = context;
    }

    public void setResponseListener(ResponseListener responseListener) {
        this.responseListener = responseListener;
    }

    public void buildParams(String userid, String email, String phone, String name, String photo, String type) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put(HttpParams.PARAM_SECRET_KEY, AppSecret.getAppSecretKey(mContext));
        hashMap.put(HttpParams.PARAM_USERID, userid);
        hashMap.put(HttpParams.PARAM_EMAIL, email);
        hashMap.put(HttpParams.PARAM_PHONE, phone);
        hashMap.put(HttpParams.PARAM_NAME, name);
        hashMap.put(HttpParams.PARAM_PHOTO, photo);
        hashMap.put(HttpParams.PARAM_TYPE, type);

        post(hashMap);
    }

    @Override
    public void onBackgroundTask(String response) {
        object = new UserParser().getLoginModel(response);
    }

    @Override
    public void onTaskComplete() {
        if (responseListener != null) {
            responseListener.onResponse(object);
        }
    }
}
