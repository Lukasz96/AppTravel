package com.my.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.my.lukasz.apptravel.db.entities.Podroz;

import java.util.Date;
import java.util.List;

@Dao
public interface PodrozDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<Podroz> podroz);

    @Insert
    long insertPodroz(Podroz podroz);

    @Query("SELECT * FROM Podroz")
    List<Podroz> getPodroze();

    @Update
    void updatePodroze(Podroz... podroze);

    @Delete
    void deletePodroze(Podroz... podroze);

    @Query("delete from Podroz where id=:id")
    int deletePodrozById(long id);

    @Query("DELETE FROM Podroz")
    void deleteAllPodroze();

    @Query("SELECT * FROM Podroz WHERE id=:id ")
    Podroz getPodrozById(long id);

    @Query("SELECT dataOd FROM podroz WHERE id=:id")
    Date getDateOdByTravelId(long id);

    @Query("SELECT dataDo FROM podroz WHERE id=:id")
    Date getDateDoByTravelId(long id);

    @Query("SELECT waluta FROM podroz WHERE id=:id")
    String getWalutaByTravelId(long id);

    @Query("UPDATE Podroz SET nazwa=:nowaNazwa, dataOd=:from, dataDo=:to, budzet=:budget, waluta=:waluta WHERE id=:id")
    void updatePodrozById(long id, String nowaNazwa, Date from, Date to, double budget, String waluta);

    @Query("SELECT * FROM Podroz ORDER BY dataOd")
    List<Podroz> getPodrozeOrderedByDate();

    @Query("SELECT id FROM podroz")
    List<Long> getAllPodrozeId();



}
