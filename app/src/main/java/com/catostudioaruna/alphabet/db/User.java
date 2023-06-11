package com.catostudioaruna.alphabet.db;

import java.sql.Timestamp;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "user")
public class User {
    @PrimaryKey (autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    private String name;

    private int score;

    private int characterId;

    @Ignore
    private Character character;

    private int activeVehicleId;

    @Ignore
    private Timestamp lastlogin;

    @Ignore
    private List<Vehicle> vehicleList;

    private int lastLevel;

    public User() {
    }

    public User(@NonNull String name, int score, @NonNull Timestamp lastlogin) {
        this.name = name;
        this.score = score;
        this.lastlogin = lastlogin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public Timestamp getLastlogin() {
        return lastlogin;
    }

    public void setLastlogin(Timestamp lastlogin) {
        this.lastlogin = lastlogin;
    }

    public int getCharacterId() {
        return characterId;
    }

    public void setCharacterId(int characterId) {
        this.characterId = characterId;
    }

    public Character getCharacter() {
        return character;
    }

    public void setCharacter(Character character) {
        this.character = character;
    }

    public List<Vehicle> getVehicleList() {
        return vehicleList;
    }

    public void setVehicleList(List<Vehicle> vehicleList) {
        this.vehicleList = vehicleList;
    }

    public int getActiveVehicleId() {
        return activeVehicleId;
    }

    public void setActiveVehicleId(int activeVehicleId) {
        this.activeVehicleId = activeVehicleId;
    }

    public int getLastLevel() {
        return lastLevel;
    }

    public void setLastLevel(int lastLevel) {
        this.lastLevel = lastLevel;
    }
}
