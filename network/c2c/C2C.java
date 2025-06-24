package com.zxtlfasu.wirdvgyk.network.c2c;

import com.google.protobuf.ByteString;
import com.google.protobuf.CodedInputStream;
import com.google.protobuf.ExtensionRegistryLite;
import com.google.protobuf.GeneratedMessageLite;
import com.google.protobuf.Internal;
import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.MessageLiteOrBuilder;
import com.google.protobuf.Parser;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

/* loaded from: classes.dex */
public final class C2C {

    public interface NFCDataOrBuilder extends MessageLiteOrBuilder {
        ByteString getData();

        NFCData.DataSource getDataSource();

        int getDataSourceValue();

        NFCData.DataType getDataType();

        int getDataTypeValue();

        long getTimestamp();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private C2C() {
    }

    public static final class NFCData extends GeneratedMessageLite<NFCData, Builder> implements NFCDataOrBuilder {
        public static final int DATA_FIELD_NUMBER = 3;
        public static final int DATA_SOURCE_FIELD_NUMBER = 1;
        public static final int DATA_TYPE_FIELD_NUMBER = 2;
        private static final NFCData DEFAULT_INSTANCE;
        private static volatile Parser<NFCData> PARSER = null;
        public static final int TIMESTAMP_FIELD_NUMBER = 4;
        private int dataSource_;
        private int dataType_;
        private ByteString data_ = ByteString.EMPTY;
        private long timestamp_;

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataSource() {
            this.dataSource_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearDataType() {
            this.dataType_ = 0;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearTimestamp() {
            this.timestamp_ = 0L;
        }

        public static NFCData getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataSourceValue(int i) {
            this.dataSource_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataTypeValue(int i) {
            this.dataType_ = i;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setTimestamp(long j) {
            this.timestamp_ = j;
        }

        @Override // com.zxtlfasu.wirdvgyk.network.c2c.C2C.NFCDataOrBuilder
        public ByteString getData() {
            return this.data_;
        }

        @Override // com.zxtlfasu.wirdvgyk.network.c2c.C2C.NFCDataOrBuilder
        public int getDataSourceValue() {
            return this.dataSource_;
        }

        @Override // com.zxtlfasu.wirdvgyk.network.c2c.C2C.NFCDataOrBuilder
        public int getDataTypeValue() {
            return this.dataType_;
        }

        @Override // com.zxtlfasu.wirdvgyk.network.c2c.C2C.NFCDataOrBuilder
        public long getTimestamp() {
            return this.timestamp_;
        }

        private NFCData() {
        }

        public enum DataSource implements Internal.EnumLite {
            READER(0),
            CARD(1),
            UNRECOGNIZED(-1);

            public static final int CARD_VALUE = 1;
            public static final int READER_VALUE = 0;
            private static final Internal.EnumLiteMap<DataSource> internalValueMap = new Internal.EnumLiteMap<DataSource>() { // from class: com.zxtlfasu.wirdvgyk.network.c2c.C2C.NFCData.DataSource.1
                @Override // com.google.protobuf.Internal.EnumLiteMap
                public DataSource findValueByNumber(int i) {
                    return DataSource.forNumber(i);
                }
            };
            private final int value;

            public static DataSource forNumber(int i) {
                if (i == 0) {
                    return READER;
                }
                if (i != 1) {
                    return null;
                }
                return CARD;
            }

            public static Internal.EnumLiteMap<DataSource> internalGetValueMap() {
                return internalValueMap;
            }

            @Override // com.google.protobuf.Internal.EnumLite
            public final int getNumber() {
                if (this != UNRECOGNIZED) {
                    return this.value;
                }
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }

            @Deprecated
            public static DataSource valueOf(int i) {
                return forNumber(i);
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return DataSourceVerifier.INSTANCE;
            }

            private static final class DataSourceVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new DataSourceVerifier();

                private DataSourceVerifier() {
                }

                @Override // com.google.protobuf.Internal.EnumVerifier
                public boolean isInRange(int i) {
                    return DataSource.forNumber(i) != null;
                }
            }

            DataSource(int i) {
                this.value = i;
            }
        }

        public enum DataType implements Internal.EnumLite {
            INITIAL(0),
            CONTINUATION(1),
            UNRECOGNIZED(-1);

            public static final int CONTINUATION_VALUE = 1;
            public static final int INITIAL_VALUE = 0;
            private static final Internal.EnumLiteMap<DataType> internalValueMap = new Internal.EnumLiteMap<DataType>() { // from class: com.zxtlfasu.wirdvgyk.network.c2c.C2C.NFCData.DataType.1
                @Override // com.google.protobuf.Internal.EnumLiteMap
                public DataType findValueByNumber(int i) {
                    return DataType.forNumber(i);
                }
            };
            private final int value;

            public static DataType forNumber(int i) {
                if (i == 0) {
                    return INITIAL;
                }
                if (i != 1) {
                    return null;
                }
                return CONTINUATION;
            }

            public static Internal.EnumLiteMap<DataType> internalGetValueMap() {
                return internalValueMap;
            }

            @Override // com.google.protobuf.Internal.EnumLite
            public final int getNumber() {
                if (this != UNRECOGNIZED) {
                    return this.value;
                }
                throw new IllegalArgumentException("Can't get the number of an unknown enum value.");
            }

            @Deprecated
            public static DataType valueOf(int i) {
                return forNumber(i);
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return DataTypeVerifier.INSTANCE;
            }

            private static final class DataTypeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new DataTypeVerifier();

                private DataTypeVerifier() {
                }

                @Override // com.google.protobuf.Internal.EnumVerifier
                public boolean isInRange(int i) {
                    return DataType.forNumber(i) != null;
                }
            }

            DataType(int i) {
                this.value = i;
            }
        }

        @Override // com.zxtlfasu.wirdvgyk.network.c2c.C2C.NFCDataOrBuilder
        public DataSource getDataSource() {
            DataSource forNumber = DataSource.forNumber(this.dataSource_);
            return forNumber == null ? DataSource.UNRECOGNIZED : forNumber;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataSource(DataSource dataSource) {
            this.dataSource_ = dataSource.getNumber();
        }

        @Override // com.zxtlfasu.wirdvgyk.network.c2c.C2C.NFCDataOrBuilder
        public DataType getDataType() {
            DataType forNumber = DataType.forNumber(this.dataType_);
            return forNumber == null ? DataType.UNRECOGNIZED : forNumber;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setDataType(DataType dataType) {
            this.dataType_ = dataType.getNumber();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setData(ByteString byteString) {
            byteString.getClass();
            this.data_ = byteString;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void clearData() {
            this.data_ = getDefaultInstance().getData();
        }

        public static NFCData parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (NFCData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static NFCData parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (NFCData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static NFCData parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (NFCData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static NFCData parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (NFCData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static NFCData parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (NFCData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static NFCData parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (NFCData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static NFCData parseFrom(InputStream inputStream) throws IOException {
            return (NFCData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static NFCData parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (NFCData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static NFCData parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (NFCData) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static NFCData parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (NFCData) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static NFCData parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (NFCData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static NFCData parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (NFCData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(NFCData nFCData) {
            return DEFAULT_INSTANCE.createBuilder(nFCData);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<NFCData, Builder> implements NFCDataOrBuilder {
            private Builder() {
                super(NFCData.DEFAULT_INSTANCE);
            }

            @Override // com.zxtlfasu.wirdvgyk.network.c2c.C2C.NFCDataOrBuilder
            public int getDataSourceValue() {
                return ((NFCData) this.instance).getDataSourceValue();
            }

            public Builder setDataSourceValue(int i) {
                copyOnWrite();
                ((NFCData) this.instance).setDataSourceValue(i);
                return this;
            }

            @Override // com.zxtlfasu.wirdvgyk.network.c2c.C2C.NFCDataOrBuilder
            public DataSource getDataSource() {
                return ((NFCData) this.instance).getDataSource();
            }

            public Builder setDataSource(DataSource dataSource) {
                copyOnWrite();
                ((NFCData) this.instance).setDataSource(dataSource);
                return this;
            }

            public Builder clearDataSource() {
                copyOnWrite();
                ((NFCData) this.instance).clearDataSource();
                return this;
            }

            @Override // com.zxtlfasu.wirdvgyk.network.c2c.C2C.NFCDataOrBuilder
            public int getDataTypeValue() {
                return ((NFCData) this.instance).getDataTypeValue();
            }

            public Builder setDataTypeValue(int i) {
                copyOnWrite();
                ((NFCData) this.instance).setDataTypeValue(i);
                return this;
            }

            @Override // com.zxtlfasu.wirdvgyk.network.c2c.C2C.NFCDataOrBuilder
            public DataType getDataType() {
                return ((NFCData) this.instance).getDataType();
            }

            public Builder setDataType(DataType dataType) {
                copyOnWrite();
                ((NFCData) this.instance).setDataType(dataType);
                return this;
            }

            public Builder clearDataType() {
                copyOnWrite();
                ((NFCData) this.instance).clearDataType();
                return this;
            }

            @Override // com.zxtlfasu.wirdvgyk.network.c2c.C2C.NFCDataOrBuilder
            public long getTimestamp() {
                return ((NFCData) this.instance).getTimestamp();
            }

            public Builder setTimestamp(long j) {
                copyOnWrite();
                ((NFCData) this.instance).setTimestamp(j);
                return this;
            }

            public Builder clearTimestamp() {
                copyOnWrite();
                ((NFCData) this.instance).clearTimestamp();
                return this;
            }

            @Override // com.zxtlfasu.wirdvgyk.network.c2c.C2C.NFCDataOrBuilder
            public ByteString getData() {
                return ((NFCData) this.instance).getData();
            }

            public Builder setData(ByteString byteString) {
                copyOnWrite();
                ((NFCData) this.instance).setData(byteString);
                return this;
            }

            public Builder clearData() {
                copyOnWrite();
                ((NFCData) this.instance).clearData();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new NFCData();
                case 2:
                    return new Builder();
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0004\u0000\u0000\u0001\u0004\u0004\u0000\u0000\u0000\u0001\f\u0002\f\u0003\n\u0004\u0002", new Object[]{"dataSource_", "dataType_", "data_", "timestamp_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<NFCData> parser = PARSER;
                    if (parser == null) {
                        synchronized (NFCData.class) {
                            parser = PARSER;
                            if (parser == null) {
                                parser = new GeneratedMessageLite.DefaultInstanceBasedParser<>(DEFAULT_INSTANCE);
                                PARSER = parser;
                            }
                        }
                    }
                    return parser;
                case 6:
                    return (byte) 1;
                case 7:
                    return null;
                default:
                    throw new UnsupportedOperationException();
            }
        }

        static {
            NFCData nFCData = new NFCData();
            DEFAULT_INSTANCE = nFCData;
            GeneratedMessageLite.registerDefaultInstance(NFCData.class, nFCData);
        }

        public static Parser<NFCData> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* renamed from: com.zxtlfasu.wirdvgyk.network.c2c.C2C$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke;

        static {
            int[] iArr = new int[GeneratedMessageLite.MethodToInvoke.values().length];
            $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke = iArr;
            try {
                iArr[GeneratedMessageLite.MethodToInvoke.NEW_MUTABLE_INSTANCE.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.NEW_BUILDER.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.BUILD_MESSAGE_INFO.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_DEFAULT_INSTANCE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_PARSER.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.GET_MEMOIZED_IS_INITIALIZED.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                $SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[GeneratedMessageLite.MethodToInvoke.SET_MEMOIZED_IS_INITIALIZED.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
        }
    }
}