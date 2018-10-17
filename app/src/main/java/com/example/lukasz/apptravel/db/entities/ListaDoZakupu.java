package com.example.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity=Podroz.class,
        parentColumns = "id",
        childColumns = "podrozId",
        onDelete = CASCADE),
        indices = {@Index(value={"podrozId"},unique = true)})
public class ListaDoZakupu {

    @PrimaryKey(autoGenerate = true)
    private int id;
    @NonNull
    private String nazwa;
    @NonNull
    private boolean czyCalaSpelniona;
    private int podrozId;

    @NonNull
    public boolean isCzyCalaSpelniona() {
        return czyCalaSpelniona;
    }

    public void setCzyCalaSpelniona(@NonNull boolean czyCalaSpelniona) {
        this.czyCalaSpelniona = czyCalaSpelniona;
    }



    public ListaDoZakupu(int id, @NonNull String nazwa, @NonNull boolean czyCalaSpelniona, int podrozId) {
        this.id = id;
        this.nazwa = nazwa;
        this.czyCalaSpelniona = czyCalaSpelniona;
        this.podrozId = podrozId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(@NonNull String nazwa) {
        this.nazwa = nazwa;
    }



    public int getPodrozId() {
        return podrozId;
    }

    public void setPodrozId(int podrozId) {
        this.podrozId = podrozId;
    }
}
