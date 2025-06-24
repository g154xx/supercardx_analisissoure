package com.zxtlfasu.wirdvgyk.db;

/* loaded from: classes.dex */
public class TagInfo {
    private byte[] data;
    private int id;
    private String name;

    public byte[] getData() {
        return this.data;
    }

    public int getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public void setData(byte[] data) {
        this.data = data;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TagInfo(String name, byte[] data) {
        this.name = name;
        this.data = data;
    }

    public String toString() {
        return getName();
    }
}