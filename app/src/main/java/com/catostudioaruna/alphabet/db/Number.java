package com.catostudioaruna.alphabet.db;

public class Number {
    private int audio;
    private int numberResourceId;
    private int objectResourceId;
    private String title;

    public Number(int audio, int numberResourceId, int objectResourceId, String title) {
        this.audio = audio;
        this.numberResourceId = numberResourceId;
        this.objectResourceId = objectResourceId;
        this.title = title;
    }

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getNumberResourceId() {
        return numberResourceId;
    }

    public void setNumberResourceId(int numberResourceId) {
        this.numberResourceId = numberResourceId;
    }

    public int getObjectResourceId() {
        return objectResourceId;
    }

    public void setObjectResourceId(int objectResourceId) {
        this.objectResourceId = objectResourceId;
    }
}
