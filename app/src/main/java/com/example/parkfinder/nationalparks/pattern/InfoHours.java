package com.example.parkfinder.nationalparks.pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class InfoHours {
    private String wednesday;
    private String monday;

    private String thursday;

    private String sunday;

    private String tuesday;

    private String friday;

    private String saturday;

    static InfoHours fill(JSONObject jsonobj) throws JSONException {
        InfoHours entity = new InfoHours();
        if (jsonobj.has("wednesday")) {
            entity.setWednesday(jsonobj.getString("wednesday"));
        }
        if (jsonobj.has("monday")) {
            entity.setMonday(jsonobj.getString("monday"));
        }
        if (jsonobj.has("thursday")) {
            entity.setThursday(jsonobj.getString("thursday"));
        }
        if (jsonobj.has("sunday")) {
            entity.setSunday(jsonobj.getString("sunday"));
        }
        if (jsonobj.has("tuesday")) {
            entity.setTuesday(jsonobj.getString("tuesday"));
        }
        if (jsonobj.has("friday")) {
            entity.setFriday(jsonobj.getString("friday"));
        }
        if (jsonobj.has("saturday")) {
            entity.setSaturday(jsonobj.getString("saturday"));
        }
        return entity;
    }

    static List<InfoHours> fillList(JSONArray jsonarray) throws JSONException {
        if (jsonarray == null || jsonarray.length() == 0)
            return null;
        List<InfoHours> hoursList = new ArrayList<>();
        for (int i = 0; i < jsonarray.length(); i++) {
            hoursList.add(fill(jsonarray.getJSONObject(i)));
        }
        return hoursList;
    }

    public String getWednesday() {
        return this.wednesday;
    }

    public void setWednesday(String wednesday) {
        this.wednesday = wednesday;
    }

    public String getMonday() {
        return this.monday;
    }

    public void setMonday(String monday) {
        this.monday = monday;
    }

    public String getThursday() {
        return this.thursday;
    }

    public void setThursday(String thursday) {
        this.thursday = thursday;
    }

    public String getSunday() {
        return this.sunday;
    }

    public void setSunday(String sunday) {
        this.sunday = sunday;
    }

    public String getTuesday() {
        return this.tuesday;
    }

    public void setTuesday(String tuesday) {
        this.tuesday = tuesday;
    }

    public String getFriday() {
        return this.friday;
    }

    public void setFriday(String friday) {
        this.friday = friday;
    }

    public String getSaturday() {
        return this.saturday;
    }

    public void setSaturday(String saturday) {
        this.saturday = saturday;
    }
}
