package com.zxtlfasu.wirdvgyk.gui.fragment;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.preference.PreferenceManager;
import com.zxtlfasu.wirdvgyk.R;
import com.zxtlfasu.wirdvgyk.db.NfcCommEntry;
import com.zxtlfasu.wirdvgyk.db.SessionLog;
import com.zxtlfasu.wirdvgyk.db.SessionLogJoin;
import com.zxtlfasu.wirdvgyk.db.model.SessionLogEntryViewModel;
import com.zxtlfasu.wirdvgyk.db.model.SessionLogEntryViewModelFactory;
import com.zxtlfasu.wirdvgyk.db.worker.LogInserter;
import com.zxtlfasu.wirdvgyk.gui.fragment.ReplayFragment;
import com.zxtlfasu.wirdvgyk.gui.log.LoggingFragment;
import com.zxtlfasu.wirdvgyk.gui.log.SessionLogEntryFragment;
import com.zxtlfasu.wirdvgyk.network.NetworkManager;
import com.zxtlfasu.wirdvgyk.network.data.NetworkStatus;
import com.zxtlfasu.wirdvgyk.nfc.NfcLogReplayer;
import com.zxtlfasu.wirdvgyk.nfc.modes.RelayMode;
import com.zxtlfasu.wirdvgyk.util.NfcComm;
import java.util.List;

/* loaded from: classes.dex */
public class ReplayFragment extends BaseNetworkFragment implements LoggingFragment.LogItemSelectedCallback, SessionLogEntryFragment.LogSelectedCallback {
    String mReplayMode;
    UIReplayer mReplayer;
    final LoggingFragment mLoggingFragment = new LoggingFragment();
    SessionLogEntryFragment mDetailFragment = null;
    List<NfcCommEntry> mSessionLog = null;
    boolean mOfflineReplay = true;

    @Override // com.zxtlfasu.wirdvgyk.gui.fragment.BaseNetworkFragment, androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View onCreateView = super.onCreateView(inflater, container, savedInstanceState);
        ((TextView) onCreateView.findViewById(R.id.txt_action)).setText(getString(R.string.replay_action));
        this.mLoggingFragment.setLogItemSelectedCallback(this);
        setSessionSelectionVisible(true);
        return onCreateView;
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroyView() {
        if (this.mLoggingFragment != null) {
            setSessionSelectionVisible(false);
        }
        if (this.mDetailFragment != null) {
            setSessionChooserVisible(false, 0);
        }
        super.onDestroyView();
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        this.mLogInserter = new LogInserter(getActivity(), SessionLog.SessionType.REPLAY, this);
        SharedPreferences defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        this.mOfflineReplay = !defaultSharedPreferences.getBoolean("network", false);
        this.mReplayMode = defaultSharedPreferences.getString("mode", "index");
        this.mStatusBanner.setVisibility(!this.mOfflineReplay);
    }

    @Override // com.zxtlfasu.wirdvgyk.gui.log.LoggingFragment.LogItemSelectedCallback
    public void onLogItemSelected(int sessionId) {
        setSessionSelectionVisible(false);
        setSessionChooserVisible(true, sessionId);
    }

    @Override // com.zxtlfasu.wirdvgyk.gui.log.SessionLogEntryFragment.LogSelectedCallback
    public void onLogSelected(long sessionId) {
        getMainActivity().getSupportActionBar().setSubtitle(getString(R.string.replay_session, Long.valueOf(sessionId)));
        setSessionChooserVisible(false, -1);
        ((SessionLogEntryViewModel) ViewModelProviders.of(this, new SessionLogEntryViewModelFactory(getActivity().getApplication(), sessionId)).get(SessionLogEntryViewModel.class)).getSession().observe(this, new AnonymousClass1());
    }

    /* renamed from: com.zxtlfasu.wirdvgyk.gui.fragment.ReplayFragment$1, reason: invalid class name */
    class AnonymousClass1 implements Observer<SessionLogJoin> {
        boolean mOnce = true;

        AnonymousClass1() {
        }

        @Override // androidx.lifecycle.Observer
        public void onChanged(SessionLogJoin sessionLogJoin) {
            if (sessionLogJoin != null && ReplayFragment.this.mSessionLog == null && this.mOnce) {
                this.mOnce = false;
                ReplayFragment.this.mSessionLog = sessionLogJoin.getNfcCommEntries();
                ReplayFragment.this.getActivity().runOnUiThread(new Runnable() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.ReplayFragment$1$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ReplayFragment.AnonymousClass1.this.m184x244aef17();
                    }
                });
            }
        }

        /* renamed from: lambda$onChanged$0$com-zxtlfasu-wirdvgyk-gui-fragment-ReplayFragment$1, reason: not valid java name */
        /* synthetic */ void m184x244aef17() {
            ReplayFragment.this.setSelectorVisible(true);
        }
    }

    @Override // com.zxtlfasu.wirdvgyk.gui.fragment.BaseNetworkFragment
    protected void reset() {
        super.reset();
        this.mStatusBanner.setVisibility(!this.mOfflineReplay);
        this.mSessionLog = null;
        setSessionSelectionVisible(true);
        setSelectorVisible(false);
        setTagWaitVisible(false, false);
        UIReplayer uIReplayer = this.mReplayer;
        if (uIReplayer != null) {
            uIReplayer.reset();
        }
        getMainActivity().getSupportActionBar().setSubtitle(getString(R.string.replay_session_select));
    }

    @Override // com.zxtlfasu.wirdvgyk.gui.fragment.BaseNetworkFragment
    protected void onSelect(boolean reader) {
        if (this.mOfflineReplay || checkNetwork()) {
            if (!this.mOfflineReplay) {
                handleStatus(NetworkStatus.CONNECTING);
            }
            setSelectorVisible(false);
            setTagWaitVisible(true, !reader);
            this.mReplayer = new UIReplayer(reader);
            getNfc().startMode(new UIReplayMode(reader));
            tickleReplayer();
        }
    }

    void setSessionSelectionVisible(boolean visible) {
        FragmentTransaction beginTransaction = getMainActivity().getSupportFragmentManager().beginTransaction();
        if (visible) {
            beginTransaction.replace(R.id.lay_content, this.mLoggingFragment).commit();
        } else {
            beginTransaction.remove(this.mLoggingFragment).commitAllowingStateLoss();
        }
    }

    void setSessionChooserVisible(boolean visible, int sessionId) {
        FragmentTransaction beginTransaction = getMainActivity().getSupportFragmentManager().beginTransaction();
        if (visible) {
            SessionLogEntryFragment newInstance = SessionLogEntryFragment.newInstance(sessionId, SessionLogEntryFragment.Type.SELECT, this);
            this.mDetailFragment = newInstance;
            beginTransaction.replace(R.id.lay_content, newInstance).commit();
        } else {
            SessionLogEntryFragment sessionLogEntryFragment = this.mDetailFragment;
            if (sessionLogEntryFragment != null) {
                beginTransaction.remove(sessionLogEntryFragment).commitAllowingStateLoss();
            }
        }
    }

    /* renamed from: lambda$tickleReplayer$0$com-zxtlfasu-wirdvgyk-gui-fragment-ReplayFragment, reason: not valid java name */
    /* synthetic */ void m183x72afdf99() {
        this.mReplayer.onReceive(null);
    }

    void tickleReplayer() {
        getActivity().runOnUiThread(new Runnable() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.ReplayFragment$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ReplayFragment.this.m183x72afdf99();
            }
        });
    }

    class UIReplayMode extends RelayMode {
        UIReplayMode(boolean reader) {
            super(reader, null, null);
            this.mOnline = !ReplayFragment.this.mOfflineReplay;
        }

        void runOnUI(Runnable r) {
            FragmentActivity activity = ReplayFragment.this.getActivity();
            if (activity != null) {
                activity.runOnUiThread(r);
            }
        }

        @Override // com.zxtlfasu.wirdvgyk.nfc.modes.RelayMode, com.zxtlfasu.wirdvgyk.nfc.modes.BaseMode
        public void onData(boolean isForeign, NfcComm data) {
            ReplayFragment.this.mLogInserter.log(data);
            runOnUI(new Runnable() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.ReplayFragment$UIReplayMode$$ExternalSyntheticLambda2
                @Override // java.lang.Runnable
                public final void run() {
                    ReplayFragment.UIReplayMode.this.m185x9c7bf08a();
                }
            });
            super.onData(isForeign, data);
        }

        /* renamed from: lambda$onData$0$com-zxtlfasu-wirdvgyk-gui-fragment-ReplayFragment$UIReplayMode, reason: not valid java name */
        /* synthetic */ void m185x9c7bf08a() {
            ReplayFragment.this.setTagWaitVisible(false, false);
        }

        @Override // com.zxtlfasu.wirdvgyk.nfc.modes.RelayMode
        protected void toNetwork(final NfcComm data) {
            if (!ReplayFragment.this.mOfflineReplay) {
                super.toNetwork(data);
            } else {
                runOnUI(new Runnable() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.ReplayFragment$UIReplayMode$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        ReplayFragment.UIReplayMode.this.m187xcde9dd79(data);
                    }
                });
            }
        }

        /* renamed from: lambda$toNetwork$1$com-zxtlfasu-wirdvgyk-gui-fragment-ReplayFragment$UIReplayMode, reason: not valid java name */
        /* synthetic */ void m187xcde9dd79(NfcComm nfcComm) {
            ReplayFragment.this.mReplayer.onReceive(nfcComm);
        }

        @Override // com.zxtlfasu.wirdvgyk.nfc.modes.RelayMode, com.zxtlfasu.wirdvgyk.nfc.modes.BaseMode
        public void onNetworkStatus(final NetworkStatus status) {
            super.onNetworkStatus(status);
            runOnUI(new Runnable() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.ReplayFragment$UIReplayMode$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ReplayFragment.UIReplayMode.this.m186xdadb106a(status);
                }
            });
        }

        /* renamed from: lambda$onNetworkStatus$2$com-zxtlfasu-wirdvgyk-gui-fragment-ReplayFragment$UIReplayMode, reason: not valid java name */
        /* synthetic */ void m186xdadb106a(NetworkStatus networkStatus) {
            ReplayFragment.this.handleStatus(networkStatus);
        }
    }

    class UIReplayer implements NetworkManager.Callback {
        NetworkManager mReplayNetwork;
        final NfcLogReplayer mReplayer;

        UIReplayer(boolean reader) {
            this.mReplayNetwork = null;
            this.mReplayer = new NfcLogReplayer(reader, ReplayFragment.this.mReplayMode, ReplayFragment.this.mSessionLog);
            if (ReplayFragment.this.mOfflineReplay) {
                return;
            }
            NetworkManager networkManager = new NetworkManager(ReplayFragment.this.getMainActivity(), this);
            this.mReplayNetwork = networkManager;
            networkManager.connect();
        }

        void reset() {
            NetworkManager networkManager = this.mReplayNetwork;
            if (networkManager != null) {
                networkManager.disconnect();
            }
        }

        @Override // com.zxtlfasu.wirdvgyk.network.NetworkManager.Callback
        public void onReceive(NfcComm data) {
            NfcComm response = this.mReplayer.getResponse(data);
            if (response != null && ReplayFragment.this.mOfflineReplay) {
                ReplayFragment.this.getNfc().handleData(true, response);
            } else if (response != null) {
                this.mReplayNetwork.send(response);
            }
            if (this.mReplayer.shouldWait()) {
                return;
            }
            ReplayFragment.this.tickleReplayer();
        }

        @Override // com.zxtlfasu.wirdvgyk.network.NetworkManager.Callback
        public void onNetworkStatus(final NetworkStatus status) {
            FragmentActivity activity = ReplayFragment.this.getActivity();
            if (activity != null) {
                activity.runOnUiThread(new Runnable() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.ReplayFragment$UIReplayer$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ReplayFragment.UIReplayer.this.m188x99f00dd6(status);
                    }
                });
            }
        }

        /* renamed from: lambda$onNetworkStatus$0$com-zxtlfasu-wirdvgyk-gui-fragment-ReplayFragment$UIReplayer, reason: not valid java name */
        /* synthetic */ void m188x99f00dd6(NetworkStatus networkStatus) {
            ReplayFragment.this.handleStatus(networkStatus);
        }
    }
}