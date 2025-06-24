package com.zxtlfasu.wirdvgyk.network.threading;

import android.util.Log;
import com.zxtlfasu.wirdvgyk.network.ServerConnection;
import java.io.DataInputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class ReceiveThread extends BaseThread {
    public static final int MAX_RECEIVE_BYTES = 104857600;
    private static final String TAG = "ReceiveThread";
    private DataInputStream mReadStream;

    public ReceiveThread(ServerConnection connection) {
        super(connection);
    }

    @Override // com.zxtlfasu.wirdvgyk.network.threading.BaseThread
    void initThread() throws IOException {
        this.mReadStream = new DataInputStream(this.mSocket.getInputStream());
    }

    @Override // com.zxtlfasu.wirdvgyk.network.threading.BaseThread
    void runInternal() throws IOException {
        int readInt = this.mReadStream.readInt();
        Log.v(TAG, "Got message of " + readInt + " bytes");
        if (readInt > 104857600) {
            throw new IOException("Invalid protocol length prefix received");
        }
        byte[] bArr = new byte[readInt];
        this.mReadStream.readFully(bArr);
        this.mConnection.onReceive(bArr);
    }

    @Override // com.zxtlfasu.wirdvgyk.network.threading.BaseThread
    void onError(Exception e) {
        Log.e(TAG, "Receive onError", e);
        super.onError(e);
    }
}