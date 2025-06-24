package com.zxtlfasu.wirdvgyk.gui.fragment;

import androidx.fragment.app.Fragment;
import com.zxtlfasu.wirdvgyk.gui.MainActivity;
import com.zxtlfasu.wirdvgyk.nfc.NfcManager;

/* loaded from: classes.dex */
public abstract class BaseFragment extends Fragment {
    protected NfcManager getNfc() {
        return getMainActivity().getNfc();
    }

    protected MainActivity getMainActivity() {
        return (MainActivity) getActivity();
    }
}