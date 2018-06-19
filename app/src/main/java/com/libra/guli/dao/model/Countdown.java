package com.libra.guli.dao.model;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * @author Libra
 * @since 2018/6/19
 */
@Entity
public class Countdown {
    public static final String TYPE_TERM = "节气";
    public static final String TYPE_FESTIVAL = "节日";
    public static final String TYPE_CUSTOM = "纪念日";
    @PrimaryKey
    private int id;
    private String content;
    private String des;
    private String type;
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

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
