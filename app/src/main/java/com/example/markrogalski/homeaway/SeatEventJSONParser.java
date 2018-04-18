package com.example.markrogalski.homeaway;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


class SeatEventJSONParser {
    private static final String JSON_EVENTS = "events";


    static List<SeatEvent> getEventsFromJSON(String jsonString) {

        List<SeatEvent> list = new ArrayList<>();
        if (jsonString != null) {
            try {
                JSONObject mainObject = new JSONObject(jsonString);
                JSONArray items = mainObject.getJSONArray(JSON_EVENTS);

                // Add each event to list
                for (int i = 0; i < items.length(); i++) {
                    JSONObject eventJson = items.getJSONObject(i);
                    list.add(new SeatEvent(eventJson));
                }
            } catch (JSONException ex) {
                Log.d(MainActivity.TAG, "Error parsing events JSON " + ex);
            }
        }
        return list;
    }
}
