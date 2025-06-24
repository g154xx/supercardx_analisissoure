package com.zxtlfasu.wirdvgyk.util;

import java.security.AlgorithmParameters;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidParameterSpecException;

/* loaded from: classes.dex */
public class CertUtils {
    public static String getPublicKeyDescription(PublicKey key) {
        StringBuilder sb = new StringBuilder(key.getAlgorithm());
        sb.append(" (");
        String algorithm = key.getAlgorithm();
        algorithm.hashCode();
        if (algorithm.equals("EC")) {
            getECPublicKeyDescription((ECPublicKey) key, sb);
        } else if (algorithm.equals("RSA")) {
            getRSAPublicKeyDescription((RSAPublicKey) key, sb);
        } else {
            sb.append("unknown key type");
        }
        return sb.append(")").toString();
    }

    private static void getECPublicKeyDescription(ECPublicKey key, StringBuilder sb) {
        try {
            AlgorithmParameters algorithmParameters = AlgorithmParameters.getInstance("EC");
            algorithmParameters.init(key.getParams());
            String name = ((ECGenParameterSpec) algorithmParameters.getParameterSpec(ECGenParameterSpec.class)).getName();
            sb.append("curve ").append(name).append(" - ").append(key.getParams().getCurve().getField().getFieldSize()).append(" bits");
        } catch (NoSuchAlgorithmException | InvalidParameterSpecException unused) {
        }
    }

    private static void getRSAPublicKeyDescription(RSAPublicKey key, StringBuilder sb) {
        sb.append(key.getModulus().bitLength()).append(" bits");
    }
}