package com.my.lukasz.apptravel.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.my.lukasz.apptravel.db.entities.User;

import java.util.List;

@Dao
public interface UserDao {

    @Insert
    long insertUser (User user);



}
