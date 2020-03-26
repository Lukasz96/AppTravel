package com.my.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.my.lukasz.apptravel.db.entities.KategoriaTransport;

import java.util.List;

@Dao
public interface KategoriaTransportDao {

    @Insert
    long insertKategoriaWakacji (KategoriaTransport kategoriaTransport);

    @Query("SELECT nazwaKategoriiTransportu FROM kategoriatransport")
    List<String> getAllNazwyKategoriiTransportu();

    @Insert
    void insertKategorieTransportu(KategoriaTransport[]kategoriaTransports);

    @Query("SELECT id FROM kategoriatransport WHERE nazwaKategoriiTransportu=:nazwaTransportu")
    long getIdTransportuOdNazwy(String nazwaTransportu);
}
