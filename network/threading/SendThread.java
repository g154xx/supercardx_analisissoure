package com.zxtlfasu.wirdvgyk.network.threading;

import android.util.Log;
import com.zxtlfasu.wirdvgyk.network.ServerConnection;
import com.zxtlfasu.wirdvgyk.network.data.SendRecord;
import java.io.DataOutputStream;
import java.io.IOException;

/* loaded from: classes.dex */
public class SendThread extends BaseThread {
    private static final String TAG = "SendThread";
    private DataOutputStream mWriteStream;

    public SendThread(ServerConnection connection) {
        super(connection);
    }

    @Override // com.zxtlfasu.wirdvgyk.network.threading.BaseThread
    void initThread() throws IOException {
        this.mWriteStream = new DataOutputStream(this.mSocket.getOutputStream());
    }

    @Override // com.zxtlfasu.wirdvgyk.network.threading.BaseThread
    void runInternal() throws IOException, InterruptedException {
        SendRecord take = this.mConnection.getSendQueue().take();
        Log.v(TAG, "Sending message of " + take.getData().length + " bytes");
        this.mWriteStream.writeInt(take.getData().length);
        this.mWriteStream.writeByte(take.getSession());
        this.mWriteStream.write(take.getData());
        this.mWriteStream.flush();
    }

    @Override // com.zxtlfasu.wirdvgyk.network.threading.BaseThread
    void onError(Exception e) {
        Log.e(TAG, "Send onError", e);
        super.onError(e);
    }
}