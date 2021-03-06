package com.my.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.my.lukasz.apptravel.db.entities.ElementListyDoSpakowania;

import java.util.List;

@Dao
public interface ElementListyDoSpakowaniaDao {

    @Insert
    long insertElementListyDoSpakowania(ElementListyDoSpakowania elementListyDoSpakowania);

    @Query("SELECT * FROM elementlistydospakowania")
    List<ElementListyDoSpakowania> getAllElementyListyDoSpakowania();

    @Query("SELECT * FROM elementlistydospakowania WHERE listaDoSpakowaniaId=:idListy")
    List<ElementListyDoSpakowania> getElementyDoSpakowaniaZDanejListy(long idListy);

    @Query("SELECT * FROM elementlistydospakowania WHERE listaDoSpakowaniaId=:listaId AND czyDoSpakowania=:czyDoSpakowania")
    List<ElementListyDoSpakowania> getElementyZDanejListyCzyDoSpakowania(long listaId, boolean czyDoSpakowania);

    @Query("SELECT * FROM elementlistydospakowania WHERE listaDoSpakowaniaId=:listaId AND czyPrzekazanoDoZakupu=:czyDoKupienia")
    List<ElementListyDoSpakowania> getElementyZDanejListyCzyDoKupienia(long listaId, boolean czyDoKupienia);

    @Query("UPDATE elementlistydospakowania SET czyspakowane=:czySpakowane WHERE id=:id")
    void updateCzySpakowanyElementById(long id, boolean czySpakowane);

    @Query("SELECT * FROM elementlistydospakowania WHERE listaDoSpakowaniaId=:listaId AND idKategorii=:kategoriaId AND czyDoSpakowania=:czyDoSpakowania")
    List<ElementListyDoSpakowania> getElementyListyDoSpakowaniaByKategoriaFromListDoSpakowania(long listaId, long kategoriaId, boolean czyDoSpakowania);

    @Query("DELETE FROM elementlistydospakowania WHERE id=:id")
    void deleteElementListyDoSpakowaniaById(long id);

    @Query("SELECT * FROM elementlistydospakowania WHERE id=:id")
    ElementListyDoSpakowania getElementListyDoSpakowaniaById(long id);

    @Query("UPDATE elementlistydospakowania SET nazwa=:nazwa, iloscDoSpakowania=:ilosc, czyprzekazanodozakupu=:czykupic, idkategorii=:idkategorii WHERE id=:id")
    void updateEditItemToPack(long id, String nazwa, int ilosc, boolean czykupic, long idkategorii);

    @Query("SELECT * FROM elementlistydospakowania WHERE listaDoSpakowaniaId=:iDlistyDoSpakowania AND czyPrzekazanoDoZakupu=:czyPrzekazanoDoZakupu")
    List<ElementListyDoSpakowania> getElementyCzyPrzekazanoDoZakupu(long iDlistyDoSpakowania, boolean czyPrzekazanoDoZakupu);

    @Query("UPDATE elementlistydospakowania SET czykupione=:czyKupione WHERE id=:id")
    void setCzyKupione(long id, boolean czyKupione);

    @Query("SELECT * FROM elementlistydospakowania WHERE listaDoSpakowaniaId=:iDlistyDoSpakowania AND czyPrzekazanoDoZakupu=:czyDoZakupu")
    List<ElementListyDoSpakowania>getAllElementyDoZakupu(long iDlistyDoSpakowania, boolean czyDoZakupu);

    @Query("UPDATE elementlistydospakowania SET czyPrzekazanoDoZakupu=:czyDoZakupu WHERE id=:id")
    void setCzyDoZakupu(long id, boolean czyDoZakupu);

    @Query("UPDATE elementlistydospakowania SET czyDoSpakowania=:czyDoSpakowania WHERE id=:id")
    void setCzyDoSpakowania(long id, boolean czyDoSpakowania);

    @Query("UPDATE elementlistydospakowania "+
            "SET czyDoSpakowania=:czyDoSpakowania "+
            "WHERE listaDoSpakowaniaId=:listaId")
    void setCzyDoSpakowaniaForWholeListByListalId(long listaId, boolean czyDoSpakowania);

    @Query("UPDATE elementlistydospakowania SET nazwa=:nazwa, iloscDoZakupu=:ilosc, cena=:cena, waluta=:waluta WHERE id=:id")
    void updateElementListyDoZakupuById(long id, String nazwa, int ilosc, double cena, String waluta);

    @Query("UPDATE elementlistydospakowania SET cena=:cena, waluta=:waluta WHERE id=:id")
    void updateCenaElementZakupuById(long id, double cena, String waluta);

    @Query("SELECT SUM(cena) FROM elementlistydospakowania WHERE listaDoSpakowaniaId=:listaid AND czyKupione=:czykupione AND czyPrzekazanoDoZakupu=:czyDoZakupu AND waluta=:waluta")
    double getSumOfShoppingList(long listaid, boolean czykupione, boolean czyDoZakupu, String waluta);

    @Query("SELECT * FROM elementlistydospakowania WHERE listaDoSpakowaniaId=:iDlistyDoSpakowania AND czyDoSpakowania=:czyDoSpakowania AND czySpakowane=:czySpakowane")
    List<ElementListyDoSpakowania>getSpakowaneElementy(long iDlistyDoSpakowania, boolean czySpakowane, boolean czyDoSpakowania);

    @Query("SELECT DISTINCT waluta FROM elementlistydospakowania WHERE listaDoSpakowaniaId=:listaId  AND czyKupione=:czykupione AND czyPrzekazanoDoZakupu=:czyDoZakupu")
    List<String> getWalutySpakowaneKupione(long listaId, boolean czykupione, boolean czyDoZakupu);

    @Query("SELECT DISTINCT waluta FROM elementlistydospakowania WHERE czyKupione=:czykupione AND czyPrzekazanoDoZakupu=:czyDoZakupu")
    List<String> getWalutySpakowaneKupioneWszystkieListy( boolean czykupione, boolean czyDoZakupu);

}