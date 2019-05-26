package com.marcoscg.dialogsheet;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.widget.FrameLayout;

import java.lang.reflect.Field;

@SuppressWarnings("unchecked")
class ExpandedBottomSheetDialog extends BottomSheetDialog {

    private BottomSheetBehavior<FrameLayout> behavior;

    ExpandedBottomSheetDialog(@NonNull Context context) {
        super(context);
    }

    ExpandedBottomSheetDialog(@NonNull Context context, int theme) {
        super(context, theme);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        try {
            Field privateField = BottomSheetDialog.class.getDeclaredField("behavior");
            privateField.setAccessible(true);
            behavior = (BottomSheetBehavior<FrameLayout>) privateField.get(this);
        } catch (NoSuchFieldException e) {
            // do nothing
        } catch (IllegalAccessException e) {
            // do nothing
        }
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (behavior != null) {
            behavior.setSkipCollapsed(true);
            behavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }
}