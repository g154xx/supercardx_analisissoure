package com.zxtlfasu.wirdvgyk.gui.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import com.zxtlfasu.wirdvgyk.BuildConfig;
import com.zxtlfasu.wirdvgyk.R;
import mehdi.sakout.aboutpage.AboutPage;
import mehdi.sakout.aboutpage.Element;

/* loaded from: classes.dex */
public class AboutFragment extends Fragment {
    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View create = new AboutPage(getContext()).setImage(R.mipmap.ic_launcher).addGitHub("nfcgate/nfcgate").addItem(new Element().setIconDrawable(Integer.valueOf(R.drawable.ic_copyright_black_24dp)).setIntent(new Intent().setAction("android.intent.action.VIEW").addCategory("android.intent.category.BROWSABLE").setData(Uri.parse("https://www.apache.org/licenses/LICENSE-2.0"))).setTitle(getString(R.string.about_license))).addItem(new Element().setIconDrawable(Integer.valueOf(R.drawable.ic_about_black_24dp)).setTitle(getString(R.string.about_version, BuildConfig.VERSION_NAME))).create();
        ((TextView) create.findViewById(R.id.description)).setText(Html.fromHtml(getString(R.string.about_text)));
        return create;
    }
}