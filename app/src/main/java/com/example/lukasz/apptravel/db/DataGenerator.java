package com.example.lukasz.apptravel.db;

import com.example.lukasz.apptravel.db.entities.Kategoria;
import com.example.lukasz.apptravel.db.entities.KategoriaPrzejazdu;

import java.util.List;

public class DataGenerator {

    public static Kategoria[] populateKategorieRzeczy() {
        return new Kategoria[] {
                new Kategoria(0, "Odzież"),
                new Kategoria(0, "Higiena"),
                new Kategoria(0, "Dokoumenty"),
                new Kategoria(0, "Inne")
        };
    }

    public static KategoriaPrzejazdu[] populateKategoriePrzejazdu(){
        return new KategoriaPrzejazdu[]{
                new KategoriaPrzejazdu(0,"Samochód"),
                new KategoriaPrzejazdu(0, "Samolot"),
                new KategoriaPrzejazdu(0, "Pociąg"),
                new KategoriaPrzejazdu(0, "Statek"),
                new KategoriaPrzejazdu(0, "Rower"),
                new KategoriaPrzejazdu(0,"Pieszo"),
                new KategoriaPrzejazdu(0,"Inne")
        };
    }
}
