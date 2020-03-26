package com.my.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class KategoriaWakacji {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String nazwaKategoriiWakacji;

    public KategoriaWakacji(long id, @NonNull String nazwaKategoriiWakacji) {
        this.id = id;
        this.nazwaKategoriiWakacji = nazwaKategoriiWakacji;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getNazwaKategoriiWakacji() {
        return nazwaKategoriiWakacji;
    }

    public void setNazwaKategoriiWakacji(@NonNull String nazwaKategoriiWakacji) {
        this.nazwaKategoriiWakacji = nazwaKategoriiWakacji;
    }
}
