package com.catostudioaruna.alphabet.db;

public class Letter {
    private Integer audio;
    private String motion;
    private String title;

    public Letter(Integer audio, String motion, String title) {
        this.audio = audio;
        this.motion = motion;
        this.title = title;
    }

    public Integer getAudio() {
        return audio;
    }

    public void setAudio(Integer audio) {
        this.audio = audio;
    }

    public String getMotion() {
        return motion;
    }

    public void setMotion(String motion) {
        this.motion = motion;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
