package com.mcc.ghurbo.api.params;


import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.util.Log;

import java.security.MessageDigest;

public class AppSecret {
    private static final String SECRET_KEY = "123456";

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
                return SECRET_KEY;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
