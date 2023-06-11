package com.catostudioaruna.alphabet.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Fruit {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String sourceName;


    private String iconSourceName;


    @NonNull
    private int audio;

    public Fruit(  @NonNull String name, @NonNull String sourceName, String iconSourceName, int audio) {
         this.name = name;
        this.sourceName = sourceName;
        this.iconSourceName = iconSourceName;
        this.audio = audio;
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

    @NonNull
    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(@NonNull String sourceName) {
        this.sourceName = sourceName;
    }

    public String getIconSourceName() {
        return iconSourceName;
    }

    public void setIconSourceName(String iconSourceName) {
        this.iconSourceName = iconSourceName;
    }

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }
}
