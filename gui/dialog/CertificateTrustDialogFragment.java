package com.zxtlfasu.wirdvgyk.gui.dialog;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.zxtlfasu.wirdvgyk.R;
import com.zxtlfasu.wirdvgyk.network.UserTrustManager;
import com.zxtlfasu.wirdvgyk.util.CertUtils;
import com.zxtlfasu.wirdvgyk.util.Utils;
import java.security.cert.X509Certificate;

/* loaded from: classes.dex */
public class CertificateTrustDialogFragment extends DialogFragment {
    CertificateListAdapter mAdapter;

    @Override // androidx.fragment.app.DialogFragment
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
        View inflate = requireActivity().getLayoutInflater().inflate(R.layout.dialog_certtrust, (ViewGroup) null);
        final X509Certificate[] cachedCertificateChain = UserTrustManager.getInstance().getCachedCertificateChain();
        this.mAdapter = new CertificateListAdapter(this, cachedCertificateChain);
        ViewPager2 viewPager2 = (ViewPager2) inflate.findViewById(R.id.pager);
        viewPager2.setAdapter(this.mAdapter);
        new TabLayoutMediator((TabLayout) inflate.findViewById(R.id.tab_layout), viewPager2, new TabLayoutMediator.TabConfigurationStrategy() { // from class: com.zxtlfasu.wirdvgyk.gui.dialog.CertificateTrustDialogFragment$$ExternalSyntheticLambda0
            @Override // com.google.android.material.tabs.TabLayoutMediator.TabConfigurationStrategy
            public final void onConfigureTab(TabLayout.Tab tab, int i) {
                CertificateTrustDialogFragment.this.m169x77a93a7c(tab, i);
            }
        }).attach();
        builder.setView(inflate).setTitle(R.string.dialog_usertrust_message).setPositiveButton(R.string.dialog_usertrust_positive, new DialogInterface.OnClickListener() { // from class: com.zxtlfasu.wirdvgyk.gui.dialog.CertificateTrustDialogFragment$$ExternalSyntheticLambda1
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                UserTrustManager.getInstance().setCertificateTrust(cachedCertificateChain, UserTrustManager.Trust.TRUSTED);
            }
        }).setNegativeButton(R.string.dialog_usertrust_negative, new DialogInterface.OnClickListener() { // from class: com.zxtlfasu.wirdvgyk.gui.dialog.CertificateTrustDialogFragment$$ExternalSyntheticLambda2
            @Override // android.content.DialogInterface.OnClickListener
            public final void onClick(DialogInterface dialogInterface, int i) {
                UserTrustManager.getInstance().setCertificateTrust(cachedCertificateChain, UserTrustManager.Trust.UNTRUSTED);
            }
        }).setNeutralButton(R.string.dialog_usertrust_neutral, (DialogInterface.OnClickListener) null);
        return builder.create();
    }

    /* renamed from: lambda$onCreateDialog$0$com-zxtlfasu-wirdvgyk-gui-dialog-CertificateTrustDialogFragment, reason: not valid java name */
    /* synthetic */ void m169x77a93a7c(TabLayout.Tab tab, int i) {
        tab.setText(this.mAdapter.getItemTitle(i));
    }

    public static class CertificateFragment extends Fragment {
        X509Certificate mCertificate;

        CertificateFragment setup(X509Certificate certificate) {
            this.mCertificate = certificate;
            return this;
        }

        public static CertificateFragment newInstance(X509Certificate certificate) {
            return new CertificateFragment().setup(certificate);
        }

        @Override // androidx.fragment.app.Fragment
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return inflater.inflate(R.layout.dialog_certtrust_certificate, container, false);
        }

        @Override // androidx.fragment.app.Fragment
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ((TextView) view.findViewById(R.id.certificate_subject_dn)).setText(this.mCertificate.getSubjectDN().toString());
            ((TextView) view.findViewById(R.id.certificate_issuer_dn)).setText(this.mCertificate.getIssuerDN().toString());
            ((TextView) view.findViewById(R.id.certificate_not_before)).setText(this.mCertificate.getNotBefore().toString());
            ((TextView) view.findViewById(R.id.certificate_not_after)).setText(this.mCertificate.getNotAfter().toString());
            ((TextView) view.findViewById(R.id.certificate_public_key_info)).setText(CertUtils.getPublicKeyDescription(this.mCertificate.getPublicKey()));
            ((TextView) view.findViewById(R.id.certificate_fingerprint)).setText(Utils.bytesToHex(UserTrustManager.certificateChainFingerprint(new X509Certificate[]{this.mCertificate}, "SHA256")));
        }
    }

    static class CertificateListAdapter extends FragmentStateAdapter {
        private final X509Certificate[] mCachedCertificateChain;
        private final Fragment mFragment;

        public CertificateListAdapter(Fragment fragment, X509Certificate[] cachedCertificateChain) {
            super(fragment);
            this.mFragment = fragment;
            this.mCachedCertificateChain = cachedCertificateChain;
        }

        @Override // androidx.viewpager2.adapter.FragmentStateAdapter
        public Fragment createFragment(int position) {
            return CertificateFragment.newInstance(this.mCachedCertificateChain[position]);
        }

        @Override // androidx.recyclerview.widget.RecyclerView.Adapter
        public int getItemCount() {
            return this.mCachedCertificateChain.length;
        }

        public CharSequence getItemTitle(int position) {
            if (position == getItemCount() - 1) {
                return this.mFragment.getString(R.string.dialog_usertrust_tab_root);
            }
            return this.mFragment.getString(R.string.dialog_usertrust_tab_cert, Integer.valueOf(position));
        }
    }
}