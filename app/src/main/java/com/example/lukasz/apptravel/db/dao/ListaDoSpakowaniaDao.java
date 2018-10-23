package com.example.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lukasz.apptravel.db.ElementDoZakupu;
import com.example.lukasz.apptravel.db.entities.ListaDoSpakowania;

import java.util.List;

@Dao
public interface ListaDoSpakowaniaDao {

    @Insert
    long insertListeDoSpakowania(ListaDoSpakowania listaDoSpakowania);

    @Query("SELECT * FROM listadospakowania")
    List<ListaDoSpakowania> getAllListyDoSpakowania();

    @Query("SELECT * FROM listadospakowania WHERE podrozId=:id")
    ListaDoSpakowania getListaDoSpakowaniaByTravelId(long id);

    @Query("delete from listadospakowania where id=:id")
    int deleteListaDoSpakowaniaById(long id);

}
