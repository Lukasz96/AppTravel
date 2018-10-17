package com.example.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.example.lukasz.apptravel.db.entities.ListaDoSpakowania;

import java.util.List;

@Dao
public interface ElementListyDoSpakowaniaDao {

    @Insert
    void insertElementListyDoSpakowania(ElementListyDoSpakowania elementListyDoSpakowania);

    @Query("SELECT * FROM elementlistydospakowania")
    List<ElementListyDoSpakowania> getAllElementyListyDoSpakowania();

    @Query("SELECT * FROM elementlistydospakowania INNER JOIN listadospakowania ON listaDoSpakowania.id=listaDoSpakowaniaId " +
            "WHERE listaDoSpakowania.id=:idListy")
    List<ElementListyDoSpakowania> getElementyZDanejListy(int idListy);
}