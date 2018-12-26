package com.mphj.todo.utils;

import android.content.Context;
import android.util.TypedValue;

public class DimensionUtils {

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

}
