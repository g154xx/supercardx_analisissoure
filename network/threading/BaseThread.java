package com.zxtlfasu.wirdvgyk.network.threading;

import com.zxtlfasu.wirdvgyk.network.ServerConnection;
import com.zxtlfasu.wirdvgyk.network.UserTrustManager;
import com.zxtlfasu.wirdvgyk.network.data.NetworkStatus;
import java.io.IOException;
import java.net.Socket;
import javax.net.ssl.SSLException;

/* loaded from: classes.dex */
public abstract class BaseThread extends Thread {
    final ServerConnection mConnection;
    private boolean mExit = false;
    Socket mSocket;

    abstract void initThread() throws IOException;

    abstract void runInternal() throws IOException, InterruptedException;

    BaseThread(ServerConnection connection) {
        this.mConnection = connection;
        setDaemon(true);
    }

    @Override // java.lang.Thread, java.lang.Runnable
    public void run() {
        Socket openSocket;
        try {
            openSocket = this.mConnection.openSocket();
            this.mSocket = openSocket;
        } catch (Exception e) {
            this.mExit = true;
            onError(e);
        }
        if (openSocket == null) {
            throw new IOException("openSocket failed");
        }
        initThread();
        while (!this.mExit && !Thread.currentThread().isInterrupted()) {
            try {
                runInternal();
            } catch (InterruptedException unused) {
            } catch (Exception e2) {
                this.mExit = true;
                onError(e2);
            }
        }
        this.mConnection.closeSocket();
    }

    void onError(Exception e) {
        Throwable th = e;
        while (th.getCause() != null) {
            th = th.getCause();
        }
        if (th instanceof UserTrustManager.UnknownTrustException) {
            this.mConnection.reportStatus(NetworkStatus.ERROR_TLS_CERT_UNKNOWN);
            return;
        }
        if (th instanceof UserTrustManager.UntrustedException) {
            this.mConnection.reportStatus(NetworkStatus.ERROR_TLS_CERT_UNTRUSTED);
        } else if (e instanceof SSLException) {
            this.mConnection.reportStatus(NetworkStatus.ERROR_TLS);
        } else {
            this.mConnection.reportStatus(NetworkStatus.ERROR);
        }
    }
}