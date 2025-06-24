package com.zxtlfasu.wirdvgyk.network.transport;

import java.io.IOException;
import java.net.Socket;

/* loaded from: classes.dex */
public class PlainTransport extends Transport {
    public PlainTransport(String hostname, int port) {
        super(hostname, port);
    }

    @Override // com.zxtlfasu.wirdvgyk.network.transport.Transport
    protected Socket createSocket() {
        return new Socket();
    }

    @Override // com.zxtlfasu.wirdvgyk.network.transport.Transport
    protected void connectSocket() throws IOException {
        this.mSocket.connect(this.mAddress, 10000);
    }
}