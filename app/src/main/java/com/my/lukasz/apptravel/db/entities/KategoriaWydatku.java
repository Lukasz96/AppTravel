package com.my.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class KategoriaWydatku {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String nazwaKategoriiWydatku;

    public KategoriaWydatku(long id, @NonNull String nazwaKategoriiWydatku) {
        this.id = id;
        this.nazwaKategoriiWydatku = nazwaKategoriiWydatku;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getNazwaKategoriiWydatku() {
        return nazwaKategoriiWydatku;
    }

    public void setNazwaKategoriiWydatku(@NonNull String nazwaKategoriiWydatku) {
        this.nazwaKategoriiWydatku = nazwaKategoriiWydatku;
    }
}
