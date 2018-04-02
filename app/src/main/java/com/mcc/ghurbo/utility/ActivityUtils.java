package com.mcc.ghurbo.utility;

import android.app.Activity;
import android.content.Intent;

import com.mcc.ghurbo.activity.WebPageActivity;
import com.mcc.ghurbo.data.constant.AppConstants;

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
}
