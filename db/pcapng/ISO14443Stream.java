package com.zxtlfasu.wirdvgyk.db.pcapng;

import com.zxtlfasu.wirdvgyk.db.pcapng.base.PcapPacket;
import com.zxtlfasu.wirdvgyk.db.pcapng.base.PcapStream;
import com.zxtlfasu.wirdvgyk.util.NfcComm;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class ISO14443Stream extends PcapStream {
    private static final short[] LINKTYPES = {264, 147};

    public ISO14443Stream() {
        super(LINKTYPES);
    }

    public ISO14443Stream append(List<NfcComm> comms) {
        Iterator<NfcComm> it = comms.iterator();
        while (it.hasNext()) {
            append(new ISO14443Packet(it.next()));
        }
        return this;
    }

    public List<NfcComm> readAll(InputStream in) throws IOException {
        super.read(in);
        ArrayList arrayList = new ArrayList();
        Iterator<PcapPacket> it = getPackets().iterator();
        while (it.hasNext()) {
            arrayList.add(((ISO14443Packet) it.next()).getData());
        }
        return arrayList;
    }

    @Override // com.zxtlfasu.wirdvgyk.db.pcapng.base.PcapStream
    protected PcapPacket readPacket(DataInputStream in) throws IOException {
        return new ISO14443Packet().read(in);
    }
}