package com.example.parkfinder.nationalparks.connector;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class HelperFunction {

    public static String getParksUrl(String stateCode) {
        return "https://developer.nps.gov/api/v1/parks?stateCode=" + stateCode + "&api_key=sptqFvukvLzopBhlCAWusyNUI5wPgv28a3Gip6xp";
    }

    public static void deactivateSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(
                Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
