package com.catostudioaruna.alphabet.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface AnimalDao {
 
        @Insert
        public void insertAnimal(Animal Animal);

        @Insert
        public void insertAnimals(Animal... Animals);

        @Update
        public int updateAnimal(Animal Animal);

        @Delete
        public void deleteAnimal(Animal Animal);

        @Query("DELETE FROM Animal")
        public void deleteAnimals();

        @Query("SELECT * FROM Animal WHERE id= :id")
        LiveData<Animal> getAnimal(int id);

        @Query("SELECT * FROM Animal ")
        LiveData<List<Animal>> getAllAnimal();

        @Query("SELECT COUNT (ID) FROM Animal")
        public int count();

        @Query("SELECT ID FROM Animal")
        LiveData<List<Integer>> allId();


}
