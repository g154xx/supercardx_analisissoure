package com.zxtlfasu.wirdvgyk.nfc.chip.detectors;

import android.util.Pair;
import com.zxtlfasu.wirdvgyk.nfc.chip.NfcChipGuess;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/* loaded from: classes.dex */
public class STDetector extends BaseConfigLineDetector {
    private static String formatSTDeviceNode(String devNode) {
        return devNode;
    }

    @Override // com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BaseConfigLineDetector
    protected List<String> getConfigFilenames() {
        return Arrays.asList("libnfc-hal-st.conf");
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BaseConfigLineDetector
    /* renamed from: onLine */
    public boolean m213x2447f431(String line, NfcChipGuess guess) {
        Pair<String, String> splitConfigLine = splitConfigLine(line);
        if (splitConfigLine == null) {
            return true;
        }
        if ("NCI_HAL_MODULE".equals(splitConfigLine.first)) {
            if (!fileExists("/dev/" + ((String) splitConfigLine.second).replace("nfc_nci.", ""))) {
                return false;
            }
            guess.confidence = 0.9f;
            if (guess.chipName != null) {
                return true;
            }
            guess.chipName = "ST Device " + formatSTDeviceNode((String) splitConfigLine.second);
            return true;
        }
        if (!"STNFC_FW_BIN_NAME".equals(splitConfigLine.first) && !"STNFC_FW_CONF_NAME".equals(splitConfigLine.first)) {
            return true;
        }
        guess.improveConfidence(0.2f);
        guess.chipName = "NXP Device " + formatFirmwareName((String) splitConfigLine.second);
        return true;
    }

    private static String formatFirmwareName(String firmware) {
        Matcher matcher = Pattern.compile("^.*/(\\w+)_").matcher(firmware);
        if (!matcher.lookingAt() || matcher.groupCount() <= 0) {
            return null;
        }
        return matcher.group(1).toUpperCase();
    }
}