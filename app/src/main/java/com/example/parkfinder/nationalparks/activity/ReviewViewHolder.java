package com.example.parkfinder.nationalparks.activity;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkfinder.R;

public class ReviewViewHolder extends RecyclerView.ViewHolder {
    TextView username;
    RatingBar rating;
    TextView reviewDate;
    TextView reviewContent;


    public ReviewViewHolder(@NonNull View itemView) {
        super(itemView);

        username = itemView.findViewById(R.id.username);
        rating = itemView.findViewById(R.id.rating);
        reviewDate = itemView.findViewById(R.id.reviewDate);
        reviewContent = itemView.findViewById(R.id.reviewContent);
    }

}
