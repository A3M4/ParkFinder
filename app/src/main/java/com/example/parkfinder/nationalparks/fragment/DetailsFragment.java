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

import com.example.parkfinder.R;
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
        viewPager = view.findViewById(R.id.details_viewpager);

        ParkStateViewModel parkStateViewModel = new ViewModelProvider(requireActivity())
                .get(ParkStateViewModel.class);

        TextView parkName = view.findViewById(R.id.details_park_name);
        TextView parkDes = view.findViewById(R.id.detailsf_park_designation);
        TextView description = view.getRootView().findViewById(R.id.details_description);
        TextView activities = view.getRootView().findViewById(R.id.details_activities);
        TextView entranceFees = view.getRootView().findViewById(R.id.details_entrancefees);
        TextView opHours = view.getRootView().findViewById(R.id.details_operatinghours);
        TextView detailsTopics = view.getRootView().findViewById(R.id.details_topics);
        TextView directions = view.getRootView().findViewById(R.id.details_directions);

        parkStateViewModel.getSelectedPark().observe(getViewLifecycleOwner(), park -> {
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
                entranceFees.setText(R.string.info_unavailable);
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
                directions.setText(R.string.no_directions);
            }
            pageControlAdapter = new PageControlAdapter(park.getImages());
            viewPager.setAdapter(pageControlAdapter);
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);
        Button reviewBtn = view.findViewById(R.id.btn_reviews);
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