package com.example.parkfinder.nationalparks.pattern;

import org.json.JSONException;
import org.json.JSONObject;

public class EntranceFees {
    private String cost;
    private String description;
    private String title;

    /**
     * Creates an EntranceFees object from the given JSONObject.
     *
     * @param jsonobj The JSONObject to parse.
     * @return The created EntranceFees object.
     */
    public static EntranceFees fill(JSONObject jsonobj) throws JSONException {
        EntranceFees entity = new EntranceFees();
        if (jsonobj.has("cost")) {
            entity.setCost(jsonobj.getString("cost"));
        }
        if (jsonobj.has("description")) {
            entity.setDescription(jsonobj.getString("description"));
        }
        if (jsonobj.has("title")) {
            entity.setTitle(jsonobj.getString("title"));
        }
        return entity;
    }

    public String getCost() {
        return this.cost;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
