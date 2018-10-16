package com.example.lukasz.apptravel.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

@Dao
public interface AddressDao {
    @Insert
    void insertAddress(Address address);

    @Query("SELECT * FROM address")
    List<Address> getAddresses();

    @Query("SELECT * FROM address WHERE userId=1")
    Address getAddress();

    @Query("SELECT city FROM address INNER JOIN user ON address.userId=user.user_id WHERE user.user_id=1")
    String getCity();
}
