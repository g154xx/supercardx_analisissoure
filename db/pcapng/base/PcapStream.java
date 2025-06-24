package com.zxtlfasu.wirdvgyk.db.pcapng.base;

import com.zxtlfasu.wirdvgyk.gui.component.FileShare;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class PcapStream implements FileShare.IFileShareable {
    private static final int BLOCK_LEN_INTERFACE = 20;
    private static final int BLOCK_LEN_SECTION = 28;
    private static final int BLOCK_TYPE_INTERFACE = 1;
    private static final int BLOCK_TYPE_SECTION = 168627466;
    private static final int BYTE_ORDER_MAGIC = 439041101;
    private final short[] mLinkTypes;
    private final List<PcapPacket> mPackets = new ArrayList();

    public List<PcapPacket> getPackets() {
        return this.mPackets;
    }

    public PcapStream(short[] linkType) {
        this.mLinkTypes = linkType;
    }

    public void append(PcapPacket packet) {
        this.mPackets.add(packet);
    }

    public void read(InputStream stream) throws IOException {
        DataInputStream dataInputStream = new DataInputStream(stream);
        assertEq("block type", BLOCK_TYPE_SECTION, dataInputStream.readInt());
        assertEq("block len", 28, dataInputStream.readInt());
        assertEq("byte order magic", BYTE_ORDER_MAGIC, dataInputStream.readInt());
        assertEq("version (major)", 1, dataInputStream.readShort());
        assertEq("version (minor)", 0, dataInputStream.readShort());
        dataInputStream.skipBytes(8);
        assertEq("block len", 28, dataInputStream.readInt());
        for (short s : this.mLinkTypes) {
            assertEq("block type", 1, dataInputStream.readInt());
            assertEq("block len", 20, dataInputStream.readInt());
            assertEq("block len", s, dataInputStream.readShort());
            dataInputStream.skipBytes(2);
            dataInputStream.skipBytes(4);
            assertEq("block len", 20, dataInputStream.readInt());
        }
        while (dataInputStream.available() > 0) {
            this.mPackets.add(readPacket(dataInputStream));
        }
        dataInputStream.close();
    }

    protected PcapPacket readPacket(DataInputStream in) throws IOException {
        return new PcapPacket().read(in);
    }

    private void assertEq(String what, int expected, int actual) throws IOException {
        if (expected != actual) {
            throw new IOException(String.format("Pcap format error. %s: %d vs %d", what, Integer.valueOf(expected), Integer.valueOf(actual)));
        }
    }

    @Override // com.zxtlfasu.wirdvgyk.gui.component.FileShare.IFileShareable
    public void write(OutputStream stream) throws IOException {
        DataOutputStream dataOutputStream = new DataOutputStream(stream);
        dataOutputStream.writeInt(BLOCK_TYPE_SECTION);
        dataOutputStream.writeInt(28);
        dataOutputStream.writeInt(BYTE_ORDER_MAGIC);
        dataOutputStream.writeShort(1);
        dataOutputStream.writeShort(0);
        dataOutputStream.writeLong(-1L);
        dataOutputStream.writeInt(28);
        for (short s : this.mLinkTypes) {
            dataOutputStream.writeInt(1);
            dataOutputStream.writeInt(20);
            dataOutputStream.writeShort(s);
            dataOutputStream.writeShort(0);
            dataOutputStream.writeInt(0);
            dataOutputStream.writeInt(20);
        }
        Iterator<PcapPacket> it = this.mPackets.iterator();
        while (it.hasNext()) {
            it.next().write(dataOutputStream);
        }
        dataOutputStream.close();
    }
}