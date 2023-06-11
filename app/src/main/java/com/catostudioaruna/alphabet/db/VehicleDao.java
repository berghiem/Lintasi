package com.catostudioaruna.alphabet.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface VehicleDao {
    @Insert
    public void insertVehicle(Vehicle v);

    @Insert
    public void insertVehicles(Vehicle... v);

    @Update
    public int updateVehicle(Vehicle s);

    @Delete
    public void deleteVehicle(Vehicle s);


    @Query("DELETE FROM vehicle")
    public void deleteAllVehicle();


    @Query("SELECT * FROM vehicle WHERE id= :id")
    Vehicle getVehicle(int id);



    @Query("SELECT * FROM vehicle")
    LiveData<List<Vehicle>> getAllVehicle();

    @Query("SELECT COUNT(id) FROM vehicle")
    LiveData<Integer> getCountAllVehicle();

}
