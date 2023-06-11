package com.catostudioaruna.alphabet.db;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Character {
    @PrimaryKey(autoGenerate = false)
    private int id;

    @NonNull
    private String name;

    @NonNull
    private String resourceName;

    @NonNull
    private int resourceId;

    @NonNull
    private String iconResourceName;

    @Ignore
    private List<SkillJoinCharacter> skillJoinCharacter;

    @Ignore
    private List<Skill> skills;


    public Character( int id, @NonNull String name, @NonNull String resourceName, @NonNull String iconResourceName, @NonNull int resourceId) {
        this.id = id;
        this.name = name;
        this.resourceName = resourceName;
        this.iconResourceName = iconResourceName;
        this.resourceId = resourceId;
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

    @NonNull
    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(@NonNull String resourceName) {
        this.resourceName = resourceName;
    }

    public List<SkillJoinCharacter> getSkillJoinCharacter() {
        return skillJoinCharacter;
    }

    public void setSkillJoinCharacter(List<SkillJoinCharacter> skillJoinCharacter) {
        this.skillJoinCharacter = skillJoinCharacter;
    }

    public List<Skill> getSkills() {
        return skills;
    }

    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }

    @NonNull
    public String getIconResourceName() {
        return iconResourceName;
    }

    public void setIconResourceName(@NonNull String iconResourceName) {
        this.iconResourceName = iconResourceName;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }
}
