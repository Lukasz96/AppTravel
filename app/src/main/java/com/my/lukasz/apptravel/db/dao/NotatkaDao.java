package com.my.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.my.lukasz.apptravel.db.entities.Notatka;

import java.util.List;

@Dao
public interface NotatkaDao {
    @Insert
    long insertNotatka(Notatka notatka);

    @Query("SELECT * FROM Notatka")
    List<Notatka> getNotatki();

    @Query("SELECT * FROM Notatka WHERE id=:id")
    Notatka getNotatkaById(long id);

    @Query("SELECT * FROM Notatka WHERE podrozId=:travelId")
    List<Notatka> getNotatkiByTravelId(long travelId);

    @Query("UPDATE Notatka SET  zdjecieUri=:zdjecieUri WHERE id=:id")
    void updateZdjecieNotatkaById(long id, String zdjecieUri);

    @Query("UPDATE Notatka SET tytul=:tytul, tresc=:tresc WHERE id=:id")
    void updateNotatkaById(long id, String tytul, String tresc);

    @Query("DELETE FROM Notatka WHERE id=:id")
    void deleteNotatkaById(long id);


}
