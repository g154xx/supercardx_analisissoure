package com.zxtlfasu.wirdvgyk.nfc.chip;

/* loaded from: classes.dex */
public class NfcChipGuess {
    public String chipName = null;
    public float confidence = 0.0f;

    public void improveConfidence(float value) {
        if (this.confidence < value) {
            this.confidence = value;
        }
    }

    public String toString() {
        return String.format("{chipName: \"%s\", confidence: %.2f", this.chipName, Float.valueOf(this.confidence));
    }
}