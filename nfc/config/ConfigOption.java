package com.zxtlfasu.wirdvgyk.nfc.config;

import com.zxtlfasu.wirdvgyk.util.Utils;

/* loaded from: classes.dex */
public class ConfigOption {
    private final byte[] mData;
    private final OptionType mID;

    ConfigOption(OptionType ID, byte[] data) {
        this.mID = ID;
        this.mData = data;
    }

    ConfigOption(OptionType ID, byte data) {
        this(ID, new byte[]{data});
    }

    public int len() {
        return this.mData.length;
    }

    public void push(byte[] data, int offset) {
        data[offset] = this.mID.getID();
        byte[] bArr = this.mData;
        data[offset + 1] = (byte) bArr.length;
        System.arraycopy(bArr, 0, data, offset + 2, bArr.length);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Type: ");
        sb.append(this.mID.toString());
        if (this.mData.length > 1) {
            sb.append(" (");
            sb.append(this.mData.length);
            sb.append(")");
        }
        sb.append(", Value: 0x");
        sb.append(Utils.bytesToHex(this.mData));
        return sb.toString();
    }
}