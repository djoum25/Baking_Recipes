package com.laurent_julien_nano_degree_project.bakingrecipes;

import android.app.Activity;
import android.util.DisplayMetrics;
import android.view.Display;

public class ScreenSizeUtility {
    private float width;

    public ScreenSizeUtility (Activity activity) {
        Display display = activity.getWindowManager().getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);
        float density = activity.getResources().getDisplayMetrics().density;
        width = metrics.widthPixels / density;
    }

    public float getWidth () {
        return width;
    }
}
