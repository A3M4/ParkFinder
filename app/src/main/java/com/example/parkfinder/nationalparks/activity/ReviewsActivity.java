package com.example.parkfinder.nationalparks.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.parkfinder.R;
import com.example.parkfinder.nationalparks.pattern.Review;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ReviewsActivity extends AppCompatActivity {
    private String curParkName;
    private String curParkId;
    private TextView tvTotalRatingScore;
    private RatingBar rbTotalRatingBar;
    private TextView tvTotalRatingNum;

    private DatabaseReference dbReference;
    private RecyclerView reviewsRecyclerView;
    private List<Review> reviews;
    private ReviewAdapter reviewAdapter;
    static final String REVIEW_TAG = "REVIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviews);
        curParkName = getIntent().getStringExtra("curParkName");
        curParkId = getIntent().getStringExtra("curParkId");

        TextView parkName = findViewById(R.id.curPark);
        parkName.setText(curParkName);

        tvTotalRatingScore = findViewById(R.id.totalRatingScore);
        rbTotalRatingBar = findViewById(R.id.totalRatingBar);
        tvTotalRatingNum = findViewById(R.id.totalRatingNum);
        rbTotalRatingBar.setStepSize(0.1f);

        reviewsRecyclerView = (RecyclerView) findViewById(R.id.reviews_recyclerview);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        reviewsRecyclerView.setHasFixedSize(true);

        dbReference = FirebaseDatabase.getInstance().getReference("reviews")
                .child(curParkId + curParkName);
        reviews = new ArrayList<>();

        reviewAdapter = new ReviewAdapter(this, reviews);
        reviewsRecyclerView.setAdapter(reviewAdapter);

        // gets reviews from database and show in recyclerview
        dbReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                reviews.clear();
                float totalRatingScore = 0;
                int totalRatingNum = 0;
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Review review = dataSnapshot.getValue(Review.class);
                    reviews.add(review);
                    assert review != null;
                    totalRatingScore += review.getRating();
                    totalRatingNum += 1;
                }

                // counts the average rating for current park
                if (totalRatingNum != 0) {
                    float avgRatingScore = totalRatingScore / totalRatingNum;
                    tvTotalRatingScore.setText(String.format("%.1f", avgRatingScore));
                    tvTotalRatingNum.setText("(" + totalRatingNum + ")");
                    rbTotalRatingBar.setRating(avgRatingScore);
                }
                reviewAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.w(REVIEW_TAG, "loadReview:onCancelled", error.toException());
            }
        });
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