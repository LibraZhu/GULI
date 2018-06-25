package com.libra.guli.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.libra.guli.dao.model.Countdown;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Flowable;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * @author Libra
 * @since 2018/6/19
 */
@Dao
public interface CountdownDao {

    @Insert(onConflict = REPLACE)
    void save(Countdown countdown);

    @Insert(onConflict = REPLACE)
    void save(List<Countdown> list);

    @Delete
    void delete(Countdown countdown);

    @Query("SELECT * FROM countdown")
    Flowable<List<Countdown>> load();

    @Query("SELECT * FROM countdown where show = 1")
    Flowable<List<Countdown>> loadShow();

    @Query("SELECT * FROM countdown where timeL >= :time")
    List<Countdown> loadRecent(long time);
}
