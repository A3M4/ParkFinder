package com.example.parkfinder.nationalparks.fragment;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.parkfinder.R;
import com.example.parkfinder.nationalparks.pattern.Park;
import com.example.parkfinder.nationalparks.regulator.Repository;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.List;

public class MapDisplayActivity extends AppCompatActivity implements
        OnMapReadyCallback {

    private GoogleMap mMap;
    private List<Park> parkList;
    private String code = "wa";
    private SupportMapFragment mapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        initializeViews();
        initializeMapFragment();

        populateMap();
    }

    private void initializeViews() {
        BottomNavigationView bottomNavigationView =
                findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.maps_nav_button) {
                navigateToMapFragment();
                return true;
            } else if (id == R.id.parks_nav_button) {
                launchParksFragment();
                return true;
            }
            return false;
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
                marker.setTag(park);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location, 5));
                Log.d("Parks", "onMapReady: " + park.getFullName());
            }
        }, code);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
    }

    private void navigateToMapFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.map, mapFragment);
        transaction.commit();
    }

    private void launchParksFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.map, ParkDisplayFragment.newInstance());
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
