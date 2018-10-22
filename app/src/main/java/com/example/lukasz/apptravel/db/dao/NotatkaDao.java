package com.example.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lukasz.apptravel.db.entities.Notatka;

import java.util.List;

@Dao
public interface NotatkaDao {
    @Insert
    long insertNotatka(Notatka notatka);

    @Query("SELECT * FROM Notatka")
    List<Notatka> getNotatki();


}
