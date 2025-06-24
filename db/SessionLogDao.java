package com.zxtlfasu.wirdvgyk.db;

import androidx.lifecycle.LiveData;
import java.util.List;

/* loaded from: classes.dex */
public interface SessionLogDao {
    void delete(SessionLog log);

    LiveData<List<SessionLog>> getAll();

    long insert(SessionLog log);
}