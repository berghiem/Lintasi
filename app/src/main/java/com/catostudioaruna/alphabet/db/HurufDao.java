package com.catostudioaruna.alphabet.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface HurufDao {
    @Insert
    public void insertHuruf(Huruf huruf);

    @Insert
    public void insertHurufs(Huruf... hurufs);

    @Update
    public int updateHuruf(Huruf huruf);

    @Delete
    public void deleteHuruf(Huruf huruf);

    @Query("DELETE FROM Huruf")
    public void deleteHurufs();

    @Query("SELECT * FROM Huruf WHERE id= :id")
    LiveData<Huruf> getHuruf(int id);

    @Query("SELECT * FROM Huruf ")
    LiveData<List<Huruf>> getAllHuruf();

    @Query("SELECT COUNT (ID) FROM Huruf")
    public int count();


    @Query("SELECT ID FROM Huruf ")
    LiveData<List<Integer>> getAllId();

}
