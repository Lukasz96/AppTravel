package com.example.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lukasz.apptravel.db.entities.Przejazd;
import com.example.lukasz.apptravel.db.entities.Wydatek;

import java.util.List;

@Dao
public interface WydatekDao {

    @Insert
    long insertWydatek(Wydatek wydatek);

    @Query("SELECT * FROM wydatek")
    List<Wydatek> getAllWydatki();

    @Query("SELECT * FROM wydatek WHERE podrozId=:travelId")
    List<Wydatek> getWydatkiDlaPodrozy(long travelId);

    @Query("DELETE FROM wydatek WHERE id=:id")
    void deleteWydatekById(long id);

    @Query("SELECT * FROM wydatek WHERE id=:id")
    Wydatek getWydatekById(long id);

    @Query("UPDATE wydatek SET nazwa=:nazwa, koszt=:koszt, kategoriaWydatkuId=:kategoriaId WHERE id=:id")
    void updateWydatekById(long id, String nazwa, double koszt, long kategoriaId);

    @Query("SELECT SUM(koszt) FROM wydatek WHERE podrozId=:travelid")
    double getSumOfWydatkiByTravelId(long travelid);
}
