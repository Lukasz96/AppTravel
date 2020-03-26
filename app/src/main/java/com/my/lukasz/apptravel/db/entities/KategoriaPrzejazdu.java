package com.my.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class KategoriaPrzejazdu {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String nazwaKategorii;

    public KategoriaPrzejazdu(long id, @NonNull String nazwaKategorii) {
        this.id = id;
        this.nazwaKategorii = nazwaKategorii;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNazwaKategorii() {
        return nazwaKategorii;
    }

    public void setNazwaKategorii(@NonNull String nazwaKategorii) {
        this.nazwaKategorii = nazwaKategorii;
    }
}
