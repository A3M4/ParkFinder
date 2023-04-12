package com.example.parkfinder.nationalparks.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parkfinder.R;
import com.example.parkfinder.nationalparks.connector.ListAdapterPark;
import com.example.parkfinder.nationalparks.connector.ParkClickResponder;
import com.example.parkfinder.nationalparks.pattern.Park;
import com.example.parkfinder.nationalparks.pattern.ParkStateViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ParkDisplayFragment extends Fragment implements ParkClickResponder {
    private RecyclerView recyclerView;
    private List<Park> parkList;


    public ParkDisplayFragment() {
    }

    public static ParkDisplayFragment newInstance() {
        ParkDisplayFragment fragment = new ParkDisplayFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        parkList = new ArrayList<>();
        ParkStateViewModel parkStateViewModel = new ViewModelProvider(requireActivity()).get(ParkStateViewModel.class);
        parkStateViewModel.getParks().observe(this, parks -> {
            parkList.clear();
            parkList.addAll(parks);
            Objects.requireNonNull(recyclerView.getAdapter()).notifyDataSetChanged();
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_parks, container, false);
        recyclerView = view.findViewById(R);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        ListAdapterPark listAdapterPark = new ListAdapterPark(parkList, this);
        recyclerView.setAdapter(listAdapterPark);
        return view;
    }

    @Override
    public void onParkRowClick(Park park) {

    }
}
