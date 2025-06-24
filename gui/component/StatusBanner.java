package com.zxtlfasu.wirdvgyk.gui.component;

import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.core.view.ViewCompat;
import com.zxtlfasu.wirdvgyk.R;
import com.zxtlfasu.wirdvgyk.gui.MainActivity;

/* loaded from: classes.dex */
public class StatusBanner {
    private final RelativeLayout mBanner;
    private final TextView mBannerText;
    private final Context mContext;

    public enum State {
        GREEN,
        YELLOW,
        RED,
        IDLE
    }

    public StatusBanner(MainActivity act) {
        this.mContext = act;
        this.mBanner = (RelativeLayout) act.findViewById(R.id.banner);
        this.mBannerText = (TextView) act.findViewById(R.id.banner_text);
    }

    /* renamed from: com.zxtlfasu.wirdvgyk.gui.component.StatusBanner$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$zxtlfasu$wirdvgyk$gui$component$StatusBanner$State;

        static {
            int[] iArr = new int[State.values().length];
            $SwitchMap$com$zxtlfasu$wirdvgyk$gui$component$StatusBanner$State = iArr;
            try {
                iArr[State.IDLE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$gui$component$StatusBanner$State[State.RED.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$gui$component$StatusBanner$State[State.YELLOW.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$gui$component$StatusBanner$State[State.GREEN.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
        }
    }

    private int colorByState(State state) {
        int i = AnonymousClass1.$SwitchMap$com$zxtlfasu$wirdvgyk$gui$component$StatusBanner$State[state.ordinal()];
        if (i == 2 || i == 3 || i == 4) {
            return -1;
        }
        return ViewCompat.MEASURED_STATE_MASK;
    }

    private int backgroundByState(State state) {
        int i = AnonymousClass1.$SwitchMap$com$zxtlfasu$wirdvgyk$gui$component$StatusBanner$State[state.ordinal()];
        return i != 2 ? i != 3 ? i != 4 ? R.color.status_idle : R.color.status_green : R.color.status_yellow : R.color.status_red;
    }

    public void set(State state, String message) {
        this.mBanner.setBackgroundResource(backgroundByState(state));
        this.mBannerText.setTextColor(colorByState(state));
        this.mBannerText.setText(message);
        setVisibility(true);
    }

    public void setSuccess(String message) {
        set(State.GREEN, message);
    }

    public void setWarning(String message) {
        set(State.YELLOW, this.mContext.getString(R.string.banner_warning, message));
    }

    public void setError(String message) {
        set(State.RED, this.mContext.getString(R.string.banner_error, message));
    }

    public void setVisibility(boolean visible) {
        this.mBanner.setVisibility(visible ? 0 : 8);
    }

    public void reset() {
        setVisibility(false);
    }
}