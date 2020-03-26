package com.my.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class KategoriaPogody {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String nazwaKategoriiPogody;

    public KategoriaPogody(long id, @NonNull String nazwaKategoriiPogody) {
        this.id = id;
        this.nazwaKategoriiPogody = nazwaKategoriiPogody;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getNazwaKategoriiPogody() {
        return nazwaKategoriiPogody;
    }

    public void setNazwaKategoriiPogody(@NonNull String nazwaKategoriiPogody) {
        this.nazwaKategoriiPogody = nazwaKategoriiPogody;
    }
}
