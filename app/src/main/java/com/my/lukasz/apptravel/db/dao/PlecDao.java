package com.my.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.my.lukasz.apptravel.db.entities.Plec;

import java.util.List;

@Dao
public interface PlecDao {

    @Insert
    long insertPlec (Plec plec);

    @Insert
    void insertPlci(Plec[]plci);

    @Query("SELECT nazwaPlci FROM plec")
    List<String> getAllNazwyPlci();

    @Query("SELECT nazwaPlci FROM plec WHERE id=:id")
    String getNazwaPlciById(Long id);

    @Query("SELECT id FROM plec WHERE nazwaPlci=:nazwaPlci")
    long getIdPlciOdNazwy(String nazwaPlci);
}
