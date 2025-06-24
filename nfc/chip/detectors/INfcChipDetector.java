package com.zxtlfasu.wirdvgyk.nfc.chip.detectors;

import com.zxtlfasu.wirdvgyk.nfc.chip.NfcChipGuess;
import java.util.List;

/* loaded from: classes.dex */
public interface INfcChipDetector {
    List<NfcChipGuess> tryDetect();
}