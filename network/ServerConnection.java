package com.zxtlfasu.wirdvgyk.network;

import android.util.Log;
import com.zxtlfasu.wirdvgyk.network.data.NetworkStatus;
import com.zxtlfasu.wirdvgyk.network.data.SendRecord;
import com.zxtlfasu.wirdvgyk.network.threading.ReceiveThread;
import com.zxtlfasu.wirdvgyk.network.threading.SendThread;
import com.zxtlfasu.wirdvgyk.network.transport.PlainTransport;
import com.zxtlfasu.wirdvgyk.network.transport.TLSTransport;
import com.zxtlfasu.wirdvgyk.network.transport.Transport;
import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/* loaded from: classes.dex */
public class ServerConnection {
    private static final String TAG = "ServerConnection";
    private Callback mCallback;
    private ReceiveThread mReceiveThread;
    private final BlockingQueue<SendRecord> mSendQueue;
    private SendThread mSendThread;
    private final Object mSocketLock;
    private Transport mTransport;

    public interface Callback {
        void onNetworkStatus(NetworkStatus status);

        void onReceive(byte[] data);
    }

    public BlockingQueue<SendRecord> getSendQueue() {
        return this.mSendQueue;
    }

    ServerConnection setCallback(Callback cb) {
        this.mCallback = cb;
        return this;
    }

    ServerConnection(String hostname, int port, boolean tls) {
        this(tls ? new TLSTransport(hostname, port) : new PlainTransport(hostname, port));
    }

    ServerConnection(Transport transport) {
        this.mSocketLock = new Object();
        this.mSendQueue = new LinkedBlockingQueue();
        this.mTransport = transport;
    }

    ServerConnection connect() {
        this.mTransport.close(true);
        this.mSendThread = new SendThread(this);
        this.mReceiveThread = new ReceiveThread(this);
        this.mSendThread.start();
        this.mReceiveThread.start();
        return this;
    }

    void disconnect() {
        SendThread sendThread = this.mSendThread;
        if (sendThread != null) {
            sendThread.interrupt();
        }
        ReceiveThread receiveThread = this.mReceiveThread;
        if (receiveThread != null) {
            receiveThread.interrupt();
        }
    }

    void sync() {
        if (this.mSendQueue.peek() != null) {
            try {
                Thread.sleep(20L);
            } catch (InterruptedException unused) {
            }
        }
    }

    public void send(int session, byte[] data) {
        Log.v(TAG, "Enqueuing message of " + data.length + " bytes");
        this.mSendQueue.add(new SendRecord(session, data));
    }

    public Socket openSocket() throws IOException {
        Socket socket;
        synchronized (this.mSocketLock) {
            Log.d(TAG, "ServerConnection.openSocket(): " + Thread.currentThread().getId());
            try {
                if (this.mTransport.connect()) {
                    reportStatus(NetworkStatus.CONNECTED);
                    this.mTransport.socket().setTcpNoDelay(true);
                }
                socket = this.mTransport.socket();
            } catch (Exception e) {
                Log.d(TAG, "mTransport.init exception: " + Thread.currentThread().getId());
                Log.e(TAG, "Transport cannot connect", e);
                this.mTransport.close(false);
                throw e;
            }
        }
        return socket;
    }

    public void closeSocket() {
        synchronized (this.mSocketLock) {
            this.mTransport.close(false);
        }
    }

    public void onReceive(byte[] data) {
        this.mCallback.onReceive(data);
    }

    public void reportStatus(NetworkStatus status) {
        this.mCallback.onNetworkStatus(status);
    }
}