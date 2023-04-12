package com.example.parkfinder.nationalparks.pattern;

import androidx.annotation.NonNull;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

class Numbers {

    static Numbers fill(JSONObject jsonobj) throws JSONException {
        Numbers entity = new Numbers();
        if (jsonobj.has("phoneNumber")) {
            entity.setPhoneNumber(jsonobj.getString("phoneNumber"));
        }
        if (jsonobj.has("description")) {
            entity.setDescription(jsonobj.getString("description"));
        }
        if (jsonobj.has("extension")) {
            entity.setExtension(jsonobj.getString("extension"));
        }
        if (jsonobj.has("type")) {
            entity.setType(jsonobj.getString("type"));
        }
        return entity;
    }

    static List<Numbers> fillList(JSONArray jsonarray) throws JSONException {
        if (jsonarray == null || jsonarray.length() == 0)
            return null;
        List<Numbers> olist = new ArrayList<Numbers>();
        for (int i = 0; i < jsonarray.length(); i++) {
            olist.add(fill(jsonarray.getJSONObject(i)));
        }
        return olist;
    }

    void setPhoneNumber(String phoneNumber) {
    }

    void setDescription(String description) {
    }

    void setExtension(String extension) {
    }

    void setType(String type) {
    }
}

class EmailAddresses {

    static EmailAddresses fill(JSONObject jsonobj) throws JSONException {
        EmailAddresses entity = new EmailAddresses();
        if (jsonobj.has("description")) {
            entity.setDescription(jsonobj.getString("description"));
        }
        if (jsonobj.has("emailAddress")) {
            entity.setEmailAddress(jsonobj.getString("emailAddress"));
        }
        return entity;
    }

    static List<EmailAddresses> fillList(JSONArray jsonarray) throws JSONException {
        if (jsonarray == null || jsonarray.length() == 0)
            return null;
        List<EmailAddresses> olist = new ArrayList<>();
        for (int i = 0; i < jsonarray.length(); i++) {
            olist.add(fill(jsonarray.getJSONObject(i)));
        }
        return olist;
    }

    void setDescription(String description) {
    }

    void setEmailAddress(String emailAddress) {
    }
}

class Contacts {

    static Contacts fill(JSONObject jsonobj) throws JSONException {
        Contacts entity = new Contacts();
        if (jsonobj.has("phoneNumbers")) {
            entity.setPhoneNumbers((List<Numbers>) jsonobj.getJSONArray("phoneNumbers"));
        }
        if (jsonobj.has("emailAddresses")) {
            entity.setEmailAddresses((List<EmailAddresses>) jsonobj.getJSONArray("emailAddresses"));
        }
        return entity;
    }

    static List<Contacts> fillList(JSONArray jsonarray) throws JSONException {
        if (jsonarray == null || jsonarray.length() == 0)
            return null;
        List<Contacts> olist = new ArrayList<>();
        for (int i = 0; i < jsonarray.length(); i++) {
            olist.add(fill(jsonarray.getJSONObject(i)));
        }
        return olist;
    }

    void setPhoneNumbers(List<Numbers> numbers) {
    }

    void setEmailAddresses(List<EmailAddresses> emailAddresses) {
    }
}


class Exceptions {

    static Exceptions fill(JSONObject jsonobj) throws JSONException {
        Exceptions entity = new Exceptions();
        if (jsonobj.has("startDate")) {
            entity.setStartDate((Date) jsonobj.get("startDate"));
        }
        if (jsonobj.has("name")) {
            entity.setName(jsonobj.getString("name"));
        }
        return entity;
    }

    static List<Exceptions> fillList(JSONArray jsonarray) throws JSONException {
        if (jsonarray == null || jsonarray.length() == 0)
            return null;
        List<Exceptions> olist = new ArrayList<>();
        for (int i = 0; i < jsonarray.length(); i++) {
            olist.add(fill(jsonarray.getJSONObject(i)));
        }
        return olist;
    }

    void setStartDate(Date startDate) {
    }

    void setName(String name) {
    }

}


public class Park {
    private String id;

    private String url;

    private String fullName;

    private String parkCode;

    private String description;

    private String latitude;

    private String longitude;

    private String latLong;

    private List<InfoActivities> activities;

    private List<InfoTopics> topics;

    private String states;

    private Contacts contacts;

    private List<EntranceFees> entranceFees;

    private List<String> entrancePasses;

    private List<String> fees;

    private String directionsInfo;

    private String directionsUrl;

    private List<InfoOperatingHours> infoOperatingHours;

    private List<InfoAddresses> addresses;

    private List<Images> images;

    private String weatherInfo;

    private String name;

    private String designation;

    static Park fill(JSONObject jsonobj) throws JSONException {
        Park entity = new Park();
        if (jsonobj.has("id")) {
            entity.setId(jsonobj.getString("id"));
        }
        if (jsonobj.has("url")) {
            entity.setUrl(jsonobj.getString("url"));
        }
        if (jsonobj.has("fullName")) {
            entity.setFullName(jsonobj.getString("fullName"));
        }
        if (jsonobj.has("parkCode")) {
            entity.setParkCode(jsonobj.getString("parkCode"));
        }
        if (jsonobj.has("description")) {
            entity.setDescription(jsonobj.getString("description"));
        }
        if (jsonobj.has("latitude")) {
            entity.setLatitude(jsonobj.getString("latitude"));
        }
        if (jsonobj.has("longitude")) {
            entity.setLongitude(jsonobj.getString("longitude"));
        }
        if (jsonobj.has("latLong")) {
            entity.setLatLong(jsonobj.getString("latLong"));
        }
        if (jsonobj.has("activities")) {
            entity.setActivities((List<InfoActivities>) jsonobj.getJSONArray("activities"));
        }
        if (jsonobj.has("topics")) {
            entity.setTopics((List<InfoTopics>) jsonobj.getJSONArray("topics"));
        }
        if (jsonobj.has("states")) {
            entity.setStates(jsonobj.getString("states"));
        }
        if (jsonobj.has("contacts")) {
            entity.setContacts((Contacts) jsonobj.get("contacts"));
        }
        if (jsonobj.has("entranceFees")) {
            entity.setEntranceFees((List<EntranceFees>) jsonobj.getJSONArray("entranceFees"));
        }
        if (jsonobj.has("entrancePasses")) {
            entity.setEntrancePasses((List<String>) jsonobj.getJSONArray("entrancePasses"));
        }
        if (jsonobj.has("fees")) {
            entity.setFees((List<String>) jsonobj.getJSONArray("fees"));
        }
        if (jsonobj.has("directionsInfo")) {
            entity.setDirectionsInfo(jsonobj.getString("directionsInfo"));
        }
        if (jsonobj.has("directionsUrl")) {
            entity.setDirectionsUrl(jsonobj.getString("directionsUrl"));
        }
        if (jsonobj.has("operatingHours")) {
            entity.setOperatingHours((List<InfoOperatingHours>) jsonobj.getJSONArray("operatingHours"));
        }
        if (jsonobj.has("addresses")) {
            entity.setAddresses((List<InfoAddresses>) jsonobj.getJSONArray("addresses"));
        }
        if (jsonobj.has("images")) {
            entity.setImages((List<Images>) jsonobj.getJSONArray("images"));
        }
        if (jsonobj.has("weatherInfo")) {
            entity.setWeatherInfo(jsonobj.getString("weatherInfo"));
        }
        if (jsonobj.has("name")) {
            entity.setName(jsonobj.getString("name"));
        }
        if (jsonobj.has("designation")) {
            entity.setDesignation(jsonobj.getString("designation"));
        }
        return entity;
    }

    static List<Park> fillList(JSONArray jsonarray) throws JSONException {
        if (jsonarray == null || jsonarray.length() == 0)
            return null;
        List<Park> olist = new ArrayList<Park>();
        for (int i = 0; i < jsonarray.length(); i++) {
            olist.add(fill(jsonarray.getJSONObject(i)));
        }
        return olist;
    }

    @NonNull
    @Override
    public String toString() {
        return "Park{" +
                "id='" + id + '\'' +
                ", url='" + url + '\'' +
                ", fullName='" + fullName + '\'' +
                ", parkCode='" + parkCode + '\'' +
                ", description='" + description + '\'' +
                ", latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", latLong='" + latLong + '\'' +
                ", activities=" + activities +
                ", topics=" + topics +
                ", states='" + states + '\'' +
                ", contacts=" + contacts +
                ", entranceFees=" + entranceFees +
                ", entrancePasses=" + entrancePasses +
                ", fees=" + fees +
                ", directionsInfo='" + directionsInfo + '\'' +
                ", directionsUrl='" + directionsUrl + '\'' +
                ", operatingHours=" + infoOperatingHours +
                ", addresses=" + addresses +
                ", images=" + images +
                ", weatherInfo='" + weatherInfo + '\'' +
                ", name='" + name + '\'' +
                ", designation='" + designation + '\'' +
                '}';
    }

    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFullName() {
        return this.fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public void setParkCode(String parkCode) {
        this.parkCode = parkCode;
    }

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLatLong(String latLong) {
        this.latLong = latLong;
    }

    public List<InfoActivities> getActivities() {
        return this.activities;
    }

    public void setActivities(List<InfoActivities> activities) {
        this.activities = activities;
    }

    public List<InfoTopics> getTopics() {
        return this.topics;
    }

    public void setTopics(List<InfoTopics> topics) {
        this.topics = topics;
    }

    public String getStates() {
        return this.states;
    }

    public void setStates(String states) {
        this.states = states;
    }

    public void setContacts(Contacts contacts) {
        this.contacts = contacts;
    }

    public List<EntranceFees> getEntranceFees() {
        return this.entranceFees;
    }

    public void setEntranceFees(List<EntranceFees> entranceFees) {
        this.entranceFees = entranceFees;
    }

    public void setEntrancePasses(List<String> entrancePasses) {
        this.entrancePasses = entrancePasses;
    }

    public void setFees(List<String> fees) {
        this.fees = fees;
    }

    public String getDirectionsInfo() {
        return this.directionsInfo;
    }

    public void setDirectionsInfo(String directionsInfo) {
        this.directionsInfo = directionsInfo;
    }

    public void setDirectionsUrl(String directionsUrl) {
        this.directionsUrl = directionsUrl;
    }

    public List<InfoOperatingHours> getOperatingHours() {
        return this.infoOperatingHours;
    }

    public void setOperatingHours(List<InfoOperatingHours> infoOperatingHours) {
        this.infoOperatingHours = infoOperatingHours;
    }

    public void setAddresses(List<InfoAddresses> addresses) {
        this.addresses = addresses;
    }

    public List<Images> getImages() {
        return this.images;
    }

    public void setImages(List<Images> images) {
        this.images = images;
    }

    public void setWeatherInfo(String weatherInfo) {
        this.weatherInfo = weatherInfo;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesignation() {
        return this.designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
}


