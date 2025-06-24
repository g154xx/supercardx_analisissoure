package com.zxtlfasu.wirdvgyk.db;

import com.zxtlfasu.wirdvgyk.db.SessionLog;
import com.zxtlfasu.wirdvgyk.util.NfcComm;
import java.util.Date;

/* loaded from: classes.dex */
public class Converters {
    public static NfcComm fromBytearray(byte[] data) {
        if (data == null) {
            return null;
        }
        return new NfcComm(data);
    }

    public static byte[] NfcCommToBytearray(NfcComm nfcComm) {
        if (nfcComm == null) {
            return null;
        }
        return nfcComm.toByteArray();
    }

    public static Date fromTimestamp(Long value) {
        if (value == null) {
            return null;
        }
        return new Date(value.longValue());
    }

    public static Long dateToTimestamp(Date date) {
        if (date == null) {
            return null;
        }
        return Long.valueOf(date.getTime());
    }

    public static SessionLog.SessionType intToType(int type) {
        return SessionLog.SessionType.values()[type];
    }

    public static int typeToInt(SessionLog.SessionType type) {
        return type.ordinal();
    }
}