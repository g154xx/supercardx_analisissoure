package com.zxtlfasu.wirdvgyk.xposed;

/* loaded from: classes.dex */
public class Native {
    static final Native Instance = new Native();

    public native boolean isHookEnabled();

    public native boolean isPatchEnabled();

    public native void resetConfig();

    public native void setConfig(byte[] bArr);
}