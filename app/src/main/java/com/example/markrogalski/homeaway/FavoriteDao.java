package com.example.markrogalski.homeaway;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Update;


@Dao
interface FavoriteDao {
    @Query("SELECT * FROM favorite WHERE id = (:id) LIMIT 1")
    Favorite findById(int id);

    @Insert
    void insertAll(Favorite... data);

    @Update
    void update(Favorite data);

    @Delete
    void delete(Favorite data);
}
