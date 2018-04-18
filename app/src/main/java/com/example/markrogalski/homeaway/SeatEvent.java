package com.example.markrogalski.homeaway;


import android.os.Bundle;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.Locale;


class SeatEvent {
    static final String JSON_ID = "id";
    static final String JSON_SHORT_TITLE = "short_title";
    static final String JSON_TITLE = "title";
    static final String JSON_DATE = "datetime_local";

    private static final String JSON_STATS = "stats";
    static final String JSON_LOW_PRICE = "lowest_price";
    static final String JSON_HIGH_PRICE = "highest_price";
    static final String JSON_AVERAGE_PRICE = "average_price";

    private static final String JSON_PERFORMERS = "performers";
    static final String JSON_PERFORMER_IMAGE = "image";

    private static final String JSON_VENUE = "venue";
    static final String JSON_VENUE_LOCATION = "display_location";

    static final String KEY_IS_FAVORITE = "EventIsFavorite";

    int mId;
    String mShortTitle;
    String mTitle;
    String mLocation;
    String mDate;
    double mLowPrice;
    double mHighPrice;
    double mAveragePrice;
    String mImageUrl;
    boolean mIsFavorite = false;

    SeatEvent(JSONObject json) {

        if (json != null && json.length() > 0) {
            try {
                mId = json.getInt(JSON_ID);
                mShortTitle = json.getString(JSON_SHORT_TITLE);
                mTitle = json.getString(JSON_TITLE);

                // Covert date from ISO format to locale specific date
                SimpleDateFormat inDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.US);
                Date date = inDateFormat.parse(json.getString(JSON_DATE));
                SimpleDateFormat outDateFormat = new SimpleDateFormat("EEE, MMM d yyyy hh:mm aaa", Locale.US);
                mDate = outDateFormat.format(date);

                try {
                    JSONObject stats = json.getJSONObject(JSON_STATS);
                    if (stats.length() > 0) {
                        mLowPrice = stats.getDouble(JSON_LOW_PRICE);
                        mHighPrice = stats.getDouble(JSON_HIGH_PRICE);
                        mAveragePrice = stats.getDouble(JSON_AVERAGE_PRICE);
                    }
                }
                catch (JSONException ex) {
                    // No worries if data does not have this field
                }

                JSONArray performers = json.getJSONArray(JSON_PERFORMERS);
                if (performers.length() > 0) {
                    JSONObject performer = performers.getJSONObject(0);
                    mImageUrl = performer.getString(JSON_PERFORMER_IMAGE);
                    if (mImageUrl.equals("null")) {
                        mImageUrl = null;
                    }
                }

                JSONObject venue = json.getJSONObject(JSON_VENUE);
                mLocation = venue.getString(JSON_VENUE_LOCATION);

            }
            catch (JSONException ex) {
                Log.d(MainActivity.TAG, "Problem parsing event json " + ex);
            }
            catch (ParseException ex) {
                Log.d(MainActivity.TAG, "Problem parsing event date " + ex);
            }
        }
    }

    SeatEvent(Bundle bundle) {

        mId = bundle.getInt(JSON_ID);
        mTitle = bundle.getString(JSON_TITLE);
        mShortTitle = bundle.getString(JSON_SHORT_TITLE);
        mDate = bundle.getString(JSON_DATE);
        mLocation = bundle.getString(JSON_VENUE_LOCATION);
        mImageUrl = bundle.getString(JSON_PERFORMER_IMAGE);
        mLowPrice = bundle.getDouble(JSON_LOW_PRICE);
        mAveragePrice = bundle.getDouble(JSON_AVERAGE_PRICE);
        mHighPrice = bundle.getDouble(JSON_HIGH_PRICE);
        mIsFavorite = bundle.getBoolean(KEY_IS_FAVORITE);
    }

    Bundle toBundle() {

        Bundle bundle = new Bundle();
        bundle.putInt(JSON_ID, mId);
        bundle.putString(JSON_TITLE, mTitle);
        bundle.putString(JSON_SHORT_TITLE, mShortTitle);
        bundle.putString(JSON_DATE, mDate);
        bundle.putString(JSON_VENUE_LOCATION, mLocation);
        bundle.putString(JSON_PERFORMER_IMAGE, mImageUrl);
        bundle.putDouble(JSON_LOW_PRICE, mLowPrice);
        bundle.putDouble(JSON_AVERAGE_PRICE, mAveragePrice);
        bundle.putDouble(JSON_HIGH_PRICE, mHighPrice);
        bundle.putBoolean(KEY_IS_FAVORITE, mIsFavorite);

        return bundle;
    }
}
