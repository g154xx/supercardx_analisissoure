package com.zxtlfasu.wirdvgyk.gui.component;

import android.content.Context;
import com.zxtlfasu.wirdvgyk.R;

/* loaded from: classes.dex */
public class StatusItem {
    private final Context mContext;
    private String mMessage;
    private final String mName;
    private State mState = State.OK;
    private String mValue;

    public enum State {
        OK,
        WARN,
        ERROR
    }

    public String getMessage() {
        return this.mMessage;
    }

    public String getName() {
        return this.mName;
    }

    public State getState() {
        return this.mState;
    }

    public String getValue() {
        return this.mValue;
    }

    public StatusItem setValue(String value) {
        this.mValue = value;
        return this;
    }

    public StatusItem(Context context, String name) {
        this.mContext = context;
        this.mName = name;
    }

    public StatusItem setValue(boolean yes) {
        return setValue(this.mContext.getString(yes ? R.string.status_yes : R.string.status_no));
    }

    public void setWarn(String message) {
        this.mState = State.WARN;
        this.mMessage = message;
    }

    public void setError(String message) {
        this.mState = State.ERROR;
        this.mMessage = message;
    }

    public String toString() {
        return this.mName + ": " + this.mValue;
    }
}