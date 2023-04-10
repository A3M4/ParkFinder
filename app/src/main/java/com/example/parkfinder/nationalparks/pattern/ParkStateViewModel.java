package com.example.parkfinder.nationalparks.pattern;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class ParkStateViewModel extends ViewModel {
    private final MutableLiveData<Park> selectedPark = new MutableLiveData<>();
    private final MutableLiveData<List<Park>> selectedParks = new MutableLiveData<>();
    private final MutableLiveData<String> code = new MutableLiveData<>();

    public LiveData<Park> getSelectedPark() {
        return selectedPark;
    }

    public void setSelectedPark(Park park) {
        selectedPark.setValue(park);
    }

    public void setSelectedParks(List<Park> parks) {
        selectedParks.setValue(parks);
    }

    public LiveData<List<Park>> getParks() {
        return selectedParks;
    }

    public void selectCode(String c) {
        code.setValue(c);
    }
}
