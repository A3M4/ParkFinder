package com.example.parkfinder.nationalparks.regulator;

import com.example.parkfinder.nationalparks.pattern.Park;

import java.util.List;

public interface AsyncResponse {
    void processPark(List<Park> parks);
}
