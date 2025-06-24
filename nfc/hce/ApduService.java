package com.zxtlfasu.wirdvgyk.nfc.hce;

import android.nfc.cardemulation.HostApduService;
import android.os.Bundle;
import android.util.Log;
import com.zxtlfasu.wirdvgyk.nfc.NfcManager;
import com.zxtlfasu.wirdvgyk.util.NfcComm;
import com.zxtlfasu.wirdvgyk.util.Utils;

/* loaded from: classes.dex */
public class ApduService extends HostApduService {
    private static final String TAG = "ApduService";
    private final byte[] DONT_RESPOND;
    private final NfcManager mNfcManager;

    public ApduService() {
        NfcManager nfcManager = NfcManager.getInstance();
        this.mNfcManager = nfcManager;
        this.DONT_RESPOND = new byte[0];
        nfcManager.setApduService(this);
    }

    @Override // android.nfc.cardemulation.HostApduService
    public byte[] processCommandApdu(byte[] apdu, Bundle extras) {
        Log.d(TAG, "APDU-IN: " + Utils.bytesToHex(apdu));
        this.mNfcManager.handleData(false, new NfcComm(false, false, apdu));
        return this.DONT_RESPOND;
    }

    @Override // android.nfc.cardemulation.HostApduService
    public void onDeactivated(int reason) {
        Log.i(TAG, "Deactivated: " + reason);
        this.mNfcManager.setApduService(null);
    }

    public void sendResponse(byte[] apdu) {
        Log.d(TAG, "APDU-OUT: " + Utils.bytesToHex(apdu));
        sendResponseApdu(apdu);
    }
}