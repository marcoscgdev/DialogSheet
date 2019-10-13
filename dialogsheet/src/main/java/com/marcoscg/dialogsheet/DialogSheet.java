package com.marcoscg.dialogsheet;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.button.MaterialButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import static com.marcoscg.dialogsheet.Utils.adjustAlpha;
import static com.marcoscg.dialogsheet.Utils.dpToPx;
import static com.marcoscg.dialogsheet.Utils.isColorLight;

/**
 * Created by @MarcosCGdev on 01/12/2017.
 */

public class DialogSheet {

    private Context context;
    private ExpandedBottomSheetDialog bottomSheetDialog;
    private int backgroundColor = 0, titleTextColor = 0, messageTextColor = 0;
    private boolean coloredNavigationBar = false;

    private AppCompatTextView titleTextView, messageTextView;
    private AppCompatImageView iconImageView;
    private MaterialButton positiveButton, negativeButton, neutralButton;
    private RelativeLayout textContainer;
    private LinearLayout messageContainer;

    private View inflatedView;

    public interface OnPositiveClickListener {
        public void onClick(View v);
    }

    public interface OnNegativeClickListener {
        public void onClick(View v);
    }

    public interface OnNeutralClickListener {
        public void onClick(View v);
    }

    public DialogSheet(Context context) {
        this.context = context;
        init(context);
    }

    public DialogSheet setTitle(CharSequence title) {
        if (title == null)
            titleTextView.setVisibility(View.GONE);
        else {
            titleTextView.setVisibility(View.VISIBLE);
            titleTextView.setText(title);
        }

        return this;
    }

    public DialogSheet setTitle(@StringRes int titleRes) {
        setTitle(context.getResources().getString(titleRes));
        return this;
    }

    public DialogSheet setMessage(CharSequence message) {
        if (message == null)
            messageTextView.setVisibility(View.GONE);
        else {
            messageTextView.setVisibility(View.VISIBLE);
            messageTextView.setText(message);
        }

        return this;
    }

    public DialogSheet setMessage(@StringRes int messageRes) {
        setMessage(context.getResources().getString(messageRes));
        return this;
    }

    public DialogSheet setIconDrawable(Drawable icon) {
        if (icon == null)
            hideIcon();
        else {
            showIcon();
            iconImageView.setImageDrawable(icon);
        }

        return this;
    }

    public DialogSheet setIconBitmap(Bitmap icon) {
        if (icon == null)
            hideIcon();
        else {
            showIcon();
            iconImageView.setImageBitmap(icon);
        }

        return this;
    }

    public DialogSheet setIconResource(@DrawableRes int icon) {
        showIcon();
        iconImageView.setImageResource(icon);

        return this;
    }

    /**
     * @param icon
     * @return
     * @deprecated use {@link #setIconDrawable(Drawable)} instead.
     */
    @Deprecated
    public DialogSheet setIcon(Drawable icon) {
        setIconDrawable(icon);

        return this;
    }

    /**
     * @param icon
     * @return
     * @deprecated use {@link #setIconBitmap(Bitmap)} instead.
     */
    @Deprecated
    public DialogSheet setIcon(Bitmap icon) {
        setIconBitmap(icon);

        return this;
    }

    /**
     * @param iconRes
     * @return
     * @deprecated use {@link #setIconResource(int)} instead.
     */
    @Deprecated
    public DialogSheet setIcon(@DrawableRes int iconRes) {
        showIcon();
        iconImageView.setImageResource(iconRes);
        return this;
    }

    public DialogSheet setPositiveButton(CharSequence text, final boolean shouldDismiss, final OnPositiveClickListener onPositiveClickListener) {
        if (text == null)
            positiveButton.setVisibility(View.GONE);
        else {
            positiveButton.setVisibility(View.VISIBLE);
            positiveButton.setText(text);
            positiveButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shouldDismiss)
                        bottomSheetDialog.dismiss();

                    if (onPositiveClickListener != null)
                        onPositiveClickListener.onClick(view);
                }
            });
        }

        return this;
    }

    public DialogSheet setPositiveButton(CharSequence text, final OnPositiveClickListener onPositiveClickListener) {
        setPositiveButton(text, true, onPositiveClickListener);
        return this;
    }

    public DialogSheet setPositiveButton(@StringRes int textRes, boolean shouldDismiss, OnPositiveClickListener onPositiveClickListener) {
        setPositiveButton(context.getResources().getString(textRes), shouldDismiss, onPositiveClickListener);
        return this;
    }

    public DialogSheet setPositiveButton(@StringRes int textRes, OnPositiveClickListener onPositiveClickListener) {
        setPositiveButton(context.getResources().getString(textRes), true, onPositiveClickListener);
        return this;
    }

    public DialogSheet setNegativeButton(CharSequence text, final boolean shouldDismiss, final OnNegativeClickListener onNegativeClickListener) {
        if (text == null)
            negativeButton.setVisibility(View.GONE);
        else {
            negativeButton.setVisibility(View.VISIBLE);
            negativeButton.setText(text);
            negativeButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shouldDismiss)
                        bottomSheetDialog.dismiss();

                    if (onNegativeClickListener != null)
                        onNegativeClickListener.onClick(view);
                }
            });
        }

        return this;
    }

    public DialogSheet setNegativeButton(CharSequence text, final OnNegativeClickListener onNegativeClickListener) {
        setNegativeButton(text, true, onNegativeClickListener);
        return this;
    }

    public DialogSheet setNegativeButton(@StringRes int textRes, boolean shouldDismiss, OnNegativeClickListener onNegativeClickListener) {
        setNegativeButton(context.getResources().getString(textRes), shouldDismiss, onNegativeClickListener);
        return this;
    }

    public DialogSheet setNegativeButton(@StringRes int textRes, OnNegativeClickListener onNegativeClickListener) {
        setNegativeButton(context.getResources().getString(textRes), true, onNegativeClickListener);
        return this;
    }

    public DialogSheet setNeutralButton(CharSequence text, final boolean shouldDismiss, final OnNeutralClickListener onNegativeClickListener) {
        if (text == null)
            neutralButton.setVisibility(View.GONE);
        else {
            neutralButton.setVisibility(View.VISIBLE);
            neutralButton.setText(text);
            neutralButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (shouldDismiss)
                        bottomSheetDialog.dismiss();

                    if (onNegativeClickListener != null)
                        onNegativeClickListener.onClick(view);
                }
            });
        }

        return this;
    }

    public DialogSheet setNeutralButton(CharSequence text, final OnNeutralClickListener onNegativeClickListener) {
        setNeutralButton(text, true, onNegativeClickListener);
        return this;
    }

    public DialogSheet setNeutralButton(@StringRes int textRes, boolean shouldDismiss, OnNeutralClickListener onNegativeClickListener) {
        setNeutralButton(context.getResources().getString(textRes), shouldDismiss, onNegativeClickListener);
        return this;
    }

    public DialogSheet setNeutralButton(@StringRes int textRes, OnNeutralClickListener onNegativeClickListener) {
        setNeutralButton(context.getResources().getString(textRes), true, onNegativeClickListener);
        return this;
    }

    public DialogSheet setButtonsTextAllCaps(boolean textAllCaps) {
        positiveButton.setAllCaps(textAllCaps);
        negativeButton.setAllCaps(textAllCaps);
        neutralButton.setAllCaps(textAllCaps);

        return this;
    }

    @SuppressLint("RestrictedApi")
    public DialogSheet setButtonsColor(@ColorInt int buttonsColor) {
        int rippleColor = adjustAlpha(buttonsColor, 0.2f);
        ColorStateList secondaryButtonColor = new ColorStateList(
                new int[][]{
                                new int[]{android.R.attr.state_pressed},
                                new int[]{android.R.attr.state_focused},
                                new int[]{android.R.attr.state_activated},
                                new int[]{}
                        },
                new int[]{
                                rippleColor,
                                rippleColor,
                                rippleColor,
                                Color.TRANSPARENT
                        });

        positiveButton.setSupportBackgroundTintList(ColorStateList.valueOf(buttonsColor));

        negativeButton.setTextColor(buttonsColor);
        negativeButton.setRippleColor(secondaryButtonColor);
        neutralButton.setTextColor(buttonsColor);
        neutralButton.setRippleColor(secondaryButtonColor);
        return this;
    }

    public DialogSheet setButtonsColorRes(@ColorRes int buttonsColorRes) {
        setButtonsColor(ContextCompat.getColor(context, buttonsColorRes));
        return this;
    }

    public DialogSheet setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public DialogSheet setBackgroundColorRes(@ColorRes int backgroundColorRes) {
        setBackgroundColor(ContextCompat.getColor(context, backgroundColorRes));
        return this;
    }

    public DialogSheet setTitleTextColor(@ColorInt int titleTextColor) {
        this.titleTextColor = titleTextColor;
        return this;
    }

    public DialogSheet setTitleTextColorRes(@ColorRes int titleTextColorRes) {
        setTitleTextColor(ContextCompat.getColor(context, titleTextColorRes));
        return this;
    }

    public DialogSheet setMessageTextColor(@ColorInt int messageTextColor) {
        this.messageTextColor = messageTextColor;
        return this;
    }

    public DialogSheet setMessageTextColorRes(@ColorRes int messageTextColorRes) {
        setMessageTextColor(ContextCompat.getColor(context, messageTextColorRes));
        return this;
    }

    public DialogSheet setSingleLineTitle(boolean singleLine) {
        titleTextView.setSingleLine(singleLine);
        return this;
    }

    public DialogSheet setColoredNavigationBar(boolean coloredNavigationBar) {
        this.coloredNavigationBar = coloredNavigationBar;
        return this;
    }

    public DialogSheet setTitleTypeface(Typeface typeface) {
        titleTextView.setTypeface(typeface);

        return this;
    }

    public DialogSheet setMessageTypeface(Typeface typeface) {
        messageTextView.setTypeface(typeface);

        return this;
    }

    public DialogSheet setButtonsTypeface(Typeface typeface) {
        negativeButton.setTypeface(typeface);
        negativeButton.setTypeface(typeface);

        return this;
    }

    public DialogSheet setCancelable(boolean cancelable) {
        bottomSheetDialog.setCancelable(cancelable);
        return this;
    }

    public DialogSheet setRoundedCorners(boolean roundedCorners) {
        if (!roundedCorners) {
            View bgView = bottomSheetDialog.findViewById(R.id.mainDialogContainer);
            if (bgView != null)
                bgView.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.dialog_sheet_main_background));
        }

        return this;
    }

    public DialogSheet setView(View view) {
        removePreviousMessageViews();
        messageContainer.addView(view);
        inflatedView = view;
        return this;
    }

    public DialogSheet setView(@LayoutRes int layoutRes) {
        removePreviousMessageViews();
        inflatedView = View.inflate(context, layoutRes, null);
        setView(inflatedView);
        return this;
    }

    public DialogSheet setOnDismissListener(DialogInterface.OnDismissListener onDismissListener) {
        bottomSheetDialog.setOnDismissListener(onDismissListener);
        return this;
    }

    public View getInflatedView() {
        return inflatedView;
    }

    public void show() {
        if (backgroundColor == 0)
            backgroundColor = Utils.getAttrColor(context, android.R.attr.windowBackground);
        if (backgroundColor != 0) {
            Drawable bgDrawable = null;
            View bgView = bottomSheetDialog.findViewById(R.id.mainDialogContainer);
            if (bgView != null)
                bgDrawable = bgView.getBackground();

            if (bgDrawable != null)
                bgDrawable.setColorFilter(backgroundColor, PorterDuff.Mode.SRC_IN);
        }

        if (titleTextColor == 0)
            titleTextColor = Utils.getTextColor(backgroundColor);
        if (messageTextColor == 0)
            messageTextColor = Utils.getTextColorSec(backgroundColor);

        titleTextView.setTextColor(titleTextColor);
        messageTextView.setTextColor(messageTextColor);

        setColoredNavBar(coloredNavigationBar);

        if (positiveButton.getVisibility() != View.VISIBLE)
            ((RelativeLayout.LayoutParams) negativeButton.getLayoutParams()).addRule(RelativeLayout.ALIGN_PARENT_RIGHT);

        if (!areButtonsVisible()) {
            int bottomPadding = 0;
            int topPadding = 0;

            if (messageTextView.getText() != null && !TextUtils.isEmpty(messageTextView.getText())) {
                bottomPadding = dpToPx(24);

                if (titleTextView.getText() == null || TextUtils.isEmpty(titleTextView.getText()))
                    topPadding = dpToPx(24);
            }

            textContainer.setPadding(0, topPadding, 0, bottomPadding);
        } else {
            if ((titleTextView.getText() == null || TextUtils.isEmpty(titleTextView.getText()))
                    && messageTextView.getText() != null && !TextUtils.isEmpty(messageTextView.getText()))
                textContainer.setPadding(0, dpToPx(24), 0, 0);
        }

        bottomSheetDialog.show();

        // Landscape fixed width
        Configuration configuration = context.getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE &&
                configuration.screenWidthDp > 400) {
            if (bottomSheetDialog.getWindow() != null)
                bottomSheetDialog.getWindow().setLayout(dpToPx(400), -1);
        }
    }

    public void dismiss() {
        bottomSheetDialog.dismiss();
    }

    private void init(Context context) {
        int accentColor = Utils.getAttrColor(context, R.attr.dialogSheetAccent);
        int posButtonTextColor = Color.WHITE;

        if (accentColor != -1) {
            bottomSheetDialog = new ExpandedBottomSheetDialog(context, R.style.DialogSheetTheme);
            posButtonTextColor = Utils.getTextColor(accentColor);
        } else bottomSheetDialog = new ExpandedBottomSheetDialog(context);

        bottomSheetDialog.setContentView(R.layout.layout_bottomdialog);

        if (bottomSheetDialog.getWindow() != null)
            bottomSheetDialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        titleTextView = bottomSheetDialog.findViewById(R.id.dialogTitle);
        messageTextView = bottomSheetDialog.findViewById(R.id.dialogMessage);
        iconImageView = bottomSheetDialog.findViewById(R.id.dialogIcon);
        positiveButton = bottomSheetDialog.findViewById(R.id.buttonPositive);
        negativeButton = bottomSheetDialog.findViewById(R.id.buttonNegative);
        neutralButton = bottomSheetDialog.findViewById(R.id.buttonNeutral);
        textContainer = bottomSheetDialog.findViewById(R.id.textContainer);
        messageContainer = bottomSheetDialog.findViewById(R.id.messageContainer);

        positiveButton.setTextColor(posButtonTextColor);
    }

    private void showIcon() {
        iconImageView.setVisibility(View.VISIBLE);
    }

    private void hideIcon() {
        iconImageView.setVisibility(View.GONE);
    }

    private void setColoredNavBar(boolean coloredNavigationBar) {
        if (coloredNavigationBar && bottomSheetDialog.getWindow() != null && Build.VERSION.SDK_INT >= 21) {
            if (isColorLight(backgroundColor)) {
                if (Build.VERSION.SDK_INT >= 26) {
                    bottomSheetDialog.getWindow().setNavigationBarColor(backgroundColor);
                    int flags = bottomSheetDialog.getWindow().getDecorView().getSystemUiVisibility();
                    flags |= View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                    bottomSheetDialog.getWindow().getDecorView().setSystemUiVisibility(flags);
                }
            } else {
                bottomSheetDialog.getWindow().setNavigationBarColor(backgroundColor);

                if (Build.VERSION.SDK_INT >= 26) {
                    int flags = bottomSheetDialog.getWindow().getDecorView().getSystemUiVisibility();
                    flags &= ~View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR;
                    bottomSheetDialog.getWindow().getDecorView().setSystemUiVisibility(flags);
                }
            }
        }
    }

    private boolean areButtonsVisible() {
        return positiveButton.getVisibility() == View.VISIBLE || negativeButton.getVisibility() == View.VISIBLE
                || neutralButton.getVisibility() == View.VISIBLE;
    }

    private void removePreviousMessageViews() {
        for (int i = 1; i < messageContainer.getChildCount(); i++) {
            messageContainer.removeViewAt(i);
        }
    }

}
