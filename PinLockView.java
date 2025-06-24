package com.zxtlfasu.wirdvgyk;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Timer;
import java.util.TimerTask;

/* loaded from: classes.dex */
public class PinLockView extends LinearLayout {
    private int curDigit;
    String curPinCode;
    private LinearLayout.LayoutParams params;
    private ArrayList<ImageButton> pinButtons;
    String pinCode;
    private LinearLayout pinContainer;
    private ArrayList<ImageView> pinImages;
    private int pinLength;
    private PinLockListener pinLockListener;
    private LinearLayout viewContainer;

    public void setPinLockListener(PinLockListener pinLockListener) {
        this.pinLockListener = pinLockListener;
    }

    public PinLockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.pinImages = new ArrayList<>();
        this.pinButtons = new ArrayList<>();
        this.pinLength = 4;
        this.curDigit = 0;
        this.pinCode = "";
        this.curPinCode = "";
        init(context, attrs, 0);
    }

    public PinLockView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.pinImages = new ArrayList<>();
        this.pinButtons = new ArrayList<>();
        this.pinLength = 4;
        this.curDigit = 0;
        this.pinCode = "";
        this.curPinCode = "";
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        inflate(context, R.layout.activity_pin_lock, this);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attrs, R.styleable.PinLockView);
        float dimension = obtainStyledAttributes.getDimension(1, 64.0f);
        this.pinLength = obtainStyledAttributes.getInt(2, 4);
        boolean z = obtainStyledAttributes.getBoolean(0, false);
        obtainStyledAttributes.recycle();
        int i = (int) dimension;
        initComponents(i, z);
        initPinLength(context, this.pinLength, i);
    }

    private void initComponents(int buttonSize, boolean borderEnabled) {
        this.viewContainer = (LinearLayout) findViewById(R.id.linearLayout1);
        this.pinContainer = (LinearLayout) findViewById(R.id.linearLayout2);
        this.pinButtons.add((ImageButton) findViewById(R.id.imageButton1));
        this.pinButtons.add((ImageButton) findViewById(R.id.imageButton2));
        this.pinButtons.add((ImageButton) findViewById(R.id.imageButton3));
        this.pinButtons.add((ImageButton) findViewById(R.id.imageButton4));
        this.pinButtons.add((ImageButton) findViewById(R.id.imageButton5));
        this.pinButtons.add((ImageButton) findViewById(R.id.imageButton6));
        this.pinButtons.add((ImageButton) findViewById(R.id.imageButton7));
        this.pinButtons.add((ImageButton) findViewById(R.id.imageButton8));
        this.pinButtons.add((ImageButton) findViewById(R.id.imageButton9));
        this.pinButtons.add((ImageButton) findViewById(R.id.imageButton10));
        this.pinButtons.add((ImageButton) findViewById(R.id.imageButton11));
        this.pinButtons.add((ImageButton) findViewById(R.id.imageButton12));
        if (borderEnabled) {
            this.viewContainer.setBackgroundResource(R.drawable.layout_border);
        }
        Iterator<ImageButton> it = this.pinButtons.iterator();
        while (it.hasNext()) {
            ImageButton next = it.next();
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(buttonSize, buttonSize);
            this.params = layoutParams;
            next.setLayoutParams(layoutParams);
            next.setOnClickListener(new View.OnClickListener() { // from class: com.zxtlfasu.wirdvgyk.PinLockView$$ExternalSyntheticLambda0
                @Override // android.view.View.OnClickListener
                public final void onClick(View view) {
                    PinLockView.this.pinButtonClickListener(view);
                }
            });
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:15:0x0006, code lost:
    
        if (r6 < 4) goto L4;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    private void initPinLength(android.content.Context r5, int r6, int r7) {
        /*
            r4 = this;
            r0 = 7
            if (r6 <= r0) goto L5
        L3:
            r6 = r0
            goto L9
        L5:
            r0 = 4
            if (r6 >= r0) goto L9
            goto L3
        L9:
            r0 = 1
        La:
            if (r0 >= r6) goto L26
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            r1.<init>()
            java.lang.String r2 = r4.pinCode
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r2 = "1"
            java.lang.StringBuilder r1 = r1.append(r2)
            java.lang.String r1 = r1.toString()
            r4.pinCode = r1
            int r0 = r0 + 1
            goto La
        L26:
            r0 = 0
        L27:
            if (r0 >= r6) goto L54
            android.widget.LinearLayout$LayoutParams r1 = new android.widget.LinearLayout$LayoutParams
            int r2 = r7 / 3
            r1.<init>(r2, r2)
            android.widget.ImageView r2 = new android.widget.ImageView
            r2.<init>(r5)
            r3 = 2131230876(0x7f08009c, float:1.8077817E38)
            r2.setImageResource(r3)
            r3 = 5
            r1.setMargins(r3, r3, r3, r3)
            r2.setLayoutParams(r1)
            android.widget.ImageView$ScaleType r1 = android.widget.ImageView.ScaleType.FIT_CENTER
            r2.setScaleType(r1)
            java.util.ArrayList<android.widget.ImageView> r1 = r4.pinImages
            r1.add(r2)
            android.widget.LinearLayout r1 = r4.pinContainer
            r1.addView(r2)
            int r0 = r0 + 1
            goto L27
        L54:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.zxtlfasu.wirdvgyk.PinLockView.initPinLength(android.content.Context, int, int):void");
    }

    public void setPinCode(String pinCode) {
        if (pinCode == "" || pinCode.length() != this.pinLength) {
            return;
        }
        try {
            this.pinCode = pinCode;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearPinCode() {
        this.curPinCode = "";
        this.curDigit = 0;
        new Timer().schedule(new TimerTask() { // from class: com.zxtlfasu.wirdvgyk.PinLockView.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                for (int i = 0; i < PinLockView.this.pinLength; i++) {
                    ((ImageView) PinLockView.this.pinImages.get(i)).setImageResource(R.drawable.ic_dot_empty);
                }
            }
        }, 300L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void pinButtonClickListener(View view) {
        int id = view.getId();
        if (id == R.id.imageButton12 || id == R.id.imageButton10) {
            int i = this.curDigit;
            if (i == 0) {
                return;
            }
            if (i != 0) {
                String str = this.curPinCode;
                this.curPinCode = str.substring(0, str.length() - 1);
                this.pinImages.get(this.curDigit - 1).setImageResource(R.drawable.ic_dot_empty);
                this.pinLockListener.onPinDelete();
                this.curDigit--;
            }
            if (this.curDigit == 0) {
                this.pinLockListener.onPinEmpty();
                return;
            }
            return;
        }
        int i2 = this.curDigit;
        int i3 = this.pinLength;
        if (i2 == i3) {
            return;
        }
        if (i2 != i3) {
            this.pinImages.get(i2).setImageResource(R.drawable.ic_dot_fill);
            this.pinLockListener.onPinEnter();
            this.curDigit++;
            this.curPinCode += ((this.pinButtons.indexOf((ImageButton) findViewById(id)) + 1) % 11);
        }
        if (this.curDigit == this.pinLength) {
            if (this.curPinCode.matches(this.pinCode)) {
                this.pinLockListener.onPinComplete(true, this.curPinCode);
                clearPinCode();
            } else {
                this.pinLockListener.onPinComplete(false, this.curPinCode);
                clearPinCode();
            }
        }
    }
}