package com.catostudioaruna.alphabet.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Animal {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    @NonNull
    private int type; //insect , small, medium, tall

    @NonNull
    private String sourceName;


    private String iconSourceName;


    @NonNull
    private int audio;

    private int sourceId;

    public Animal(@NonNull String name, int type, @NonNull String sourceName, int audio, String iconSourceName, int sourceId) {
        this.name = name;
        this.type = type;
        this.sourceName = sourceName;
        this.audio = audio;
        this.iconSourceName = iconSourceName;
        this.sourceId = sourceId;
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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @NonNull
    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(@NonNull String sourceName) {
        this.sourceName = sourceName;
    }

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }

    public String getIconSourceName() {
        return iconSourceName;
    }

    public void setIconSourceName(String iconSourceName) {
        this.iconSourceName = iconSourceName;
    }

    public int getSourceId() {
        return sourceId;
    }

    public void setSourceId(int sourceId) {
        this.sourceId = sourceId;
    }
}
