package com.zxtlfasu.wirdvgyk.gui.log;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.zxtlfasu.wirdvgyk.db.AppDatabase;
import com.zxtlfasu.wirdvgyk.db.NfcCommEntry;
import com.zxtlfasu.wirdvgyk.db.SessionLog;
import com.zxtlfasu.wirdvgyk.db.SessionLogJoin;
import com.zxtlfasu.wirdvgyk.db.model.SessionLogEntryViewModel;
import com.zxtlfasu.wirdvgyk.db.model.SessionLogEntryViewModelFactory;
import com.zxtlfasu.wirdvgyk.db.pcapng.ISO14443Stream;
import com.zxtlfasu.wirdvgyk.gui.component.FileShare;
import com.zxtlfasu.wirdvgyk.util.NfcComm;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class LogAction {
    private final Fragment mFragment;
    private final List<NfcComm> mLogItems = new ArrayList();

    public LogAction(Fragment fragment) {
        this.mFragment = fragment;
    }

    /* JADX WARN: Type inference failed for: r0v0, types: [com.zxtlfasu.wirdvgyk.gui.log.LogAction$1] */
    public void delete(final SessionLog session) {
        new Thread() { // from class: com.zxtlfasu.wirdvgyk.gui.log.LogAction.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                AppDatabase.getDatabase(LogAction.this.mFragment.getActivity()).sessionLogDao().delete(session);
            }
        }.start();
    }

    public void share(final SessionLog session) {
        this.mLogItems.clear();
        ((SessionLogEntryViewModel) ViewModelProviders.of(this.mFragment, new SessionLogEntryViewModelFactory(this.mFragment.getActivity().getApplication(), session.getId())).get(SessionLogEntryViewModel.class)).getSession().observe(this.mFragment, new Observer() { // from class: com.zxtlfasu.wirdvgyk.gui.log.LogAction$$ExternalSyntheticLambda0
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                LogAction.this.m190lambda$share$0$comzxtlfasuwirdvgykguilogLogAction(session, (SessionLogJoin) obj);
            }
        });
    }

    /* renamed from: lambda$share$0$com-zxtlfasu-wirdvgyk-gui-log-LogAction, reason: not valid java name */
    /* synthetic */ void m190lambda$share$0$comzxtlfasuwirdvgykguilogLogAction(SessionLog sessionLog, SessionLogJoin sessionLogJoin) {
        if (sessionLogJoin == null || !this.mLogItems.isEmpty()) {
            return;
        }
        Iterator<NfcCommEntry> it = sessionLogJoin.getNfcCommEntries().iterator();
        while (it.hasNext()) {
            this.mLogItems.add(it.next().getNfcComm());
        }
        share(sessionLog, this.mLogItems);
    }

    public void share(SessionLog sessionLog, List<NfcComm> logItems) {
        new FileShare(this.mFragment.getActivity()).setPrefix(sessionLog.toString()).setExtension(".pcapng").setMimeType("application/*").share(new ISO14443Stream().append(logItems));
    }
}