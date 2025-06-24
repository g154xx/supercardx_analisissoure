package com.zxtlfasu.wirdvgyk.network;

import android.content.SharedPreferences;
import android.util.Log;
import androidx.preference.PreferenceManager;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.zxtlfasu.wirdvgyk.gui.MainActivity;
import com.zxtlfasu.wirdvgyk.network.ServerConnection;
import com.zxtlfasu.wirdvgyk.network.c2s.C2S;
import com.zxtlfasu.wirdvgyk.network.data.NetworkStatus;
import com.zxtlfasu.wirdvgyk.util.NfcComm;

/* loaded from: classes.dex */
public class NetworkManager implements ServerConnection.Callback {
    private static final String TAG = "NetworkManager";
    private final MainActivity mActivity;
    private final Callback mCallback;
    private ServerConnection mConnection;
    private String mHostname;
    private int mPort;
    private int mSessionNumber;

    public interface Callback {
        void onNetworkStatus(NetworkStatus status);

        void onReceive(NfcComm data);
    }

    public NetworkManager(MainActivity activity, Callback cb) {
        this.mActivity = activity;
        this.mCallback = cb;
    }

    public void connect() {
        loadPreferenceData();
        if (this.mConnection != null) {
            disconnect();
        }
        this.mConnection = new ServerConnection(this.mHostname, this.mPort, PreferenceManager.getDefaultSharedPreferences(this.mActivity).getBoolean("tls", false)).setCallback(this).connect();
        sendServer(C2S.ServerData.Opcode.OP_SYN, null);
    }

    public void disconnect() {
        if (this.mConnection != null) {
            sendServer(C2S.ServerData.Opcode.OP_FIN, null);
            this.mConnection.sync();
            this.mConnection.disconnect();
        }
    }

    public void send(NfcComm data) {
        sendServer(C2S.ServerData.Opcode.OP_PSH, data.toByteArray());
    }

    @Override // com.zxtlfasu.wirdvgyk.network.ServerConnection.Callback
    public void onReceive(byte[] data) {
        try {
            C2S.ServerData parseFrom = C2S.ServerData.parseFrom(data);
            Log.v(TAG, "Got message " + parseFrom.getOpcode().toString());
            int i = AnonymousClass1.$SwitchMap$com$zxtlfasu$wirdvgyk$network$c2s$C2S$ServerData$Opcode[parseFrom.getOpcode().ordinal()];
            if (i == 1) {
                onNetworkStatus(NetworkStatus.PARTNER_CONNECT);
                sendServer(C2S.ServerData.Opcode.OP_ACK, null);
            } else {
                if (i == 2) {
                    onNetworkStatus(NetworkStatus.PARTNER_CONNECT);
                    return;
                }
                if (i == 3) {
                    onNetworkStatus(NetworkStatus.PARTNER_LEFT);
                    this.mConnection.disconnect();
                } else {
                    if (i != 4) {
                        return;
                    }
                    this.mCallback.onReceive(new NfcComm(parseFrom.getData().toByteArray()));
                }
            }
        } catch (InvalidProtocolBufferException e) {
            Log.e(TAG, "Message parsing failed", e);
        }
    }

    /* renamed from: com.zxtlfasu.wirdvgyk.network.NetworkManager$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$zxtlfasu$wirdvgyk$network$c2s$C2S$ServerData$Opcode;

        static {
            int[] iArr = new int[C2S.ServerData.Opcode.values().length];
            $SwitchMap$com$zxtlfasu$wirdvgyk$network$c2s$C2S$ServerData$Opcode = iArr;
            try {
                iArr[C2S.ServerData.Opcode.OP_SYN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$network$c2s$C2S$ServerData$Opcode[C2S.ServerData.Opcode.OP_ACK.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$network$c2s$C2S$ServerData$Opcode[C2S.ServerData.Opcode.OP_FIN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$network$c2s$C2S$ServerData$Opcode[C2S.ServerData.Opcode.OP_PSH.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    @Override // com.zxtlfasu.wirdvgyk.network.ServerConnection.Callback
    public void onNetworkStatus(NetworkStatus status) {
        this.mCallback.onNetworkStatus(status);
    }

    private void loadPreferenceData() {
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this.mActivity);
        this.mHostname = defaultSharedPreferences.getString("host", null);
        this.mPort = Integer.parseInt(defaultSharedPreferences.getString("port", "0"));
        this.mSessionNumber = Integer.parseInt(defaultSharedPreferences.getString("session", "0"));
    }

    private void sendServer(C2S.ServerData.Opcode opcode, byte[] data) {
        this.mConnection.send(this.mSessionNumber, C2S.ServerData.newBuilder().setOpcode(opcode).setData(data == null ? ByteString.EMPTY : ByteString.copyFrom(data)).build().toByteArray());
    }
}