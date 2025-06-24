package com.zxtlfasu.wirdvgyk.network.transport;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

/* loaded from: classes.dex */
public abstract class Transport {
    protected InetSocketAddress mAddress;
    protected final String mHostname;
    protected final int mPort;
    protected Socket mSocket = null;
    protected boolean connectCalled = false;

    protected abstract void connectSocket() throws IOException;

    protected abstract Socket createSocket() throws IOException;

    public Socket socket() {
        return this.mSocket;
    }

    public Transport(String hostname, int port) {
        this.mHostname = hostname;
        this.mPort = port;
    }

    public boolean connect() throws IOException {
        if (this.mSocket != null || this.connectCalled) {
            return false;
        }
        this.mAddress = new InetSocketAddress(this.mHostname, this.mPort);
        this.connectCalled = true;
        this.mSocket = createSocket();
        connectSocket();
        return true;
    }

    public void close(boolean allowReconnects) {
        Socket socket = this.mSocket;
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException unused) {
            }
        }
        this.mSocket = null;
        if (allowReconnects) {
            this.connectCalled = false;
        }
    }
}