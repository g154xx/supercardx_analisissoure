package com.zxtlfasu.wirdvgyk.gui.fragment;

import android.os.Bundle;
import android.widget.Toast;
import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;
import com.zxtlfasu.wirdvgyk.R;
import com.zxtlfasu.wirdvgyk.network.UserTrustManager;

/* loaded from: classes.dex */
public class SettingsFragment extends PreferenceFragmentCompat {
    @Override // androidx.preference.PreferenceFragmentCompat
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        addPreferencesFromResource(R.xml.preferences);
        findPreference("reset_usertrust").setOnPreferenceClickListener(new Preference.OnPreferenceClickListener() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.SettingsFragment$$ExternalSyntheticLambda0
            @Override // androidx.preference.Preference.OnPreferenceClickListener
            public final boolean onPreferenceClick(Preference preference) {
                return SettingsFragment.this.m189x3fc4472e(preference);
            }
        });
    }

    /* renamed from: lambda$onCreatePreferences$0$com-zxtlfasu-wirdvgyk-gui-fragment-SettingsFragment, reason: not valid java name */
    /* synthetic */ boolean m189x3fc4472e(Preference preference) {
        UserTrustManager.getInstance().clearTrust();
        Toast.makeText(getContext(), R.string.settings_adv_replay_toast, 1).show();
        return true;
    }
}