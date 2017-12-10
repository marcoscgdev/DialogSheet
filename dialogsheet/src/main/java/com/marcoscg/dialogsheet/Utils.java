package com.marcoscg.dialogsheet;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.graphics.ColorUtils;
import android.util.TypedValue;
import android.widget.Button;

/**
 * Created by @MarcosCGdev on 01/12/2017.
 */

public class Utils {

    @ColorInt
    static int darkenColor(@ColorInt int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.85f;
        return Color.HSVToColor(hsv);
    }

    @ColorInt
    static int lightenColor(@ColorInt int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 1.60f;
        return Color.HSVToColor(hsv);
    }

    static boolean isColorLight(@ColorInt int color) {
        if (color == Color.BLACK) return false;
        else if (color == Color.WHITE || color == Color.TRANSPARENT) return true;
        final double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness < 0.4;
    }

    static int buttonTextColor(@ColorInt int color) {
        if (isColorLight(color))
            return Color.BLACK;
        else return Color.WHITE;
    }

    static int getThemeAccentColor(Context context) {
        int colorAttr;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            colorAttr = android.R.attr.colorAccent;
        } else {
            colorAttr = context.getResources().getIdentifier("colorAccent", "attr", context.getPackageName());
        }
        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(colorAttr, outValue, true);
        return outValue.data;
    }

    static void setButton(Context context, @ColorInt int color, Button button, boolean colored) {
        if (!colored) {
            if (getThemeBgColor(context)!=-1)
                color = getThemeBgColor(context);
            else
                color = Color.parseColor("#ffffff");
        }

        int selDark = ColorUtils.blendARGB(color, Color.parseColor("#1A000000"), 0.15f);
        int selLight = ColorUtils.blendARGB(color, Color.parseColor("#1AFFFFFF"), 0.15f);
        int selectedColor = isColorLight(color) ? selDark : selLight;
        //int selectedColor = isColorLight(color) ? Color.parseColor("#1A000000") : Color.parseColor("#1AFFFFFF");
        //int selectedColor = isColorLight(color) ? darkenColor(color) : lightenColor(color);

        GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                new int[]{selectedColor, selectedColor});
        GradientDrawable drawable2 = new GradientDrawable(GradientDrawable.Orientation.BOTTOM_TOP,
                new int[]{color, color});

        drawable.setCornerRadius(dpToPx(2));
        drawable2.setCornerRadius(dpToPx(2));

        StateListDrawable button1bg = new StateListDrawable();
        button1bg.addState(new int[] {android.R.attr.state_pressed}, drawable);
        button1bg.addState(new int[] {}, drawable2);
        button1bg.setExitFadeDuration(250);

        button.setBackgroundDrawable(button1bg);
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

    private static int dpToPx(int dp) {
        return (int) (dp * Resources.getSystem().getDisplayMetrics().density);
    }
}
