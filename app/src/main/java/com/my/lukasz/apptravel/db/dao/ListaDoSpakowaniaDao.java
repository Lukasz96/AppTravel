package com.my.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.my.lukasz.apptravel.db.entities.ListaDoSpakowania;

import java.util.List;

@Dao
public interface ListaDoSpakowaniaDao {

    @Insert
    long insertListeDoSpakowania(ListaDoSpakowania listaDoSpakowania);

    @Query("SELECT * FROM listadospakowania")
    List<ListaDoSpakowania> getAllListyDoSpakowania();

    @Query("SELECT * FROM listadospakowania WHERE id <> :idlisty")
    List<ListaDoSpakowania> getAllListyDoSpakowaniaExceptCurrent(long idlisty);

    @Query("SELECT listadospakowania.id,listadospakowania.nazwa,listadospakowania.podrozId FROM listadospakowania INNER JOIN podroz ON listadospakowania.podrozId=podroz.id ORDER BY podroz.dataOd")
    List<ListaDoSpakowania> getAllListyDoSpakowaniaOrderedByDate();



    @Query("SELECT * FROM listadospakowania WHERE podrozId=:id")
    ListaDoSpakowania getListaDoSpakowaniaByTravelId(long id);

    @Query("delete from listadospakowania where id=:id")
    int deleteListaDoSpakowaniaById(long id);

    @Query("delete from listadospakowania where podrozId=:id")
    int deleteListaDoSpakowaniaByTravelId(long id);

    @Query("SELECT podrozId FROM listadospakowania WHERE id=:listaId")
    long getPodrozIdFromListaDoSpakowaniaId(long listaId);

}
