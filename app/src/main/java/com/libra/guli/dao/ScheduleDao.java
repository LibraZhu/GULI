package com.libra.guli.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.libra.guli.dao.model.Schedule;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * @author Libra
 * @since 2018/6/19
 */
@Dao
public interface ScheduleDao {

    @Insert(onConflict = REPLACE)
    void save(Schedule schedule);

    @Delete
    void delete(Schedule schedule);

    @Query("SELECT * FROM schedule")
    Flowable<List<Schedule>> load();
}
