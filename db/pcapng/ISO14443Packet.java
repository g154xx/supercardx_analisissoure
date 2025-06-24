package com.zxtlfasu.wirdvgyk.db.pcapng;

import com.zxtlfasu.wirdvgyk.db.pcapng.base.PcapPacket;
import com.zxtlfasu.wirdvgyk.util.NfcComm;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class ISO14443Packet extends PcapPacket {
    private static final byte DATA_PCD_TO_PICC_CRC_DROPPED = -6;
    private static final byte DATA_PICC_TO_PCD_CRC_DROPPED = -5;
    private NfcComm mData;

    public NfcComm getData() {
        return this.mData;
    }

    public ISO14443Packet() {
    }

    public ISO14443Packet(NfcComm data) {
        this.mData = data;
    }

    @Override // com.zxtlfasu.wirdvgyk.db.pcapng.base.PcapPacket
    public PcapPacket read(DataInputStream in) throws IOException {
        super.read(in);
        DataInputStream dataInputStream = new DataInputStream(new ByteArrayInputStream(this.mPayload));
        try {
            dataInputStream.skipBytes(1);
            boolean z = dataInputStream.readByte() == -5;
            short readShort = dataInputStream.readShort();
            dataInputStream.skipBytes(1);
            int i = readShort - 1;
            byte[] bArr = new byte[i];
            dataInputStream.read(bArr, 0, i);
            this.mData = new NfcComm(z, this.mInterfaceIndex == 1, bArr, this.mTimestamp);
            dataInputStream.close();
            return this;
        } catch (Throwable th) {
            try {
                dataInputStream.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }

    @Override // com.zxtlfasu.wirdvgyk.db.pcapng.base.PcapPacket
    public int write(DataOutputStream dataOutputStream) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        DataOutputStream dataOutputStream2 = new DataOutputStream(byteArrayOutputStream);
        try {
            byte[] data = this.mData.getData();
            dataOutputStream2.writeByte(0);
            dataOutputStream2.writeByte(this.mData.isCard() ? -5 : -6);
            dataOutputStream2.writeShort(data.length + 1);
            dataOutputStream2.writeByte(2);
            dataOutputStream2.write(data);
            dataOutputStream2.close();
            this.mInterfaceIndex = this.mData.isInitial() ? 1 : 0;
            this.mPayload = byteArrayOutputStream.toByteArray();
            this.mTimestamp = this.mData.getTimestamp();
            return super.write(dataOutputStream);
        } catch (Throwable th) {
            try {
                dataOutputStream2.close();
            } catch (Throwable th2) {
                th.addSuppressed(th2);
            }
            throw th;
        }
    }
}