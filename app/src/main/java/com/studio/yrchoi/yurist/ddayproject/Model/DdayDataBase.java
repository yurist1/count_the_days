package com.studio.yrchoi.yurist.ddayproject.Model;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {Dday.class}, version = 1)
public abstract class DdayDataBase extends RoomDatabase {
    private static final String DB_NAME = "d_day.db";
    private static volatile DdayDataBase instance;
    public abstract DdayDAO ddayDAO();

    public static synchronized DdayDataBase getInstance(Context context) {
        if (instance == null) {
            instance = create(context);
        }
        return instance;
    }

    private static DdayDataBase create(final Context context) {
        return Room.databaseBuilder(
                context,
                DdayDataBase.class,
                DB_NAME).build();
    }


}
