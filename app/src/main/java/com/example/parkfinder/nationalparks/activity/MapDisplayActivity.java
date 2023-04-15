package com.example.parkfinder.nationalparks.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
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

import java.util.ArrayList;
import java.util.List;

public class MapDisplayActivity extends AppCompatActivity implements
        OnMapReadyCallback, GoogleMap.OnInfoWindowClickListener {

    private GoogleMap mMap;
    private ParkStateViewModel parkStateViewModel;
    private List<Park> parkList;
    private CardView cardView;
    private EditText stateCodeEt;
    private String code = "wa";
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
}

