package com.zxtlfasu.wirdvgyk;

/* loaded from: classes.dex */
public interface PinLockListener {
    void onPinComplete(boolean result, String pinCode);

    void onPinDelete();

    void onPinEmpty();

    void onPinEnter();
}