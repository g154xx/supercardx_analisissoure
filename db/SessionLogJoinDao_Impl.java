package com.zxtlfasu.wirdvgyk.db;

import android.database.Cursor;
import androidx.collection.LongSparseArray;
import androidx.lifecycle.LiveData;
import androidx.room.RoomDatabase;
import androidx.room.RoomSQLiteQuery;
import androidx.room.util.CursorUtil;
import androidx.room.util.DBUtil;
import androidx.room.util.RelationUtil;
import androidx.room.util.StringUtil;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final class SessionLogJoinDao_Impl implements SessionLogJoinDao {
    private final RoomDatabase __db;

    public SessionLogJoinDao_Impl(final RoomDatabase __db) {
        this.__db = __db;
    }

    @Override // com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao
    public LiveData<SessionLogJoin> get(final long sessionId) {
        final RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire("SELECT id, date, type FROM SessionLog INNER JOIN NfcCommEntry ON SessionLog.id = NfcCommEntry.sessionId WHERE SessionLog.id=? ORDER BY NfcCommEntry.entryId ASC", 1);
        acquire.bindLong(1, sessionId);
        return this.__db.getInvalidationTracker().createLiveData(new String[]{"NfcCommEntry", "SessionLog"}, true, new Callable<SessionLogJoin>() { // from class: com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl.1
            /* JADX WARN: Can't rename method to resolve collision */
            /* JADX WARN: Removed duplicated region for block: B:35:0x009f  */
            /* JADX WARN: Removed duplicated region for block: B:37:0x00aa A[Catch: all -> 0x00d8, TryCatch #0 {all -> 0x00d8, blocks: (B:5:0x0017, B:6:0x001c, B:8:0x0023, B:13:0x0035, B:16:0x003f, B:21:0x002b, B:23:0x004c, B:25:0x005b, B:27:0x0062, B:29:0x0068, B:33:0x0099, B:37:0x00aa, B:38:0x00ba, B:39:0x00b5, B:40:0x00a0, B:41:0x0071, B:44:0x0081, B:45:0x0079, B:46:0x00c2), top: B:4:0x0017, outer: #1 }] */
            /* JADX WARN: Removed duplicated region for block: B:39:0x00b5 A[Catch: all -> 0x00d8, TryCatch #0 {all -> 0x00d8, blocks: (B:5:0x0017, B:6:0x001c, B:8:0x0023, B:13:0x0035, B:16:0x003f, B:21:0x002b, B:23:0x004c, B:25:0x005b, B:27:0x0062, B:29:0x0068, B:33:0x0099, B:37:0x00aa, B:38:0x00ba, B:39:0x00b5, B:40:0x00a0, B:41:0x0071, B:44:0x0081, B:45:0x0079, B:46:0x00c2), top: B:4:0x0017, outer: #1 }] */
            /* JADX WARN: Removed duplicated region for block: B:40:0x00a0 A[Catch: all -> 0x00d8, TryCatch #0 {all -> 0x00d8, blocks: (B:5:0x0017, B:6:0x001c, B:8:0x0023, B:13:0x0035, B:16:0x003f, B:21:0x002b, B:23:0x004c, B:25:0x005b, B:27:0x0062, B:29:0x0068, B:33:0x0099, B:37:0x00aa, B:38:0x00ba, B:39:0x00b5, B:40:0x00a0, B:41:0x0071, B:44:0x0081, B:45:0x0079, B:46:0x00c2), top: B:4:0x0017, outer: #1 }] */
            @Override // java.util.concurrent.Callable
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public com.zxtlfasu.wirdvgyk.db.SessionLogJoin call() throws java.lang.Exception {
                /*
                    r9 = this;
                    com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl r0 = com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl.this
                    androidx.room.RoomDatabase r0 = com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl.access$000(r0)
                    r0.beginTransaction()
                    com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl r0 = com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl.this     // Catch: java.lang.Throwable -> Ldd
                    androidx.room.RoomDatabase r0 = com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl.access$000(r0)     // Catch: java.lang.Throwable -> Ldd
                    androidx.room.RoomSQLiteQuery r1 = r2     // Catch: java.lang.Throwable -> Ldd
                    r2 = 1
                    r3 = 0
                    android.database.Cursor r0 = androidx.room.util.DBUtil.query(r0, r1, r2, r3)     // Catch: java.lang.Throwable -> Ldd
                    androidx.collection.LongSparseArray r1 = new androidx.collection.LongSparseArray     // Catch: java.lang.Throwable -> Ld8
                    r1.<init>()     // Catch: java.lang.Throwable -> Ld8
                L1c:
                    boolean r4 = r0.moveToNext()     // Catch: java.lang.Throwable -> Ld8
                    r5 = 0
                    if (r4 == 0) goto L4c
                    boolean r4 = r0.isNull(r5)     // Catch: java.lang.Throwable -> Ld8
                    if (r4 == 0) goto L2b
                    r4 = r3
                    goto L33
                L2b:
                    long r4 = r0.getLong(r5)     // Catch: java.lang.Throwable -> Ld8
                    java.lang.Long r4 = java.lang.Long.valueOf(r4)     // Catch: java.lang.Throwable -> Ld8
                L33:
                    if (r4 == 0) goto L1c
                    long r5 = r4.longValue()     // Catch: java.lang.Throwable -> Ld8
                    boolean r5 = r1.containsKey(r5)     // Catch: java.lang.Throwable -> Ld8
                    if (r5 != 0) goto L1c
                    long r4 = r4.longValue()     // Catch: java.lang.Throwable -> Ld8
                    java.util.ArrayList r6 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Ld8
                    r6.<init>()     // Catch: java.lang.Throwable -> Ld8
                    r1.put(r4, r6)     // Catch: java.lang.Throwable -> Ld8
                    goto L1c
                L4c:
                    r4 = -1
                    r0.moveToPosition(r4)     // Catch: java.lang.Throwable -> Ld8
                    com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl r4 = com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl.this     // Catch: java.lang.Throwable -> Ld8
                    com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl.access$100(r4, r1)     // Catch: java.lang.Throwable -> Ld8
                    boolean r4 = r0.moveToFirst()     // Catch: java.lang.Throwable -> Ld8
                    if (r4 == 0) goto Lc2
                    boolean r4 = r0.isNull(r5)     // Catch: java.lang.Throwable -> Ld8
                    r6 = 2
                    if (r4 == 0) goto L71
                    boolean r4 = r0.isNull(r2)     // Catch: java.lang.Throwable -> Ld8
                    if (r4 == 0) goto L71
                    boolean r4 = r0.isNull(r6)     // Catch: java.lang.Throwable -> Ld8
                    if (r4 != 0) goto L6f
                    goto L71
                L6f:
                    r6 = r3
                    goto L99
                L71:
                    boolean r4 = r0.isNull(r2)     // Catch: java.lang.Throwable -> Ld8
                    if (r4 == 0) goto L79
                    r2 = r3
                    goto L81
                L79:
                    long r7 = r0.getLong(r2)     // Catch: java.lang.Throwable -> Ld8
                    java.lang.Long r2 = java.lang.Long.valueOf(r7)     // Catch: java.lang.Throwable -> Ld8
                L81:
                    java.util.Date r2 = com.zxtlfasu.wirdvgyk.db.Converters.fromTimestamp(r2)     // Catch: java.lang.Throwable -> Ld8
                    int r4 = r0.getInt(r6)     // Catch: java.lang.Throwable -> Ld8
                    com.zxtlfasu.wirdvgyk.db.SessionLog$SessionType r4 = com.zxtlfasu.wirdvgyk.db.Converters.intToType(r4)     // Catch: java.lang.Throwable -> Ld8
                    com.zxtlfasu.wirdvgyk.db.SessionLog r6 = new com.zxtlfasu.wirdvgyk.db.SessionLog     // Catch: java.lang.Throwable -> Ld8
                    r6.<init>(r2, r4)     // Catch: java.lang.Throwable -> Ld8
                    int r2 = r0.getInt(r5)     // Catch: java.lang.Throwable -> Ld8
                    r6.setId(r2)     // Catch: java.lang.Throwable -> Ld8
                L99:
                    boolean r2 = r0.isNull(r5)     // Catch: java.lang.Throwable -> Ld8
                    if (r2 == 0) goto La0
                    goto La8
                La0:
                    long r2 = r0.getLong(r5)     // Catch: java.lang.Throwable -> Ld8
                    java.lang.Long r3 = java.lang.Long.valueOf(r2)     // Catch: java.lang.Throwable -> Ld8
                La8:
                    if (r3 == 0) goto Lb5
                    long r2 = r3.longValue()     // Catch: java.lang.Throwable -> Ld8
                    java.lang.Object r1 = r1.get(r2)     // Catch: java.lang.Throwable -> Ld8
                    java.util.ArrayList r1 = (java.util.ArrayList) r1     // Catch: java.lang.Throwable -> Ld8
                    goto Lba
                Lb5:
                    java.util.ArrayList r1 = new java.util.ArrayList     // Catch: java.lang.Throwable -> Ld8
                    r1.<init>()     // Catch: java.lang.Throwable -> Ld8
                Lba:
                    com.zxtlfasu.wirdvgyk.db.SessionLogJoin r3 = new com.zxtlfasu.wirdvgyk.db.SessionLogJoin     // Catch: java.lang.Throwable -> Ld8
                    r3.<init>(r6)     // Catch: java.lang.Throwable -> Ld8
                    r3.setNfcCommEntries(r1)     // Catch: java.lang.Throwable -> Ld8
                Lc2:
                    com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl r1 = com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl.this     // Catch: java.lang.Throwable -> Ld8
                    androidx.room.RoomDatabase r1 = com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl.access$000(r1)     // Catch: java.lang.Throwable -> Ld8
                    r1.setTransactionSuccessful()     // Catch: java.lang.Throwable -> Ld8
                    r0.close()     // Catch: java.lang.Throwable -> Ldd
                    com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl r0 = com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl.this
                    androidx.room.RoomDatabase r0 = com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl.access$000(r0)
                    r0.endTransaction()
                    return r3
                Ld8:
                    r1 = move-exception
                    r0.close()     // Catch: java.lang.Throwable -> Ldd
                    throw r1     // Catch: java.lang.Throwable -> Ldd
                Ldd:
                    r0 = move-exception
                    com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl r1 = com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl.this
                    androidx.room.RoomDatabase r1 = com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl.access$000(r1)
                    r1.endTransaction()
                    throw r0
                */
                throw new UnsupportedOperationException("Method not decompiled: com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl.AnonymousClass1.call():com.zxtlfasu.wirdvgyk.db.SessionLogJoin");
            }

            protected void finalize() {
                acquire.release();
            }
        });
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void __fetchRelationshipNfcCommEntryAscomZxtlfasuWirdvgykDbNfcCommEntry(final LongSparseArray<ArrayList<NfcCommEntry>> _map) {
        if (_map.isEmpty()) {
            return;
        }
        if (_map.size() > 999) {
            RelationUtil.recursiveFetchLongSparseArray(_map, true, new Function1() { // from class: com.zxtlfasu.wirdvgyk.db.SessionLogJoinDao_Impl$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    return SessionLogJoinDao_Impl.this.m164xa50ff1f6((LongSparseArray) obj);
                }
            });
            return;
        }
        StringBuilder newStringBuilder = StringUtil.newStringBuilder();
        newStringBuilder.append("SELECT `entryId`,`nfcComm`,`sessionId` FROM `NfcCommEntry` WHERE `sessionId` IN (");
        int size = _map.size();
        StringUtil.appendPlaceholders(newStringBuilder, size);
        newStringBuilder.append(")");
        RoomSQLiteQuery acquire = RoomSQLiteQuery.acquire(newStringBuilder.toString(), size);
        int i = 1;
        for (int i2 = 0; i2 < _map.size(); i2++) {
            acquire.bindLong(i, _map.keyAt(i2));
            i++;
        }
        Cursor query = DBUtil.query(this.__db, acquire, false, null);
        try {
            int columnIndex = CursorUtil.getColumnIndex(query, "sessionId");
            if (columnIndex == -1) {
                return;
            }
            while (query.moveToNext()) {
                ArrayList<NfcCommEntry> arrayList = _map.get(query.getLong(columnIndex));
                if (arrayList != null) {
                    NfcCommEntry nfcCommEntry = new NfcCommEntry(Converters.fromBytearray(query.isNull(1) ? null : query.getBlob(1)), query.getLong(2));
                    nfcCommEntry.setEntryId(query.getInt(0));
                    arrayList.add(nfcCommEntry);
                }
            }
        } finally {
            query.close();
        }
    }

    /* renamed from: lambda$__fetchRelationshipNfcCommEntryAscomZxtlfasuWirdvgykDbNfcCommEntry$0$com-zxtlfasu-wirdvgyk-db-SessionLogJoinDao_Impl, reason: not valid java name */
    /* synthetic */ Unit m164xa50ff1f6(LongSparseArray longSparseArray) {
        __fetchRelationshipNfcCommEntryAscomZxtlfasuWirdvgykDbNfcCommEntry(longSparseArray);
        return Unit.INSTANCE;
    }
}