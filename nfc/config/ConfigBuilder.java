package com.zxtlfasu.wirdvgyk.nfc.config;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class ConfigBuilder {
    private final List<ConfigOption> mOptions = new ArrayList();

    public List<ConfigOption> getOptions() {
        return this.mOptions;
    }

    public ConfigBuilder() {
    }

    public ConfigBuilder(byte[] config) {
        parse(config);
    }

    public void add(OptionType ID, byte[] data) {
        this.mOptions.add(new ConfigOption(ID, data));
    }

    public void add(OptionType ID, byte data) {
        this.mOptions.add(new ConfigOption(ID, data));
    }

    public void add(ConfigOption option) {
        this.mOptions.add(option);
    }

    private void parse(byte[] config) {
        this.mOptions.clear();
        int i = 0;
        while (true) {
            int i2 = i + 2;
            if (i2 >= config.length) {
                return;
            }
            byte b = config[i];
            int i3 = config[i + 1];
            byte[] bArr = new byte[i3];
            System.arraycopy(config, i2, bArr, 0, i3);
            add(OptionType.fromType(b), bArr);
            i += i3 + 2;
        }
    }

    public byte[] build() {
        Iterator<ConfigOption> it = this.mOptions.iterator();
        int i = 0;
        int i2 = 0;
        while (it.hasNext()) {
            i2 += it.next().len() + 2;
        }
        byte[] bArr = new byte[i2];
        for (ConfigOption configOption : this.mOptions) {
            configOption.push(bArr, i);
            i += configOption.len() + 2;
        }
        return bArr;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (ConfigOption configOption : this.mOptions) {
            if (sb.length() > 0) {
                sb.append("\n");
            }
            sb.append(configOption.toString());
        }
        return sb.toString();
    }
}