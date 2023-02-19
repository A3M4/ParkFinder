package com.example.parkfinder;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.io.BufferedReader;
import java.io.IOException;

import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class AtYourService extends AppCompatActivity {
    public EditText zipCode;
    private static final String URL_PREFIX = "https://app.zipcodebase.com/api/v1/search?apikey=%&country=us&codes=";
    private static final String API_KEY = "dee01750-af1d-11ed-887f-351785f052d5";
    private static final String TAG = "AtYourService";
    public Button searchButton;
    public ProgressBar loadingCircle;
    public TextView loadingText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        zipCode = findViewById(R.id.input);
        searchButton = findViewById(R.id.search_button);
        loadingCircle = findViewById(R.id.loadingCircle);
        loadingText = findViewById(R.id.loadingText);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                search();
                loadingCircle.setVisibility(View.VISIBLE);
                loadingText.setVisibility(View.VISIBLE);
            }
        });
    }

    public void search() {
        GetRequest getRequest = new GetRequest();
        String zip = zipCode.getText().toString();
        String url = URL_PREFIX.replace("%", API_KEY) + zip;
        try {
            getRequest.execute(url);
        } catch (Exception e) {
            Log.e(TAG, "Exception");
            e.printStackTrace();
        }
    }

    private class GetRequest {
        TextView city = findViewById(R.id.city);
        TextView state = findViewById(R.id.state);
        TextView country = findViewById(R.id.country);
        TextView latitude = findViewById(R.id.latitude);
        TextView longitude = findViewById(R.id.longitude);
        Handler handler = new Handler();

        public void execute(String... params) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    JSONObject jObject = new JSONObject();
                    try {
                        URL url = new URL(params[0]);
                        String resp = httpResponse(url);

                        jObject = new JSONObject(resp);
                    } catch (MalformedURLException e) {
                        Log.e(TAG, "MalformedURLException");
                        e.printStackTrace();
                    } catch (IOException e) {
                        Log.e(TAG, "IOException");
                        e.printStackTrace();
                    } catch (JSONException e) {
                        Log.e(TAG, "JSONException");
                        e.printStackTrace();
                    }

                    final JSONObject result = jObject;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            String query_zip = null;

                            try {
                                query_zip = result.getJSONObject("query").getJSONArray("codes").get(0).toString();
                                Log.d("test zip", query_zip);
                                city.setText("City: " + result.getJSONObject("results").getJSONArray(query_zip).getJSONObject(0).getString("city"));
                                state.setText("State: " + result.getJSONObject("results").getJSONArray(query_zip).getJSONObject(0).getString("state"));
                                country.setText("Country: USA");
                                latitude.setText("Latitude: " + result.getJSONObject("results").getJSONArray(query_zip).getJSONObject(0).getString("latitude"));
                                longitude.setText("Longitude: " + result.getJSONObject("results").getJSONArray(query_zip).getJSONObject(0).getString("longitude"));
                            } catch (JSONException e) {
                                Log.d("onPost Error", "invalid zip code");
                                city.setText(":");
                                state.setText(":");
                                country.setText(":");
                                latitude.setText(":");
                                longitude.setText(":");

                                final AlertDialog.Builder builder = new AlertDialog.Builder(AtYourService.this);
                                builder.setMessage("Invalid ZIP Code \nPlease enter a valid 5-digit US ZIP code.");
                                builder.setNegativeButton("Please try it again", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                            loadingCircle.setVisibility(View.INVISIBLE);
                            loadingText.setVisibility(View.INVISIBLE);
                        }
                    });
                }
            }).start();
        }
    }

    public static String httpResponse(URL url) throws IOException {
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setDoInput(true);
        connection.connect();

        InputStream stream = connection.getInputStream();
        StringBuilder builder = new StringBuilder();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line).append(",\n");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return builder.toString();

    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) {
        super.onSaveInstanceState(savedInstanceState);
        TextView city = findViewById(R.id.city);
        TextView state = findViewById(R.id.state);
        TextView country = findViewById(R.id.country);
        TextView latitude = findViewById(R.id.latitude);
        TextView longitude = findViewById(R.id.longitude);
        savedInstanceState.putString("city", city.getText().toString());
        savedInstanceState.putString("state", state.getText().toString());
        savedInstanceState.putString("country", country.getText().toString());
        savedInstanceState.putString("latitude", latitude.getText().toString());
        savedInstanceState.putString("longitude", longitude.getText().toString());
    }

    @Override
    public void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        TextView city = findViewById(R.id.city);
        TextView state = findViewById(R.id.state);
        TextView country = findViewById(R.id.country);
        TextView latitude = findViewById(R.id.latitude);
        TextView longitude = findViewById(R.id.longitude);
        city.setText(savedInstanceState.getString("city"));
        state.setText(savedInstanceState.getString("state"));
        country.setText(savedInstanceState.getString("country"));
        latitude.setText(savedInstanceState.getString("latitude"));
        longitude.setText(savedInstanceState.getString("longitude"));
    }
}