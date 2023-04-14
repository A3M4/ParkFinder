package com.example.parkfinder.nationalparks.connector;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import com.example.parkfinder.R;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.Marker;
public class InfoPanel implements GoogleMap.InfoWindowAdapter {
    private final View view;

    // Constructor
    @SuppressLint("InflateParams")
    public InfoPanel(Context context) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        view = layoutInflater.inflate(R.layout.custom_info_window, null);
    }

    // Gets the view for the information window. If null is returned, the default window view will be used.
    @Override
    public View getInfoWindow(Marker marker) {
        return null;
    }

    // Gets the contents view of the information window.
    @Override
    public View getInfoContents(Marker marker) {
        // Get the views from the layout file.
        TextView parkName = view.findViewById(R.id.info_title);
        TextView parkState = view.findViewById(R.id.info_state);

        // Set the title and snippet of the marker as the text of the corresponding views.
        parkName.setText(marker.getTitle());
        parkState.setText(marker.getSnippet());
        return view;
    }
}
