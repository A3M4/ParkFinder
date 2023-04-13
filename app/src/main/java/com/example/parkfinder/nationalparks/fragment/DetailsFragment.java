package com.example.parkfinder.nationalparks.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.example.parkfinder.nationalparks.connector.PageControlAdapter;
import com.example.parkfinder.nationalparks.pattern.ParkStateViewModel;


public class DetailsFragment extends Fragment {
    private PageControlAdapter pageControlAdapter;
    private ViewPager2 viewPager;
    private String curParkId;
    private String curParkName;


    public DetailsFragment() {
    }

    public static DetailsFragment newInstance() {
        return new DetailsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = view.findViewById();

        ParkStateViewModel parkStateViewModel = new ViewModelProvider(requireActivity())
                .get(ParkStateViewModel.class);

        TextView parkName = view.findViewById();
        TextView parkDes = view.findViewById();
        TextView description = view.getRootView().findViewById();
        TextView activities = view.getRootView().findViewById();
        TextView entranceFees = view.getRootView().findViewById();
        TextView opHours = view.getRootView().findViewById();
        TextView detailsTopics = view.getRootView().findViewById();
        TextView directions = view.getRootView().findViewById();

        parkStateViewModel.getSelectedPark().observe(this, park -> {
            curParkId = park.getId();
            curParkName = park.getName();
            parkName.setText(curParkName);
            parkDes.setText(park.getDesignation());
            description.setText(park.getDescription());
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < park.getActivities().size(); i++) {
                stringBuilder.append(park.getActivities().get(i).getName())
                        .append(" / ");

            }
            activities.setText(stringBuilder);
            if (park.getEntranceFees().size() > 0) {
                entranceFees.setText(String.format("Costs: $%s", park.getEntranceFees().get(0).getCost()));
            } else {
                entranceFees.setText();
            }
            StringBuilder opsString = new StringBuilder();
            opsString.append("MON: ").append(park.getOperatingHours().get(0).getStandardHours().getMonday()).append("\n")
                    .append("TUE: ").append(park.getOperatingHours().get(0).getStandardHours().getTuesday()).append("\n")
                    .append("WED: ").append(park.getOperatingHours().get(0).getStandardHours().getWednesday()).append("\n")
                    .append("THU: ").append(park.getOperatingHours().get(0).getStandardHours().getThursday()).append("\n")
                    .append("FRI: ").append(park.getOperatingHours().get(0).getStandardHours().getFriday()).append("\n")
                    .append("SAT: ").append(park.getOperatingHours().get(0).getStandardHours().getSaturday()).append("\n")
                    .append("SUN: ").append(park.getOperatingHours().get(0).getStandardHours().getSunday());
            opHours.setText(opsString);
            StringBuilder topicBuilder = new StringBuilder();
            for (int i = 0; i < park.getTopics().size(); i++) {
                topicBuilder.append(park.getTopics().get(i).getName()).append(" / ");
            }
            detailsTopics.setText(topicBuilder);
            if (!TextUtils.isEmpty(park.getDirectionsInfo())) {
                directions.setText(park.getDirectionsInfo());
            } else {
                directions.setText();
            }
            pageControlAdapter = new PageControlAdapter(park.getImages());
            viewPager.setAdapter(pageControlAdapter);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate();
        Button reviewBtn = view.findViewById();
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent reviewsIntent = new Intent();
                reviewsIntent.putExtra("curParkId", curParkId);
                reviewsIntent.putExtra("curParkName", curParkName);
                reviewsIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(reviewsIntent);
//                new Thread(new Runnable() {
//                    @Override
//                    public void run() {
//                        Intent reviewsIntent = new Intent(getActivity(), ReviewsActivity.class);
//                        reviewsIntent.putExtra("curParkName", curParkName);
//                        reviewsIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                        startActivity(reviewsIntent);
//
//                    }
//                }).start();
            }
        });
        return view;
    }

}