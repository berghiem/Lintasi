package com.catostudioaruna.alphabet.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;


@Entity(tableName = "level_join_badge",
        primaryKeys = { "levelId", "badgeId" },
        foreignKeys = {
                @ForeignKey(entity = Badge.class,
                        parentColumns = "id",
                        childColumns = "badgeId"),
                @ForeignKey(entity = Level.class,
                        parentColumns = "id",
                        childColumns = "levelId")
        },
        indices = {@Index("levelId")})
public class BadgeJoinLevel {

    public final int badgeId;
    public final int levelId;

    public BadgeJoinLevel(final int badgeId, final int levelId ) {
        this.badgeId = badgeId;
        this.levelId = levelId;
    }

    public int getBadgeId() {
        return badgeId;
    }

    public int getLevelId() {
        return levelId;
    }
}
