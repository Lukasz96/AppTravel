package com.example.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lukasz.apptravel.db.entities.Kategoria;

import java.util.List;

@Dao
public interface KategoriaDao {

    @Insert
    long insertKategoria(Kategoria kategoria);

    @Query("SELECT * FROM kategoria")
    List<Kategoria> getAllKategoria();

    @Insert
    void insertKategorie(Kategoria[]kategorias);

}
