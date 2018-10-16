package com.example.lukasz.apptravel.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(version = 1, entities = {User.class, Book.class,Address.class})

public abstract class AppDatabase extends RoomDatabase {
    private static AppDatabase INSTANCE;
    // BookDao is a class annotated with @Dao.
    abstract public BookDao bookDao();
    // UserDao is a class annotated with @Dao.
    abstract public UserDao userDao();
    abstract public AddressDao addressDao();
    // UserBookDao is a class annotated with @Dao.
   // abstract public UserBookDao userBookDao();
    public static AppDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase.class, "my-sample-app.db").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }



}
