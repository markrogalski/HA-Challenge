package com.example.markrogalski.homeaway;

import android.arch.persistence.room.Room;
import android.content.Context;

import java.util.Map;
import java.util.HashMap;


/*
 * This class abstracts favorite data storage.
 * It provides good performance by utilizing an in memory cache in addition to
 * persistent storage in a database.
 */
class FavoriteRepo {
    // HashMap is explicitly used here so we can tell if there is a cache hit or not
    private final static Map<Integer, Boolean> sMap = new HashMap<>();
    private static FavoriteRepo sSingleton;

    final private AppDatabase mDb;
    private EventLiveData mLiveData;

    private FavoriteRepo(Context context) {
        mDb = Room.databaseBuilder(context, AppDatabase.class, "favorite-data").build();
    }

    /**
     * First call must pass the application context. Subsequent calls can pass null.
     *
     * @param context app context
     * @throws IllegalArgumentException context is required
     */
    static FavoriteRepo getSingleton(Context context) {
        if (sSingleton == null) {
            if (context != null) {
                sSingleton = new FavoriteRepo(context);
            }
            else {
                throw new IllegalArgumentException("Must pass application context if repo is not initialized.");
            }
        }
        return sSingleton;
    }

    void setLiveData(EventLiveData liveData) {
        mLiveData = liveData;
    }

    void setFavoriteStatus(final SeatEvent event) {
        sMap.put(event.mId, event.mIsFavorite);

        // Quick save to DB must be done in the background
        new Thread() {
            @Override
            public void run() {
                super.run();
                if (mDb.favoriteDao().findById(event.mId) == null) {
                    mDb.favoriteDao().insertAll(new Favorite(event.mId, event.mIsFavorite));
                }
                else {
                    mDb.favoriteDao().update(new Favorite(event.mId, event.mIsFavorite));
                }
            }
        }.start();

        if (mLiveData != null) {
            mLiveData.loadData();
        }
    }

    boolean getFavoriteStatus(int id) {
        Boolean result = sMap.get(id);
        if (result != null) {
            return result;
        }
        Favorite data = mDb.favoriteDao().findById(id);
        return data != null && data.getIsFavorite();
    }
}
