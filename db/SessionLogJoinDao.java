package com.zxtlfasu.wirdvgyk.db;

import androidx.lifecycle.LiveData;

/* loaded from: classes.dex */
public interface SessionLogJoinDao {
    LiveData<SessionLogJoin> get(long sessionId);
}