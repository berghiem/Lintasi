package com.catostudioaruna.alphabet.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface BadgeDao {
    @Insert
    public void insertBadge(Badge badge);

    @Insert
    public void insertBadges(Badge... badges);

    @Insert
    public void insertBadges(List<Badge> badges);

    @Update
    public int updateBadge(Badge badge);

    @Update
    public int updateBadges(List<Badge> badges);

    @Delete
    public void deleteBadge(Badge badge);

    @Query("DELETE FROM Badge")
    public void deleteBadges();

    @Query("SELECT * FROM Badge WHERE id= :id")
    LiveData<Badge> getBadge(int id);



    @Query("SELECT * FROM Badge ")
    LiveData<List<Badge>> getAllBadge();

    @Query("SELECT * FROM Badge WHERE levelId= :levelId ")
    LiveData<List<Badge>> getBadgeByLevelId(int levelId);

    @Query("SELECT * FROM Badge WHERE levelId= :levelId AND  isAchieved = 0 ORDER BY ID")
    LiveData<List<Badge>> getUnlockedBadgeByLevelId(int levelId);

    @Query("SELECT COUNT (ID) FROM Badge")
    public int count();

    @Query("SELECT COUNT (ID) FROM Badge WHERE isAchieved = 1")
    public int countIsAchieved();

    @Query("SELECT ID FROM Badge ")
    LiveData<List<Integer>> getAllId();

    @Query("SELECT * FROM Badge WHERE isAchieved = 1")
    LiveData<List<Badge>> getAllAchievedBadge();

    @Query("SELECT COUNT(ID) FROM Badge WHERE isAchieved = 1")
    LiveData<Integer>  countGetAllAchievedBadge();

    @Query("UPDATE Badge SET isAchieved = 0")
    public int resetBadgeStatus();

}
