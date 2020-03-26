package com.my.lukasz.apptravel.db.entities;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {@ForeignKey(entity = Plec.class,
        parentColumns = "id",
        childColumns = "plecId",
        onDelete = CASCADE)})
public class User {

    @PrimaryKey(autoGenerate = true)
    private long id;
    private long plecId;
    @NonNull
    private Integer wiek;

    public User(long id, long plecId, @NonNull Integer wiek) {
        this.id = id;
        this.plecId = plecId;
        this.wiek = wiek;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPlecId() {
        return plecId;
    }

    public void setPlecId(long plecId) {
        this.plecId = plecId;
    }

    @NonNull
    public Integer getWiek() {
        return wiek;
    }

    public void setWiek(@NonNull Integer wiek) {
        this.wiek = wiek;
    }
}
