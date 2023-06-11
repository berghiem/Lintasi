package com.catostudioaruna.alphabet.db;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

@Dao
public interface StickerDao {
    @Insert
    public void insertSticker(Sticker sticker);

    @Insert
    public void insertStickers(Sticker... stickers);

    @Update
    public int updateSticker(Sticker sticker);

    @Delete
    public void deleteSticker(Sticker sticker);

    @Query("DELETE FROM STICKER")
    public void deleteStickers();


    @Query("SELECT * FROM sticker WHERE id= :id")
    LiveData<Sticker> getSticker(int id);

    @Query("SELECT * FROM sticker WHERE isCollected = 0 ORDER BY id ASC LIMIT 1")
    LiveData<Sticker> getReward();


    @Query("SELECT * FROM sticker WHERE isCollected = 1")
    LiveData<List<Sticker>> getCollectedSticker();


    @Query("SELECT * FROM sticker WHERE isCollected = 0")
    List<Sticker> getUncollectedSticker();


    @Query("SELECT * FROM sticker")
    LiveData<List<Sticker>> getAllSticker();

    @Query("SELECT COUNT(id) FROM sticker")
    LiveData<Integer> getCountAllSticker();

}
