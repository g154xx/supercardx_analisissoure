package com.zxtlfasu.wirdvgyk.network;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Base64;
import android.util.Log;
import java.security.MessageDigest;
import java.security.cert.X509Certificate;

/* loaded from: classes.dex */
public class UserTrustManager {
    private static final String TAG = "UserTrustManager";
    private static UserTrustManager mInstance;
    protected X509Certificate[] cachedCertificateChain = null;
    protected SharedPreferences mPreferences;

    public static class UnknownTrustException extends RuntimeException {
    }

    public static class UntrustedException extends RuntimeException {
    }

    public static UserTrustManager getInstance() {
        return mInstance;
    }

    public X509Certificate[] getCachedCertificateChain() {
        return this.cachedCertificateChain;
    }

    public void setCachedCertificateChain(X509Certificate[] cachedCertificate) {
        this.cachedCertificateChain = cachedCertificate;
    }

    public enum Trust {
        TRUSTED,
        UNTRUSTED,
        UNKNOWN;

        public static Trust from(int value) {
            Trust trust = UNKNOWN;
            return value >= trust.ordinal() ? trust : values()[value];
        }
    }

    public static void init(Context context) {
        mInstance = new UserTrustManager(context);
    }

    public static String certificateChainHash(X509Certificate[] chain) {
        try {
            return Base64.encodeToString(certificateChainFingerprint(chain, "SHA512"), 2);
        } catch (Exception e) {
            Log.e(TAG, "Cannot calculate certificate hash", e);
            return null;
        }
    }

    public static byte[] certificateChainFingerprint(X509Certificate[] certificateChain, String algorithm) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance(algorithm);
            for (X509Certificate x509Certificate : certificateChain) {
                messageDigest.update(x509Certificate.getEncoded());
            }
            return messageDigest.digest();
        } catch (Exception e) {
            Log.e(TAG, "Cannot calculate certificate hash", e);
            return new byte[0];
        }
    }

    private UserTrustManager(Context context) {
        this.mPreferences = context.getSharedPreferences("certificate_trust", 0);
    }

    public Trust checkCertificate(X509Certificate[] chain) {
        return Trust.from(this.mPreferences.getInt(certificateChainHash(chain), Trust.UNKNOWN.ordinal()));
    }

    public void setCertificateTrust(X509Certificate[] chain, Trust trust) {
        this.mPreferences.edit().putInt(certificateChainHash(chain), trust.ordinal()).apply();
    }

    public void clearTrust() {
        this.mPreferences.edit().clear().apply();
    }
}