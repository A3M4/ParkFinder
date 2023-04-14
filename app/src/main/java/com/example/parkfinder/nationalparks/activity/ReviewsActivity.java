package com.example.parkfinder.nationalparks.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.parkfinder.R;

public class ReviewsActivity extends AppCompatActivity {
    private String curParkName;
    private String curParkId;
    static final String REVIEW_TAG = "REVIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        curParkName = getIntent().getStringExtra("curParkName");
        curParkId = getIntent().getStringExtra("curParkId");
    }

    // opens write review activity to rate and write review for current park
    public void writeReview(View view) {
        Intent writeReviewIntent = new Intent(this, WriteReviewActivity.class);
        Log.d(REVIEW_TAG, curParkId + curParkName);
        writeReviewIntent.putExtra("curParkName", curParkName);
        writeReviewIntent.putExtra("curParkId", curParkId);
        startActivity(writeReviewIntent);
    }
}