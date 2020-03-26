package com.my.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.my.lukasz.apptravel.db.entities.KategoriaWakacji;

import java.util.List;

@Dao
public interface KategoriaWakacjiDao {

    @Insert
    long insertKategoriaWakacji (KategoriaWakacji kategoriaWakacji);

    @Query("SELECT nazwaKategoriiWakacji FROM kategoriawakacji")
    List<String> getAllNazwyKategoriiWakacji();

    @Insert
    void insertKategorieWakacji(KategoriaWakacji[]kategoriaWakacjis);

    @Query("SELECT id FROM kategoriawakacji WHERE nazwaKategoriiWakacji=:nazwaKatWakacji")
    long getIdKatWakacjiOdNazwy(String nazwaKatWakacji);
}
