package com.example.parkfinder.nationalparks.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.example.parkfinder.nationalparks.connector.HelperFunction;
import com.example.parkfinder.nationalparks.connector.InfoPanel;
import com.example.parkfinder.nationalparks.pattern.Park;
import com.example.parkfinder.nationalparks.pattern.ParkStateViewModel;
import com.example.parkfinder.nationalparks.regulator.Repository;
import com.example.parkfinder.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class MapDisplayActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private GoogleMap mMap;
    private ParkStateViewModel parkStateViewModel;
    private List<Park> parkList;
    private CardView cardView;
    private EditText stateCodeEt;
    private String code;
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        parkStateViewModel = new ViewModelProvider(this)
                .get(ParkStateViewModel.class);

        initializeViews();
        initializeMapFragment();

        parkList = new ArrayList<>();
        populateMap();
    }

    private void initializeViews() {
        cardView = findViewById(R.id.cardview);
        stateCodeEt = findViewById(R.id.floating_state_value_et);
        ImageButton searchButton = findViewById(R.id.floating_search_button);

        BottomNavigationView bottomNavigationView =
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.maps_nav_button) {
                if (cardView.getVisibility() == View.INVISIBLE ||
                        cardView.getVisibility() == View.GONE) {
                    cardView.setVisibility(View.VISIBLE);
                }
                navigateToMapFragment();
                return true;
            } else if (id == R.id.parks_nav_button) {
                launchParksFragment();
                return true;
            }
            return false;
        });

        setParksInUserStateInitialView();

        searchButton.setOnClickListener(view -> {
            HelperFunction.deactivateSoftInput(view);
            String stateCode = stateCodeEt.getText().toString().trim();
            if (!TextUtils.isEmpty(stateCode)) {
                code = stateCode;
                parkStateViewModel.selectCode(code);
                populateMap();
                stateCodeEt.setText("");
            }
        });
    }

    private void initializeMapFragment() {
        mapFragment = SupportMapFragment.newInstance();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.map, mapFragment);
        transaction.commit();
        mapFragment.getMapAsync(this);
    }

    private void populateMap() {
        Repository.getParks(parks -> {
            mMap.clear();
            parkList = parks;
            for (Park park : parks) {
                LatLng location = new LatLng(Double.parseDouble(park.getLatitude()),
                        Double.parseDouble(park.getLongitude()));

                MarkerOptions markerOptions =
                        new MarkerOptions()
                                .position(location)
                                .title(park.getName())
                                .icon(BitmapDescriptorFactory.defaultMarker(
                                        BitmapDescriptorFactory.HUE_VIOLET))
                                .snippet(park.getStates());

                Marker marker = mMap.addMarker(markerOptions);
                assert marker != null;
                marker.setTag(park);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5));
                Log.d("Parks", "onMapReady: " + park.getFullName());
            }
            parkStateViewModel.setSelectedParks(parkList);
            Log.d("SIZE", "populateMap: " + parkList.size());
        }, code);
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setInfoWindowAdapter(new InfoPanel(getApplicationContext()));
        mMap.setOnInfoWindowClickListener(this);

        // Enable user location
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            mMap.setMyLocationEnabled(true);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        || ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                }
            } else {
                // Permission denied, show an AlertDialog to inform the user
                new AlertDialog.Builder(this)
                        .setTitle("Location Permission Denied")
                        .setMessage("Location permission is required for showing your current location on the map. To enable the permission, please go to Settings > Apps > YourAppName > Permissions and turn on the location permission.")
                        .setPositiveButton("OK", (dialog, which) -> dialog.dismiss())
                        .create()
                        .show();
            }
        }
    }

    @Override
    public void onInfoWindowClick(@NonNull Marker marker) {
        cardView.setVisibility(View.GONE);
        navigateToDetailsFragment(marker);
    }

    private void navigateToDetailsFragment(Marker marker) {
        parkStateViewModel.setSelectedPark((Park) marker.getTag());
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.map, DetailsFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void navigateToMapFragment() {
        if (cardView.getVisibility() == View.INVISIBLE ||
                cardView.getVisibility() == View.GONE) {
            cardView.setVisibility(View.VISIBLE);
        }
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.map, mapFragment);
        transaction.commit();
    }

    private void launchParksFragment() {
        cardView.setVisibility(View.GONE);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.map, ParkDisplayFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void setParksInUserStateInitialView() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, LOCATION_PERMISSION_REQUEST_CODE);
        } else {
            // Get the user's current location
            LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            Location myLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (myLocation == null) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        // Get the user's state code when location data becomes available
                        String stateCode = getStateCode(location);
                        locationManager.removeUpdates(this);
                        code = getStateCode(stateCode);
                    }
                });
            } else {
                // Get the user's state code from the location data
                String stateCode = getStateCode(myLocation);
                code = getStateCode(stateCode);
            }
        }
    }

    private String getStateCode(Location location) {
        double latitude = location.getLatitude();
        double longitude = location.getLongitude();

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                return address.getAdminArea();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "";
    }

    private static String getStateCode(String stateName) {
        HashMap<String, String> stateCodeMap = new HashMap<>();
        stateCodeMap.put("Alabama", "al");
        stateCodeMap.put("Alaska", "ak");
        stateCodeMap.put("Arizona", "az");
        stateCodeMap.put("Arkansas", "ar");
        stateCodeMap.put("California", "ca");
        stateCodeMap.put("Colorado", "co");
        stateCodeMap.put("Connecticut", "ct");
        stateCodeMap.put("Delaware", "de");
        stateCodeMap.put("Florida", "fl");
        stateCodeMap.put("Georgia", "ga");
        stateCodeMap.put("Hawaii", "hi");
        stateCodeMap.put("Idaho", "id");
        stateCodeMap.put("Illinois", "il");
        stateCodeMap.put("Indiana", "in");
        stateCodeMap.put("Iowa", "ia");
        stateCodeMap.put("Kansas", "ks");
        stateCodeMap.put("Kentucky", "ky");
        stateCodeMap.put("Louisiana", "la");
        stateCodeMap.put("Maine", "me");
        stateCodeMap.put("Maryland", "md");
        stateCodeMap.put("Massachusetts", "ma");
        stateCodeMap.put("Michigan", "mi");
        stateCodeMap.put("Minnesota", "mn");
        stateCodeMap.put("Mississippi", "ms");
        stateCodeMap.put("Missouri", "mo");
        stateCodeMap.put("Montana", "mt");
        stateCodeMap.put("Nebraska", "ne");
        stateCodeMap.put("Nevada", "nv");
        stateCodeMap.put("New Hampshire", "nh");
        stateCodeMap.put("New Jersey", "nj");
        stateCodeMap.put("New Mexico", "nm");
        stateCodeMap.put("New York", "ny");
        stateCodeMap.put("North Carolina", "nc");
        stateCodeMap.put("North Dakota", "nd");
        stateCodeMap.put("Ohio", "oh");
        stateCodeMap.put("Oklahoma", "ok");
        stateCodeMap.put("Oregon", "or");
        stateCodeMap.put("Pennsylvania", "pa");
        stateCodeMap.put("Rhode Island", "ri");
        stateCodeMap.put("South Carolina", "sc");
        stateCodeMap.put("South Dakota", "sd");
        stateCodeMap.put("Tennessee", "tn");
        stateCodeMap.put("Texas", "tx");
        stateCodeMap.put("Utah", "ut");
        stateCodeMap.put("Vermont", "vt");
        stateCodeMap.put("Virginia", "va");
        stateCodeMap.put("Washington", "wa");
        stateCodeMap.put("West Virginia", "wv");
        stateCodeMap.put("Wisconsin", "wi");
        stateCodeMap.put("Wyoming", "wy");

        return stateCodeMap.get(stateName);
    }
}

