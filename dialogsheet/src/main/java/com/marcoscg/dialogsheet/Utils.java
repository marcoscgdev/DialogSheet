package com.marcoscg.dialogsheet;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.annotation.ColorInt;
import android.util.TypedValue;

/**
 * Created by @MarcosCGdev on 01/12/2017.
 */

public class Utils {

    static boolean isColorLight(@ColorInt int color) {
        if (color == Color.BLACK) return false;
        else if (color == Color.WHITE || color == Color.TRANSPARENT) return true;
        final double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness < 0.4;
    }

    static int getTextColor(int color) {
        return isColorLight(color) ? Color.parseColor("#DE000000") : Color.parseColor("#FFFFFFFF");
    }

    static int getTextColorSec(int color) {
        return isColorLight(color) ? Color.parseColor("#8A000000") : Color.parseColor("#B3FFFFFF");
    }

    static int getThemeBgColor(Context context) {
        TypedValue typedValue = new TypedValue();
        if (context.getTheme().resolveAttribute(android.R.attr.windowBackground, typedValue, true)) {
            return typedValue.data;
        }
        return -1;
    }

    static int getAttrColor(Context context, String attr) {
        int colorAttr = context.getResources().getIdentifier(attr, "attr", context.getPackageName());
        TypedValue outValue = new TypedValue();
        if (context.getTheme().resolveAttribute(colorAttr, outValue, true))
            return outValue.data;

        return -1;
    }

    static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
