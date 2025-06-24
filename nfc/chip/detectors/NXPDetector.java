package com.zxtlfasu.wirdvgyk.nfc.chip.detectors;

import android.util.Pair;
import com.zxtlfasu.wirdvgyk.nfc.chip.NfcChipGuess;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/* loaded from: classes.dex */
public class NXPDetector extends BaseConfigLineDetector {
    private static final Map<String, String> NXPChipMap = new HashMap<String, String>() { // from class: com.zxtlfasu.wirdvgyk.nfc.chip.detectors.NXPDetector.1
        {
            put("0x01", "PN547C2");
            put("0x02", "PN65T");
            put("0x03", "PN548AD");
            put("0x04", "PN66T");
            put("0x05", "PN551");
            put("0x06", "PN67T");
            put("0x07", "PN553");
            put("0x08", "PN80T");
            put("0x09", "PN557");
            put("0x0A", "PN81T");
            put("0x0B", "SN1X0");
            put("0x0C", "SN2X0");
        }
    };
    private static final Set<String> NfcChipKeywords = new HashSet<String>() { // from class: com.zxtlfasu.wirdvgyk.nfc.chip.detectors.NXPDetector.2
        {
            add("NXP_NFC_CHIP");
            add("NXP_NFC_CHIP_TYPE");
        }
    };

    @Override // com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BaseConfigLineDetector
    protected List<String> getConfigFilenames() {
        ArrayList arrayList = new ArrayList(Arrays.asList("libnfc-nxp.conf"));
        String systemProp = getSystemProp("ro.boot.product.hardware.sku");
        if (!systemProp.isEmpty()) {
            arrayList.addAll(Arrays.asList("libnfc-" + systemProp + ".conf", "libnfc-nxp-" + systemProp + ".conf"));
        }
        String systemProp2 = getSystemProp("persist.vendor.nfc.config_file_name");
        if (!systemProp2.isEmpty()) {
            arrayList.add(systemProp2);
        }
        return arrayList;
    }

    /* JADX INFO: Access modifiers changed from: protected */
    @Override // com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BaseConfigLineDetector
    /* renamed from: onLine */
    public boolean m213x2447f431(String line, NfcChipGuess guess) {
        Pair<String, String> splitConfigLine = splitConfigLine(line);
        if (splitConfigLine == null) {
            return true;
        }
        if ("NXP_NFC_DEV_NODE".equals(splitConfigLine.first)) {
            if (!fileExists((String) splitConfigLine.second)) {
                return false;
            }
            guess.improveConfidence(0.9f);
            if (guess.chipName != null) {
                return true;
            }
            guess.chipName = "NXP Device " + formatNXPDeviceNode((String) splitConfigLine.second);
            return true;
        }
        if (!NfcChipKeywords.contains(splitConfigLine.first)) {
            return true;
        }
        guess.improveConfidence(0.2f);
        guess.chipName = "NXP " + resolveNXPChipCode((String) splitConfigLine.second);
        return true;
    }

    private static String formatNXPDeviceNode(String devNode) {
        return devNode.replace("/dev/", "");
    }

    private static String resolveNXPChipCode(String code) {
        Map<String, String> map = NXPChipMap;
        return map.containsKey(code) ? map.get(code) : "Unknown";
    }

    protected static String getSystemProp(String prop) {
        String str;
        Process process = null;
        try {
            process = new ProcessBuilder("getprop", prop).redirectErrorStream(true).start();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            str = "";
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        break;
                    }
                    str = readLine.trim();
                } catch (IOException unused) {
                }
            }
        } catch (IOException unused2) {
            str = "";
        }
        if (process != null) {
            process.destroy();
        }
        return !str.isEmpty() ? str : "";
    }
}