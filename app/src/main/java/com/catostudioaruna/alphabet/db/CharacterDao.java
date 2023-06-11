package com.catostudioaruna.alphabet.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface CharacterDao {
    @Insert
    public void insertCharacter(Character character);

    @Insert
    public void insertCharacters(Character... characters);

    @Update
    public int updateCharacter(Character character);

    @Delete
    public void deleteCharacter(Character character);

    @Query("DELETE FROM CHARACTER")
    public void deleteCharacters();

    @Query("SELECT * FROM CHARACTER WHERE id= :id")
    LiveData<Character> getCharacter(int id);

    @Query("SELECT * FROM Character ")
    LiveData<List<Character>> getAllCharacter();

    @Query("SELECT COUNT (ID) FROM Character")
    public int count();
}
