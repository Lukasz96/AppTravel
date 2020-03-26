package com.my.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.my.lukasz.apptravel.db.entities.KategoriaPrzejazdu;

import java.util.List;

@Dao
public interface KategoriaPrzejazduDao {

    @Insert
    long insertKategoriaPrzejazdu(KategoriaPrzejazdu kategoriaPrzejazdu);

    @Query("SELECT * FROM kategoriaprzejazdu")
    List<KategoriaPrzejazdu> getAllKategoriaPrzejazdu();

    @Insert
    void insertKategoriePrzejazdu(KategoriaPrzejazdu[]kategoriePrzejazdu);

    @Query("SELECT nazwaKategorii FROM kategoriaprzejazdu")
    List<String> getAllNazwyKategoriiPrzejazdu();

    @Query("SELECT id FROM kategoriaprzejazdu WHERE nazwaKategorii=:nazwaKategorii")
    long getIdKategoriiPrzejazduOdNazwy(String nazwaKategorii);
}
