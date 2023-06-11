package com.catostudioaruna.alphabet.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface SkillDao {
    @Insert
    public void insertSkill(Skill s);

    @Insert
    public void insertSkills(Skill... s);

    @Update
    public int updateSkill(Skill s);

    @Delete
    public void deleteSkill(Skill s);

    @Query("DELETE FROM SKILL")
    public void deleteSkills();


    @Query("SELECT * FROM Skill WHERE id= :id")
    LiveData<Skill> getSkill(int id);



    @Query("SELECT * FROM SKILL")
    LiveData<List<Skill>> getAllSkill();

    @Query("SELECT COUNT(id) FROM SKILL")
    LiveData<Integer> getCountAllSkill();

}
