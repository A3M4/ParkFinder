package com.example.parkfinder.nationalparks.connector;

public class HelperFunction {

    public static String getParksUrl(String stateCode) {
        return "https://developer.nps.gov/api/v1/parks?stateCode=" + stateCode + "&api_key=sptqFvukvLzopBhlCAWusyNUI5wPgv28a3Gip6xp";
    }
}
