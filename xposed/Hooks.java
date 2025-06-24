package com.zxtlfasu.wirdvgyk.xposed;

import android.app.Application;
import android.content.Context;
import android.content.pm.PackageManager;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import com.zxtlfasu.wirdvgyk.BuildConfig;
import com.zxtlfasu.wirdvgyk.util.Utils;
import de.robv.android.xposed.IXposedHookLoadPackage;
import de.robv.android.xposed.XC_MethodHook;
import de.robv.android.xposed.XC_MethodReplacement;
import de.robv.android.xposed.XposedBridge;
import de.robv.android.xposed.XposedHelpers;
import de.robv.android.xposed.callbacks.XC_LoadPackage;
import java.lang.reflect.Constructor;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/* loaded from: classes.dex */
public class Hooks implements IXposedHookLoadPackage {
    private Object mNfcServiceInstance;
    private Object mReceiver;

    private interface NfcServiceConstructorHook {
        void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam);
    }

    private String preLollipop(String str, String str2) {
        return str2;
    }

    public void handleLoadPackage(final XC_LoadPackage.LoadPackageParam loadPackageParam) {
        if (BuildConfig.APPLICATION_ID.equals(loadPackageParam.packageName)) {
            XposedHelpers.findAndHookMethod("com.zxtlfasu.wirdvgyk.nfc.NfcManager", loadPackageParam.classLoader, "isModuleLoaded", new Object[]{XC_MethodReplacement.returnConstant(true)});
            return;
        }
        if ("com.android.nfc".equals(loadPackageParam.packageName)) {
            hookNfcServiceConstructor(loadPackageParam.classLoader, new NfcServiceConstructorHook() { // from class: com.zxtlfasu.wirdvgyk.xposed.Hooks.1
                @Override // com.zxtlfasu.wirdvgyk.xposed.Hooks.NfcServiceConstructorHook
                public void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    if (Hooks.this.mNfcServiceInstance != null) {
                        return;
                    }
                    Log.i("HOOKNFC", "Launching InjectionBroadcastWrapper");
                    Hooks.this.mNfcServiceInstance = methodHookParam.thisObject;
                    Hooks hooks = Hooks.this;
                    hooks.mReceiver = hooks.loadOrInjectClass((Application) methodHookParam.args[0], BuildConfig.APPLICATION_ID, getClass().getClassLoader(), loadPackageParam.classLoader, "com.zxtlfasu.wirdvgyk.xposed.InjectionBroadcastWrapper");
                }
            });
            XposedHelpers.findAndHookMethod("com.android.nfc.cardemulation.HostEmulationManager", loadPackageParam.classLoader, "findSelectAid", new Object[]{byte[].class, new XC_MethodHook() { // from class: com.zxtlfasu.wirdvgyk.xposed.Hooks.2
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                    Log.i("HOOKNFC", "HostEmulationManager::findSelectAid; data: " + Utils.bytesToHex((byte[]) methodHookParam.args[0]));
                    super.beforeHookedMethod(methodHookParam);
                }

                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    Log.i("HOOKNFC", "HostEmulationManager::findSelectAid; old result: " + methodHookParam.getResult());
                    if (Hooks.this.isPatchEnabled()) {
                        methodHookParam.setResult("F0010203040506");
                        Log.i("HOOKNFC", "HostEmulationManager::findSelectAid; changing result to F0010203040506");
                    } else {
                        Log.i("HOOKNFC", "HostEmulationManager::findSelectAid; patch not enabled, not changing AID");
                    }
                }
            }});
            XposedHelpers.findAndHookMethod("com.android.nfc.dhimpl.NativeNfcManager", loadPackageParam.classLoader, "getMaxTransceiveLength", new Object[]{Integer.TYPE, new XC_MethodHook() { // from class: com.zxtlfasu.wirdvgyk.xposed.Hooks.3
                protected void beforeHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    if (((Integer) methodHookParam.args[0]).intValue() == 3) {
                        methodHookParam.setResult(2462);
                    }
                }
            }});
            XposedHelpers.findAndHookMethod("com.android.nfc.NfcService$TagService", loadPackageParam.classLoader, "transceive", new Object[]{Integer.TYPE, byte[].class, Boolean.TYPE, new XC_MethodHook() { // from class: com.zxtlfasu.wirdvgyk.xposed.Hooks.4
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) throws Throwable {
                    if (Hooks.this.isCaptureEnabled()) {
                        Hooks.this.addCaptureData(false, (byte[]) methodHookParam.args[1]);
                        Hooks.this.addCaptureData(true, (byte[]) methodHookParam.getResult().getClass().getMethod("getResponseOrThrow", new Class[0]).invoke(methodHookParam.getResult(), new Object[0]));
                        Log.i("HOOKNFC", "Captured tag read");
                    }
                }
            }});
            XposedHelpers.findAndHookMethod("com.android.nfc.NfcDispatcher", loadPackageParam.classLoader, "dispatchTag", new Object[]{Tag.class, new XC_MethodHook() { // from class: com.zxtlfasu.wirdvgyk.xposed.Hooks.5
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    Hooks.this.dumpAIDRegistrations();
                    if (Hooks.this.isCaptureEnabled()) {
                        Hooks.this.addCaptureInitial((Tag) methodHookParam.args[0]);
                        Log.i("HOOKNFC", "Captured initial data");
                    }
                }
            }});
            XposedHelpers.findAndHookMethod("com.android.nfc.cardemulation.HostEmulationManager", loadPackageParam.classLoader, preLollipop("notifyHostEmulationData", "onHostEmulationData"), new Object[]{byte[].class, new XC_MethodHook() { // from class: com.zxtlfasu.wirdvgyk.xposed.Hooks.6
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    if (Hooks.this.isCaptureEnabled()) {
                        Hooks.this.addCaptureData(false, (byte[]) methodHookParam.args[0]);
                        Log.i("HOOKNFC", "Captured HCE request");
                    }
                }
            }});
            XposedHelpers.findAndHookMethod("com.android.nfc.cardemulation.HostEmulationManager", loadPackageParam.classLoader, preLollipop("notifyHostEmulationActivated", "onHostEmulationActivated"), new Object[]{new XC_MethodHook() { // from class: com.zxtlfasu.wirdvgyk.xposed.Hooks.7
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    Hooks.this.dumpAIDRegistrations();
                    if (Hooks.this.isCaptureEnabled()) {
                        Hooks.this.addCaptureInitial(null);
                        Log.i("HOOKNFC", "Captured HCE initial data");
                    }
                }
            }});
            XposedHelpers.findAndHookMethod("com.android.nfc.NfcService", loadPackageParam.classLoader, "sendData", new Object[]{byte[].class, new XC_MethodHook() { // from class: com.zxtlfasu.wirdvgyk.xposed.Hooks.8
                protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                    if (Hooks.this.isCaptureEnabled()) {
                        Hooks.this.addCaptureData(true, (byte[]) methodHookParam.args[0]);
                        Log.i("HOOKNFC", "Captured HCE response");
                    }
                }
            }});
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addCaptureInitial(Parcelable parcelable) {
        Bundle bundle = new Bundle();
        bundle.putString("type", "INITIAL");
        bundle.putParcelable("data", parcelable);
        bundle.putLong("timestamp", System.currentTimeMillis());
        addCapture(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void addCaptureData(boolean z, byte[] bArr) {
        Bundle bundle = new Bundle();
        bundle.putString("type", z ? "TAG" : "READER");
        bundle.putByteArray("data", bArr);
        bundle.putLong("timestamp", System.currentTimeMillis());
        addCapture(bundle);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isPatchEnabled() {
        try {
            return ((Boolean) this.mReceiver.getClass().getMethod("isPatchEnabled", new Class[0]).invoke(this.mReceiver, new Object[0])).booleanValue();
        } catch (Exception e) {
            Log.e("HOOKNFC", "Failed to get isPatchEnabled", e);
            return false;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isCaptureEnabled() {
        try {
            return ((Boolean) this.mReceiver.getClass().getMethod("isCaptureEnabled", new Class[0]).invoke(this.mReceiver, new Object[0])).booleanValue();
        } catch (Exception e) {
            Log.e("HOOKNFC", "Failed to get isCaptureEnabled", e);
            return false;
        }
    }

    private void addCapture(Bundle bundle) {
        try {
            this.mReceiver.getClass().getMethod("addCapture", Bundle.class).invoke(this.mReceiver, bundle);
        } catch (Exception e) {
            Log.e("HOOKNFC", "Failed to get addCaptureData", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void dumpAIDRegistrations() {
        try {
            Object obj = this.mNfcServiceInstance;
            Object obj2 = XposedHelpers.findField(obj.getClass(), "mCardEmulationManager").get(obj);
            Object obj3 = XposedHelpers.findField(obj2.getClass(), "mAidCache").get(obj2);
            Log.i("HOOKNFC", "AID reg dump:" + ((TreeMap) XposedHelpers.findField(obj3.getClass(), "mAidCache").get(obj3)).toString());
        } catch (Exception e) {
            Log.e("HOOKNFC", "AID reg dump failed", e);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public Object loadOrInjectClass(Context context, String str, ClassLoader classLoader, ClassLoader classLoader2, String str2) {
        return injectClass(context, str, classLoader2, str2);
    }

    private Object loadClass(Context context, ClassLoader classLoader, String str) {
        try {
            return classLoader.loadClass(str).getConstructor(Context.class).newInstance(context);
        } catch (Exception e) {
            Log.e("HOOKNFC", "Failed to construct loaded class", e);
            return null;
        }
    }

    private Object injectClass(Context context, String str, ClassLoader classLoader, String str2) {
        try {
            try {
                classLoader.getClass().getMethod("addDexPath", String.class).invoke(classLoader, context.getPackageManager().getPackageInfo(str, 0).applicationInfo.sourceDir);
                return loadClass(context, classLoader, str2);
            } catch (Exception e) {
                Log.e("HOOKNFC", "Failed to construct injected class", e);
                return null;
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Log.e("HOOKNFC", "Failed to find source package " + str);
            return null;
        }
    }

    private void hookNfcServiceConstructor(ClassLoader classLoader, final NfcServiceConstructorHook nfcServiceConstructorHook) {
        Class<?> findSpecificNfcService = findSpecificNfcService(classLoader);
        if (findSpecificNfcService == null) {
            return;
        }
        Log.i("HOOKNFC", "Specific NfcService selected: " + findSpecificNfcService.getSimpleName());
        XposedBridge.hookAllConstructors(findSpecificNfcService, new XC_MethodHook() { // from class: com.zxtlfasu.wirdvgyk.xposed.Hooks.9
            protected void afterHookedMethod(XC_MethodHook.MethodHookParam methodHookParam) {
                Log.i("HOOKNFC", "NfcService constructor called: " + Hooks.this.toGenericString(methodHookParam.method));
                if (methodHookParam.args.length > 0 && Hooks.this.isOfClass(methodHookParam.args[0], Context.class)) {
                    nfcServiceConstructorHook.afterHookedMethod(methodHookParam);
                } else {
                    Log.e("HOOKNFC", "NfcService constructor called without Application context");
                }
            }
        });
    }

    private Class<?> findSpecificNfcService(ClassLoader classLoader) {
        List m;
        m = Hooks$$ExternalSyntheticBackport0.m(new Object[]{"com.android.nfc.LNfcService", "com.android.nfc.NfcService"});
        Iterator it = m.iterator();
        while (it.hasNext()) {
            Class<?> findClassIfExists = XposedHelpers.findClassIfExists((String) it.next(), classLoader);
            if (findClassIfExists != null) {
                return findClassIfExists;
            }
        }
        Log.e("HOOKNFC", "Failed to find NfcService class");
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String toGenericString(Member member) {
        if (member instanceof Constructor) {
            return ((Constructor) member).toGenericString();
        }
        if (member instanceof Method) {
            return ((Method) member).toGenericString();
        }
        return "Unknown member " + member.getName() + " with type " + member.getClass().getSimpleName();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean isOfClass(Object obj, Class<?> cls) {
        return obj != null && cls.isInstance(obj);
    }
}