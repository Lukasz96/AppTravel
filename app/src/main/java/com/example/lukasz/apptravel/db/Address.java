package com.example.lukasz.apptravel.db;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = @ForeignKey(entity=User.class,
        parentColumns = "user_id",
        childColumns = "userId",
        onDelete = CASCADE))
public class Address implements Serializable {

    @PrimaryKey
    private int id;
    private String street;
    private String city;
    private long userId;


    public Address(int id, String street, String city, long userId) {
        this.id = id;
        this.street = street;
        this.city = city;
        this.userId=userId;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }
}
