package com.example.parkfinder.nationalparks.pattern;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Images {
    private String url;

    /**
     * Creates an Images object from the given JSONObject.
     *
     * @param jsonobj The JSONObject to parse.
     * @return The created Images object.
     */
    public static Images fill(JSONObject jsonobj) throws JSONException {
        Images entity = new Images();
        if (jsonobj.has("credit")) {
            entity.setCredit(jsonobj.getString("credit"));
        }
        if (jsonobj.has("title")) {
            entity.setTitle(jsonobj.getString("title"));
        }
        if (jsonobj.has("altText")) {
            entity.setAltText(jsonobj.getString("altText"));
        }
        if (jsonobj.has("caption")) {
            entity.setCaption(jsonobj.getString("caption"));
        }
        if (jsonobj.has("url")) {
            entity.setUrl(jsonobj.getString("url"));
        }
        return entity;
    }

    /**
     * Creates a List of Images objects from the given JSONArray.
     *
     * @param jsonarray The JSONArray to parse.
     * @return The created List of Images objects.
     */
    public static List<Images> fillList(JSONArray jsonarray) throws JSONException {
        if (jsonarray == null || jsonarray.length() == 0)
            return null;
        List<Images> olist = new ArrayList<Images>();
        for (int i = 0; i < jsonarray.length(); i++) {
            olist.add(fill(jsonarray.getJSONObject(i)));
        }
        return olist;
    }

    public void setCredit(String credit) {
    }

    public void setTitle(String title) {
    }

    public void setAltText(String altText) {
    }

    public void setCaption(String caption) {
    }

    public String getUrl() {
        return this.url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
