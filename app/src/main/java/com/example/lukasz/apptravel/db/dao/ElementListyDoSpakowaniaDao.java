package com.example.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lukasz.apptravel.db.ElementDoZakupu;
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.example.lukasz.apptravel.db.entities.ListaDoSpakowania;

import java.util.List;

@Dao
public interface ElementListyDoSpakowaniaDao {

    @Insert
    long insertElementListyDoSpakowania(ElementListyDoSpakowania elementListyDoSpakowania);

    @Query("SELECT * FROM elementlistydospakowania")
    List<ElementListyDoSpakowania> getAllElementyListyDoSpakowania();

    @Query("SELECT elementlistydospakowania.id, listaDoSpakowaniaId, elementlistydospakowania.nazwa, czySpakowane, czyPrzekazanoDoZakupu, ilosc, cena, czyKupione, idKategorii FROM elementlistydospakowania INNER JOIN listadospakowania ON listaDoSpakowania.id=listaDoSpakowaniaId " +
            "WHERE listaDoSpakowania.id=:idListy")
    List<ElementListyDoSpakowania> getElementyDoSpakowaniaZDanejListy(long idListy);

    @Query("SELECT elementlistydospakowania.nazwa,ilosc,cena,czyKupione,kategoria.nazwaKategorii FROM elementlistydospakowania" +
            " INNER JOIN kategoria ON kategoria.id=idKategorii" +
            " INNER JOIN listadospakowania ON listadospakowania.id=listaDoSpakowaniaId" +
            " INNER JOIN podroz ON podroz.id=listadospakowania.podrozId" +
            " WHERE podroz.id=:idPodrozy")
    List<ElementDoZakupu> getElementyDoZakupuDlaPodrozy(long idPodrozy);

    @Query("UPDATE elementlistydospakowania SET czyspakowane=:czySpakowane WHERE id=:id")
    void updateCzySpakowanyElementById(long id, boolean czySpakowane);

    @Query("SELECT * FROM elementlistydospakowania WHERE listaDoSpakowaniaId=:listaId AND idKategorii=:kategoriaId")
    List<ElementListyDoSpakowania> getElementyListyDoSpakowaniaByKategoriaFromList(long listaId, long kategoriaId);

    @Query("DELETE FROM elementlistydospakowania WHERE id=:id")
    void deleteElementListyDoSpakowaniaById(long id);
}