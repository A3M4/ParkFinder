package com.example.parkfinder.nationalparks.regulator;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.parkfinder.nationalparks.connector.HelperFunction;
import com.example.parkfinder.nationalparks.pattern.Park;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {

    public static void getParks(final AsyncResponse callback, String stateCode) {
        String url = HelperFunction.getParksUrl(stateCode);
        Log.d("URL", "getParks: " + url);
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(Request.Method.GET, url, null, response -> {
                    try {
                        JSONArray jsonArray = response.getJSONArray("data");
                        List<Park> parkList = new ArrayList<>();
                        for (int i = 0; i < jsonArray.length(); i++) {
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            Park park = createParkFromJson(jsonObject);
                            parkList.add(park);
                        }
                        if (null != callback) {
                            callback.processPark(parkList);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, Throwable::printStackTrace);
        AppController.getInstance().addToRequestQueue(jsonObjectRequest);
    }

    /**
     * Creates a Park object from the given JSONObject.
     *
     * @param jsonObject The JSONObject to parse.
     * @return The created Park object.
     */
    private static Park createParkFromJson(JSONObject jsonObject) throws JSONException {
        Park park = new Park();
        park.setId(jsonObject.getString("id"));
        park.setFullName(jsonObject.getString("fullName"));
        park.setLatitude(jsonObject.getString("latitude"));
        park.setLongitude(jsonObject.getString("longitude"));
        return park;
    }
}


