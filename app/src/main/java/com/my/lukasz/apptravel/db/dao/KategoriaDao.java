package com.my.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.my.lukasz.apptravel.db.entities.Kategoria;

import java.util.List;

@Dao
public interface KategoriaDao {

    @Insert
    long insertKategoria(Kategoria kategoria);

    @Query("SELECT * FROM kategoria")
    List<Kategoria> getAllKategoria();

    @Insert
    void insertKategorie(Kategoria[]kategorias);

    @Query("SELECT nazwaKategorii FROM kategoria")
    List<String> getAllNazwyKategorii();

    @Query("SELECT id FROM kategoria WHERE nazwaKategorii=:nazwaKategorii")
    long getIdKategoriiOdNazwy(String nazwaKategorii);

}
