package com.zxtlfasu.wirdvgyk.db;

import com.zxtlfasu.wirdvgyk.util.NfcComm;

/* loaded from: classes.dex */
public class NfcCommEntry {
    private int entryId;
    private NfcComm nfcComm;
    private long sessionId;

    public int getEntryId() {
        return this.entryId;
    }

    public NfcComm getNfcComm() {
        return this.nfcComm;
    }

    public long getSessionId() {
        return this.sessionId;
    }

    public void setEntryId(int entryId) {
        this.entryId = entryId;
    }

    public void setNfcComm(NfcComm nfcComm) {
        this.nfcComm = nfcComm;
    }

    public void setSessionId(long sessionId) {
        this.sessionId = sessionId;
    }

    public NfcCommEntry(NfcComm nfcComm, long sessionId) {
        this.nfcComm = nfcComm;
        this.sessionId = sessionId;
    }

    public String toString() {
        return this.nfcComm.toString();
    }
}