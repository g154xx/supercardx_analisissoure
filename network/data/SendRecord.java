package com.zxtlfasu.wirdvgyk.network.data;

/* loaded from: classes.dex */
public class SendRecord {
    private final byte[] mData;
    private final int mSession;

    public byte[] getData() {
        return this.mData;
    }

    public int getSession() {
        return this.mSession;
    }

    public SendRecord(int session, byte[] data) {
        this.mSession = session;
        this.mData = data;
    }
}