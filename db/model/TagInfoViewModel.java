package com.zxtlfasu.wirdvgyk.db.model;

import android.app.Application;
import android.os.AsyncTask;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import com.zxtlfasu.wirdvgyk.db.AppDatabase;
import com.zxtlfasu.wirdvgyk.db.TagInfo;
import com.zxtlfasu.wirdvgyk.db.TagInfoDao;
import java.util.List;

/* loaded from: classes.dex */
public class TagInfoViewModel extends AndroidViewModel {
    private final AppDatabase mAppDb;
    private final LiveData<List<TagInfo>> mTagInfos;

    public LiveData<List<TagInfo>> getTagInfos() {
        return this.mTagInfos;
    }

    public TagInfoViewModel(Application application) {
        super(application);
        AppDatabase database = AppDatabase.getDatabase(application);
        this.mAppDb = database;
        this.mTagInfos = database.tagInfoDao().getAll();
    }

    public void insert(TagInfo tagInfo) {
        new insertAsyncTask(this.mAppDb.tagInfoDao()).execute(tagInfo);
    }

    public void delete(TagInfo tagInfo) {
        new deleteAsyncTask(this.mAppDb.tagInfoDao()).execute(tagInfo);
    }

    private static class insertAsyncTask extends AsyncTask<TagInfo, Void, Void> {
        private final TagInfoDao mAsyncTaskDao;

        insertAsyncTask(TagInfoDao dao) {
            this.mAsyncTaskDao = dao;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(final TagInfo... params) {
            this.mAsyncTaskDao.insert(params[0]);
            return null;
        }
    }

    private static class deleteAsyncTask extends AsyncTask<TagInfo, Void, Void> {
        private final TagInfoDao mAsyncTaskDao;

        deleteAsyncTask(TagInfoDao dao) {
            this.mAsyncTaskDao = dao;
        }

        /* JADX INFO: Access modifiers changed from: protected */
        @Override // android.os.AsyncTask
        public Void doInBackground(final TagInfo... params) {
            this.mAsyncTaskDao.delete(params[0]);
            return null;
        }
    }
}