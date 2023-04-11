package com.example.parkfinder.nationalparks.regulator;


import android.app.Application;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;


public class AppController extends Application {
    private static AppController instance;
    private RequestQueue requestQueue;

    // Returns the singleton instance of the AppController class.
    public static synchronized AppController getInstance() {
        return instance;
    }

    // Returns the RequestQueue object, creating it if it does not already exist.
    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(getApplicationContext());
        }
        return requestQueue;
    }

    // Adds a request to the RequestQueue.
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    // Called when the application is starting.
    @Override
    public void onCreate() {
        super.onCreate();
        // Set the instance variable to this instance of the AppController class.
        instance = this;
    }
}

