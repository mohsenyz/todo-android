package com.mphj.todo.view;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class BorderedDrawable extends Drawable {

    private int gravity;
    private int width;

    private Paint paint;

    public BorderedDrawable() {
        paint = new Paint();
    }

    public BorderedDrawable(int color, int gravity, int width) {
        paint = new Paint();
        paint.setColor(color);
        this.gravity = gravity;
        this.width = width;
    }

    public void setColor(int color) {
        paint.setColor(color);
        invalidateSelf();
    }

    public void setWidth(int width) {
        this.width = width;
        invalidateSelf();
    }

    public void setGravity(int gravity) {
        this.gravity = gravity;
        invalidateSelf();
    }

    @Override
    public void draw(@NonNull Canvas canvas) {
        int x1 = 0, x2 = 0, y1 = 0, y2 = 0;
        Rect rect = getBounds();
        if (gravity == Gravity.RIGHT) {
            x1 = rect.right - width;
            x2 = rect.right;
            y1 = 0;
            y2 = rect.bottom;
        }
        Log.d("TAG", "x1 : " + x1 + ", x2 : " + x2);
        canvas.drawRect(new Rect(x1, y1, x2, y2), paint);
    }

    @Override
    public void setAlpha(int alpha) {

    }

    @Override
    public void setColorFilter(@Nullable ColorFilter colorFilter) {

    }

    @Override
    public int getOpacity() {
        return PixelFormat.TRANSLUCENT;
    }
}
