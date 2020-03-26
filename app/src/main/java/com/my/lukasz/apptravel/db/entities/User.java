package com.my.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String plec;
    @NonNull
    private Integer wiek;

    public User(long id, @NonNull String plec, @NonNull Integer wiek) {
        this.id = id;
        this.plec = plec;
        this.wiek = wiek;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getPlec() {
        return plec;
    }

    public void setPlec(@NonNull String plec) {
        this.plec = plec;
    }

    @NonNull
    public Integer getWiek() {
        return wiek;
    }

    public void setWiek(@NonNull Integer wiek) {
        this.wiek = wiek;
    }
}
