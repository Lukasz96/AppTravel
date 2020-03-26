package com.my.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.arch.persistence.room.TypeConverters;
import android.support.annotation.NonNull;

import com.my.lukasz.apptravel.db.DateTypeConverter;

import java.io.Serializable;
import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {@ForeignKey(entity = User.class,
        parentColumns = "id",
        childColumns = "userId",
        onDelete = CASCADE),
        @ForeignKey(entity = KategoriaTransport.class,
                parentColumns = "id",
                childColumns = "kategoriaTransportuId",
                onDelete = CASCADE),
        @ForeignKey(entity = KategoriaWakacji.class,
                parentColumns = "id",
                childColumns = "kategoriaWakacjiId",
                onDelete = CASCADE),
        @ForeignKey(entity = KategoriaPogody.class,
                parentColumns = "id",
                childColumns = "kategoriaPogodyId",
                onDelete = CASCADE)
})
public class Podroz implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long userId;
    private long kategoriaTransportuId;
    private long kategoriaWakacjiId;
    private long kategoriaPogodyId;
    @NonNull
    private String nazwa;
    @NonNull
    @TypeConverters({DateTypeConverter.class})
    private Date dataOd;
    @NonNull
    @TypeConverters({DateTypeConverter.class})
    private Date dataDo;
    private Double budzet;
    private String waluta;

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

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getKategoriaTransportuId() {
        return kategoriaTransportuId;
    }

    public void setKategoriaTransportuId(long kategoriaTransportuId) {
        this.kategoriaTransportuId = kategoriaTransportuId;
    }

    public long getKategoriaWakacjiId() {
        return kategoriaWakacjiId;
    }

    public void setKategoriaWakacjiId(long kategoriaWakacjiId) {
        this.kategoriaWakacjiId = kategoriaWakacjiId;
    }

    public long getKategoriaPogodyId() {
        return kategoriaPogodyId;
    }

    public void setKategoriaPogodyId(long kategoriaPogodyId) {
        this.kategoriaPogodyId = kategoriaPogodyId;
    }

    public void setBudzet(Double budzet) {
        this.budzet = budzet;
    }

    public Podroz(long id, long userId, long kategoriaTransportuId, long kategoriaWakacjiId, long kategoriaPogodyId, @NonNull String nazwa, @NonNull Date dataOd, @NonNull Date dataDo, Double budzet, String waluta) {
        this.id = id;
        this.userId = userId;
        this.kategoriaTransportuId = kategoriaTransportuId;
        this.kategoriaWakacjiId = kategoriaWakacjiId;
        this.kategoriaPogodyId = kategoriaPogodyId;
        this.nazwa = nazwa;
        this.dataOd = dataOd;
        this.dataDo = dataDo;
        this.budzet = budzet;
        this.waluta = waluta;
    }
}
