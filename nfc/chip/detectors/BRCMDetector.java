package com.zxtlfasu.wirdvgyk.nfc.chip.detectors;

import android.util.Pair;
import com.zxtlfasu.wirdvgyk.nfc.chip.NfcChipGuess;
import java.util.Arrays;
import java.util.List;

/* loaded from: classes.dex */
public class BRCMDetector extends BaseConfigLineDetector {
    @Override // com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BaseConfigLineDetector
    protected List<String> getConfigFilenames() {
        return Arrays.asList("libnfc-brcm.conf");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BaseConfigLineDetector
    /* renamed from: onLine */
    public boolean m213x2447f431(String line, NfcChipGuess guess) {
        Pair<String, String> splitConfigLine = splitConfigLine(line);
        if (splitConfigLine == null || !"TRANSPORT_DRIVER".equals(splitConfigLine.first)) {
            return true;
        }
        if (!fileExists((String) splitConfigLine.second)) {
            return false;
        }
        guess.confidence = 0.9f;
        if (guess.chipName != null) {
            return true;
        }
        guess.chipName = "Broadcom Device " + formatBRCMDeviceNode((String) splitConfigLine.second);
        return true;
    }

    private static String formatBRCMDeviceNode(String devNode) {
        return devNode.replace("/dev/", "");
    }
}