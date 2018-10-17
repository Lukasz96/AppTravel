package com.example.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lukasz.apptravel.db.entities.Przejazd;

import java.util.List;

@Dao
public interface PrzejazdDao {

    @Insert
    void insertPrzejazd(Przejazd przejazd);

    @Query("SELECT * FROM przejazd")
    List<Przejazd> getAllPrzejazdy();

}
