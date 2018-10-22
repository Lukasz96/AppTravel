package com.example.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.example.lukasz.apptravel.db.entities.KategoriaPrzejazdu;

import java.util.List;

@Dao
public interface KategoriaPrzejazduDao {

    @Insert
    long insertKategoriaPrzejazdu(KategoriaPrzejazdu kategoriaPrzejazdu);

    @Query("SELECT * FROM kategoriaprzejazdu")
    List<KategoriaPrzejazdu> getAllKategoriaPrzejazdu();

    @Insert
    void insertKategoriePrzejazdu(KategoriaPrzejazdu[]kategoriePrzejazdu);
}
