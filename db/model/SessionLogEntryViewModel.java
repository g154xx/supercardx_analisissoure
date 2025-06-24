package com.zxtlfasu.wirdvgyk.db.model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.zxtlfasu.wirdvgyk.db.AppDatabase;
import com.zxtlfasu.wirdvgyk.db.SessionLogJoin;

/* loaded from: classes.dex */
public class SessionLogEntryViewModel extends AndroidViewModel {
    private final LiveData<SessionLogJoin> mSession;

    public LiveData<SessionLogJoin> getSession() {
        return this.mSession;
    }

    public SessionLogEntryViewModel(Application application, long sessionid) {
        super(application);
        this.mSession = AppDatabase.getDatabase(application).sessionLogJoinDao().get(sessionid);
    }
}