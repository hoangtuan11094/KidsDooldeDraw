package com.hoangtuan.kidsdoodledraw.view;

import android.graphics.Color;

public class PaintOptions {
    private int color = Color.GREEN;
    private float strokeWidth = 5f;
    private boolean isEraser = false;

    public PaintOptions() {
    }

    public PaintOptions(int color, float strokeWidth, boolean isEraser) {
        this.color = color;
        this.strokeWidth = strokeWidth;
        this.isEraser = isEraser;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getStrokeWidth() {
        return strokeWidth;
    }

    public void setStrokeWidth(float strokeWidth) {
        this.strokeWidth = strokeWidth;
    }

    public boolean isEraser() {
        return isEraser;
    }

    public void setEraser(boolean eraser) {
        isEraser = eraser;
    }
}
