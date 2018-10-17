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

    @PrimaryKey
    private int id;
    private String tresc;
    private int podrozId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTresc() {
        return tresc;
    }

    public void setTresc(String tresc) {
        this.tresc = tresc;
    }

    public int getPodrozId() {
        return podrozId;
    }

    public void setPodrozId(int podrozId) {
        this.podrozId = podrozId;
    }

    public Notatka(int id, String tresc, int podrozId) {
        this.id = id;
        this.tresc = tresc;
        this.podrozId = podrozId;
    }
}
