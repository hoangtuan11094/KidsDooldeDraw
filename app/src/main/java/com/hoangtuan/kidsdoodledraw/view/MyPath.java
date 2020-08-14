package com.hoangtuan.kidsdoodledraw.view;

import android.graphics.Path;

public class MyPath extends Path {
    private int TYPE = 0;
    private boolean isChecked = false;
    private PaintOptions paintOptions;

    public int getTYPE() {
        return TYPE;
    }

    public void setTYPE(int TYPE) {
        this.TYPE = TYPE;
    }

    public void setPaintOptions(PaintOptions paintOptions) {
        this.paintOptions = paintOptions;
    }

    public PaintOptions getPaintOptions() {
        return paintOptions;
    }

    public MyPath() {
        paintOptions = new PaintOptions();
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }
}
