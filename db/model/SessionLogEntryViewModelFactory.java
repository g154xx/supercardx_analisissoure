package com.zxtlfasu.wirdvgyk.db.model;

import android.app.Application;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

/* loaded from: classes.dex */
public class SessionLogEntryViewModelFactory extends ViewModelProvider.NewInstanceFactory {
    private final Application mApplication;
    private final long mSessionLog;

    public SessionLogEntryViewModelFactory(Application application, long sessionLog) {
        this.mApplication = application;
        this.mSessionLog = sessionLog;
    }

    @Override // androidx.lifecycle.ViewModelProvider.NewInstanceFactory, androidx.lifecycle.ViewModelProvider.Factory
    public <T extends ViewModel> T create(Class<T> modelClass) {
        return new SessionLogEntryViewModel(this.mApplication, this.mSessionLog);
    }
}