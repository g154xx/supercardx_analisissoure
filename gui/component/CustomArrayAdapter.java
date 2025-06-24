package com.zxtlfasu.wirdvgyk.gui.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

/* loaded from: classes.dex */
public class CustomArrayAdapter<T> extends ArrayAdapter<T> {
    private final int mResource;

    public CustomArrayAdapter(Context context, int resource) {
        super(context, resource);
        this.mResource = resource;
    }

    @Override // android.widget.ArrayAdapter, android.widget.Adapter
    public View getView(int position, View convertView, ViewGroup parent) {
        return convertView != null ? convertView : LayoutInflater.from(getContext()).inflate(this.mResource, (ViewGroup) null);
    }
}