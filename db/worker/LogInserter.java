package com.zxtlfasu.wirdvgyk.db.worker;

import android.content.Context;
import com.zxtlfasu.wirdvgyk.db.AppDatabase;
import com.zxtlfasu.wirdvgyk.db.NfcCommEntry;
import com.zxtlfasu.wirdvgyk.db.SessionLog;
import com.zxtlfasu.wirdvgyk.util.NfcComm;
import java.util.Date;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes.dex */
public class LogInserter {
    private final AppDatabase mDatabase;
    private final SIDChangedListener mListener;
    private final BlockingQueue<LogEntry> mQueue = new LinkedBlockingQueue();
    private long mSessionId = -1;
    private final SessionLog.SessionType mSessionType;

    public interface SIDChangedListener {
        void onSIDChanged(long sessionID);
    }

    public LogInserter(Context ctx, SessionLog.SessionType sessionType, SIDChangedListener listener) {
        this.mDatabase = AppDatabase.getDatabase(ctx);
        this.mSessionType = sessionType;
        this.mListener = listener;
        new LogInserterThread().start();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void setSessionId(long sid) {
        this.mSessionId = sid;
        SIDChangedListener sIDChangedListener = this.mListener;
        if (sIDChangedListener != null) {
            sIDChangedListener.onSIDChanged(sid);
        }
    }

    public void log(NfcComm data) {
        try {
            this.mQueue.put(new LogEntry(data));
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
        }
    }

    public void reset() {
        try {
            this.mQueue.put(new LogEntry());
        } catch (InterruptedException unused) {
            Thread.currentThread().interrupt();
        }
    }

    class LogInserterThread extends Thread {
        LogInserterThread() {
            setDaemon(true);
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public void run() {
            while (true) {
                try {
                    LogEntry logEntry = (LogEntry) LogInserter.this.mQueue.take();
                    if (!logEntry.isValid()) {
                        LogInserter.this.setSessionId(-1L);
                    } else if (LogInserter.this.mSessionId == -1) {
                        LogInserter logInserter = LogInserter.this;
                        logInserter.setSessionId(logInserter.mDatabase.sessionLogDao().insert(new SessionLog(new Date(), LogInserter.this.mSessionType)));
                    }
                    if (logEntry.isValid()) {
                        LogInserter.this.mDatabase.nfcCommEntryDao().insert(new NfcCommEntry(logEntry.getData(), LogInserter.this.mSessionId));
                    }
                } catch (InterruptedException unused) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }
}