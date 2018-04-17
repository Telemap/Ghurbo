package com.mcc.ghurbo.data.preference;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mcc.ghurbo.R;

public class AppPreference {


    // declare context
    private Context mContext;

    // singleton
    private static AppPreference appPreference = null;

    // common
    private SharedPreferences sharedPreferences, settingsPreferences;
    private SharedPreferences.Editor editor;

    public static AppPreference getInstance(Context context) {
        if(appPreference == null) {
            appPreference = new AppPreference(context);
        }
        return appPreference;
    }
    private AppPreference(Context context) {
        mContext = context;
        sharedPreferences = mContext.getSharedPreferences(PrefKey.APP_PREFERENCE, Context.MODE_PRIVATE);
        settingsPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
        editor = sharedPreferences.edit();
    }

    public void setString(String key, String value) {
        editor.putString(key , value);
        editor.commit();
    }
    public String getString(String key) {
        return sharedPreferences.getString(key,null);
    }

    public void setBoolean(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }
    public Boolean getBoolean(String key) {
        return sharedPreferences.getBoolean(key, false);
    }
    public void setBooleanForPush(String key, boolean value) {
        editor.putBoolean(key, value);
        editor.commit();
    }

    public void setInteger(String key, int value) {
        editor.putInt(key, value);
        editor.commit();
    }
    public int getInteger(String key) {
        return sharedPreferences.getInt(key, 0);
    }

    public boolean isNotificationOn() {
        return settingsPreferences.getBoolean(mContext.getResources().getString(R.string.pref_notification_key), true);
    }

}
