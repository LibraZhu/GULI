package com.libra.guli.dao.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author Libra
 * @since 2018/6/19
 */
@Entity
public class Schedule {
    @PrimaryKey
    private int id;
    private String content;
    private String time;
    private long timeL;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public long getTimeL() {
        return timeL;
    }

    public void setTimeL(long timeL) {
        this.timeL = timeL;
    }
}
