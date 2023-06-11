package com.catostudioaruna.alphabet.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Huruf {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    private String iconName;

    @NonNull
    private String sourceName;


    private String iconSourceName;


    private String iconOnQuizName;



    @NonNull
    private int audio;

    public Huruf(@NonNull String name, @NonNull String sourceName, String iconSourceName, String iconName ,int audio, String iconOnQuizName) {
        this.name = name;
        this.sourceName = sourceName;
        this.iconSourceName = iconSourceName;
        this.audio = audio;
        this.iconName = iconName;
        this.iconOnQuizName = iconOnQuizName;
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

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getIconOnQuizName() {
        return iconOnQuizName;
    }

    public void setIconOnQuizName(String iconOnQuizName) {
        this.iconOnQuizName = iconOnQuizName;
    }
}
