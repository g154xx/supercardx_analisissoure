package com.zxtlfasu.wirdvgyk.nfc.chip;

import com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BRCMDetector;
import com.zxtlfasu.wirdvgyk.nfc.chip.detectors.INfcChipDetector;
import com.zxtlfasu.wirdvgyk.nfc.chip.detectors.NXPDetector;
import com.zxtlfasu.wirdvgyk.nfc.chip.detectors.NXPOppoDetector;
import com.zxtlfasu.wirdvgyk.nfc.chip.detectors.STDetector;
import com.zxtlfasu.wirdvgyk.nfc.chip.detectors.SamsungDetector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class NfcChip {
    private NfcChip() {
    }

    public static String detect() {
        NfcChipGuess nfcChipGuess = new NfcChipGuess();
        for (NfcChipGuess nfcChipGuess2 : collectGuesses()) {
            if (nfcChipGuess2.confidence > nfcChipGuess.confidence) {
                nfcChipGuess = nfcChipGuess2;
            }
        }
        if (nfcChipGuess.chipName == null || nfcChipGuess.chipName.isEmpty()) {
            return null;
        }
        return nfcChipGuess.chipName;
    }

    private static List<NfcChipGuess> collectGuesses() {
        ArrayList arrayList = new ArrayList();
        Iterator it = Arrays.asList(new BRCMDetector(), new NXPDetector(), new NXPOppoDetector(), new SamsungDetector(), new STDetector()).iterator();
        while (it.hasNext()) {
            arrayList.addAll(((INfcChipDetector) it.next()).tryDetect());
        }
        return arrayList;
    }
}