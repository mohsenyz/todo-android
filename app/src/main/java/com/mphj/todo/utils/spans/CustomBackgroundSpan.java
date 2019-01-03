package com.mphj.todo.utils.spans;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.text.style.ReplacementSpan;

public class CustomBackgroundSpan extends ReplacementSpan {

    private int color;
    private int corner;
    private Typeface typeface;
    private float textSize;

    public CustomBackgroundSpan(int color, int corner, Typeface typeface, float textSize) {
        this.color = color;
        this.corner = corner;
        this.typeface = typeface;
        this.textSize = textSize;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        paint.setTextSize(textSize);
        paint.setTypeface(typeface);
        Rect bound = new Rect();
        paint.getTextBounds(text.toString(), start, end, bound);
        return bound.width() + corner;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        paint.setColor(color);
        paint.setTextSize(textSize);
        paint.setTypeface(typeface);
        RectF rect = new RectF(x, top, x + paint.measureText(text, start, end) + corner, bottom);
        canvas.drawRoundRect(rect, corner, corner, paint);
        paint.setColor(Color.WHITE);
        canvas.drawText(text, start, end, x + corner / 2, y, paint);
    }
}