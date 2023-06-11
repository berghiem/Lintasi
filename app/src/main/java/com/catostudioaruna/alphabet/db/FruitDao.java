package com.catostudioaruna.alphabet.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface FruitDao  {
    @Insert
    public void insertFruit(Fruit fruit);

    @Insert
    public void insertFruits(Fruit... fruits);

    @Update
    public int updateFruit(Fruit fruit);

    @Delete
    public void deleteFruit(Fruit fruit);

    @Query("DELETE FROM fruit")
    public void deleteFruits();

    @Query("SELECT * FROM fruit WHERE id= :id")
    LiveData<Fruit> getFruit(int id);

    @Query("SELECT * FROM fruit ")
    LiveData<List<Fruit>> getAllFruit();

    @Query("SELECT COUNT (ID) FROM fruit")
    public int count();

    @Query("SELECT ID FROM fruit")
    LiveData<List<Integer>>  allId();
}
