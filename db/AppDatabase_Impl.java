package com.zxtlfasu.wirdvgyk.db;

import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.RoomDatabase;
import androidx.room.RoomMasterTable;
import androidx.room.RoomOpenHelper;
import androidx.room.migration.AutoMigrationSpec;
import androidx.room.migration.Migration;
import androidx.room.util.DBUtil;
import androidx.room.util.TableInfo;
import androidx.sqlite.db.SupportSQLiteDatabase;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public final class AppDatabase_Impl extends AppDatabase {
    private volatile NfcCommEntryDao _nfcCommEntryDao;
    private volatile SessionLogDao _sessionLogDao;
    private volatile SessionLogJoinDao _sessionLogJoinDao;
    private volatile TagInfoDao _tagInfoDao;

    @Override // androidx.room.RoomDatabase
    protected SupportSQLiteOpenHelper createOpenHelper(final DatabaseConfiguration config) {
        return config.sqliteOpenHelperFactory.create(SupportSQLiteOpenHelper.Configuration.builder(config.context).name(config.name).callback(new RoomOpenHelper(config, new RoomOpenHelper.Delegate(2) { // from class: com.zxtlfasu.wirdvgyk.db.AppDatabase_Impl.1
            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPostMigrate(final SupportSQLiteDatabase db) {
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void createAllTables(final SupportSQLiteDatabase db) {
                db.execSQL("CREATE TABLE IF NOT EXISTS `TagInfo` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `name` TEXT, `data` BLOB)");
                db.execSQL("CREATE TABLE IF NOT EXISTS `SessionLog` (`id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `date` INTEGER, `type` INTEGER)");
                db.execSQL("CREATE TABLE IF NOT EXISTS `NfcCommEntry` (`entryId` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, `nfcComm` BLOB, `sessionId` INTEGER NOT NULL, FOREIGN KEY(`sessionId`) REFERENCES `SessionLog`(`id`) ON UPDATE NO ACTION ON DELETE CASCADE )");
                db.execSQL("CREATE INDEX IF NOT EXISTS `index_NfcCommEntry_sessionId` ON `NfcCommEntry` (`sessionId`)");
                db.execSQL(RoomMasterTable.CREATE_QUERY);
                db.execSQL("INSERT OR REPLACE INTO room_master_table (id,identity_hash) VALUES(42, '59ebfcb9a4cb945ecc5cf04b9a6cce1a')");
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void dropAllTables(final SupportSQLiteDatabase db) {
                db.execSQL("DROP TABLE IF EXISTS `TagInfo`");
                db.execSQL("DROP TABLE IF EXISTS `SessionLog`");
                db.execSQL("DROP TABLE IF EXISTS `NfcCommEntry`");
                List list = AppDatabase_Impl.this.mCallbacks;
                if (list != null) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        ((RoomDatabase.Callback) it.next()).onDestructiveMigration(db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onCreate(final SupportSQLiteDatabase db) {
                List list = AppDatabase_Impl.this.mCallbacks;
                if (list != null) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        ((RoomDatabase.Callback) it.next()).onCreate(db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onOpen(final SupportSQLiteDatabase db) {
                AppDatabase_Impl.this.mDatabase = db;
                db.execSQL("PRAGMA foreign_keys = ON");
                AppDatabase_Impl.this.internalInitInvalidationTracker(db);
                List list = AppDatabase_Impl.this.mCallbacks;
                if (list != null) {
                    Iterator it = list.iterator();
                    while (it.hasNext()) {
                        ((RoomDatabase.Callback) it.next()).onOpen(db);
                    }
                }
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public void onPreMigrate(final SupportSQLiteDatabase db) {
                DBUtil.dropFtsSyncTriggers(db);
            }

            @Override // androidx.room.RoomOpenHelper.Delegate
            public RoomOpenHelper.ValidationResult onValidateSchema(final SupportSQLiteDatabase db) {
                HashMap hashMap = new HashMap(3);
                hashMap.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
                hashMap.put("name", new TableInfo.Column("name", "TEXT", false, 0, null, 1));
                hashMap.put("data", new TableInfo.Column("data", "BLOB", false, 0, null, 1));
                TableInfo tableInfo = new TableInfo("TagInfo", hashMap, new HashSet(0), new HashSet(0));
                TableInfo read = TableInfo.read(db, "TagInfo");
                if (!tableInfo.equals(read)) {
                    return new RoomOpenHelper.ValidationResult(false, "TagInfo(com.zxtlfasu.wirdvgyk.db.TagInfo).\n Expected:\n" + tableInfo + "\n Found:\n" + read);
                }
                HashMap hashMap2 = new HashMap(3);
                hashMap2.put("id", new TableInfo.Column("id", "INTEGER", true, 1, null, 1));
                hashMap2.put("date", new TableInfo.Column("date", "INTEGER", false, 0, null, 1));
                hashMap2.put("type", new TableInfo.Column("type", "INTEGER", false, 0, null, 1));
                TableInfo tableInfo2 = new TableInfo("SessionLog", hashMap2, new HashSet(0), new HashSet(0));
                TableInfo read2 = TableInfo.read(db, "SessionLog");
                if (!tableInfo2.equals(read2)) {
                    return new RoomOpenHelper.ValidationResult(false, "SessionLog(com.zxtlfasu.wirdvgyk.db.SessionLog).\n Expected:\n" + tableInfo2 + "\n Found:\n" + read2);
                }
                HashMap hashMap3 = new HashMap(3);
                hashMap3.put("entryId", new TableInfo.Column("entryId", "INTEGER", true, 1, null, 1));
                hashMap3.put("nfcComm", new TableInfo.Column("nfcComm", "BLOB", false, 0, null, 1));
                hashMap3.put("sessionId", new TableInfo.Column("sessionId", "INTEGER", true, 0, null, 1));
                HashSet hashSet = new HashSet(1);
                hashSet.add(new TableInfo.ForeignKey("SessionLog", "CASCADE", "NO ACTION", Arrays.asList("sessionId"), Arrays.asList("id")));
                HashSet hashSet2 = new HashSet(1);
                hashSet2.add(new TableInfo.Index("index_NfcCommEntry_sessionId", false, Arrays.asList("sessionId"), Arrays.asList("ASC")));
                TableInfo tableInfo3 = new TableInfo("NfcCommEntry", hashMap3, hashSet, hashSet2);
                TableInfo read3 = TableInfo.read(db, "NfcCommEntry");
                if (!tableInfo3.equals(read3)) {
                    return new RoomOpenHelper.ValidationResult(false, "NfcCommEntry(com.zxtlfasu.wirdvgyk.db.NfcCommEntry).\n Expected:\n" + tableInfo3 + "\n Found:\n" + read3);
                }
                return new RoomOpenHelper.ValidationResult(true, null);
            }
        }, "59ebfcb9a4cb945ecc5cf04b9a6cce1a", "2858dd39e73c190fcae137a225c423b6")).build());
    }

    @Override // androidx.room.RoomDatabase
    protected InvalidationTracker createInvalidationTracker() {
        return new InvalidationTracker(this, new HashMap(0), new HashMap(0), "TagInfo", "SessionLog", "NfcCommEntry");
    }

    @Override // androidx.room.RoomDatabase
    public void clearAllTables() {
        super.assertNotMainThread();
        SupportSQLiteDatabase writableDatabase = super.getOpenHelper().getWritableDatabase();
        try {
            super.beginTransaction();
            writableDatabase.execSQL("PRAGMA defer_foreign_keys = TRUE");
            writableDatabase.execSQL("DELETE FROM `TagInfo`");
            writableDatabase.execSQL("DELETE FROM `SessionLog`");
            writableDatabase.execSQL("DELETE FROM `NfcCommEntry`");
            super.setTransactionSuccessful();
        } finally {
            super.endTransaction();
            writableDatabase.query("PRAGMA wal_checkpoint(FULL)").close();
            if (!writableDatabase.inTransaction()) {
                writableDatabase.execSQL("VACUUM");
            }
        }
    }

    @Override // androidx.room.RoomDatabase
    protected Map<Class<?>, List<Class<?>>> getRequiredTypeConverters() {
        HashMap hashMap = new HashMap();
        hashMap.put(TagInfoDao.class, TagInfoDao_Impl.getRequiredConverters());
        hashMap.put(SessionLogDao.class, SessionLogDao_Impl.getRequiredConverters());
        hashMap.put(SessionLogJoinDao.class, SessionLogJoinDao_Impl.getRequiredConverters());
        hashMap.put(NfcCommEntryDao.class, NfcCommEntryDao_Impl.getRequiredConverters());
        return hashMap;
    }

    @Override // androidx.room.RoomDatabase
    public Set<Class<? extends AutoMigrationSpec>> getRequiredAutoMigrationSpecs() {
        return new HashSet();
    }

    @Override // androidx.room.RoomDatabase
    public List<Migration> getAutoMigrations(final Map<Class<? extends AutoMigrationSpec>, AutoMigrationSpec> autoMigrationSpecs) {
        return new ArrayList();
    }

    @Override // com.zxtlfasu.wirdvgyk.db.AppDatabase
    public TagInfoDao tagInfoDao() {
        TagInfoDao tagInfoDao;
        if (this._tagInfoDao != null) {
            return this._tagInfoDao;
        }
        synchronized (this) {
            if (this._tagInfoDao == null) {
                this._tagInfoDao = new TagInfoDao_Impl(this);
            }
            tagInfoDao = this._tagInfoDao;
        }
        return tagInfoDao;
    }

    @Override // com.zxtlfasu.wirdvgyk.db.AppDatabase
    public SessionLogDao sessionLogDao() {
        SessionLogDao sessionLogDao;
        if (this._sessionLogDao != null) {
            return this._sessionLogDao;
        }
        synchronized (this) {
            if (this._sessionLogDao == null) {
                this._sessionLogDao = new SessionLogDao_Impl(this);
            }
            sessionLogDao = this._sessionLogDao;
        }
        return sessionLogDao;
    }

    @Override // com.zxtlfasu.wirdvgyk.db.AppDatabase
    public SessionLogJoinDao sessionLogJoinDao() {
        SessionLogJoinDao sessionLogJoinDao;
        if (this._sessionLogJoinDao != null) {
            return this._sessionLogJoinDao;
        }
        synchronized (this) {
            if (this._sessionLogJoinDao == null) {
                this._sessionLogJoinDao = new SessionLogJoinDao_Impl(this);
            }
            sessionLogJoinDao = this._sessionLogJoinDao;
        }
        return sessionLogJoinDao;
    }

    @Override // com.zxtlfasu.wirdvgyk.db.AppDatabase
    public NfcCommEntryDao nfcCommEntryDao() {
        NfcCommEntryDao nfcCommEntryDao;
        if (this._nfcCommEntryDao != null) {
            return this._nfcCommEntryDao;
        }
        synchronized (this) {
            if (this._nfcCommEntryDao == null) {
                this._nfcCommEntryDao = new NfcCommEntryDao_Impl(this);
            }
            nfcCommEntryDao = this._nfcCommEntryDao;
        }
        return nfcCommEntryDao;
    }
}