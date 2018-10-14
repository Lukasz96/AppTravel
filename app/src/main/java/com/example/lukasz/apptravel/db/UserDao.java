package com.example.lukasz.apptravel.db;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.RequiresPermission;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertAll(List<User> user);

    @Insert
    void insertUser(User user);

    @Query("SELECT * FROM user")
    public List<User> getUsers();
    @Update
    public void updateUsers(User... users);
    @Delete
    public void deleteUsers(User... users);

    @Query("delete from user where user_id=:id")
    int deleteUser(int id);

    @Query("DELETE FROM user")
    public void deleteAllUsers();

}
