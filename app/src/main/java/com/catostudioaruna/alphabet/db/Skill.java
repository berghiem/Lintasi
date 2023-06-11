package com.catostudioaruna.alphabet.db;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Skill {
    @PrimaryKey(autoGenerate = true)
    private int id;

    @NonNull
    private String name;

    public Skill(  @NonNull String name) {
        this.name = name;
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
}
