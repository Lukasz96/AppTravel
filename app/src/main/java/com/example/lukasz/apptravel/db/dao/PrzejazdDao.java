package com.example.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lukasz.apptravel.db.entities.Przejazd;

import java.util.Date;
import java.util.List;

@Dao
public interface PrzejazdDao {

    @Insert
    long insertPrzejazd(Przejazd przejazd);

    @Query("SELECT * FROM przejazd")
    List<Przejazd> getAllPrzejazdy();

    @Query("SELECT * FROM przejazd WHERE podrozId=:travelId ORDER BY dataOd")
    List<Przejazd> getPrzejazdyDlaPodrozy(long travelId);

    @Query("DELETE FROM przejazd WHERE id=:id")
    void deletePrzejazdById(long id);

    @Query("SELECT * FROM przejazd WHERE id=:id")
    Przejazd getPrzejazdById(long id);

    @Query("UPDATE przejazd SET kategoriaPrzejazduId=:kategoriaId, nazwa=:nazwa, dataOd=:data, koszt=:koszt WHERE id=:id")
    void updatePrzejazd(long id, long kategoriaId, String nazwa, Date data, double koszt);

}
