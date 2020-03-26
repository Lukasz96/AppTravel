package com.my.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class Plec {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String nazwaPlci;

    public Plec(long id, @NonNull String nazwaPlci) {
        this.id = id;
        this.nazwaPlci = nazwaPlci;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getNazwaPlci() {
        return nazwaPlci;
    }

    public void setNazwaPlci(@NonNull String nazwaPlci) {
        this.nazwaPlci = nazwaPlci;
    }
}
