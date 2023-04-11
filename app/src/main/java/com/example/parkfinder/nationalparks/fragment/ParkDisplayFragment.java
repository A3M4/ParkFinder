package com.example.parkfinder.nationalparks.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.parkfinder.R;

public class ParkDisplayFragment extends Fragment {
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_parks, container, false);
        return view;
    }
}
