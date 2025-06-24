package com.zxtlfasu.wirdvgyk.db.worker;

import com.zxtlfasu.wirdvgyk.util.NfcComm;

/* loaded from: classes.dex */
public class LogEntry {
    private final NfcComm mData;
    private final boolean mValid;

    NfcComm getData() {
        return this.mData;
    }

    boolean isValid() {
        return this.mValid;
    }

    LogEntry() {
        this.mData = null;
        this.mValid = false;
    }

    LogEntry(NfcComm data) {
        this.mData = data;
        this.mValid = true;
    }
}