package com.zxtlfasu.wirdvgyk.network.transport;

import android.util.Log;
import com.zxtlfasu.wirdvgyk.network.UserTrustManager;
import java.io.IOException;
import java.net.Socket;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLHandshakeException;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509TrustManager;

/* loaded from: classes.dex */
public class TLSTransport extends Transport {
    private static final String TAG = "TLSTransport";
    protected SSLContext mSslContext;

    public TLSTransport(String hostname, int port) {
        super(hostname, port);
        createSslContext();
    }

    protected void createSslContext() {
        try {
            SSLContext sSLContext = SSLContext.getInstance("TLS");
            this.mSslContext = sSLContext;
            sSLContext.init(null, buildTrustManagers(), null);
        } catch (KeyManagementException | NoSuchAlgorithmException e) {
            Log.wtf(TAG, "Cannot instantiate SSLContext");
            throw new RuntimeException(e);
        }
    }

    protected TrustManager[] buildTrustManagers() {
        try {
            TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
            trustManagerFactory.init((KeyStore) null);
            return new X509TrustManager[]{new UserX509TrustManager((X509TrustManager) trustManagerFactory.getTrustManagers()[0])};
        } catch (KeyStoreException | NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.zxtlfasu.wirdvgyk.network.transport.Transport
    protected Socket createSocket() {
        try {
            return this.mSslContext.getSocketFactory().createSocket();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override // com.zxtlfasu.wirdvgyk.network.transport.Transport
    protected void connectSocket() throws IOException {
        this.mSocket.connect(this.mAddress, 10000);
        ((SSLSocket) this.mSocket).startHandshake();
        if (!HttpsURLConnection.getDefaultHostnameVerifier().verify(this.mAddress.getHostName(), ((SSLSocket) this.mSocket).getSession())) {
            throw new SSLHandshakeException("Hostname in certificate does not match");
        }
    }

    protected static class UserX509TrustManager implements X509TrustManager {
        protected X509TrustManager mDefaultManager;

        public UserX509TrustManager(X509TrustManager defaultManager) {
            this.mDefaultManager = defaultManager;
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            this.mDefaultManager.checkClientTrusted(chain, authType);
        }

        @Override // javax.net.ssl.X509TrustManager
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
            try {
                this.mDefaultManager.checkServerTrusted(chain, authType);
            } catch (CertificateException e) {
                if (!(e.getCause() instanceof CertPathValidatorException)) {
                    throw e;
                }
                int i = AnonymousClass1.$SwitchMap$com$zxtlfasu$wirdvgyk$network$UserTrustManager$Trust[UserTrustManager.getInstance().checkCertificate(chain).ordinal()];
                if (i != 1) {
                    if (i == 2) {
                        UserTrustManager.getInstance().setCachedCertificateChain(chain);
                        throw new UserTrustManager.UnknownTrustException();
                    }
                    throw new UserTrustManager.UntrustedException();
                }
            }
        }

        @Override // javax.net.ssl.X509TrustManager
        public X509Certificate[] getAcceptedIssuers() {
            return this.mDefaultManager.getAcceptedIssuers();
        }
    }

    /* renamed from: com.zxtlfasu.wirdvgyk.network.transport.TLSTransport$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$zxtlfasu$wirdvgyk$network$UserTrustManager$Trust;

        static {
            int[] iArr = new int[UserTrustManager.Trust.values().length];
            $SwitchMap$com$zxtlfasu$wirdvgyk$network$UserTrustManager$Trust = iArr;
            try {
                iArr[UserTrustManager.Trust.TRUSTED.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$network$UserTrustManager$Trust[UserTrustManager.Trust.UNKNOWN.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$zxtlfasu$wirdvgyk$network$UserTrustManager$Trust[UserTrustManager.Trust.UNTRUSTED.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
        }
    }
}