package com.mcc.ghurbo.api.params;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import com.mcc.ghurbo.BuildConfig;

import java.security.MessageDigest;

public class AppSecret {

    public static String getAppSecretKey(Context context) {
        String hashKey = null;
        try {
            PackageInfo info = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA1");
                md.update(signature.toByteArray());
                //hashKey = Base64.encodeToString(md.digest(), Base64.DEFAULT);

                StringBuilder hexString = new StringBuilder();
                for (byte aMessageDigest : md.digest()) {
                    String h = Integer.toHexString(0xFF & aMessageDigest);
                    while (h.length() < 2)
                        h = "0" + h;
                    hexString.append(h);
                }
                hashKey = hexString.toString();

                Log.e("KeyHash:", "Value: " + hashKey);
                //return hashKey;
                return BuildConfig.API_SECRET; // the testing API_SECRET is: 123456
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
