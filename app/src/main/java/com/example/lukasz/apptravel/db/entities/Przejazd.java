package com.example.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.example.lukasz.apptravel.db.DateTypeConverter;

import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {@ForeignKey(entity = Podroz.class,
        parentColumns = "id",
        childColumns = "podrozId",
        onDelete = CASCADE),
        @ForeignKey(entity = KategoriaPrzejazdu.class,
                parentColumns = "id",
                childColumns = "kategoriaPrzejazduId",
                onDelete = CASCADE)
        })
public class Przejazd {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long podrozId;
    private long kategoriaPrzejazduId;
    @NonNull
    private String nazwa;
    @NonNull
    @TypeConverters({DateTypeConverter.class})
    private Date dataOd;
    @NonNull
    @TypeConverters({DateTypeConverter.class})
    private Date dataDo;
    private Double koszt;

    public Przejazd(long id, long podrozId, long kategoriaPrzejazduId, @NonNull String nazwa, @NonNull Date dataOd, @NonNull Date dataDo, Double koszt) {
        this.id = id;
        this.podrozId = podrozId;
        this.kategoriaPrzejazduId = kategoriaPrzejazduId;
        this.nazwa = nazwa;
        this.dataOd = dataOd;
        this.dataDo = dataDo;
        this.koszt = koszt;
    }

    public long getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getPodrozId() {
        return podrozId;
    }

    public void setPodrozId(int podrozId) {
        this.podrozId = podrozId;
    }

    public long getKategoriaPrzejazduId() {
        return kategoriaPrzejazduId;
    }

    public void setKategoriaPrzejazduId(int kategoriaPrzejazduId) {
        this.kategoriaPrzejazduId = kategoriaPrzejazduId;
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

    public Double getKoszt() {
        return koszt;
    }

    public void setKoszt(Double koszt) {
        this.koszt = koszt;
    }
}
