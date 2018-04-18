package com.example.markrogalski.homeaway;

import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.text.TextUtils;

import java.util.List;

/**
 * This class handles querying the Seat Geek API and providing results to the view model
 */
class EventLiveData extends LiveData<List<SeatEvent>> {
    private String mQuery;

    EventLiveData() {
        loadData();
    }

    /**
     * Called when the query string changes and we want to get new search results
     *
     * @param query search text entered by user
     */
    void doQuery(String query) {
        mQuery = query;
        loadData();
    }

    void loadData() {
        new AsyncTask<String, Void, List<SeatEvent>>() {
            @Override
            protected List<SeatEvent> doInBackground(String... query) {
                List<SeatEvent> events = null;

                // Perform network, jsopn processing, and DB operations in background
                if (!TextUtils.isEmpty(query[0])) {
                    String jsonResults = SeatGeek.query(query[0]);

                    // Process returned JSON
                    events = SeatEventJSONParser.getEventsFromJSON(jsonResults);

                    // Add favorites info
                    for (SeatEvent event : events) {
                        event.mIsFavorite = FavoriteRepo.getSingleton(null).getFavoriteStatus(event.mId);
                    }
                }

                return events;
            }

            @Override
            protected void onPostExecute(List<SeatEvent> data) {
                setValue(data);
            }
        }.execute(mQuery);
    }
}
