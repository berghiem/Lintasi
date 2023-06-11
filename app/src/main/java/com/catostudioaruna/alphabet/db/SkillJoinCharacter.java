package com.catostudioaruna.alphabet.db;

import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;


@Entity(tableName = "skill_join_character",
        primaryKeys = { "skillId", "characterId" },
        foreignKeys = {
                @ForeignKey(entity = Skill.class,
                        parentColumns = "id",
                        childColumns = "skillId"),
                @ForeignKey(entity = Character.class,
                        parentColumns = "id",
                        childColumns = "characterId")
        },
        indices = {@Index("characterId")})
public class SkillJoinCharacter {

    public final int skillId;
    public final int characterId;
    public final int value;

    public SkillJoinCharacter(final int skillId, final int characterId , final  int value) {
         this.skillId = skillId;
         this.characterId = characterId;
         this.value = value;
    }

    public int getSkillId() {
        return skillId;
    }

    public int getCharacterId() {
        return characterId;
    }

    public int getValue() {
        return value;
    }
}
