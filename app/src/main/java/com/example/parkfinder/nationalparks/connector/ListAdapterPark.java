package com.example.parkfinder.nationalparks.connector;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkfinder.R.layout;
import com.example.parkfinder.nationalparks.pattern.Park;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ListAdapterPark extends RecyclerView.Adapter<ListAdapterPark.ParkViewHolder> {
    private final List<Park> parkList;
    private final ParkClickResponder parkClickListener;

    // Constructor
    public ListAdapterPark(List<Park> parkList, ParkClickResponder parkClickListener) {
        this.parkList = parkList;
        this.parkClickListener = parkClickListener;
    }

    // Called when RecyclerView needs a new ViewHolder of the given type to represent an item.
    @NonNull
    @Override
    public ParkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflate the layout for the park row item view.
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layout, parent, false);
        return new ParkViewHolder(view);
    }

    // Called by RecyclerView to display the data at the specified position.
    @Override
    public void onBindViewHolder(@NonNull ParkViewHolder holder, int position) {
        // Get the park at the current position.
        Park park = parkList.get(position);

        // Set the park name, type, and state in the corresponding views.
        holder.parkName.setText(park.getName());
        holder.parkType.setText(park.getDesignation());
        holder.parkState.setText(park.getStates());

        // If the park has images, load the first one into the ImageView using Picasso.
        if (park.getImages().size() > 0) {
            Picasso.get()
                    .load(park.getImages().get(0).getUrl())
                    .placeholder(android.R.drawable.stat_sys_download)
                    .error(android.R.drawable.stat_notify_error)
                    .resize(100, 100)
                    .centerCrop()
                    .into(holder.parkImage);
        }
    }

    // Returns the total number of parks in the dataset held by the adapter.
    @Override
    public int getItemCount() {
        return parkList.size();
    }

    // ViewHolder class that describes an item view and metadata about its place within the RecyclerView.
    public class ParkViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView parkImage;
        public TextView parkName;
        public TextView parkType;
        public TextView parkState;
        ParkClickResponder parkClickResponder;

        // Constructor
        public ParkViewHolder(@NonNull View itemView) {
            super(itemView);
            // Get references to the views within the park row item view.

            this.parkClickResponder = parkClickListener;
            itemView.setOnClickListener(this);
        }

        // Called when the park row item view is clicked.
        @Override
        public void onClick(View view) {
            // Get the current park and call the onParkClicked method of the OnParkClickListener interface.
            Park currPark = parkList.get(getAdapterPosition());
            parkClickResponder.onParkRowClick(currPark);
        }
    }
}
