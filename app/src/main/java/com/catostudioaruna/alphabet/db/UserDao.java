package com.catostudioaruna.alphabet.db;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface UserDao {
    @Insert
    public void insert(User user);

    @Query("DELETE FROM USER")
    public void delete();

    @Update
    public int update(User user);

    @Query("SELECT * FROM user")
    public LiveData<User> getUser();

    @Query("SELECT COUNT (ID) FROM user")
    public int count();
}
