package com.example.parkfinder.nationalparks.activity;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkfinder.R;
import com.example.parkfinder.nationalparks.pattern.Review;

import java.util.List;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewViewHolder> {
    private Context context;
    private List<Review> reviews;

    public ReviewAdapter(Context context, List<Review> reviews) {
        this.context = context;
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ReviewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ReviewViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.reviews_row, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ReviewViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.username.setText(review.getUserName());
        holder.rating.setRating(review.getRating());
        holder.reviewDate.setText(review.getDate());
        holder.reviewContent.setText(review.getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }
}
