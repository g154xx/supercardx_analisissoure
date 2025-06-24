package com.zxtlfasu.wirdvgyk.nfc.chip.detectors;

import android.util.Log;
import com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BaseConfigLineDetector;
import com.zxtlfasu.wirdvgyk.nfc.chip.detectors.NXPOppoDetector;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import org.apache.commons.lang3.StringUtils;

/* loaded from: classes.dex */
public class NXPOppoDetector extends NXPDetector {

    protected static class ConfigTable {
        protected final List<Entry> mEntries = new ArrayList();

        protected ConfigTable() {
        }

        protected static class Entry {
            public final List<String> projectNames;
            public final String targetConfig;

            public Entry(String tc, List<String> pns) {
                this.targetConfig = tc;
                this.projectNames = pns;
            }

            public String toString() {
                StringBuilder sb = new StringBuilder();
                sb.append(this.targetConfig).append(":");
                Iterator<String> it = this.projectNames.iterator();
                while (it.hasNext()) {
                    sb.append(it.next()).append(StringUtils.SPACE);
                }
                return sb.toString();
            }
        }

        public void add(String tc, List<String> pns) {
            this.mEntries.add(new Entry(tc, pns));
        }

        public String findTargetByProjectName(String pn) {
            for (Entry entry : this.mEntries) {
                if (entry.projectNames.contains(pn)) {
                    return entry.targetConfig;
                }
            }
            return null;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            Iterator<Entry> it = this.mEntries.iterator();
            while (it.hasNext()) {
                sb.append(it.next().toString()).append("\n");
            }
            return sb.toString();
        }
    }

    @Override // com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BaseConfigLineDetector
    protected List<String> getConfigDirs() {
        ArrayList arrayList = new ArrayList();
        Iterator<String> it = super.getConfigDirs().iterator();
        while (it.hasNext()) {
            arrayList.add(it.next() + "nfc/");
        }
        return arrayList;
    }

    String getConfRefPath() {
        List<String> findConfigs = findConfigs(Arrays.asList("nfc_conf_ref"));
        if (findConfigs.isEmpty()) {
            return null;
        }
        return findConfigs.get(0);
    }

    ConfigTable parseTable(String confRefPath) {
        final ConfigTable configTable = new ConfigTable();
        if (readFileLines(confRefPath, new BaseConfigLineDetector.ILineProcessor() { // from class: com.zxtlfasu.wirdvgyk.nfc.chip.detectors.NXPOppoDetector$$ExternalSyntheticLambda0
            @Override // com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BaseConfigLineDetector.ILineProcessor
            public final boolean processLine(String str) {
                return NXPOppoDetector.lambda$parseTable$0(NXPOppoDetector.ConfigTable.this, str);
            }
        })) {
            return configTable;
        }
        return null;
    }

    static /* synthetic */ boolean lambda$parseTable$0(ConfigTable configTable, String str) {
        if (!str.startsWith("#") && !str.isEmpty()) {
            String[] split = str.split(":", 2);
            if (split.length != 2) {
                return false;
            }
            String[] split2 = split[1].split(StringUtils.SPACE);
            if (split2.length == 0) {
                return false;
            }
            ArrayList arrayList = new ArrayList();
            for (String str2 : split2) {
                arrayList.add(str2.replaceFirst("(\\(.*\\))", ""));
            }
            configTable.add(split[0], arrayList);
        }
        return true;
    }

    List<String> getProjectNameCandidates() {
        return Arrays.asList(getSystemProp("ro.separate.soft"), getSystemProp("ro.build.product"), getFileContents("/proc/oppoVersion/prjName"), getFileContents("/proc/oppoVersion/prjVersion"));
    }

    String getConfigSuffix() {
        String confRefPath = getConfRefPath();
        if (confRefPath != null && !confRefPath.isEmpty()) {
            Log.d("NFCCONFIG", String.format("Got nfc_conf_ref at %s", confRefPath));
            ConfigTable parseTable = parseTable(confRefPath);
            if (parseTable == null) {
                return null;
            }
            Log.d("NFCCONFIG", String.format("Got config table: %s", parseTable));
            for (String str : getProjectNameCandidates()) {
                Log.d("NFCCONFIG", String.format("Checking candidate %s", str));
                String findTargetByProjectName = parseTable.findTargetByProjectName(str);
                if (findTargetByProjectName != null && !findTargetByProjectName.isEmpty()) {
                    return findTargetByProjectName;
                }
            }
        }
        return null;
    }

    @Override // com.zxtlfasu.wirdvgyk.nfc.chip.detectors.NXPDetector, com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BaseConfigLineDetector
    protected List<String> getConfigFilenames() {
        List<String> configFilenames = super.getConfigFilenames();
        String configSuffix = getConfigSuffix();
        if (configSuffix != null) {
            Log.d("NFCCONFIG", String.format("Found suffix %s", configSuffix));
            configFilenames.add("libnfc-nxp.conf_" + configSuffix);
        }
        return configFilenames;
    }

    protected static String getFileContents(String path) {
        final StringBuilder sb = new StringBuilder();
        if (readFileLines(path, new BaseConfigLineDetector.ILineProcessor() { // from class: com.zxtlfasu.wirdvgyk.nfc.chip.detectors.NXPOppoDetector$$ExternalSyntheticLambda1
            @Override // com.zxtlfasu.wirdvgyk.nfc.chip.detectors.BaseConfigLineDetector.ILineProcessor
            public final boolean processLine(String str) {
                return NXPOppoDetector.lambda$getFileContents$1(sb, str);
            }
        })) {
            return sb.toString();
        }
        return null;
    }

    static /* synthetic */ boolean lambda$getFileContents$1(StringBuilder sb, String str) {
        sb.append(str).append("\n");
        return true;
    }
}