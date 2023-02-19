package com.example.parkfinder;

import androidx.appcompat.app.AppCompatActivity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import java.io.IOException;

import android.os.Handler;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;

import androidx.appcompat.app.ActionBar;
public class AtYourService extends AppCompatActivity {
    public EditText zipCode;
    private static final String URL_PREFIX = "https://app.zipcodebase.com/api/v1/search?apikey=%&country=us&codes=";
    private static final String API_KEY = "dee01750-af1d-11ed-887f-351785f052d5";
    private static final String TAG = "AtYourService";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_your_service);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        zipCode = findViewById(R.id.input);
    }

    public void search(View view) {
        String zip = zipCode.getText().toString();
        String url = URL_PREFIX.replace("%", API_KEY) + zip;
    }

    private class GetRequest {
        TextView city = (TextView) findViewById(R.id.city);
        TextView state = (TextView) findViewById(R.id.state);
        TextView country = (TextView) findViewById(R.id.country);
        TextView latitude = (TextView) findViewById(R.id.latitude);
        TextView longitude = (TextView) findViewById(R.id.longitude);
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
                                country.setText("USA");
                                latitude.setText(result.getJSONObject("results").getJSONArray(query_zip).getJSONObject(0).getString("latitude"));
                                longitude.setText(result.getJSONObject("results").getJSONArray(query_zip).getJSONObject(0).getString("longitude"));
                                city.setText(result.getJSONObject("results").getJSONArray(query_zip).getJSONObject(0).getString("city"));
                                state.setText(result.getJSONObject("results").getJSONArray(query_zip).getJSONObject(0).getString("state"));
                            } catch (JSONException e) {
                                Log.d("onPost Error", "invalid zip code");
                                city.setText(":");
                                state.setText(":");
                                country.setText(":");
                                latitude.setText(":");
                                longitude.setText(":");

                                final AlertDialog.Builder builder = new AlertDialog.Builder(AtYourService.this);
                                builder.setMessage("Invalid ZIP Code \nPlease enter a valid 5-digit US ZIP code.");
                                builder.setNegativeButton("We receive your input now", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.cancel();
                                    }
                                });
                                AlertDialog alertDialog = builder.create();
                                alertDialog.show();
                            }
                        }
                    });
                }
            }).start();
        }
    }

    private static String httpResponse(URL url) throws IOException {
        return null;
    }
}