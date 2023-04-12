package com.example.parkfinder.nationalparks.connector;

import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkfinder.nationalparks.pattern.Images;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PageControlAdapter extends RecyclerView.Adapter<PageControlAdapter.ImageScroller> {
    private final List<Images> imagesList;

    // Constructor
    public PageControlAdapter(List<Images> imagesList) {
        this.imagesList = imagesList;
    }

    // Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
    @NonNull
    @Override
    public ImageScroller onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the view pager row item view.

    }

    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(@NonNull ImageScroller holder, int position) {
        // Load the image at the current position into the ImageView using Picasso.
        Picasso.get()
                .load(imagesList.get(position).getUrl())
                .fit()
                .error(android.R.drawable.stat_notify_error)
                .placeholder(android.R.drawable.stat_sys_download)
                .into(holder.imageView);
    }

    // Returns the total number of images in the dataset held by the adapter.
    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    // ViewHolder class that describes an item view and metadata about its place within the RecyclerView.
    public static class ImageScroller extends RecyclerView.ViewHolder {
        public ImageView imageView;

        // Constructor
        public ImageScroller(@NonNull View itemView) {
            super(itemView);

        }
    }
}
