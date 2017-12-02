package com.marcoscg.dialogsheet;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.util.TypedValue;
import android.widget.Button;

/**
 * Created by marco on 01/12/2017.
 */

public class Utils {

    @ColorInt
    public static int darkenColor(@ColorInt int color) {
        float[] hsv = new float[3];
        Color.colorToHSV(color, hsv);
        hsv[2] *= 0.85f;
        return Color.HSVToColor(hsv);
    }

    public static boolean isColorLight(@ColorInt int color) {
        if (color == Color.BLACK) return false;
        else if (color == Color.WHITE || color == Color.TRANSPARENT) return true;
        final double darkness = 1 - (0.299 * Color.red(color) + 0.587 * Color.green(color) + 0.114 * Color.blue(color)) / 255;
        return darkness < 0.4;
    }

    public static int buttonTextColor(@ColorInt int color) {
        if (isColorLight(color))
            return Color.BLACK;
        else return Color.WHITE;
    }

    public static int getThemeAccentColor(Context context) {
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

    public static void setButton(Context context, @ColorInt int color, Button button, boolean colored) {
        if (!colored)
            color = Color.parseColor("#ffffff");
        Drawable icon = ContextCompat.getDrawable(context, R.drawable.bottomdialog_button_bg).mutate();
        Drawable icon2 = ContextCompat.getDrawable(context, R.drawable.bottomdialog_button_bg).mutate();
        Drawable icon3 = ContextCompat.getDrawable(context, R.drawable.bottomdialog_button_bg).mutate();
        icon.setColorFilter(Utils.darkenColor(color), PorterDuff.Mode.SRC_ATOP);
        icon2.setColorFilter(Utils.darkenColor(color), PorterDuff.Mode.SRC_ATOP);
        icon3.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
        StateListDrawable button1bg = new StateListDrawable();
        button1bg.addState(new int[] {android.R.attr.state_pressed}, icon);
        button1bg.addState(new int[] {android.R.attr.state_focused}, icon2);
        button1bg.addState(new int[] { }, icon3);
        button.setBackgroundDrawable(button1bg);
    }
}
