package com.zxtlfasu.wirdvgyk.db;

import androidx.lifecycle.LiveData;
import java.util.List;

/* loaded from: classes.dex */
public interface TagInfoDao {
    void delete(TagInfo tagInfo);

    LiveData<List<TagInfo>> getAll();

    void insert(TagInfo tagInfo);
}