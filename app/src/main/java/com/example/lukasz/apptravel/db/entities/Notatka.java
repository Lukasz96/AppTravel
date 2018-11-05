package com.example.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity=Podroz.class,
        parentColumns = "id",
        childColumns = "podrozId",
        onDelete = CASCADE))
public class Notatka implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private String zdjecieUri;
    private String tytul;
    private String tresc;
    private long podrozId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getZdjecieUri() {
        return zdjecieUri;
    }

    public void setZdjecieUri(String zdjecieUri) {
        this.zdjecieUri = zdjecieUri;
    }

    public String getTytul() {
        return tytul;
    }

    public void setTytul(String tytul) {
        this.tytul = tytul;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public long getPodrozId() {
        return podrozId;
    }

    public void setPodrozId(long podrozId) {
        this.podrozId = podrozId;
    }

    public Notatka(long id, String zdjecieUri, String tytul, String tresc, long podrozId) {
        this.id = id;
        this.zdjecieUri=zdjecieUri;
        this.tytul=tytul;
        this.tresc = tresc;
        this.podrozId = podrozId;
    }
}
