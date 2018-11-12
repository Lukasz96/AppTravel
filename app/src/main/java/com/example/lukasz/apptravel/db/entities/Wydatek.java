package com.example.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {@ForeignKey(entity = Podroz.class,
        parentColumns = "id",
        childColumns = "podrozId",
        onDelete = CASCADE),
        @ForeignKey(entity = KategoriaPrzejazdu.class,
                parentColumns = "id",
                childColumns = "kategoriaWydatkuId",
                onDelete = CASCADE)
})
public class Wydatek {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long podrozId;
    private long kategoriaWydatkuId;
    @NonNull
    private String nazwa;
    @NonNull
    private Double koszt;
    private String waluta;

    public Wydatek(long id, long podrozId, long kategoriaWydatkuId, @NonNull String nazwa, @NonNull Double koszt, String waluta) {
        this.id = id;
        this.podrozId = podrozId;
        this.kategoriaWydatkuId = kategoriaWydatkuId;
        this.nazwa = nazwa;
        this.koszt = koszt;
        this.waluta = waluta;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPodrozId() {
        return podrozId;
    }

    public void setPodrozId(long podrozId) {
        this.podrozId = podrozId;
    }

    public long getKategoriaWydatkuId() {
        return kategoriaWydatkuId;
    }

    public void setKategoriaWydatkuId(long kategoriaWydatkuId) {
        this.kategoriaWydatkuId = kategoriaWydatkuId;
    }

    @NonNull
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(@NonNull String nazwa) {
        this.nazwa = nazwa;
    }

    @NonNull
    public Double getKoszt() {
        return koszt;
    }

    public void setKoszt(@NonNull Double koszt) {
        this.koszt = koszt;
    }

    public String getWaluta() {
        return waluta;
    }

    public void setWaluta(String waluta) {
        this.waluta = waluta;
    }
}
