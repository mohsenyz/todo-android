package com.mphj.todo.utils;

import android.content.Context;
import android.graphics.Typeface;

public class FontUtils {

    private static final String DEFAULT_FONT = "iran.ttf";

    public static Typeface def(Context context) {
        return Typeface.createFromAsset(context.getAssets(), DEFAULT_FONT);
    }

}
