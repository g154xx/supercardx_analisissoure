package com.zxtlfasu.wirdvgyk.db.model;

import android.app.Application;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.zxtlfasu.wirdvgyk.db.AppDatabase;
import com.zxtlfasu.wirdvgyk.db.SessionLog;
import java.util.List;

/* loaded from: classes.dex */
public class SessionLogViewModel extends AndroidViewModel {
    private final LiveData<List<SessionLog>> mSessionLog;

    public LiveData<List<SessionLog>> getSessionLogs() {
        return this.mSessionLog;
    }

    public SessionLogViewModel(Application application) {
        super(application);
        this.mSessionLog = AppDatabase.getDatabase(application).sessionLogDao().getAll();
    }
}