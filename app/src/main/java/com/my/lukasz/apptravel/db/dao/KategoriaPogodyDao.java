package com.my.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.my.lukasz.apptravel.db.entities.KategoriaPogody;
import com.my.lukasz.apptravel.db.entities.KategoriaWakacji;

import java.util.List;

@Dao
public interface KategoriaPogodyDao {

    @Insert
    long insertKategoriaPogody(KategoriaPogody kategoriaPogody);

    @Query("SELECT nazwaKategoriiPogody FROM kategoriapogody")
    List<String> getAllNazwyKategoriiPogody();

    @Insert
    void insertKategoriePogody(KategoriaPogody[]kategoriaPogodies);

    @Query("SELECT id FROM kategoriapogody WHERE nazwaKategoriiPogody=:nazwaPogody")
    long getIdPogodyOdNazwy(String nazwaPogody);
}
