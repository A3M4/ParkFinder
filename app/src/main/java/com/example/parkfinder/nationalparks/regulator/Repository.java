package com.example.parkfinder.nationalparks.regulator;

import android.util.Log;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.parkfinder.nationalparks.connector.HelperFunction;
import com.example.parkfinder.nationalparks.pattern.EntranceFees;
import com.example.parkfinder.nationalparks.pattern.Images;
import com.example.parkfinder.nationalparks.pattern.InfoActivities;
import com.example.parkfinder.nationalparks.pattern.InfoHours;
import com.example.parkfinder.nationalparks.pattern.InfoOperatingHours;
import com.example.parkfinder.nationalparks.pattern.InfoTopics;
import com.example.parkfinder.nationalparks.pattern.Park;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Repository {
    static List<Park> parkList = new ArrayList<>();

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
        park.setParkCode(jsonObject.getString("parkCode"));
        park.setStates(jsonObject.getString("states"));
        park.setImages(createImagesList(jsonObject.getJSONArray("images")));
        park.setWeatherInfo(jsonObject.getString("weatherInfo"));
        park.setName(jsonObject.getString("name"));
        park.setDesignation(jsonObject.getString("designation"));
        park.setActivities(createActivitiesList(jsonObject.getJSONArray("activities")));
        park.setTopics(createTopicsList(jsonObject.getJSONArray("topics")));
        park.setOperatingHours(createOperatingHoursList(jsonObject.getJSONArray("operatingHours")));
        park.setDirectionsInfo(jsonObject.getString("directionsInfo"));
        park.setDescription(jsonObject.getString("description"));
        park.setEntranceFees(createEntranceFeesList(jsonObject.getJSONArray("entranceFees")));
        return park;
    }

    /**
     * Creates a List of Images from the given JSONArray.
     *
     * @param imageArray The JSONArray to parse.
     * @return The created List of Images.
     */
    private static List<Images> createImagesList(JSONArray imageArray) throws JSONException {
        List<Images> imagesList = new ArrayList<>();
        for (int i = 0; i < imageArray.length(); i++) {
            JSONObject imageObject = imageArray.getJSONObject(i);
            Images images = new Images();
            images.setCredit(imageObject.getString("credit"));
            images.setTitle(imageObject.getString("title"));
            images.setUrl(imageObject.getString("url"));
            imagesList.add(images);
        }
        return imagesList;
    }

    /**
     * Creates a List of Activities from the given JSONArray.
     *
     * @param activityArray The JSONArray to parse.
     * @return The created List of Activities.
     */
    private static List<InfoActivities> createActivitiesList(JSONArray activityArray) throws JSONException {
        List<InfoActivities> infoActivitiesList = new ArrayList<>();
        for (int i = 0; i < activityArray.length(); i++) {
            JSONObject activityObject = activityArray.getJSONObject(i);
            InfoActivities infoActivities = new InfoActivities();
            infoActivities.setId(activityObject.getString("id"));
            infoActivities.setName(activityObject.getString("name"));
            infoActivitiesList.add(infoActivities);
        }
        return infoActivitiesList;
    }

    /**
     * Creates a List of Topics from the given JSONArray.
     *
     * @param topicsArray The JSONArray to parse.
     * @return The created List of Topics.
     */
    private static List<InfoTopics> createTopicsList(JSONArray topicsArray) throws JSONException {
        List<InfoTopics> infoTopicsList = new ArrayList<>();
        for (int i = 0; i < topicsArray.length(); i++) {
            JSONObject topicsObject = topicsArray.getJSONObject(i);
            InfoTopics infoTopics = new InfoTopics();
            infoTopics.setId(topicsObject.getString("id"));
            infoTopics.setName(topicsObject.getString("name"));
            infoTopicsList.add(infoTopics);
        }
        return infoTopicsList;
    }

    /**
     * Creates a List of OperatingHours from the given JSONArray.
     *
     * @param opHoursArray The JSONArray to parse.
     * @return The created List of OperatingHours.
     */
    private static List<InfoOperatingHours> createOperatingHoursList(JSONArray opHoursArray) throws JSONException {
        List<InfoOperatingHours> infoOperatingHoursList = new ArrayList<>();
        for (int i = 0; i < opHoursArray.length(); i++) {
            JSONObject opHoursObject = opHoursArray.getJSONObject(i);
            InfoOperatingHours opHours = new InfoOperatingHours();
            opHours.setDescription(opHoursObject.getString("description"));
            opHours.setStandardHours(createStandardHours(opHoursObject.getJSONObject("standardHours")));
            infoOperatingHoursList.add(opHours);
        }
        return infoOperatingHoursList;
    }

    /**
     * Creates a StandardHours object from the given JSONObject.
     *
     * @param hoursObject The JSONObject to parse.
     * @return The created StandardHours object.
     */
    private static InfoHours createStandardHours(JSONObject hoursObject) throws JSONException {
        InfoHours infoHours = new InfoHours();
        infoHours.setWednesday(hoursObject.getString("wednesday"));
        infoHours.setMonday(hoursObject.getString("monday"));
        infoHours.setThursday(hoursObject.getString("thursday"));
        infoHours.setSunday(hoursObject.getString("sunday"));
        infoHours.setTuesday(hoursObject.getString("tuesday"));
        infoHours.setFriday(hoursObject.getString("friday"));
        infoHours.setSaturday(hoursObject.getString("saturday"));
        return infoHours;
    }

    /**
     * Creates a List of EntranceFees from the given JSONArray.
     *
     * @param entranceFeesArray The JSONArray to parse.
     * @return The created List of EntranceFees.
     */
    private static List<EntranceFees> createEntranceFeesList(JSONArray entranceFeesArray) throws JSONException {
        List<EntranceFees> entranceFeesList = new ArrayList<>();
        for (int i = 0; i < entranceFeesArray.length(); i++) {
            JSONObject entranceFeesObject = entranceFeesArray.getJSONObject(i);
            EntranceFees entranceFees = new EntranceFees();
            entranceFees.setCost(entranceFeesObject.getString("cost"));
            entranceFees.setDescription(entranceFeesObject.getString("description"));
            entranceFees.setTitle(entranceFeesObject.getString("title"));
            entranceFeesList.add(entranceFees);
        }
        return entranceFeesList;
    }
}

