package com.my.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class KategoriaTransport {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String nazwaKategoriiTransportu;

    public KategoriaTransport(long id, @NonNull String nazwaKategoriiTransportu) {
        this.id = id;
        this.nazwaKategoriiTransportu = nazwaKategoriiTransportu;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getNazwaKategoriiTransportu() {
        return nazwaKategoriiTransportu;
    }

    public void setNazwaKategoriiTransportu(@NonNull String nazwaKategoriiTransportu) {
        this.nazwaKategoriiTransportu = nazwaKategoriiTransportu;
    }
}
