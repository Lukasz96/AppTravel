package com.my.lukasz.apptravel.db;

import android.content.Context;

import com.my.lukasz.apptravel.R;
import com.my.lukasz.apptravel.db.entities.Kategoria;
import com.my.lukasz.apptravel.db.entities.KategoriaPogody;
import com.my.lukasz.apptravel.db.entities.KategoriaPrzejazdu;
import com.my.lukasz.apptravel.db.entities.KategoriaTransport;
import com.my.lukasz.apptravel.db.entities.KategoriaWakacji;
import com.my.lukasz.apptravel.db.entities.KategoriaWydatku;

public class DataGenerator {

    public static Kategoria[] populateKategorieRzeczy(Context context) {
        return new Kategoria[] {
                new Kategoria(0, context.getString(R.string.tabclotheslabel)),
                new Kategoria(0, context.getString(R.string.tabhygienelabel)),
                new Kategoria(0, context.getString(R.string.tabdocumentslabel)),
                new Kategoria(0, context.getString(R.string.tabelectroinicslabel)),
                new Kategoria(0, context.getString(R.string.otherlabel))
        };
    }

    public static KategoriaWakacji[] populateKategorieWakacji(Context context) {
        return new KategoriaWakacji[] {
                new KategoriaWakacji(0, context.getString(R.string.holidaycategoryrest)),
                new KategoriaWakacji(0, context.getString(R.string.holidaycategorysport)),
                new KategoriaWakacji(0, context.getString(R.string.holidaycategorytourist)),
                new KategoriaWakacji(0, context.getString(R.string.holidaycategorybusiness))
        };
    }

    public static KategoriaTransport[] populateKategorieTransportu(Context context) {
        return new KategoriaTransport[] {
                new KategoriaTransport(0, context.getString(R.string.carlabel)),
                new KategoriaTransport(0, context.getString(R.string.planelabel)),
                new KategoriaTransport(0, context.getString(R.string.puplictransportlabel)),
                new KategoriaTransport(0, context.getString(R.string.shiplabel)),
                new KategoriaTransport(0, context.getString(R.string.bikelabel)),
                new KategoriaTransport(0, context.getString(R.string.onfootlabel))
        };
    }

    public static String[] populatePlci(Context context) {
        return new String[] {
                context.getString(R.string.femalelabel),
                context.getString(R.string.malelabel)
        };
    }

    public static KategoriaPogody[] populateKategoriePogody(Context context) {
        return new KategoriaPogody[] {
                new KategoriaPogody(0, context.getString(R.string.coldweatherlabel)),
                new KategoriaPogody(0, context.getString(R.string.mildweatherlabel)),
                new KategoriaPogody(0, context.getString(R.string.warmweatherlabel)),
                new KategoriaPogody(0, context.getString(R.string.hotweatherlabel)),
                new KategoriaPogody(0, context.getString(R.string.changingweatherlabel)),
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
