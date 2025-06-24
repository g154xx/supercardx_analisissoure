package com.zxtlfasu.wirdvgyk.gui.fragment;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.preference.PreferenceManager;
import com.apireflectionmanager.AdvancedApiReflection;
import com.zxtlfasu.wirdvgyk.R;
import com.zxtlfasu.wirdvgyk.db.worker.LogInserter;
import com.zxtlfasu.wirdvgyk.gui.component.StatusBanner;
import com.zxtlfasu.wirdvgyk.gui.dialog.CertificateTrustDialogFragment;
import com.zxtlfasu.wirdvgyk.gui.log.SessionLogEntryFragment;
import com.zxtlfasu.wirdvgyk.network.data.NetworkStatus;

/* loaded from: classes.dex */
public abstract class BaseNetworkFragment extends BaseFragment implements LogInserter.SIDChangedListener {
    SessionLogEntryFragment mLogFragment;
    LogInserter mLogInserter;
    LinearLayout mSelector;
    StatusBanner mStatusBanner;
    View mTagWaiting;
    TextView mTagWaitingText;

    protected abstract void onSelect(boolean reader);

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_network, container, false);
        this.mTagWaiting = inflate.findViewById(R.id.tag_wait);
        this.mTagWaitingText = (TextView) inflate.findViewById(R.id.tag_wait_text);
        this.mSelector = (LinearLayout) inflate.findViewById(R.id.selector);
        this.mStatusBanner = new StatusBanner(getMainActivity());
        ((LinearLayout) inflate.findViewById(R.id.select_reader)).setOnClickListener(new View.OnClickListener() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.BaseNetworkFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BaseNetworkFragment.this.m170xa35da5d5(view);
            }
        });
        ((LinearLayout) inflate.findViewById(R.id.select_tag)).setOnClickListener(new View.OnClickListener() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.BaseNetworkFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                BaseNetworkFragment.this.m171xa493f8b4(view);
            }
        });
        setHasOptionsMenu(true);
        reset();
        return inflate;
    }

    /* renamed from: lambda$onCreateView$0$com-zxtlfasu-wirdvgyk-gui-fragment-BaseNetworkFragment, reason: not valid java name */
    /* synthetic */ void m170xa35da5d5(View view) {
        onSelect(true);
    }

    /* renamed from: lambda$onCreateView$1$com-zxtlfasu-wirdvgyk-gui-fragment-BaseNetworkFragment, reason: not valid java name */
    /* synthetic */ void m171xa493f8b4(View view) {
        onSelect(false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_relay, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_refresh) {
            reset();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override // com.zxtlfasu.wirdvgyk.db.worker.LogInserter.SIDChangedListener
    public void onSIDChanged(long sessionID) {
        if (this.mLogFragment != null) {
            getMainActivity().getSupportFragmentManager().beginTransaction().remove(this.mLogFragment).commit();
        }
        if (sessionID > -1) {
            this.mLogFragment = SessionLogEntryFragment.newInstance(sessionID, SessionLogEntryFragment.Type.LIVE, null);
            getMainActivity().getSupportFragmentManager().beginTransaction().replace(R.id.lay_content, this.mLogFragment).commit();
        }
    }

    protected void setSelectorVisible(boolean visible) {
        this.mSelector.setVisibility(visible ? 0 : 8);
    }

    protected void setTagWaitVisible(boolean visible, boolean reader) {
        TextView textView = this.mTagWaitingText;
        Object[] objArr = new Object[1];
        objArr[0] = getString(reader ? R.string.network_reader : R.string.network_tag);
        textView.setText(getString(R.string.network_waiting_for, objArr));
        this.mTagWaiting.setVisibility(visible ? 0 : 8);
    }

    /* renamed from: com.zxtlfasu.wirdvgyk.gui.fragment.BaseNetworkFragment$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$zxtlfasu$wirdvgyk$network$data$NetworkStatus;

        static {
            int[] iArr = new int[NetworkStatus.values().length];
            $SwitchMap$com$zxtlfasu$wirdvgyk$network$data$NetworkStatus = iArr;
            try {
                iArr[NetworkStatus.ERROR.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$network$data$NetworkStatus[NetworkStatus.ERROR_TLS.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$network$data$NetworkStatus[NetworkStatus.ERROR_TLS_CERT_UNKNOWN.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$network$data$NetworkStatus[NetworkStatus.ERROR_TLS_CERT_UNTRUSTED.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$network$data$NetworkStatus[NetworkStatus.CONNECTING.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$network$data$NetworkStatus[NetworkStatus.CONNECTED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$network$data$NetworkStatus[NetworkStatus.PARTNER_CONNECT.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$network$data$NetworkStatus[NetworkStatus.PARTNER_LEFT.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
        }
    }

    protected void handleStatus(NetworkStatus status) {
        switch (AnonymousClass1.$SwitchMap$com$zxtlfasu$wirdvgyk$network$data$NetworkStatus[status.ordinal()]) {
            case 1:
                this.mStatusBanner.setError(getString(R.string.network_error));
                break;
            case 2:
                this.mStatusBanner.setError(getString(R.string.network_tls_error));
                break;
            case 3:
                this.mStatusBanner.setWarning(getString(R.string.network_tls_unknown));
                new CertificateTrustDialogFragment().show(getMainActivity().getSupportFragmentManager(), "trust");
                break;
            case 4:
                this.mStatusBanner.setError(getString(R.string.network_tls_untrusted));
                break;
            case 5:
                this.mStatusBanner.setError(getString(R.string.network_connecting));
                break;
            case 6:
                this.mStatusBanner.setWarning(getString(R.string.network_connected_wait));
                break;
            case 7:
                this.mStatusBanner.setSuccess(getString(R.string.network_connected));
                break;
            case 8:
                this.mStatusBanner.setError(getString(R.string.network_disconnected));
                break;
        }
    }

    private boolean isNetworkAvailable() {
        NetworkInfo networkInfo = (NetworkInfo) AdvancedApiReflection.obfuscate(19, (ConnectivityManager) getActivity().getSystemService("connectivity"), null);
        return networkInfo != null && networkInfo.isConnected();
    }

    private boolean isServerConfigured() {
        return !PreferenceManager.getDefaultSharedPreferences(getActivity()).getString("host", "").isEmpty();
    }

    protected boolean checkNetwork() {
        if (!isNetworkAvailable()) {
            getMainActivity().showWarning(getString(R.string.error_no_connection));
            return false;
        }
        if (!isServerConfigured()) {
            getMainActivity().showWarning(getString(R.string.error_no_hostname));
            return false;
        }
        if (getNfc().isEnabled()) {
            return true;
        }
        getMainActivity().showWarning(getString(R.string.error_nfc_disabled));
        return false;
    }

    protected void reset() {
        getNfc().stopMode();
        this.mStatusBanner.set(StatusBanner.State.IDLE, getString(R.string.network_idle));
        LogInserter logInserter = this.mLogInserter;
        if (logInserter != null) {
            logInserter.reset();
        }
    }
}