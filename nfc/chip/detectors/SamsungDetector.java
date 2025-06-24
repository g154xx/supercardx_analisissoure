package com.zxtlfasu.wirdvgyk.nfc.chip.detectors;

import android.util.Pair;
import com.zxtlfasu.wirdvgyk.nfc.chip.NfcChipGuess;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class SamsungDetector extends BaseConfigLineDetector {
    @Override // com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BaseConfigLineDetector
    protected List<String> getConfigFilenames() {
        return Arrays.asList("libnfc-sec-vendor.conf");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BaseConfigLineDetector
    /* renamed from: onLine */
    public boolean m213x2447f431(String line, NfcChipGuess guess) {
        Pair<String, String> splitConfigLine = splitConfigLine(line);
        if (splitConfigLine == null) {
            return true;
        }
        if ("TRANS_DRIVER".equals(splitConfigLine.first)) {
            if (!fileExists((String) splitConfigLine.second)) {
                return false;
            }
            guess.confidence = 0.9f;
            if (guess.chipName != null) {
                return true;
            }
            guess.chipName = "Samsung Unknown";
            return true;
        }
        if (!"FW_FILE_NAME".equals(splitConfigLine.first) && !"RF_FILE_NAME".equals(splitConfigLine.first)) {
            return true;
        }
        guess.improveConfidence(0.2f);
        guess.chipName = "Samsung " + formatFirmwareName((String) splitConfigLine.second);
        return true;
    }

    private static String formatFirmwareName(String firmware) {
        Matcher matcher = Pattern.compile("^\\w+_(\\w+)_").matcher(firmware);
        if (!matcher.lookingAt() || matcher.groupCount() <= 0) {
            return null;
        }
        return matcher.group(1).toUpperCase();
    }
}