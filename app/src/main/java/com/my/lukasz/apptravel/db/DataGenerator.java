package com.my.lukasz.apptravel.db;

import android.content.Context;

import com.my.lukasz.apptravel.R;
import com.my.lukasz.apptravel.db.entities.Kategoria;
import com.my.lukasz.apptravel.db.entities.KategoriaPrzejazdu;
import com.my.lukasz.apptravel.db.entities.KategoriaWydatku;

public class DataGenerator {

    public static Kategoria[] populateKategorieRzeczy(Context context) {


        return new Kategoria[] {
                new Kategoria(0, context.getString(R.string.tabclotheslabel)),
                new Kategoria(0, context.getString(R.string.tabhygienelabel)),
                new Kategoria(0, context.getString(R.string.tabdocumentslabel)),
                new Kategoria(0, context.getString(R.string.otherlabel))
        };
    }

    public static KategoriaPrzejazdu[] populateKategoriePrzejazdu(Context context){
        return new KategoriaPrzejazdu[]{
                new KategoriaPrzejazdu(0,context.getString(R.string.carlabel)),
                new KategoriaPrzejazdu(0, context.getString(R.string.planelabel)),
                new KategoriaPrzejazdu(0, context.getString(R.string.trainlabel)),
                new KategoriaPrzejazdu(0, context.getString(R.string.shiplabel)),
                new KategoriaPrzejazdu(0, context.getString(R.string.bikelabel)),
                new KategoriaPrzejazdu(0,context.getString(R.string.onfootlabel)),
                new KategoriaPrzejazdu(0,context.getString(R.string.otherlabel))
        };
    }
    public static KategoriaWydatku[] populateKategorieWydatku(Context context) {
        return new KategoriaWydatku[] {
                new KategoriaWydatku(0, context.getString(R.string.accomodationlabel)),
                new KategoriaWydatku(0, context.getString(R.string.foodlabel)),
                new KategoriaWydatku(0, context.getString(R.string.sightseeinglabel)),
                new KategoriaWydatku(0, context.getString(R.string.otherlabel))
        };
    }
}
