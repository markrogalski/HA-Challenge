package com.example.markrogalski.homeaway;

import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.app.Application;

import java.util.List;


class EventViewModel extends AndroidViewModel {
    private final EventLiveData mData;

    public EventViewModel(Application application) {
        super(application);
        mData = new EventLiveData();
    }

    public LiveData<List<SeatEvent>> getData() {
        return mData;
    }
}