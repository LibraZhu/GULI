package com.libra.guli.dao;

import android.arch.persistence.room.RoomDatabase;

import com.libra.guli.dao.model.Countdown;
import com.libra.guli.dao.model.Schedule;

/**
 * @author Libra
 * @since 2018/6/19
 */
@android.arch.persistence.room.Database(entities = {Schedule.class, Countdown.class}, version = 1)
public abstract class GLDatabase extends RoomDatabase {

    public abstract ScheduleDao scheduleDao();

    public abstract CountdownDao countdownDao();

}
