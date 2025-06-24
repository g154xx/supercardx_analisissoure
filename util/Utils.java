package com.zxtlfasu.wirdvgyk.util;

import kotlin.UByte;

/* loaded from: classes.dex */
public class Utils {
    private static final char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bArr) {
        return bytesToHex(bArr, ':');
    }

    public static String bytesToHex(byte[] bArr, char c) {
        int length = bArr.length * 3;
        char[] cArr = new char[length];
        for (int i = 0; i < bArr.length; i++) {
            byte b = bArr[i];
            int i2 = b & UByte.MAX_VALUE;
            int i3 = i * 3;
            char[] cArr2 = hexArray;
            cArr[i3] = cArr2[i2 >>> 4];
            cArr[i3 + 1] = cArr2[b & 15];
            cArr[i3 + 2] = c;
        }
        return new String(cArr, 0, length - 1);
    }

    public static String bytesToHex(byte b) {
        return bytesToHex(new byte[]{b});
    }

    public static String bytesToHexDump(byte[] bArr) {
        int length = (bArr.length * 3) + (((bArr.length / 16) + 1) * 5);
        char[] cArr = new char[length];
        int i = 0;
        for (int i2 = 0; i2 < bArr.length; i2++) {
            byte b = bArr[i2];
            int i3 = b & UByte.MAX_VALUE;
            int i4 = i2 * 3;
            int i5 = (i * 5) + i4;
            if (i2 % 16 == 0) {
                if (i2 != 0) {
                    i++;
                    i5 = (i * 5) + i4;
                    cArr[i5] = '\n';
                }
                char[] cArr2 = hexArray;
                cArr[i5 + 1] = cArr2[(i2 >>> 8) & 15];
                cArr[i5 + 2] = cArr2[(i2 >>> 4) & 15];
                cArr[i5 + 3] = cArr2[i2 & 15];
                cArr[i5 + 4] = ' ';
            }
            cArr[i5 + 5] = ' ';
            char[] cArr3 = hexArray;
            cArr[i5 + 6] = cArr3[i3 >>> 4];
            cArr[i5 + 7] = cArr3[b & 15];
        }
        return new String(cArr, 1, length - 1);
    }
}