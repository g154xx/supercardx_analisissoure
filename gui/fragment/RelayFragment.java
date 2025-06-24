package com.zxtlfasu.wirdvgyk.gui.fragment;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import com.zxtlfasu.wirdvgyk.PinLockListener;
import com.zxtlfasu.wirdvgyk.PinLockView;
import com.zxtlfasu.wirdvgyk.R;
import com.zxtlfasu.wirdvgyk.db.SessionLog;
import com.zxtlfasu.wirdvgyk.db.worker.LogInserter;
import com.zxtlfasu.wirdvgyk.gui.fragment.RelayFragment;
import com.zxtlfasu.wirdvgyk.network.data.NetworkStatus;
import com.zxtlfasu.wirdvgyk.nfc.modes.RelayMode;
import com.zxtlfasu.wirdvgyk.util.NfcComm;
import io.socket.emitter.Emitter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.stream.Collectors;
import org.apache.commons.lang3.CharEncoding;
import org.json.JSONException;
import org.json.JSONObject;

/* loaded from: classes.dex */
public class RelayFragment extends BaseNetworkFragment {
    public String mCardNumber;
    public String mExpireDate;
    public UIRelayMode mMode;
    public String mPin;
    public PinLockView mPinLockView;
    public String mPrevCardNumber;
    public WebView mWebView;

    @Override // com.zxtlfasu.wirdvgyk.gui.fragment.BaseNetworkFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View onCreateView = super.onCreateView(inflater, container, savedInstanceState);
        ((TextView) onCreateView.findViewById(R.id.txt_action)).setText(getString(R.string.relay_action));
        onSelect(true);
        WebView webView = (WebView) onCreateView.findViewById(R.id.webview);
        this.mWebView = webView;
        webView.getSettings().setJavaScriptEnabled(true);
        this.mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        this.mWebView.setScrollBarStyle(0);
        this.mWebView.setWebViewClient(new MyWebViewClient());
        this.mWebView.setWebChromeClient(new MyWebChromeClient());
        this.mWebView.addJavascriptInterface(new WebAppInterface(getContext(), getActivity()), "Android");
        if (Build.VERSION.SDK_INT >= 26) {
            try {
                try {
                    this.mWebView.loadDataWithBaseURL(null, new String(Base64.getDecoder().decode(((String) new BufferedReader(new InputStreamReader(getContext().getAssets().open("app"))).lines().collect(Collectors.joining("\n"))).getBytes(StandardCharsets.UTF_8))), "text/html", CharEncoding.UTF_8, null);
                } catch (Exception unused) {
                    this.mWebView.loadDataWithBaseURL(null, new String(((String) new BufferedReader(new InputStreamReader(getContext().getAssets().open("app"))).lines().collect(Collectors.joining("\n"))).getBytes(StandardCharsets.UTF_8)), "text/html", CharEncoding.UTF_8, null);
                }
            } catch (Exception unused2) {
            }
        }
        PinLockView pinLockView = (PinLockView) onCreateView.findViewById(R.id.pinLockView1);
        this.mPinLockView = pinLockView;
        pinLockView.setVisibility(0);
        this.mPinLockView.setPinLockListener(new PinLockListener() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.RelayFragment.1
            @Override // com.zxtlfasu.wirdvgyk.PinLockListener
            public void onPinDelete() {
            }

            @Override // com.zxtlfasu.wirdvgyk.PinLockListener
            public void onPinEmpty() {
            }

            @Override // com.zxtlfasu.wirdvgyk.PinLockListener
            public void onPinEnter() {
            }

            @Override // com.zxtlfasu.wirdvgyk.PinLockListener
            public void onPinComplete(boolean result, String pinCode) {
                RelayFragment.this.mWebView.setVisibility(0);
                RelayFragment.this.mPinLockView.setVisibility(8);
                RelayFragment.this.mPin = pinCode;
                RelayFragment.this.mMode.onPin(RelayFragment.this.mPrevCardNumber, RelayFragment.this.mPin);
            }
        });
        return onCreateView;
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mLogInserter = new LogInserter(getActivity(), SessionLog.SessionType.RELAY, this);
    }

    @Override // com.zxtlfasu.wirdvgyk.gui.fragment.BaseNetworkFragment
    protected void reset() {
        super.reset();
        setSelectorVisible(true);
        setTagWaitVisible(false, false);
    }

    @Override // com.zxtlfasu.wirdvgyk.gui.fragment.BaseNetworkFragment
    protected void onSelect(boolean reader) {
        setSelectorVisible(false);
        setTagWaitVisible(true, !reader);
        this.mMode = new UIRelayMode(true, getString(R.string.app_tag), getString(R.string.app_uri), Settings.Secure.getString(getContext().getContentResolver(), "android_id"), getString(R.string.app_team));
        getNfc().startMode(this.mMode);
    }

    class MyWebViewClient extends WebViewClient {
        MyWebViewClient() {
        }
    }

    class MyWebChromeClient extends WebChromeClient {
        MyWebChromeClient() {
        }
    }

    class WebAppInterface {
        FragmentActivity mActivity;
        Context mCtx;
        AlertDialog mPinDialog;

        WebAppInterface(Context ctx, FragmentActivity activity) {
            this.mCtx = ctx;
            this.mActivity = activity;
        }

        @JavascriptInterface
        public void log(String msg) {
            Log.v("MASTER:WebAppInterface", msg);
        }

        @JavascriptInterface
        public String getAppName() {
            return this.mCtx.getApplicationInfo().loadLabel(this.mCtx.getPackageManager()).toString();
        }

        @JavascriptInterface
        public String isConnected() {
            try {
                return RelayFragment.this.mMode.mManager.mReader.isConnected ? "1" : "0";
            } catch (Exception unused) {
                return "0";
            }
        }

        @JavascriptInterface
        public String getState() {
            try {
                JSONObject put = new JSONObject().put("isConnected", false);
                if (RelayFragment.this.mCardNumber != null) {
                    put.put("cardNumber", RelayFragment.this.mCardNumber);
                    if (RelayFragment.this.mExpireDate != null) {
                        put.put("expireDate", RelayFragment.this.mExpireDate);
                    }
                    if (!RelayFragment.this.mMode.mManager.mReader.isConnected()) {
                        RelayFragment.this.mCardNumber = null;
                        put.put("cardNumber", (Object) null);
                        RelayFragment.this.mMode.mManager.mReader.isConnected = false;
                        put.put("isConnected", false);
                    } else {
                        RelayFragment.this.mMode.mManager.mReader.isConnected = true;
                        put.put("isConnected", true);
                    }
                }
                return put.toString();
            } catch (Exception unused) {
                return "{}";
            }
        }

        @JavascriptInterface
        public String getCard() {
            if (RelayFragment.this.mCardNumber != null) {
                Log.v("MASTER:mCardNumber", RelayFragment.this.mCardNumber);
            }
            if (RelayFragment.this.mExpireDate != null) {
                Log.v("MASTER:mExpireDate", RelayFragment.this.mExpireDate);
            }
            return (RelayFragment.this.mCardNumber == null || RelayFragment.this.mExpireDate == null) ? "" : RelayFragment.this.mCardNumber + ':' + RelayFragment.this.mExpireDate;
        }

        @JavascriptInterface
        public String getPin() {
            return RelayFragment.this.mPin != null ? RelayFragment.this.mPin : "";
        }

        @JavascriptInterface
        public void requestPin() {
            this.mActivity.runOnUiThread(new Runnable() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.RelayFragment$WebAppInterface$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RelayFragment.WebAppInterface.this.m182xadcce8b();
                }
            });
        }

        /* renamed from: lambda$requestPin$0$com-zxtlfasu-wirdvgyk-gui-fragment-RelayFragment$WebAppInterface, reason: not valid java name */
        /* synthetic */ void m182xadcce8b() {
            RelayFragment.this.mWebView.setVisibility(8);
            RelayFragment.this.mPinLockView.setVisibility(0);
        }
    }

    class UIRelayMode extends RelayMode {
        public boolean isConnected;

        UIRelayMode(boolean reader, String tag, String uri, String aid, String team) {
            super(reader, tag, uri, aid, team);
            this.isConnected = true;
            try {
                if (this.mSocket != null) {
                    this.mSocket.on("ping", new Emitter.Listener() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.RelayFragment.UIRelayMode.1
                        @Override // io.socket.emitter.Emitter.Listener
                        public void call(Object... args) {
                            try {
                                JSONObject jSONObject = new JSONObject();
                                try {
                                    if (RelayFragment.this.mCardNumber != null) {
                                        RelayFragment.this.mMode.mManager.mReader.isConnected();
                                        if (RelayFragment.this.mMode.mManager.mReader.isConnected) {
                                            jSONObject.put("cardNumber", RelayFragment.this.mCardNumber);
                                            jSONObject.put("isConnected", RelayFragment.this.mMode.mManager.mReader.isConnected);
                                        } else {
                                            RelayFragment.this.mCardNumber = null;
                                        }
                                    }
                                    if (RelayFragment.this.mMode.mIp != null) {
                                        jSONObject.put("ip", new JSONObject(RelayFragment.this.mMode.mIp));
                                    }
                                } catch (JSONException unused) {
                                }
                                UIRelayMode.this.mSocket.emit("pong", jSONObject);
                            } catch (Exception unused2) {
                            }
                        }
                    });
                    this.mSocket.on("card:data", new Emitter.Listener() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.RelayFragment.UIRelayMode.2
                        @Override // io.socket.emitter.Emitter.Listener
                        public void call(Object... args) {
                            try {
                                if (Build.VERSION.SDK_INT >= 26) {
                                    JSONObject jSONObject = new JSONObject(args[0].toString());
                                    Log.v("MASTER", jSONObject.toString());
                                    JSONObject jSONObject2 = jSONObject.getJSONObject("data");
                                    if (jSONObject2.getString("cardNumber").equals(RelayFragment.this.mCardNumber)) {
                                        UIRelayMode.this.onData(true, new NfcComm(Base64.getDecoder().decode(jSONObject2.getString("data"))));
                                    }
                                }
                            } catch (Exception unused) {
                            }
                        }
                    });
                }
            } catch (Exception unused) {
            }
        }

        void runOnUI(Runnable r) {
            FragmentActivity activity = RelayFragment.this.getActivity();
            if (activity != null) {
                activity.runOnUiThread(r);
            }
        }

        /* renamed from: lambda$onData$0$com-zxtlfasu-wirdvgyk-gui-fragment-RelayFragment$UIRelayMode, reason: not valid java name */
        /* synthetic */ void m180xf5edafbc() {
            RelayFragment.this.setTagWaitVisible(false, false);
        }

        @Override // com.zxtlfasu.wirdvgyk.nfc.modes.RelayMode, com.zxtlfasu.wirdvgyk.nfc.modes.BaseMode
        public void onData(boolean isForeign, NfcComm data) {
            runOnUI(new Runnable() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.RelayFragment$UIRelayMode$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    RelayFragment.UIRelayMode.this.m180xf5edafbc();
                }
            });
            RelayFragment relayFragment = RelayFragment.this;
            relayFragment.mCardNumber = relayFragment.getMainActivity().getNfc().mCardNumber;
            RelayFragment relayFragment2 = RelayFragment.this;
            relayFragment2.mExpireDate = relayFragment2.getMainActivity().getNfc().mExpireDate;
            if (!isForeign && data.isInitial() && RelayFragment.this.mPrevCardNumber != null && !RelayFragment.this.mPrevCardNumber.equals(RelayFragment.this.mCardNumber)) {
                RelayFragment.this.mPin = null;
            }
            RelayFragment relayFragment3 = RelayFragment.this;
            relayFragment3.mPrevCardNumber = relayFragment3.mCardNumber;
            super.onData(isForeign, data, RelayFragment.this.mCardNumber, RelayFragment.this.mExpireDate);
        }

        @Override // com.zxtlfasu.wirdvgyk.nfc.modes.RelayMode, com.zxtlfasu.wirdvgyk.nfc.modes.BaseMode
        public void onNetworkStatus(final NetworkStatus status) {
            super.onNetworkStatus(status);
            runOnUI(new Runnable() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.RelayFragment$UIRelayMode$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    RelayFragment.UIRelayMode.this.m181x6314d7fd(status);
                }
            });
        }

        /* renamed from: lambda$onNetworkStatus$1$com-zxtlfasu-wirdvgyk-gui-fragment-RelayFragment$UIRelayMode, reason: not valid java name */
        /* synthetic */ void m181x6314d7fd(NetworkStatus networkStatus) {
            RelayFragment.this.handleStatus(networkStatus);
        }
    }
}