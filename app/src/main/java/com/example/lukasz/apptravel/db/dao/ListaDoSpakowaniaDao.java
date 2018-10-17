package com.example.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lukasz.apptravel.db.entities.ListaDoSpakowania;

import java.util.List;

@Dao
public interface ListaDoSpakowaniaDao {

    @Insert
    void insertListeDoSpakowania(ListaDoSpakowania listaDoSpakowania);

    @Query("SELECT * FROM listadospakowania")
    List<ListaDoSpakowania> getAllListyDoSpakowania();
}
