package com.example.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.lukasz.apptravel.db.DateTypeConverter;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Podroz implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String nazwa;
    @NonNull
    @TypeConverters({DateTypeConverter.class})
    private Date dataOd;
    @NonNull
    @TypeConverters({DateTypeConverter.class})
    private Date dataDo;
    private Double budzet;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @NonNull
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(@NonNull String nazwa) {
        this.nazwa = nazwa;
    }

    @NonNull
    public Date getDataOd() {
        return dataOd;
    }

    public void setDataOd(@NonNull Date dataOd) {
        this.dataOd = dataOd;
    }

    @NonNull
    public Date getDataDo() {
        return dataDo;
    }

    public void setDataDo(@NonNull Date dataDo) {
        this.dataDo = dataDo;
    }

    public Double getBudzet() {
        return budzet;
    }

    public void setBudzet(double budzet) {
        this.budzet = budzet;
    }

    public Podroz(long id, @NonNull String nazwa, @NonNull Date dataOd, @NonNull Date dataDo, Double budzet) {
        this.id = id;
        this.nazwa = nazwa;
        this.dataOd = dataOd;
        this.dataDo = dataDo;
        this.budzet = budzet;
    }
}
