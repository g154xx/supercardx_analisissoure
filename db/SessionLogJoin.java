package com.zxtlfasu.wirdvgyk.db;

import java.util.List;

/* loaded from: classes.dex */
public class SessionLogJoin {
    private List<NfcCommEntry> nfcCommEntries;
    private SessionLog sessionLog;

    public List<NfcCommEntry> getNfcCommEntries() {
        return this.nfcCommEntries;
    }

    public SessionLog getSessionLog() {
        return this.sessionLog;
    }

    public void setNfcCommEntries(List<NfcCommEntry> nfcCommEntries) {
        this.nfcCommEntries = nfcCommEntries;
    }

    public void setSessionLog(SessionLog sessionLog) {
        this.sessionLog = sessionLog;
    }

    public SessionLogJoin(SessionLog sessionLog) {
        this.sessionLog = sessionLog;
    }
}