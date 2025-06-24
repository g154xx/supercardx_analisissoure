package com.zxtlfasu.wirdvgyk.db;

import androidx.room.EntityInsertionAdapter;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteStatement;
import java.util.Collections;
import java.util.List;

/* loaded from: classes.dex */
public final class NfcCommEntryDao_Impl implements NfcCommEntryDao {
    private final RoomDatabase __db;
    private final EntityInsertionAdapter<NfcCommEntry> __insertionAdapterOfNfcCommEntry;

    public NfcCommEntryDao_Impl(final RoomDatabase __db) {
        this.__db = __db;
        this.__insertionAdapterOfNfcCommEntry = new EntityInsertionAdapter<NfcCommEntry>(__db) { // from class: com.zxtlfasu.wirdvgyk.db.NfcCommEntryDao_Impl.1
            @Override // androidx.room.SharedSQLiteStatement
            protected String createQuery() {
                return "INSERT OR ABORT INTO `NfcCommEntry` (`entryId`,`nfcComm`,`sessionId`) VALUES (nullif(?, 0),?,?)";
            }

            /* JADX INFO: Access modifiers changed from: protected */
            @Override // androidx.room.EntityInsertionAdapter
            public void bind(final SupportSQLiteStatement statement, final NfcCommEntry entity) {
                statement.bindLong(1, entity.getEntryId());
                byte[] NfcCommToBytearray = Converters.NfcCommToBytearray(entity.getNfcComm());
                if (NfcCommToBytearray == null) {
                    statement.bindNull(2);
                } else {
                    statement.bindBlob(2, NfcCommToBytearray);
                }
                statement.bindLong(3, entity.getSessionId());
            }
        };
    }

    @Override // com.zxtlfasu.wirdvgyk.db.NfcCommEntryDao
    public void insert(final NfcCommEntry log) {
        this.__db.assertNotSuspendingTransaction();
        this.__db.beginTransaction();
        try {
            this.__insertionAdapterOfNfcCommEntry.insert((EntityInsertionAdapter<NfcCommEntry>) log);
            this.__db.setTransactionSuccessful();
        } finally {
            this.__db.endTransaction();
        }
    }

    public static List<Class<?>> getRequiredConverters() {
        return Collections.emptyList();
    }
}