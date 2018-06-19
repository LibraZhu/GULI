package com.libra.guli.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.libra.guli.dao.model.Countdown;

import java.util.ArrayList;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

/**
 * @author Libra
 * @since 2018/6/19
 */
@Dao
public interface CountdownDao {

    @Insert(onConflict = REPLACE)
    void save(Countdown countdown);

    @Delete
    void delete(Countdown countdown);

    @Query("SELECT * FROM countdown")
    ArrayList<Countdown> load();
}
