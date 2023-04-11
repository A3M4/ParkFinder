package com.example.parkfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.parkfinder.nationalparks.fragment.MapDisplayActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void AtYourServiceButton(View view) {
        Intent intent = new Intent(this, AtYourService.class);
        startActivity(intent);
    }

    public void getAboutInfo(View view) {
        Intent aboutIntent = new Intent(this, About.class);
        startActivity(aboutIntent);
    }

    public void openStickItToEm(View view) {
        Intent stickerIntent = new Intent(this, StickItToEm.class);
        startActivity(stickerIntent);
    }

    public void openNationalParks(View view) {
        Intent parkIntent = new Intent(this, MapDisplayActivity.class);
        startActivity(parkIntent);
    }
}