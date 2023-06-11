package com.catostudioaruna.alphabet.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Bodypart {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String name;

    private String iconSource;

    private String mediumSource;

    private int animalId;

    private boolean isHidden;

    public Bodypart( String name, String iconSource, String mediumSource, int animalId) {

        this.name = name;
        this.iconSource = iconSource;
        this.mediumSource = mediumSource;
        this.animalId = animalId;
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

    public String getIconSource() {
        return iconSource;
    }

    public void setIconSource(String iconSource) {
        this.iconSource = iconSource;
    }

    public String getMediumSource() {
        return mediumSource;
    }

    public void setMediumSource(String mediumSource) {
        this.mediumSource = mediumSource;
    }

    public int getAnimalId() {
        return animalId;
    }

    public void setAnimalId(int animalId) {
        this.animalId = animalId;
    }

    public boolean isHidden() {
        return isHidden;
    }

    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
}
