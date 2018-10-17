package com.example.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.example.lukasz.apptravel.db.entities.ElementListyZakupow;

import java.util.List;

@Dao
public interface ElementListyZakupowDao {

    @Insert
    void insertElementListyZakupow(ElementListyZakupow elementListyZakupow);

    @Query("SELECT * FROM elementlistyzakupow")
    List<ElementListyZakupow> getAllElementyListyZakupow();

    @Query("SELECT * FROM elementlistyzakupow INNER JOIN listadozakupu ON listadozakupu.id=listaDoZakupuId " +
            "WHERE listadozakupu.id=:idListy")
    List<ElementListyZakupow> getElementyDoZakupuZDanejListy(int idListy);
}
