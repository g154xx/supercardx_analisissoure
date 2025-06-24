package com.zxtlfasu.wirdvgyk.gui.component;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;
import androidx.core.content.FileProvider;
import com.zxtlfasu.wirdvgyk.BuildConfig;
import com.zxtlfasu.wirdvgyk.R;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/* loaded from: classes.dex */
public class FileShare {
    private final Context mContext;
    private String mPrefix = "";
    private String mExtension = ".bin";
    private String mMimeType = "application/*";

    public interface IFileShareable {
        void write(OutputStream stream) throws IOException;
    }

    public FileShare setExtension(String extension) {
        this.mExtension = extension;
        return this;
    }

    public FileShare setMimeType(String mimeType) {
        this.mMimeType = mimeType;
        return this;
    }

    public FileShare setPrefix(String prefix) {
        this.mPrefix = prefix;
        return this;
    }

    public FileShare(Context context) {
        this.mContext = context;
    }

    public void share(IFileShareable share) {
        File file = new File(this.mContext.getCacheDir() + "/share/");
        file.mkdir();
        File file2 = new File(file, this.mPrefix + this.mExtension);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file2);
            try {
                share.write(fileOutputStream);
                fileOutputStream.close();
                Intent flags = new Intent("android.intent.action.SEND").setType(this.mMimeType).putExtra("android.intent.extra.STREAM", FileProvider.getUriForFile(this.mContext, BuildConfig.APPLICATION_ID, file2)).setFlags(1);
                Context context = this.mContext;
                context.startActivity(Intent.createChooser(flags, context.getString(R.string.share_file)));
            } finally {
            }
        } catch (IOException e) {
            Context context2 = this.mContext;
            Toast.makeText(context2, context2.getString(R.string.share_error), 1).show();
            e.printStackTrace();
        }
    }
}