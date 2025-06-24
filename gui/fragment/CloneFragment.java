package com.zxtlfasu.wirdvgyk.gui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import com.zxtlfasu.wirdvgyk.R;
import com.zxtlfasu.wirdvgyk.db.TagInfo;
import com.zxtlfasu.wirdvgyk.db.model.TagInfoViewModel;
import com.zxtlfasu.wirdvgyk.gui.component.StatusBanner;
import com.zxtlfasu.wirdvgyk.gui.fragment.CloneFragment;
import com.zxtlfasu.wirdvgyk.nfc.NfcManager;
import com.zxtlfasu.wirdvgyk.nfc.config.ConfigBuilder;
import com.zxtlfasu.wirdvgyk.nfc.modes.CloneMode;
import com.zxtlfasu.wirdvgyk.util.NfcComm;
import java.util.List;

/* loaded from: classes.dex */
public class CloneFragment extends BaseFragment {
    TextView mCloneContent;
    byte[] mCloneData;
    ListView mCloneSaved;
    ImageView mCloneType;
    StatusBanner mStatusBanner;
    private ArrayAdapter<TagInfo> mTagInfoAdapter;
    boolean mTagInfoDisplayed = false;
    private TagInfoViewModel mTagInfoViewModel;
    View mTagWaiting;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_clone, container, false);
        this.mTagWaiting = inflate.findViewById(R.id.tag_wait);
        this.mCloneType = (ImageView) inflate.findViewById(R.id.type);
        this.mCloneContent = (TextView) inflate.findViewById(R.id.data);
        this.mCloneSaved = (ListView) inflate.findViewById(R.id.clone_saved);
        this.mStatusBanner = new StatusBanner(getMainActivity());
        setHasOptionsMenu(true);
        beginClone();
        TagInfoViewModel tagInfoViewModel = (TagInfoViewModel) ViewModelProviders.of(this).get(TagInfoViewModel.class);
        this.mTagInfoViewModel = tagInfoViewModel;
        tagInfoViewModel.getTagInfos().observe(this, new Observer() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.CloneFragment$$ExternalSyntheticLambda1
            @Override // androidx.lifecycle.Observer
            public final void onChanged(Object obj) {
                CloneFragment.this.m176xc01fcb15((List) obj);
            }
        });
        this.mCloneSaved.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.CloneFragment$$ExternalSyntheticLambda2
            @Override // android.widget.AdapterView.OnItemLongClickListener
            public final boolean onItemLongClick(AdapterView adapterView, View view, int i, long j) {
                return CloneFragment.this.m177xb1c97134(adapterView, view, i, j);
            }
        });
        this.mCloneSaved.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.CloneFragment$$ExternalSyntheticLambda3
            @Override // android.widget.AdapterView.OnItemClickListener
            public final void onItemClick(AdapterView adapterView, View view, int i, long j) {
                CloneFragment.this.m178xa3731753(adapterView, view, i, j);
            }
        });
        return inflate;
    }

    /* renamed from: lambda$onCreateView$0$com-zxtlfasu-wirdvgyk-gui-fragment-CloneFragment, reason: not valid java name */
    /* synthetic */ void m176xc01fcb15(List list) {
        this.mTagInfoAdapter.clear();
        this.mTagInfoAdapter.addAll(list);
        this.mTagInfoAdapter.notifyDataSetChanged();
    }

    /* renamed from: lambda$onCreateView$1$com-zxtlfasu-wirdvgyk-gui-fragment-CloneFragment, reason: not valid java name */
    /* synthetic */ boolean m177xb1c97134(AdapterView adapterView, View view, int i, long j) {
        if (i < 0) {
            return false;
        }
        this.mTagInfoViewModel.delete(this.mTagInfoAdapter.getItem(i));
        return true;
    }

    /* renamed from: lambda$onCreateView$2$com-zxtlfasu-wirdvgyk-gui-fragment-CloneFragment, reason: not valid java name */
    /* synthetic */ void m178xa3731753(AdapterView adapterView, View view, int i, long j) {
        if (i >= 0) {
            getNfc().handleData(false, new NfcComm(this.mTagInfoAdapter.getItem(i).getData()));
        }
    }

    @Override // androidx.fragment.app.Fragment
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<TagInfo> arrayAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);
        this.mTagInfoAdapter = arrayAdapter;
        this.mCloneSaved.setAdapter((ListAdapter) arrayAdapter);
        getNfc().setStatusChangedHandler(new NfcManager.StatusChangedListener() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.CloneFragment$$ExternalSyntheticLambda0
            @Override // com.zxtlfasu.wirdvgyk.nfc.NfcManager.StatusChangedListener
            public final void onChange() {
                CloneFragment.this.m175x925e6ad2();
            }
        });
    }

    /* renamed from: lambda$onActivityCreated$3$com-zxtlfasu-wirdvgyk-gui-fragment-CloneFragment, reason: not valid java name */
    /* synthetic */ void m175x925e6ad2() {
        this.mStatusBanner.reset();
        if (!NfcManager.isModuleLoaded() || !getNfc().isHookEnabled()) {
            this.mStatusBanner.setWarning(getString(R.string.error_xposed));
        }
        if (getNfc().isEnabled()) {
            return;
        }
        this.mStatusBanner.setError(getString(R.string.error_nfc_disabled));
    }

    @Override // androidx.fragment.app.Fragment
    public void onDestroy() {
        super.onDestroy();
        getNfc().setStatusChangedHandler(null);
    }

    @Override // androidx.fragment.app.Fragment
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        setSaveEnabled(menu, this.mTagInfoDisplayed);
    }

    @Override // androidx.fragment.app.Fragment
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_clone, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.action_clone == item.getItemId()) {
            beginClone();
            return true;
        }
        if (R.id.action_save == item.getItemId()) {
            beginSave();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setSaveEnabled(Menu menu, boolean enabled) {
        MenuItem findItem = menu.findItem(R.id.action_save);
        if (findItem != null) {
            findItem.setEnabled(enabled);
            findItem.getIcon().mutate().setAlpha(enabled ? 255 : 130);
        }
    }

    private void setTagInfoDisplayed(boolean tagInfoDisplayed) {
        this.mTagInfoDisplayed = tagInfoDisplayed;
        getActivity().invalidateOptionsMenu();
    }

    void setCloneWait(boolean waiting) {
        setTagInfoDisplayed(!waiting);
        this.mTagWaiting.setVisibility(waiting ? 0 : 8);
        this.mCloneContent.setVisibility(waiting ? 8 : 0);
        this.mCloneType.setVisibility(waiting ? 8 : 0);
    }

    void setCloneContent(NfcComm data) {
        this.mCloneType.setImageResource(data.isCard() ? R.drawable.ic_tag_grey_60dp : R.drawable.ic_reader_grey_60dp);
        this.mCloneContent.setText(new ConfigBuilder(data.getData()).toString());
        this.mCloneData = data.toByteArray();
    }

    void beginClone() {
        setCloneWait(true);
        getNfc().stopMode();
        getNfc().startMode(new UICloneMode());
    }

    private void beginSave() {
        final EditText editText = new EditText(getContext());
        editText.setInputType(1);
        new AlertDialog.Builder(getContext()).setTitle(getString(R.string.clone_save_title)).setView(editText).setPositiveButton(getString(R.string.button_ok), new DialogInterface.OnClickListener() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.CloneFragment$$ExternalSyntheticLambda4
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                CloneFragment.this.m174xb7f625a5(editText, dialogInterface, i);
            }
        }).setNegativeButton(getString(R.string.button_cancel), (DialogInterface.OnClickListener) null).show();
    }

    /* renamed from: lambda$beginSave$4$com-zxtlfasu-wirdvgyk-gui-fragment-CloneFragment, reason: not valid java name */
    /* synthetic */ void m174xb7f625a5(EditText editText, DialogInterface dialogInterface, int i) {
        String obj = editText.getText().toString();
        if (obj.isEmpty()) {
            return;
        }
        this.mTagInfoViewModel.insert(new TagInfo(obj, this.mCloneData));
    }

    class UICloneMode extends CloneMode {
        UICloneMode() {
        }

        @Override // com.zxtlfasu.wirdvgyk.nfc.modes.CloneMode, com.zxtlfasu.wirdvgyk.nfc.modes.BaseMode
        public void onData(boolean isForeign, final NfcComm data) {
            super.onData(isForeign, data);
            FragmentActivity activity = CloneFragment.this.getActivity();
            if (activity == null || !data.isInitial()) {
                return;
            }
            activity.runOnUiThread(new Runnable() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.CloneFragment$UICloneMode$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    CloneFragment.UICloneMode.this.m179xad7dc154(data);
                }
            });
        }

        /* renamed from: lambda$onData$0$com-zxtlfasu-wirdvgyk-gui-fragment-CloneFragment$UICloneMode, reason: not valid java name */
        /* synthetic */ void m179xad7dc154(NfcComm nfcComm) {
            CloneFragment.this.setCloneWait(false);
            CloneFragment.this.setCloneContent(nfcComm);
        }
    }
}