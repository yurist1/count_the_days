package com.studio.yrchoi.yurist.ddayproject.Model;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

@Dao
public interface DdayDAO {

    @Query("SELECT * FROM d_day")
    LiveData<List<Dday>> getAllDday();

    @Insert
    void insertDday(Dday... d_day);

    @Update
    void updateDday(Dday... d_day);

    @Delete
    void deleteDday(Dday... d_day);
}
