package com.zxtlfasu.wirdvgyk.db;

import android.content.Context;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

/* loaded from: classes.dex */
public abstract class AppDatabase extends RoomDatabase {
    private static final Migration MIGRATION_1_2 = new Migration(1, 2) { // from class: com.zxtlfasu.wirdvgyk.db.AppDatabase.1
        @Override // androidx.room.migration.Migration
        public void migrate(SupportSQLiteDatabase database) {
            database.execSQL("ALTER TABLE SessionLog ADD COLUMN type INTEGER DEFAULT 0");
        }
    };
    private static AppDatabase mInstance;

    public abstract NfcCommEntryDao nfcCommEntryDao();

    public abstract SessionLogDao sessionLogDao();

    public abstract SessionLogJoinDao sessionLogJoinDao();

    public abstract TagInfoDao tagInfoDao();

    public static AppDatabase getDatabase(Context context) {
        if (mInstance == null) {
            mInstance = (AppDatabase) Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "nfcgate").addMigrations(MIGRATION_1_2).build();
        }
        return mInstance;
    }
}