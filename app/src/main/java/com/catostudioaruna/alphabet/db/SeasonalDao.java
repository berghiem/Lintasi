package com.catostudioaruna.alphabet.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SeasonalDao {

    @Insert
    public void insertSeasonal(Seasonal Seasonal);

    @Insert
    public void insertSeasonals(Seasonal... Seasonals);

    @Update
    public int updateSeasonal(Seasonal Seasonal);

    @Delete
    public void deleteSeasonal(Seasonal Seasonal);

    @Query("DELETE FROM Seasonal")
    public void deleteSeasonals();

    @Query("SELECT * FROM Seasonal WHERE id= :id AND seasonalCode = :seasonalCode")
    LiveData<Seasonal> getSeasonal(int id, int seasonalCode);

    @Query("SELECT * FROM Seasonal WHERE seasonalCode = :seasonalCode")
    LiveData<List<Seasonal>> getAllSeasonal(int seasonalCode);

    @Query("SELECT COUNT (ID) FROM Seasonal WHERE seasonalCode = :seasonalCode")
    public int count(int seasonalCode);

    @Query("SELECT ID FROM Seasonal WHERE seasonalCode = :seasonalCode")
    LiveData<List<Integer>>  allId(int seasonalCode);
}

