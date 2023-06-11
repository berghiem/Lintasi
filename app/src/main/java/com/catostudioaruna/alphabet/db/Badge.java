package com.catostudioaruna.alphabet.db;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Badge {
    @PrimaryKey(autoGenerate = false)
    private int id;
    private String iconSourceName;
    private String giftSourceNameGirl;
    private String giftSourceNameBoy;

    private String description;
    private boolean isAchieved;
    private int levelId;
    private int sourceIdBoy;
    private int sourceIdGirl;

    public Badge(int id, String iconSourceName, String giftSourceNameGirl,String giftSourceNameBoy,
                 String description, boolean isAchieved, int levelId, int sourceIdBoy, int sourceIdGirl) {
        this.id = id;
        this.iconSourceName = iconSourceName;
        this.giftSourceNameGirl = giftSourceNameGirl;
        this.giftSourceNameBoy = giftSourceNameBoy;
        this.description = description;
        this.isAchieved = isAchieved;
        this.levelId = levelId;
        this.sourceIdBoy = sourceIdBoy ;
        this.sourceIdGirl = sourceIdGirl;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIconSourceName() {
        return iconSourceName;
    }

    public void setIconSourceName(String iconSourceName) {
        this.iconSourceName = iconSourceName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isAchieved() {
        return isAchieved;
    }

    public void setAchieved(boolean achieved) {
        isAchieved = achieved;
    }

    public int getLevelId() {
        return levelId;
    }

    public void setLevelId(int levelId) {
        this.levelId = levelId;
    }

    public String getGiftSourceNameGirl() {
        return giftSourceNameGirl;
    }

    public void setGiftSourceNameGirl(String giftSourceNameGirl) {
        this.giftSourceNameGirl = giftSourceNameGirl;
    }

    public String getGiftSourceNameBoy() {
        return giftSourceNameBoy;
    }

    public void setGiftSourceNameBoy(String giftSourceNameBoy) {
        this.giftSourceNameBoy = giftSourceNameBoy;
    }

    public int getSourceIdBoy() {
        return sourceIdBoy;
    }

    public void setSourceIdBoy(int sourceIdBoy) {
        this.sourceIdBoy = sourceIdBoy;
    }

    public int getSourceIdGirl() {
        return sourceIdGirl;
    }

    public void setSourceIdGirl(int sourceIdGirl) {
        this.sourceIdGirl = sourceIdGirl;
    }
}
