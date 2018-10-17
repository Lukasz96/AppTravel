package com.example.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lukasz.apptravel.db.entities.ListaDoSpakowania;
import com.example.lukasz.apptravel.db.entities.ListaDoZakupu;

import java.util.List;

@Dao
public interface ListaDoZakupuDao {
    @Insert
    void insertListaDoZakupu(ListaDoZakupu listaDoZakupu);

    @Query("SELECT * FROM listadozakupu")
    List<ListaDoZakupu> getAllListyDoZakupu();
}
