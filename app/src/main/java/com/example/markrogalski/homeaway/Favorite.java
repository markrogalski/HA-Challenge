package com.example.markrogalski.homeaway;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.Entity;

@Entity
public class Favorite {
    @PrimaryKey
    private int id;
    @ColumnInfo(name = "is_favorite")
    private boolean isFavorite;

    public Favorite(int id, boolean isFavorite) {
        this.id = id;
        this.isFavorite = isFavorite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    boolean getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(boolean isFavorite) {
        this.isFavorite = isFavorite;
    }
}
