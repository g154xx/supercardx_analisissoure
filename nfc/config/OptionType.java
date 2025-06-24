package com.zxtlfasu.wirdvgyk.nfc.config;

/* loaded from: classes.dex */
public enum OptionType {
    LA_BIT_FRAME_SDD(48),
    LA_PLATFORM_CONFIG(49),
    LA_SEL_INFO(50),
    LA_NFCID1(51),
    LB_NFCID0(57),
    LB_APPLICATION_DATA(58),
    LB_SFGI(59),
    LB_SENSB_INFO(56),
    LB_ADC_FO(60),
    LF_T3T_IDENTIFIERS_1(64),
    LF_T3T_FLAGS(83),
    LF_T3T_PMM(81),
    LA_HIST_BY(89),
    LB_H_INFO_RSP(90);

    final int value;

    public byte getID() {
        return (byte) this.value;
    }

    OptionType(int val) {
        this.value = val;
    }

    public static OptionType fromType(byte type) {
        for (OptionType optionType : values()) {
            if (optionType.getID() == type) {
                return optionType;
            }
        }
        return null;
    }
}