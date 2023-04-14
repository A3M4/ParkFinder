package com.example.parkfinder.nationalparks.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;

import com.example.parkfinder.R;

public class WriteReviewActivity extends AppCompatActivity {
    private String curParkName;
    private String curParkId;
    static final String WRITE_REVIEW_TAG = "WRITE_REVIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        curParkName = getIntent().getStringExtra("curParkName");
        curParkId = getIntent().getStringExtra("curParkId");
        Log.d(WRITE_REVIEW_TAG, curParkId + curParkName);
    }
}