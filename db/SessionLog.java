package com.zxtlfasu.wirdvgyk.db;

import java.text.SimpleDateFormat;
import java.util.Date;

/* loaded from: classes.dex */
public class SessionLog {
    private Date date;
    private int id;
    private SessionType type;

    public enum SessionType {
        RELAY,
        REPLAY,
        CAPTURE
    }

    public Date getDate() {
        return this.date;
    }

    public int getId() {
        return this.id;
    }

    public SessionType getType() {
        return this.type;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setType(SessionType type) {
        this.type = type;
    }

    public SessionLog(Date date, SessionType type) {
        this.date = date;
        this.type = type;
    }

    public static SimpleDateFormat isoDateFormatter() {
        return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");
    }

    public String toString() {
        return isoDateFormatter().format(this.date);
    }
}