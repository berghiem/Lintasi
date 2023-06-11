package com.catostudioaruna.alphabet.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface LevelDao {
    @Insert
    public void insertLevel(Level level);

    @Insert
    public void insertLevels(Level... levels);

    @Update
    public int updateLevel(Level level);

    @Delete
    public void deleteLevel(Level level);

    @Query("DELETE FROM Level")
    public void deleteLevels();

    @Query("SELECT * FROM Level WHERE id= :id")
    LiveData<Level> getLevel(int id);

    @Query("SELECT * FROM Level ")
    LiveData<List<Level>> getAllLevel();

    @Query("SELECT COUNT (ID) FROM Level")
    public int count();


    @Query("SELECT ID FROM Level ")
    LiveData<List<Integer>> getAllId();

}
