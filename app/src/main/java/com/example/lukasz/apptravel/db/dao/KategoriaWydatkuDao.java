package com.example.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lukasz.apptravel.db.entities.KategoriaPrzejazdu;
import com.example.lukasz.apptravel.db.entities.KategoriaWydatku;

import java.util.List;

@Dao
public interface KategoriaWydatkuDao {

    @Insert
    long insertKategoriaWydatku(KategoriaWydatku kategoriaWydatku);

    @Query("SELECT * FROM kategoriawydatku")
    List<KategoriaWydatku> getAllKategoriaWydatku();

    @Insert
    void insertKategorieWydatku(KategoriaWydatku[]kategoriaWydatku);

    @Query("SELECT nazwaKategoriiWydatku FROM kategoriawydatku")
    List<String>getAllNazwyKategoriiWydatku();

    @Query("SELECT id FROM kategoriawydatku WHERE nazwaKategoriiWydatku=:nazwaKategorii")
    long getIdKategoriiWydatkuOdNazwy(String nazwaKategorii);
}
