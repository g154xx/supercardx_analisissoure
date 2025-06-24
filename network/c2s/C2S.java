package com.zxtlfasu.wirdvgyk.network.c2s;

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
public final class C2S {

    public interface ServerDataOrBuilder extends MessageLiteOrBuilder {
        ByteString getData();

        ServerData.Opcode getOpcode();

        int getOpcodeValue();
    }

    public static void registerAllExtensions(ExtensionRegistryLite extensionRegistryLite) {
    }

    private C2S() {
    }

    public static final class ServerData extends GeneratedMessageLite<ServerData, Builder> implements ServerDataOrBuilder {
        public static final int DATA_FIELD_NUMBER = 2;
        private static final ServerData DEFAULT_INSTANCE;
        public static final int OPCODE_FIELD_NUMBER = 1;
        private static volatile Parser<ServerData> PARSER;
        private ByteString data_ = ByteString.EMPTY;
        private int opcode_;

        /* JADX INFO: Access modifiers changed from: private */
        public void clearOpcode() {
            this.opcode_ = 0;
        }

        public static ServerData getDefaultInstance() {
            return DEFAULT_INSTANCE;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOpcodeValue(int i) {
            this.opcode_ = i;
        }

        @Override // com.zxtlfasu.wirdvgyk.network.c2s.C2S.ServerDataOrBuilder
        public ByteString getData() {
            return this.data_;
        }

        @Override // com.zxtlfasu.wirdvgyk.network.c2s.C2S.ServerDataOrBuilder
        public int getOpcodeValue() {
            return this.opcode_;
        }

        private ServerData() {
        }

        public enum Opcode implements Internal.EnumLite {
            OP_PSH(0),
            OP_SYN(1),
            OP_ACK(2),
            OP_FIN(3),
            UNRECOGNIZED(-1);

            public static final int OP_ACK_VALUE = 2;
            public static final int OP_FIN_VALUE = 3;
            public static final int OP_PSH_VALUE = 0;
            public static final int OP_SYN_VALUE = 1;
            private static final Internal.EnumLiteMap<Opcode> internalValueMap = new Internal.EnumLiteMap<Opcode>() { // from class: com.zxtlfasu.wirdvgyk.network.c2s.C2S.ServerData.Opcode.1
                @Override // com.google.protobuf.Internal.EnumLiteMap
                public Opcode findValueByNumber(int i) {
                    return Opcode.forNumber(i);
                }
            };
            private final int value;

            public static Opcode forNumber(int i) {
                if (i == 0) {
                    return OP_PSH;
                }
                if (i == 1) {
                    return OP_SYN;
                }
                if (i == 2) {
                    return OP_ACK;
                }
                if (i != 3) {
                    return null;
                }
                return OP_FIN;
            }

            public static Internal.EnumLiteMap<Opcode> internalGetValueMap() {
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
            public static Opcode valueOf(int i) {
                return forNumber(i);
            }

            public static Internal.EnumVerifier internalGetVerifier() {
                return OpcodeVerifier.INSTANCE;
            }

            private static final class OpcodeVerifier implements Internal.EnumVerifier {
                static final Internal.EnumVerifier INSTANCE = new OpcodeVerifier();

                private OpcodeVerifier() {
                }

                @Override // com.google.protobuf.Internal.EnumVerifier
                public boolean isInRange(int i) {
                    return Opcode.forNumber(i) != null;
                }
            }

            Opcode(int i) {
                this.value = i;
            }
        }

        @Override // com.zxtlfasu.wirdvgyk.network.c2s.C2S.ServerDataOrBuilder
        public Opcode getOpcode() {
            Opcode forNumber = Opcode.forNumber(this.opcode_);
            return forNumber == null ? Opcode.UNRECOGNIZED : forNumber;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public void setOpcode(Opcode opcode) {
            this.opcode_ = opcode.getNumber();
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

        public static ServerData parseFrom(ByteBuffer byteBuffer) throws InvalidProtocolBufferException {
            return (ServerData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer);
        }

        public static ServerData parseFrom(ByteBuffer byteBuffer, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ServerData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteBuffer, extensionRegistryLite);
        }

        public static ServerData parseFrom(ByteString byteString) throws InvalidProtocolBufferException {
            return (ServerData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString);
        }

        public static ServerData parseFrom(ByteString byteString, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ServerData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, byteString, extensionRegistryLite);
        }

        public static ServerData parseFrom(byte[] bArr) throws InvalidProtocolBufferException {
            return (ServerData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr);
        }

        public static ServerData parseFrom(byte[] bArr, ExtensionRegistryLite extensionRegistryLite) throws InvalidProtocolBufferException {
            return (ServerData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, bArr, extensionRegistryLite);
        }

        public static ServerData parseFrom(InputStream inputStream) throws IOException {
            return (ServerData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static ServerData parseFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ServerData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ServerData parseDelimitedFrom(InputStream inputStream) throws IOException {
            return (ServerData) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream);
        }

        public static ServerData parseDelimitedFrom(InputStream inputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ServerData) parseDelimitedFrom(DEFAULT_INSTANCE, inputStream, extensionRegistryLite);
        }

        public static ServerData parseFrom(CodedInputStream codedInputStream) throws IOException {
            return (ServerData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream);
        }

        public static ServerData parseFrom(CodedInputStream codedInputStream, ExtensionRegistryLite extensionRegistryLite) throws IOException {
            return (ServerData) GeneratedMessageLite.parseFrom(DEFAULT_INSTANCE, codedInputStream, extensionRegistryLite);
        }

        public static Builder newBuilder() {
            return DEFAULT_INSTANCE.createBuilder();
        }

        public static Builder newBuilder(ServerData serverData) {
            return DEFAULT_INSTANCE.createBuilder(serverData);
        }

        public static final class Builder extends GeneratedMessageLite.Builder<ServerData, Builder> implements ServerDataOrBuilder {
            private Builder() {
                super(ServerData.DEFAULT_INSTANCE);
            }

            @Override // com.zxtlfasu.wirdvgyk.network.c2s.C2S.ServerDataOrBuilder
            public int getOpcodeValue() {
                return ((ServerData) this.instance).getOpcodeValue();
            }

            public Builder setOpcodeValue(int i) {
                copyOnWrite();
                ((ServerData) this.instance).setOpcodeValue(i);
                return this;
            }

            @Override // com.zxtlfasu.wirdvgyk.network.c2s.C2S.ServerDataOrBuilder
            public Opcode getOpcode() {
                return ((ServerData) this.instance).getOpcode();
            }

            public Builder setOpcode(Opcode opcode) {
                copyOnWrite();
                ((ServerData) this.instance).setOpcode(opcode);
                return this;
            }

            public Builder clearOpcode() {
                copyOnWrite();
                ((ServerData) this.instance).clearOpcode();
                return this;
            }

            @Override // com.zxtlfasu.wirdvgyk.network.c2s.C2S.ServerDataOrBuilder
            public ByteString getData() {
                return ((ServerData) this.instance).getData();
            }

            public Builder setData(ByteString byteString) {
                copyOnWrite();
                ((ServerData) this.instance).setData(byteString);
                return this;
            }

            public Builder clearData() {
                copyOnWrite();
                ((ServerData) this.instance).clearData();
                return this;
            }
        }

        @Override // com.google.protobuf.GeneratedMessageLite
        protected final Object dynamicMethod(GeneratedMessageLite.MethodToInvoke methodToInvoke, Object obj, Object obj2) {
            switch (AnonymousClass1.$SwitchMap$com$google$protobuf$GeneratedMessageLite$MethodToInvoke[methodToInvoke.ordinal()]) {
                case 1:
                    return new ServerData();
                case 2:
                    return new Builder();
                case 3:
                    return newMessageInfo(DEFAULT_INSTANCE, "\u0000\u0002\u0000\u0000\u0001\u0002\u0002\u0000\u0000\u0000\u0001\f\u0002\n", new Object[]{"opcode_", "data_"});
                case 4:
                    return DEFAULT_INSTANCE;
                case 5:
                    Parser<ServerData> parser = PARSER;
                    if (parser == null) {
                        synchronized (ServerData.class) {
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
            ServerData serverData = new ServerData();
            DEFAULT_INSTANCE = serverData;
            GeneratedMessageLite.registerDefaultInstance(ServerData.class, serverData);
        }

        public static Parser<ServerData> parser() {
            return DEFAULT_INSTANCE.getParserForType();
        }
    }

    /* renamed from: com.zxtlfasu.wirdvgyk.network.c2s.C2S$1, reason: invalid class name */
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