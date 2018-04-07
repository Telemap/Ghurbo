package com.mcc.ghurbo.utility;

import android.app.Activity;
import android.content.Intent;

import com.mcc.ghurbo.activity.HotelListActivity;
import com.mcc.ghurbo.activity.ProfilingActivity;
import com.mcc.ghurbo.activity.TourDetailsActivity;
import com.mcc.ghurbo.activity.TourListActivity;
import com.mcc.ghurbo.activity.WebPageActivity;
import com.mcc.ghurbo.data.constant.AppConstants;
import com.mcc.ghurbo.login.LoginModel;
import com.mcc.ghurbo.model.SearchHotelModel;
import com.mcc.ghurbo.model.SearchTourModel;

public class ActivityUtils {

    private static ActivityUtils sActivityUtils = null;

    public static ActivityUtils getInstance() {
        if (sActivityUtils == null) {
            sActivityUtils = new ActivityUtils();
        }
        return sActivityUtils;
    }

    public void invokeActivity(Activity activity, Class<?> tClass, boolean shouldFinish) {
        Intent intent = new Intent(activity, tClass);
        activity.startActivity(intent);
        if (shouldFinish) {
            activity.finish();
        }
    }

    public void invokeWebPageActivity(Activity activity, String pageTitle, String url) {
        Intent intent = new Intent(activity, WebPageActivity.class);
        intent.putExtra(AppConstants.BUNDLE_KEY_TITLE, pageTitle);
        intent.putExtra(AppConstants.BUNDLE_KEY_URL, url);
        activity.startActivity(intent);
    }

    public void invokeTourListActivity(Activity activity, SearchTourModel searchTourModel) {
        Intent intent = new Intent(activity, TourListActivity.class);
        intent.putExtra(AppConstants.BUNDLE_KEY_TOUR_MODEL, searchTourModel);
        activity.startActivity(intent);
    }

    public void invokeHotelListActivity(Activity activity, SearchHotelModel searchHotelModel) {
        Intent intent = new Intent(activity, HotelListActivity.class);
        intent.putExtra(AppConstants.BUNDLE_KEY_HOTEL_MODEL, searchHotelModel);
        activity.startActivity(intent);
    }

    public void invokeTourDetailsActivity(Activity activity, SearchTourModel searchTourModel) {
        Intent intent = new Intent(activity, TourDetailsActivity.class);
        intent.putExtra(AppConstants.BUNDLE_KEY_TOUR_MODEL, searchTourModel);
        activity.startActivity(intent);
    }

    public void invokeProfilingActivity(Activity activity, LoginModel loginModel) {
        Intent intent = new Intent(activity, ProfilingActivity.class);
        intent.putExtra(AppConstants.BUNDLE_KEY_LOGIN_MODEL, loginModel);
        activity.startActivity(intent);
        activity.finish();
    }
}
