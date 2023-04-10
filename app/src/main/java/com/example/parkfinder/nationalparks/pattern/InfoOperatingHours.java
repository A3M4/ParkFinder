package com.example.parkfinder.nationalparks.pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InfoOperatingHours {
    private List<Exceptions> exceptions;
    private InfoHours infoHours;
    private String name;

    // Constructors
    public InfoOperatingHours() {
    }

    // Methods to fill data from JSON
    public static InfoOperatingHours fromJson(JSONObject jsonobj) throws JSONException {
        InfoOperatingHours entity = new InfoOperatingHours();

        jsonobj.has("exceptions");
        if (jsonobj.has("description")) {
            entity.setDescription(jsonobj.getString("description"));
        }
        if (jsonobj.has("standardHours")) {
            entity.setStandardHours((InfoHours) jsonobj.get("standardHours"));
        }
        if (jsonobj.has("name")) {
            entity.setName(jsonobj.getString("name"));
        }

        return entity;
    }

    public static List<InfoOperatingHours> fromJson(JSONArray jsonarray) throws JSONException {
        if (jsonarray == null || jsonarray.length() == 0) {
            return null;
        }

        List<InfoOperatingHours> olist = new ArrayList<>();

        for (int i = 0; i < jsonarray.length(); i++) {
            olist.add(fromJson(jsonarray.getJSONObject(i)));
        }

        return olist;
    }

    public void setExceptions(List<Exceptions> exceptions) {
        this.exceptions = exceptions;
    }

    public void setDescription(String description) {
    }

    public InfoHours getStandardHours() {
        return infoHours;
    }

    public void setStandardHours(InfoHours infoHours) {
        this.infoHours = infoHours;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
