package com.mcc.ghurbo.api.parser;


import com.mcc.ghurbo.login.LoginModel;

import org.json.JSONException;
import org.json.JSONObject;

public class UserParser {

    public LoginModel getLoginModel(String response) {

        try {
            if (response != null && !response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                    JSONObject userData = jsonObject.getJSONObject("data");

                    String userId = null, name = null, email = null, profilePic = null, phone = null, type = null;

                    if(userData.has("id")) {
                        userId = userData.getString("id");
                    }

                    if(userData.has("name")) {
                        name = userData.getString("name");
                    }

                    if(userData.has("email")) {
                        email = userData.getString("email");
                    }

                    if(userData.has("image")) {
                        profilePic = userData.getString("image");
                    }

                    if(userData.has("phone")) {
                        phone = userData.getString("phone");
                    }

                    if(userData.has("auth_provider")) {
                        type = userData.getString("auth_provider");
                    }

                   return new LoginModel(userId, name, profilePic, email, phone, type);

                }
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
