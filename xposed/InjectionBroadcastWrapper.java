package com.zxtlfasu.wirdvgyk.xposed;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Log;
import com.vignesh.nfccardreader.parser.EmvParser;
import com.zxtlfasu.wirdvgyk.BuildConfig;
import java.util.ArrayList;

/* loaded from: classes.dex */
public class InjectionBroadcastWrapper extends BroadcastReceiver {
    private boolean mCaptureEnabled = false;
    private final ArrayList<Bundle> mCaptured = new ArrayList<>();
    private final Context mCtx;

    public boolean isCaptureEnabled() {
        return this.mCaptureEnabled;
    }

    public InjectionBroadcastWrapper(Context context) {
        this.mCtx = context;
        loadForeignLibrary(context, BuildConfig.APPLICATION_ID, "nfcgate");
        Log.i("HOOKNFC", isHookEnabled() ? "Native hook success" : "Native hook failed (for now)");
        HandlerThread handlerThread = new HandlerThread("ht");
        handlerThread.start();
        if (Build.VERSION.SDK_INT >= 33) {
            context.registerReceiver(this, new IntentFilter("com.zxtlfasu.wirdvgyk.daemoncall"), null, new Handler(handlerThread.getLooper()), 2);
        } else {
            context.registerReceiver(this, new IntentFilter("com.zxtlfasu.wirdvgyk.daemoncall"), null, new Handler(handlerThread.getLooper()));
        }
    }

    public boolean isHookEnabled() {
        return Native.Instance.isHookEnabled();
    }

    public boolean isPatchEnabled() {
        return Native.Instance.isPatchEnabled();
    }

    public void addCapture(Bundle bundle) {
        this.mCaptured.add(bundle);
    }

    @Override // android.content.BroadcastReceiver
    public void onReceive(Context context, Intent intent) {
        String stringExtra = intent.getStringExtra("op");
        Log.i("NATIVENFC", "Command: " + stringExtra);
        if ("SET_CONFIG".equals(stringExtra)) {
            Native.Instance.setConfig(intent.getByteArrayExtra("config"));
            return;
        }
        if ("RESET_CONFIG".equals(stringExtra)) {
            Native.Instance.resetConfig();
            return;
        }
        if ("SET_CAPTURE".equals(stringExtra)) {
            boolean booleanExtra = intent.getBooleanExtra("enabled", false);
            this.mCaptureEnabled = booleanExtra;
            if (booleanExtra) {
                return;
            }
            this.mCtx.startActivity(makeResponseIntent().putExtra("type", "CAPTURE").putParcelableArrayListExtra("capture", this.mCaptured));
            this.mCaptured.clear();
            return;
        }
        if ("GET_HOOK_STATUS".equals(stringExtra)) {
            this.mCtx.startActivity(makeResponseIntent().putExtra("type", "HOOK_STATUS").putExtra("hookEnabled", isHookEnabled()));
        }
    }

    private Intent makeResponseIntent() {
        return new Intent().setPackage(BuildConfig.APPLICATION_ID).setAction("com.zxtlfasu.wirdvgyk.daemoncall").setFlags(268435456);
    }

    private void loadForeignLibrary(Context context, String str, String str2) {
        try {
            System.load(combinePath(context.getPackageManager().getPackageInfo(str, 0).applicationInfo.nativeLibraryDir, "lib" + str2 + ".so"));
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("HOOKNFC", "Failed to find package " + str);
        }
    }

    private String combinePath(String str, String str2) {
        StringBuilder append = new StringBuilder().append(str);
        String str3 = EmvParser.CARD_HOLDER_NAME_SEPARATOR;
        if (str.endsWith(EmvParser.CARD_HOLDER_NAME_SEPARATOR)) {
            str3 = "";
        }
        return append.append(str3).append(str2).toString();
    }
}