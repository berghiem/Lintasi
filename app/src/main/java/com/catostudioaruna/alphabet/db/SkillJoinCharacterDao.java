package com.catostudioaruna.alphabet.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.RoomWarnings;

@Dao
public interface SkillJoinCharacterDao {
    @Insert
    void insert(SkillJoinCharacter skillJoinCharacter);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT id, name FROM skill INNER JOIN skill_join_character ON skill.id=skill_join_character.skillId WHERE  skill_join_character.characterId=:characterId")
    List<Skill> getSkillForCharacter(final int characterId);

    @SuppressWarnings(RoomWarnings.CURSOR_MISMATCH)
    @Query("SELECT id, name, iconResourceName, resourceName,resourceId FROM character INNER JOIN skill_join_character ON  character.id = skill_join_character.characterId WHERE\n" +
            "            skill_join_character.skillId =:skillId")
    List<Character> getRepositoriesForUsers(final int skillId);

    @Query("SELECT * FROM skill_join_character WHERE   characterId=:characterId ORDER BY skillId")
    List<SkillJoinCharacter> getSkillByCharacter(final int characterId);


    @Query("SELECT * FROM skill_join_character ")
    LiveData<List<SkillJoinCharacter>> getAllSkillByCharacter();

}


