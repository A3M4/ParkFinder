package com.example.parkfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //dummy comment for testing github only, please ignore
    }

    public void AtYourServiceButton(View view) {
        Intent intent = new Intent(this, AtYourService.class);
        startActivity(intent);
    }
}