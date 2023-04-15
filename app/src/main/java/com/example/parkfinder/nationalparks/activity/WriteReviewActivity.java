package com.example.parkfinder.nationalparks.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.parkfinder.R;
import com.example.parkfinder.nationalparks.pattern.Review;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class WriteReviewActivity extends AppCompatActivity {
    private String curParkName;
    private String curParkId;
    private TextView parkToReview;
    private RatingBar userRatingBar;
    private TextView reviewContent;
    private TextView userName;
    private DatabaseReference databaseReference;
    static final String WRITE_REVIEW_TAG = "WRITE_REVIEW";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_review);
        curParkName = getIntent().getStringExtra("curParkName");
        curParkId = getIntent().getStringExtra("curParkId");
        Log.d(WRITE_REVIEW_TAG, curParkId + curParkName);

        // sets the current park name that to be reviewed
        parkToReview = findViewById(R.id.parkToReview);
        parkToReview.setText(curParkName);

        userRatingBar = findViewById(R.id.ratingBar);
        userRatingBar.setStepSize(1f);
        reviewContent = findViewById(R.id.reviewContent);
        userName = findViewById(R.id.txtUserName);

        databaseReference = FirebaseDatabase.getInstance().getReference();
    }

    // when clicks the post button, add review to database
    public void postReview(View view) {
        // handles empty values
        if (userRatingBar.getRating() == 0) {
            Toast.makeText(this, "Please add a rating at the rating bar!", Toast.LENGTH_LONG).show();
            return;
        }
        if (reviewContent.getText() == null || reviewContent.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please write a review content.", Toast.LENGTH_LONG).show();
            return;
        }
        if (userName.getText() == null || userName.getText().toString().trim().equals("")) {
            Toast.makeText(this, "Please enter a name.", Toast.LENGTH_LONG).show();
            return;
        }

        float rating = userRatingBar.getRating();
        String content = reviewContent.getText().toString().trim();
        String curTime = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date());
        String name = userName.getText().toString().trim();
        Review curReview = new Review(name, rating, content, curTime);

        // adds review to database, path: reviews/parkId+parkName/autoUid/Review
        databaseReference.child("reviews")
                .child(curParkId+curParkName)
                .push()
                .setValue(curReview);
        Toast.makeText(this, "Post successfully.", Toast.LENGTH_SHORT).show();
        // back to show the reviews
        finish();
    }

    // when clicks the back button， asks before closing the activity
    @Override
    public void onBackPressed() {
        closeWriteReviewActivity();
    }

    // when clicks the close icon, asks before closing the activity
    public void closeWritingReview(View view) {
        closeWriteReviewActivity();
    }

    // Helper function that asks before closing the activity
    private void closeWriteReviewActivity() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(this);
        alertBuilder.setCancelable(false)
                .setMessage(
                        "Are you sure you want to leave before posting your review? Your changes " +
                                "will be discarded.")
                .setPositiveButton("Yes", (dialogInterface, i) -> {
                    finish();
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).create().show();
    }

}