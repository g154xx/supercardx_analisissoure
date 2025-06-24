package com.zxtlfasu.wirdvgyk.db.pcapng.base;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class PcapPacket {
    private static final int BLOCK_LEN_EPB = 32;
    protected static final int BLOCK_TYPE_EPB = 6;
    protected int mInterfaceIndex = 0;
    protected long mTimestamp = 0;
    protected byte[] mPayload = null;

    protected PcapPacket() {
    }

    public PcapPacket read(DataInputStream in) throws IOException {
        in.skipBytes(4);
        int readInt = in.readInt();
        this.mInterfaceIndex = in.readInt();
        int readInt2 = in.readInt();
        int readInt3 = in.readInt();
        in.skipBytes(4);
        int readInt4 = in.readInt();
        byte[] bArr = new byte[readInt4];
        this.mPayload = bArr;
        in.read(bArr, 0, readInt4);
        in.skipBytes((readInt - readInt4) - 32);
        in.skipBytes(4);
        this.mTimestamp = ((readInt2 << 32) | (readInt3 & 4294967295L)) / 1000;
        return this;
    }

    public int write(DataOutputStream out) throws IOException {
        long j = this.mTimestamp * 1000;
        byte[] bArr = this.mPayload;
        int length = (4 - (bArr.length % 4)) % 4;
        int length2 = bArr.length + 32 + length;
        out.writeInt(6);
        out.writeInt(length2);
        out.writeInt(this.mInterfaceIndex);
        out.writeInt((int) (j >> 32));
        out.writeInt((int) j);
        out.writeInt(this.mPayload.length);
        out.writeInt(this.mPayload.length);
        out.write(this.mPayload);
        out.write(new byte[length]);
        out.writeInt(length2);
        return length2;
    }
}