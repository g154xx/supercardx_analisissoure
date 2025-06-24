package com.zxtlfasu.wirdvgyk.db;

import android.database.Cursor;
import androidx.lifecycle.LiveData;
import androidx.room.EntityDeletionOrUpdateAdapter;
import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;

/* loaded from: classes.dex */
public final class TagInfoDao_Impl implements TagInfoDao {
    private final RoomDatabase __db;
    private final EntityDeletionOrUpdateAdapter<TagInfo> __deletionAdapterOfTagInfo;
    private final EntityInsertionAdapter<TagInfo> __insertionAdapterOfTagInfo;

    public TagInfoDao_Impl(final RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfTagInfo = new EntityInsertionAdapter<TagInfo>(__db) { // from class: com.zxtlfasu.wirdvgyk.db.TagInfoDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            protected String createQuery() {
                return "INSERT OR ABORT INTO `TagInfo` (`id`,`name`,`data`) VALUES (nullif(?, 0),?,?)";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityInsertionAdapter
            public void bind(final SupportSQLiteStatement statement, final TagInfo entity) {
                statement.bindLong(1, entity.getId());
                if (entity.getName() == null) {
                    statement.bindNull(2);
                } else {
                    statement.bindString(2, entity.getName());
                }
                if (entity.getData() == null) {
                    statement.bindNull(3);
                } else {
                    statement.bindBlob(3, entity.getData());
                }
            }
        };
        this.__deletionAdapterOfTagInfo = new EntityDeletionOrUpdateAdapter<TagInfo>(__db) { // from class: com.zxtlfasu.wirdvgyk.db.TagInfoDao_Impl.2
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            protected String createQuery() {
                return "DELETE FROM `TagInfo` WHERE `id` = ?";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(final SupportSQLiteStatement statement, final TagInfo entity) {
                statement.bindLong(1, entity.getId());
            }
        };
    }

    @Override // com.zxtlfasu.wirdvgyk.db.TagInfoDao
    public void insert(final TagInfo tagInfo) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfTagInfo.insert((EntityInsertionAdapter<TagInfo>) tagInfo);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.zxtlfasu.wirdvgyk.db.TagInfoDao
    public void delete(final TagInfo tagInfo) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__deletionAdapterOfTagInfo.handle(tagInfo);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.zxtlfasu.wirdvgyk.db.TagInfoDao
    public LiveData<List<TagInfo>> getAll() {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM TagInfo ORDER BY Name ASC", 0);
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"TagInfo"}, false, new Callable<List<TagInfo>>() { // from class: com.zxtlfasu.wirdvgyk.db.TagInfoDao_Impl.3
            @Override // java.util.concurrent.Callable
            public List<TagInfo> call() throws Exception {
                Cursor query = DBUtil.query(TagInfoDao_Impl.this.__db, acquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "name");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "data");
                    ArrayList arrayList = new ArrayList(query.getCount());
                    while (query.moveToNext()) {
                        TagInfo tagInfo = new TagInfo(query.isNull(columnIndexOrThrow2) ? null : query.getString(columnIndexOrThrow2), query.isNull(columnIndexOrThrow3) ? null : query.getBlob(columnIndexOrThrow3));
                        tagInfo.setId(query.getInt(columnIndexOrThrow));
                        arrayList.add(tagInfo);
                    }
                    return arrayList;
                } finally {
                    query.close();
                }
            }

            protected void finalize() {
                acquire.release();
            }
        });
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}