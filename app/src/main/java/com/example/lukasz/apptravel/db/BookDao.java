package com.example.lukasz.apptravel.db;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

@Dao
public interface BookDao {
   // @Query("SELECT user.*,count(book.user_id) as borrowe FROM user LEFT JOIN book ON user.user_id = book.user_id group by user.user_id,book.user_id")
  //          LiveData<List<UserBooks>> fetchBookBorrower();
}
