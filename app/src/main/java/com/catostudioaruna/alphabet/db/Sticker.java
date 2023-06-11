package com.catostudioaruna.alphabet.db;

import java.sql.Timestamp;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "sticker")
public class Sticker {
    @PrimaryKey (autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    private String name;


    private String theme;

    @NonNull
    private Integer sourceId;

    @NonNull
    private boolean isCollected;

    @Ignore
    private Timestamp dateAdded;

    @Ignore
    private Timestamp dateCollected;

    public Sticker(@NonNull String name, String theme, Integer sourceId, boolean isCollected) {
        this.name = name;
        this.theme = theme;
        this.sourceId = sourceId;
        this.isCollected = isCollected;
        this.dateAdded = new Timestamp(System.currentTimeMillis());
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @NonNull
    public String getName() {
        return name;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public Integer getSourceId() {
        return sourceId;
    }

    public void setSourceId(Integer sourceId) {
        this.sourceId = sourceId;
    }

    public boolean isCollected() {
        return isCollected;
    }

    public void setCollected(boolean collected) {
        isCollected = collected;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Timestamp getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Timestamp dateAdded) {
        this.dateAdded = dateAdded;
    }

    public Timestamp getDateCollected() {
        return dateCollected;
    }

    public void setDateCollected(Timestamp dateCollected) {
        this.dateCollected = dateCollected;
    }
}
