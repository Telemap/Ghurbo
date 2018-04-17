package com.mcc.ghurbo.api.parser;


import org.json.JSONException;
import org.json.JSONObject;

public class BookingParser {

    public String getBookingInformation(String response) {

        try {
            if (response != null && !response.isEmpty()) {
                JSONObject jsonObject = new JSONObject(response);
                if (jsonObject.has("status") && jsonObject.getString("status").equals("success")) {
                    JSONObject userData = jsonObject.getJSONObject("data");

                    String invoiceId = null;
                    if(userData.has("invoice_id")) {
                        invoiceId = userData.getString("invoice_id");
                    }

                   return invoiceId;
                }
            }

        }catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

}
