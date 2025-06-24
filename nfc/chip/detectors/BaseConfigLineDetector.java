package com.zxtlfasu.wirdvgyk.nfc.chip.detectors;

import android.util.Log;
import android.util.Pair;
import com.zxtlfasu.wirdvgyk.nfc.chip.NfcChipGuess;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public abstract class BaseConfigLineDetector implements INfcChipDetector {
    private final String[] configDirs = {"/product/etc/", "/odm/etc/", "/vendor/etc/", "/system_ext/etc/", "/etc/"};

    protected interface ILineProcessor {
        boolean processLine(String line);
    }

    protected abstract List<String> getConfigFilenames();

    /* JADX INFO: Access modifiers changed from: protected */
    /* renamed from: onLine, reason: merged with bridge method [inline-methods] */
    public abstract boolean m213x2447f431(String line, NfcChipGuess guess);

    @Override // com.zxtlfasu.wirdvgyk.nfc.chip.detectors.INfcChipDetector
    public List<NfcChipGuess> tryDetect() {
        ArrayList arrayList = new ArrayList();
        for (String str : findConfigs(getConfigFilenames())) {
            NfcChipGuess readConfig = readConfig(str);
            if (readConfig != null) {
                Log.d("NFCCONFIG", String.format("Guess %s from %s", readConfig, str));
                arrayList.add(readConfig);
            }
        }
        return arrayList;
    }

    protected List<String> findConfigs(List<String> fileNames) {
        ArrayList arrayList = new ArrayList();
        for (String str : getConfigDirs()) {
            Iterator<String> it = fileNames.iterator();
            while (it.hasNext()) {
                String str2 = str + it.next();
                if (fileExists(str2)) {
                    arrayList.add(str2);
                }
            }
        }
        return arrayList;
    }

    protected NfcChipGuess readConfig(String path) {
        final NfcChipGuess nfcChipGuess = new NfcChipGuess();
        if (readFileLines(path, new ILineProcessor() { // from class: com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BaseConfigLineDetector$$ExternalSyntheticLambda0
            @Override // com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BaseConfigLineDetector.ILineProcessor
            public final boolean processLine(String str) {
                return BaseConfigLineDetector.this.m213x2447f431(nfcChipGuess, str);
            }
        })) {
            return nfcChipGuess;
        }
        return null;
    }

    protected static boolean readFileLines(String path, ILineProcessor processor) {
        String readLine;
        File file = new File(path);
        if (!file.exists()) {
            return true;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            do {
                try {
                    readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        bufferedReader.close();
                        return true;
                    }
                } finally {
                }
            } while (processor.processLine(readLine.trim()));
            bufferedReader.close();
            return false;
        } catch (IOException unused) {
            return true;
        }
    }

    protected static Pair<String, String> splitConfigLine(String line) {
        String[] split = line.split("=", 2);
        if (split.length == 2) {
            return new Pair<>(split[0].trim(), split[1].trim().replaceAll("(^\")|(\"$)", ""));
        }
        return null;
    }

    protected static boolean fileExists(String fileName) {
        return new File(fileName).exists();
    }

    protected List<String> getConfigDirs() {
        return Arrays.asList(this.configDirs);
    }
}