package com.zxtlfasu.wirdvgyk.gui.fragment;

import android.nfc.Tag;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.zxtlfasu.wirdvgyk.R;
import com.zxtlfasu.wirdvgyk.nfc.reader.NFCTagReader;
import com.zxtlfasu.wirdvgyk.util.NfcComm;

/* loaded from: classes.dex */
public class CaptureFragment extends BaseFragment {
    boolean mCaptureActive = false;
    TextView mIdleText;
    TextView mProgressText;
    LinearLayout mStartButton;
    ImageView mStartIcon;
    LinearLayout mStopButton;
    ImageView mStopIcon;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.fragment_capture, container, false);
        this.mStartButton = (LinearLayout) inflate.findViewById(R.id.capture_start);
        this.mStopButton = (LinearLayout) inflate.findViewById(R.id.capture_stop);
        this.mIdleText = (TextView) inflate.findViewById(R.id.capture_idle_text);
        this.mProgressText = (TextView) inflate.findViewById(R.id.capture_progress_text);
        this.mStartIcon = (ImageView) inflate.findViewById(R.id.capture_start_icon);
        this.mStopIcon = (ImageView) inflate.findViewById(R.id.capture_stop_icon);
        updateState();
        this.mStartButton.setOnClickListener(new View.OnClickListener() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.CaptureFragment$$ExternalSyntheticLambda0
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CaptureFragment.this.m172x9fb5c9fe(view);
            }
        });
        this.mStopButton.setOnClickListener(new View.OnClickListener() { // from class: com.zxtlfasu.wirdvgyk.gui.fragment.CaptureFragment$$ExternalSyntheticLambda1
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                CaptureFragment.this.m173xcd8e645d(view);
            }
        });
        return inflate;
    }

    /* renamed from: lambda$onCreateView$0$com-zxtlfasu-wirdvgyk-gui-fragment-CaptureFragment, reason: not valid java name */
    /* synthetic */ void m172x9fb5c9fe(View view) {
        startCapture();
    }

    /* renamed from: lambda$onCreateView$1$com-zxtlfasu-wirdvgyk-gui-fragment-CaptureFragment, reason: not valid java name */
    /* synthetic */ void m173xcd8e645d(View view) {
        stopCapture();
    }

    void updateState() {
        this.mStartIcon.setColorFilter(this.mCaptureActive ? 0 : -2818048);
        this.mStartButton.setEnabled(!this.mCaptureActive);
        this.mStopIcon.setColorFilter(this.mCaptureActive ? -2818048 : 0);
        this.mStopButton.setEnabled(this.mCaptureActive);
        this.mIdleText.setVisibility(this.mCaptureActive ? 8 : 0);
        this.mProgressText.setVisibility(this.mCaptureActive ? 0 : 8);
    }

    void startCapture() {
        this.mCaptureActive = true;
        updateState();
        getNfc().setCaptureEnabled(true);
    }

    void stopCapture() {
        this.mCaptureActive = false;
        updateState();
        getNfc().setCaptureEnabled(false);
    }

    public static NfcComm fromBundle(Bundle b) {
        String string = b.getString("type");
        long j = b.getLong("timestamp");
        if ("INITIAL".equals(string)) {
            Tag tag = (Tag) b.getParcelable("data");
            return new NfcComm(true, true, tag != null ? NFCTagReader.create(tag).getConfig().build() : new byte[0], j);
        }
        return new NfcComm("TAG".equals(string), false, b.getByteArray("data"), j);
    }
}