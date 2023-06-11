package com.catostudioaruna.alphabet.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;

@Dao
public interface BadgeJoinLevelDao {
    @Insert
    void insert(BadgeJoinLevel badgeJoinLevel);

    @Query("SELECT id, description, iconSourceName FROM badge INNER JOIN level_join_badge ON badge.id=level_join_badge.badgeId WHERE  level_join_badge.levelId=:levelId")
    List<Badge> getSkillForCharacter(final int levelId);


}
