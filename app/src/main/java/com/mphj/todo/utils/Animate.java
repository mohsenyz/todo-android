package com.mphj.todo.utils;

import android.view.View;

public class Animate {

    public static void toBottom(View view, int duration) {
        view.animate()
                .translationY(view.getHeight() + view.getBottom())
                .setDuration(duration)
                .start();
    }

    public static void toTop(View view, int duration) {
        view.animate()
                .translationY(0)
                .setDuration(duration)
                .start();
    }


}
