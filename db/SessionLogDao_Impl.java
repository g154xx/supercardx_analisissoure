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
public final class SessionLogDao_Impl implements SessionLogDao {
    private final RoomDatabase __db;
    private final EntityDeletionOrUpdateAdapter<SessionLog> __deletionAdapterOfSessionLog;
    private final EntityInsertionAdapter<SessionLog> __insertionAdapterOfSessionLog;

    public SessionLogDao_Impl(final RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfSessionLog = new EntityInsertionAdapter<SessionLog>(__db) { // from class: com.zxtlfasu.wirdvgyk.db.SessionLogDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            protected String createQuery() {
                return "INSERT OR ABORT INTO `SessionLog` (`id`,`date`,`type`) VALUES (nullif(?, 0),?,?)";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityInsertionAdapter
            public void bind(final SupportSQLiteStatement statement, final SessionLog entity) {
                statement.bindLong(1, entity.getId());
                Long dateToTimestamp = Converters.dateToTimestamp(entity.getDate());
                if (dateToTimestamp == null) {
                    statement.bindNull(2);
                } else {
                    statement.bindLong(2, dateToTimestamp.longValue());
                }
                statement.bindLong(3, Converters.typeToInt(entity.getType()));
            }
        };
        this.__deletionAdapterOfSessionLog = new EntityDeletionOrUpdateAdapter<SessionLog>(__db) { // from class: com.zxtlfasu.wirdvgyk.db.SessionLogDao_Impl.2
            @Override // androidx.room.EntityDeletionOrUpdateAdapter, androidx.room.SharedSQLiteStatement
            protected String createQuery() {
                return "DELETE FROM `SessionLog` WHERE `id` = ?";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityDeletionOrUpdateAdapter
            public void bind(final SupportSQLiteStatement statement, final SessionLog entity) {
                statement.bindLong(1, entity.getId());
            }
        };
    }

    @Override // com.zxtlfasu.wirdvgyk.db.SessionLogDao
    public long insert(final SessionLog log) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            long insertAndReturnId = this.__insertionAdapterOfSessionLog.insertAndReturnId(log);
            this.__db.setTransactionSuccessful();
            return insertAndReturnId;
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.zxtlfasu.wirdvgyk.db.SessionLogDao
    public void delete(final SessionLog log) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__deletionAdapterOfSessionLog.handle(log);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    @Override // com.zxtlfasu.wirdvgyk.db.SessionLogDao
    public LiveData<List<SessionLog>> getAll() {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT * FROM SessionLog ORDER BY Date DESC", 0);
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"SessionLog"}, false, new Callable<List<SessionLog>>() { // from class: com.zxtlfasu.wirdvgyk.db.SessionLogDao_Impl.3
            @Override // java.util.concurrent.Callable
            public List<SessionLog> call() throws Exception {
                Cursor query = DBUtil.query(SessionLogDao_Impl.this.__db, acquire, false, null);
                try {
                    int columnIndexOrThrow = CursorUtil.getColumnIndexOrThrow(query, "id");
                    int columnIndexOrThrow2 = CursorUtil.getColumnIndexOrThrow(query, "date");
                    int columnIndexOrThrow3 = CursorUtil.getColumnIndexOrThrow(query, "type");
                    ArrayList arrayList = new ArrayList(query.getCount());
                    while (query.moveToNext()) {
                        SessionLog sessionLog = new SessionLog(Converters.fromTimestamp(query.isNull(columnIndexOrThrow2) ? null : Long.valueOf(query.getLong(columnIndexOrThrow2))), Converters.intToType(query.getInt(columnIndexOrThrow3)));
                        sessionLog.setId(query.getInt(columnIndexOrThrow));
                        arrayList.add(sessionLog);
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