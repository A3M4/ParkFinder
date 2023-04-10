package com.example.parkfinder.nationalparks.pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class InfoAddresses {

    /**
     * Creates an Addresses object from the given JSONObject.
     *
     * @param jsonobj The JSONObject to parse.
     * @return The created Addresses object.
     */
    public static InfoAddresses fill(JSONObject jsonobj) throws JSONException {
        InfoAddresses entity = new InfoAddresses();
        if (jsonobj.has("postalCode")) {
            entity.setPostalCode(jsonobj.getString("postalCode"));
        }
        if (jsonobj.has("city")) {
            entity.setCity(jsonobj.getString("city"));
        }
        if (jsonobj.has("stateCode")) {
            entity.setStateCode(jsonobj.getString("stateCode"));
        }
        if (jsonobj.has("line1")) {
            entity.setLine1(jsonobj.getString("line1"));
        }
        if (jsonobj.has("type")) {
            entity.setType(jsonobj.getString("type"));
        }
        if (jsonobj.has("line")) {
            entity.setLine(jsonobj.getString("line"));
        }
        if (jsonobj.has("line2")) {
            entity.setLine2(jsonobj.getString("line2"));
        }
        return entity;
    }

    /**
     * Creates a List of Addresses objects from the given JSONArray.
     *
     * @param jsonarray The JSONArray to parse.
     * @return The created List of Addresses objects.
     */
    public static List<InfoAddresses> fillList(JSONArray jsonarray) throws JSONException {
        if (jsonarray == null || jsonarray.length() == 0)
            return null;
        List<InfoAddresses> olist = new ArrayList<>();
        for (int i = 0; i < jsonarray.length(); i++) {
            olist.add(fill(jsonarray.getJSONObject(i)));
        }
        return olist;
    }

    public void setPostalCode(String postalCode) {
    }

    public void setCity(String city) {
    }

    public void setStateCode(String stateCode) {
    }

    public void setLine1(String line1) {
    }

    public void setType(String type) {
    }

    public void setLine(String line) {
    }

    public void setLine2(String line2) {
    }
}
