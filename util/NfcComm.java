package com.zxtlfasu.wirdvgyk.util;

import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zxtlfasu.wirdvgyk.network.c2c.C2C;

/* loaded from: classes.dex */
public class NfcComm {
    private C2C.NFCData mData;

    public NfcComm(boolean fromCard, boolean isInitial, byte[] data) {
        this(fromCard, isInitial, data, System.currentTimeMillis());
    }

    public NfcComm(boolean fromCard, boolean isInitial, byte[] data, long timestamp) {
        this.mData = C2C.NFCData.newBuilder().setDataSource(fromCard ? C2C.NFCData.DataSource.CARD : C2C.NFCData.DataSource.READER).setDataType(isInitial ? C2C.NFCData.DataType.INITIAL : C2C.NFCData.DataType.CONTINUATION).setTimestamp(timestamp).setData(ByteString.copyFrom(data)).build();
    }

    public NfcComm(byte[] data) {
        try {
            this.mData = C2C.NFCData.parseFrom(data);
        } catch (InvalidProtocolBufferException e) {
            e.printStackTrace();
        }
    }

    public boolean isInitial() {
        return this.mData.getDataType() == C2C.NFCData.DataType.INITIAL;
    }

    public boolean isCard() {
        return this.mData.getDataSource() == C2C.NFCData.DataSource.CARD;
    }

    public long getTimestamp() {
        return this.mData.getTimestamp();
    }

    public byte[] getData() {
        return this.mData.getData().toByteArray();
    }

    public byte[] toByteArray() {
        return this.mData.toByteArray();
    }

    public String toString() {
        return String.format("%s: %s%s", isCard() ? "C" : "R", isInitial() ? "(initial) " : "", Utils.bytesToHex(getData()));
    }
}