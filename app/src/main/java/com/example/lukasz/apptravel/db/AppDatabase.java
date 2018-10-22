package com.example.lukasz.apptravel.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.example.lukasz.apptravel.db.dao.ElementListyDoSpakowaniaDao;
import com.example.lukasz.apptravel.db.dao.KategoriaDao;
import com.example.lukasz.apptravel.db.dao.KategoriaPrzejazduDao;
import com.example.lukasz.apptravel.db.dao.KategoriaWydatkuDao;
import com.example.lukasz.apptravel.db.dao.ListaDoSpakowaniaDao;
import com.example.lukasz.apptravel.db.dao.NotatkaDao;
import com.example.lukasz.apptravel.db.dao.PodrozDao;
import com.example.lukasz.apptravel.db.dao.PrzejazdDao;
import com.example.lukasz.apptravel.db.dao.WydatekDao;
import com.example.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.example.lukasz.apptravel.db.entities.Kategoria;
import com.example.lukasz.apptravel.db.entities.KategoriaPrzejazdu;
import com.example.lukasz.apptravel.db.entities.KategoriaWydatku;
import com.example.lukasz.apptravel.db.entities.ListaDoSpakowania;
import com.example.lukasz.apptravel.db.entities.Notatka;
import com.example.lukasz.apptravel.db.entities.Podroz;
import com.example.lukasz.apptravel.db.entities.Przejazd;
import com.example.lukasz.apptravel.db.entities.Wydatek;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executors;

@Database(version = 1, entities = {Podroz.class, Notatka.class, ElementListyDoSpakowania.class,
        Kategoria.class, ListaDoSpakowania.class, Przejazd.class, KategoriaPrzejazdu.class,
        Wydatek.class, KategoriaWydatku.class})
@TypeConverters({DateTypeConverter.class})

public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    abstract public PodrozDao podrozDao();
    abstract public NotatkaDao notatkaDao();
    abstract public ListaDoSpakowaniaDao listaDoSpakowaniaDao();
    abstract public KategoriaDao kategoriaDao();
    abstract public ElementListyDoSpakowaniaDao elementListyDoSpakowaniaDao();
    abstract public PrzejazdDao przejazdDao();
    abstract public KategoriaPrzejazduDao kategoriaPrzejazduDao();
    abstract public WydatekDao wydatekDao();
    abstract public KategoriaWydatkuDao kategoriaWydatkuDao();

    private static final List<String> KATEGORIALIST = Arrays.asList("Odzież","Higiena","Dokumenty","Inne");

    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = buildDatabase(context);
        }
        return INSTANCE;
    }
    private static AppDatabase buildDatabase(final Context context) {
        final DataGenerator dataGenerator=new DataGenerator();
        return Room.databaseBuilder(context,
                AppDatabase.class,
                "my-database")
                .addCallback(new Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                getInstance(context).kategoriaDao().insertKategorie(dataGenerator.populateKategorieRzeczy());
                                getInstance(context).kategoriaPrzejazduDao().insertKategoriePrzejazdu(dataGenerator.populateKategoriePrzejazdu());
                                getInstance(context).kategoriaWydatkuDao().insertKategorieWydatku(dataGenerator.populateKategorieWydatku());
                            }
                        });
                    }
                })
                .allowMainThreadQueries().build();
    }

}
