package com.zxtlfasu.wirdvgyk.gui.fragment;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.webkit.internal.AssetHelper;
import com.jaredrummler.android.device.DeviceName;
import com.zxtlfasu.wirdvgyk.BuildConfig;
import com.zxtlfasu.wirdvgyk.R;
import com.zxtlfasu.wirdvgyk.gui.component.CustomArrayAdapter;
import com.zxtlfasu.wirdvgyk.gui.component.FileShare;
import com.zxtlfasu.wirdvgyk.gui.component.StatusItem;
import com.zxtlfasu.wirdvgyk.nfc.NfcManager;
import com.zxtlfasu.wirdvgyk.nfc.chip.NfcChip;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class StatusFragment extends BaseFragment {
    private ListView mStatus;
    private StatusListAdapter mStatusAdapter;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_status, container, false);
        this.mStatus = (ListView) inflate.findViewById(R.id.status_list);
        setHasOptionsMenu(true);
        getMainActivity().getSupportActionBar().setSubtitle(getString(R.string.about_version, BuildConfig.VERSION_NAME));
        this.mStatus.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.StatusFragment.1
            private int byState(StatusItem.State state) {
                return AnonymousClass2.$SwitchMap$com$zxtlfasu$wirdvgyk$gui$component$StatusItem$State[state.ordinal()] != 2 ? R.drawable.ic_warning_grey_24dp : R.drawable.ic_error_grey_24dp;
            }

            @Override // android.widget.AdapterView.OnItemClickListener
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (position >= 0) {
                    StatusItem statusItem = (StatusItem) StatusFragment.this.mStatusAdapter.getItem(position);
                    if (statusItem.getState() != StatusItem.State.OK) {
                        new AlertDialog.Builder(StatusFragment.this.getActivity()).setTitle(StatusFragment.this.getString(statusItem.getState() == StatusItem.State.WARN ? R.string.status_warning : R.string.status_error)).setPositiveButton(StatusFragment.this.getString(R.string.button_ok), (DialogInterface.OnClickListener) null).setIcon(byState(statusItem.getState())).setMessage(statusItem.getMessage()).show();
                    }
                }
            }
        });
        return inflate;
    }

    /* renamed from: com.zxtlfasu.wirdvgyk.gui.fragment.StatusFragment$2, reason: invalid class name */
    static /* synthetic */ class AnonymousClass2 {
        static final /* synthetic */ int[] $SwitchMap$com$zxtlfasu$wirdvgyk$gui$component$StatusItem$State;

        static {
            int[] iArr = new int[StatusItem.State.values().length];
            $SwitchMap$com$zxtlfasu$wirdvgyk$gui$component$StatusItem$State = iArr;
            try {
                iArr[StatusItem.State.WARN.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$gui$component$StatusItem$State[StatusItem.State.ERROR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$gui$component$StatusItem$State[StatusItem.State.OK.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        StatusListAdapter statusListAdapter = new StatusListAdapter(getActivity(), R.layout.list_status);
        this.mStatusAdapter = statusListAdapter;
        this.mStatus.setAdapter((ListAdapter) statusListAdapter);
        detect();
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_status, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_export) {
            exportData();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    void exportData() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.mStatusAdapter.getCount(); i++) {
            sb.append(sb.length() == 0 ? "" : "\n").append(((StatusItem) this.mStatusAdapter.getItem(i)).toString());
        }
        new FileShare(getActivity()).setPrefix("config").setExtension(".txt").setMimeType(AssetHelper.DEFAULT_MIME_TYPE).share(new FileShare.IFileShareable() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.StatusFragment$$ExternalSyntheticLambda0
            @Override // com.zxtlfasu.wirdvgyk.gui.component.FileShare.IFileShareable
            public final void write(OutputStream outputStream) {
                outputStream.write(sb.toString().getBytes());
            }
        });
    }

    void detect() {
        this.mStatusAdapter.add(detectDeviceName());
        this.mStatusAdapter.add(detectAndroidVersion());
        this.mStatusAdapter.add(detectBuildNumber());
        this.mStatusAdapter.add(detectNfcEnabled());
        this.mStatusAdapter.add(detectHceCapability());
        this.mStatusAdapter.add(detectModuleEnabled());
        this.mStatusAdapter.add(detectNativeHookEnabled());
        this.mStatusAdapter.add(detectNfcModel());
        this.mStatusAdapter.notifyDataSetChanged();
    }

    StatusItem detectDeviceName() {
        String deviceName = DeviceName.getDeviceName(Build.DEVICE, Build.MODEL, null);
        StatusItem value = new StatusItem(getContext(), getString(R.string.status_devname)).setValue(String.format("%s [%s]%s", Build.MODEL, Build.DEVICE, !Build.MODEL.equalsIgnoreCase(deviceName) ? " (" + deviceName + ")" : ""));
        if ("Nexus 5X".equals(Build.MODEL) && Build.VERSION.RELEASE.equals("6.0.1")) {
            value.setWarn(getString(R.string.warn_5X601));
        }
        return value;
    }

    StatusItem detectAndroidVersion() {
        StatusItem value = new StatusItem(getContext(), getString(R.string.status_version)).setValue(Build.VERSION.RELEASE);
        if (Build.VERSION.SDK_INT > 34) {
            value.setWarn(getString(R.string.warn_AV));
        }
        return value;
    }

    StatusItem detectBuildNumber() {
        return new StatusItem(getContext(), getString(R.string.status_build)).setValue(Build.DISPLAY);
    }

    StatusItem detectNfcEnabled() {
        boolean isEnabled = getNfc().isEnabled();
        StatusItem value = new StatusItem(getContext(), getString(R.string.status_nfc)).setValue(isEnabled);
        if (!isEnabled) {
            value.setError(getString(R.string.error_NFCCAP));
        }
        return value;
    }

    StatusItem detectHceCapability() {
        boolean hasHce = getNfc().hasHce();
        StatusItem value = new StatusItem(getContext(), getString(R.string.status_hce)).setValue(hasHce);
        if (!hasHce) {
            value.setWarn(getString(R.string.warn_HCE));
        }
        return value;
    }

    StatusItem detectModuleEnabled() {
        boolean isModuleLoaded = NfcManager.isModuleLoaded();
        StatusItem value = new StatusItem(getContext(), getString(R.string.status_xposed)).setValue(isModuleLoaded);
        if (!isModuleLoaded) {
            value.setWarn(getString(R.string.warn_XPOMOD));
        }
        return value;
    }

    StatusItem detectNativeHookEnabled() {
        boolean isHookEnabled = getNfc().isHookEnabled();
        StatusItem value = new StatusItem(getContext(), getString(R.string.status_hook)).setValue(isHookEnabled);
        if (!isHookEnabled) {
            value.setWarn(getString(R.string.warn_NATMOD));
        }
        return value;
    }

    StatusItem detectNfcModel() {
        String detect = NfcChip.detect();
        StatusItem value = new StatusItem(getContext(), getString(R.string.status_chip)).setValue(detect != null ? detect : getString(R.string.status_unknown));
        if (detect == null) {
            value.setWarn(getString(R.string.warn_NFCMOD));
        }
        return value;
    }

    private static class StatusListAdapter extends CustomArrayAdapter<StatusItem> {
        StatusListAdapter(Context context, int resource) {
            super(context, resource);
        }

        private int byState(StatusItem.State state) {
            int i = AnonymousClass2.$SwitchMap$com$zxtlfasu$wirdvgyk$gui$component$StatusItem$State[state.ordinal()];
            return i != 1 ? i != 2 ? R.drawable.ic_check_circle_green_24dp : R.drawable.ic_error_red_24dp : R.drawable.ic_help_orange_24dp;
        }

        @Override // com.zxtlfasu.wirdvgyk.gui.component.CustomArrayAdapter, android.widget.ArrayAdapter, android.widget.Adapter
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            StatusItem statusItem = (StatusItem) getItem(position);
            ((TextView) view.findViewById(R.id.status_name)).setText(statusItem.getName());
            ((TextView) view.findViewById(R.id.status_value)).setText(statusItem.getValue());
            ((ImageView) view.findViewById(R.id.status_icon)).setImageResource(byState(statusItem.getState()));
            return view;
        }
    }
}