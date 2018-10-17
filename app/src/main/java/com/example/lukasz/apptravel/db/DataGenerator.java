package com.example.lukasz.apptravel.db;

import com.example.lukasz.apptravel.db.entities.Kategoria;

import java.util.List;

public class DataGenerator {

    public static Kategoria[] populateData() {
        return new Kategoria[] {
                new Kategoria(0, "Odzież"),
                new Kategoria(0, "Higiena"),
                new Kategoria(0, "Dokoumenty"),
                new Kategoria(0, "Inne")
        };
    }
}
