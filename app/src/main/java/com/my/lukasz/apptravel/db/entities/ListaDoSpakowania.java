package com.my.lukasz.apptravel.db.entities;

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
public class ListaDoSpakowania {

    @PrimaryKey(autoGenerate = true)
    private long id;
    @NonNull
    private String nazwa;
    private long podrozId;





    public ListaDoSpakowania(long id, @NonNull String nazwa, long podrozId) {
        this.id = id;
        this.nazwa = nazwa;
        this.podrozId = podrozId;
    }

    public long getId() {
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



    public long getPodrozId() {
        return podrozId;
    }

    public void setPodrozId(int podrozId) {
        this.podrozId = podrozId;
    }
}
