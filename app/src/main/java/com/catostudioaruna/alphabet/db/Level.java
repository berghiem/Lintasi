package com.catostudioaruna.alphabet.db;

import java.util.List;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Level {
    @PrimaryKey (autoGenerate = true)
    private int id;
    private String name;
    private String sourceNameGirl;
    private String sourceNameBoy;
    private int sourceIdGirl;
    private int sourceIdBoy;

    @Ignore
    private List<Badge> badges;
    @Ignore
    private List<BadgeJoinLevel> badgeJoinLevels;
    private int status; //[not yet, on progress, completed]


    public Level(String name, String sourceNameGirl, String sourceNameBoy, int status , int sourceIdGirl ,int sourceIdBoy) {
        this.name = name;
        this.sourceNameGirl = sourceNameGirl;
        this.sourceNameBoy = sourceNameBoy;
        this.status = status;
        this.sourceIdGirl = sourceIdGirl;
        this.sourceIdBoy = sourceIdBoy;
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


    public List<Badge> getBadges() {
        return badges;
    }

    public void setBadges(List<Badge> badges) {
        this.badges = badges;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getSourceNameGirl() {
        return sourceNameGirl;
    }

    public void setSourceNameGirl(String sourceNameGirl) {
        this.sourceNameGirl = sourceNameGirl;
    }

    public String getSourceNameBoy() {
        return sourceNameBoy;
    }

    public void setSourceNameBoy(String sourceNameBoy) {
        this.sourceNameBoy = sourceNameBoy;
    }

    public int getSourceIdGirl() {
        return sourceIdGirl;
    }

    public void setSourceIdGirl(int sourceIdGirl) {
        this.sourceIdGirl = sourceIdGirl;
    }

    public int getSourceIdBoy() {
        return sourceIdBoy;
    }

    public void setSourceIdBoy(int sourceIdBoy) {
        this.sourceIdBoy = sourceIdBoy;
    }
}
