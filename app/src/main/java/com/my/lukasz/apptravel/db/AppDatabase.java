package com.my.lukasz.apptravel.db;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;
import android.support.annotation.NonNull;

import com.my.lukasz.apptravel.db.dao.ElementListyDoSpakowaniaDao;
import com.my.lukasz.apptravel.db.dao.KategoriaDao;
import com.my.lukasz.apptravel.db.dao.KategoriaPogodyDao;
import com.my.lukasz.apptravel.db.dao.KategoriaPrzejazduDao;
import com.my.lukasz.apptravel.db.dao.KategoriaTransportDao;
import com.my.lukasz.apptravel.db.dao.KategoriaWakacjiDao;
import com.my.lukasz.apptravel.db.dao.KategoriaWydatkuDao;
import com.my.lukasz.apptravel.db.dao.ListaDoSpakowaniaDao;
import com.my.lukasz.apptravel.db.dao.NotatkaDao;
import com.my.lukasz.apptravel.db.dao.PlecDao;
import com.my.lukasz.apptravel.db.dao.PodrozDao;
import com.my.lukasz.apptravel.db.dao.PrzejazdDao;
import com.my.lukasz.apptravel.db.dao.UserDao;
import com.my.lukasz.apptravel.db.dao.WydatekDao;
import com.my.lukasz.apptravel.db.entities.ElementListyDoSpakowania;
import com.my.lukasz.apptravel.db.entities.Kategoria;
import com.my.lukasz.apptravel.db.entities.KategoriaPogody;
import com.my.lukasz.apptravel.db.entities.KategoriaPrzejazdu;
import com.my.lukasz.apptravel.db.entities.KategoriaTransport;
import com.my.lukasz.apptravel.db.entities.KategoriaWakacji;
import com.my.lukasz.apptravel.db.entities.KategoriaWydatku;
import com.my.lukasz.apptravel.db.entities.ListaDoSpakowania;
import com.my.lukasz.apptravel.db.entities.Notatka;
import com.my.lukasz.apptravel.db.entities.Plec;
import com.my.lukasz.apptravel.db.entities.Podroz;
import com.my.lukasz.apptravel.db.entities.Przejazd;
import com.my.lukasz.apptravel.db.entities.User;
import com.my.lukasz.apptravel.db.entities.Wydatek;

import java.util.concurrent.Executors;

@Database(version = 1, entities = {Podroz.class, Notatka.class, ElementListyDoSpakowania.class,
        Kategoria.class, ListaDoSpakowania.class, Przejazd.class, KategoriaPrzejazdu.class,
        Wydatek.class, KategoriaWydatku.class, KategoriaPogody.class, KategoriaTransport.class, KategoriaWakacji.class, User.class, Plec.class})
@TypeConverters({DateTypeConverter.class})

public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;

    abstract public PodrozDao podrozDao();
    abstract public NotatkaDao notatkaDao();
    abstract public ListaDoSpakowaniaDao listaDoSpakowaniaDao();
    abstract public KategoriaDao kategoriaDao();
    abstract public UserDao userDao();
    abstract public KategoriaPogodyDao kategoriaPogodyDao();
    abstract public KategoriaTransportDao kategoriaTransportDao();
    abstract public KategoriaWakacjiDao kategoriaWakacjiDao();
    abstract public PlecDao plecDao();
    abstract public ElementListyDoSpakowaniaDao elementListyDoSpakowaniaDao();
    abstract public PrzejazdDao przejazdDao();
    abstract public KategoriaPrzejazduDao kategoriaPrzejazduDao();
    abstract public WydatekDao wydatekDao();
    abstract public KategoriaWydatkuDao kategoriaWydatkuDao();



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
                                getInstance(context).kategoriaDao().insertKategorie(dataGenerator.populateKategorieRzeczy(context));
                                getInstance(context).kategoriaPrzejazduDao().insertKategoriePrzejazdu(dataGenerator.populateKategoriePrzejazdu(context));
                                getInstance(context).kategoriaWydatkuDao().insertKategorieWydatku(dataGenerator.populateKategorieWydatku(context));
                                getInstance(context).plecDao().insertPlci(dataGenerator.populatePlci(context));
                                getInstance(context).kategoriaPogodyDao().insertKategoriePogody(dataGenerator.populateKategoriePogody(context));
                                getInstance(context).kategoriaTransportDao().insertKategorieTransportu(dataGenerator.populateKategorieTransportu(context));
                                getInstance(context).kategoriaWakacjiDao().insertKategorieWakacji(dataGenerator.populateKategorieWakacji(context));
                            }
                        });
                    }
                })
                .allowMainThreadQueries().build();
    }

}
