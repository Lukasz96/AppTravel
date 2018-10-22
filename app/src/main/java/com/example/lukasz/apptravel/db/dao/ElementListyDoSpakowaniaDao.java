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

    @Query("SELECT elementlistydospakowania.id, listaDoSpakowaniaId, elementlistydospakowania.nazwa, czySpakowane, czyPrzekazanoDoZakupu, ilosc, cena, czyKupione, idKategorii, uwaga FROM elementlistydospakowania INNER JOIN listadospakowania ON listaDoSpakowania.id=listaDoSpakowaniaId " +
            "WHERE listaDoSpakowania.id=:idListy")
    List<ElementListyDoSpakowania> getElementyDoSpakowaniaZDanejListy(long idListy);

    @Query("SELECT elementlistydospakowania.nazwa,ilosc,cena,czyKupione,kategoria.nazwaKategorii,uwaga FROM elementlistydospakowania" +
            " INNER JOIN kategoria ON kategoria.id=idKategorii" +
            " INNER JOIN listadospakowania ON listadospakowania.id=listaDoSpakowaniaId" +
            " INNER JOIN podroz ON podroz.id=listadospakowania.podrozId" +
            " WHERE podroz.id=:idPodrozy")
    List<ElementDoZakupu> getElementyDoZakupuDlaPodrozy(long idPodrozy);
}