package com.marcoscg.dialogsheet;

import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.StringRes;
import android.support.design.button.MaterialButton;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by @MarcosCGdev on 01/12/2017.
 */

public class DialogSheet {

    private Context context;
    private BottomSheetDialog bottomSheetDialog;
    private int backgroundColor = -1;
    private boolean showButtons = false;

    private TextView titleTextView, messageTextView;
    private ImageView iconImageView;
    private MaterialButton positiveButton, negativeButton;
    private RelativeLayout textContainer;
    private LinearLayout messageContainer;

    private View inflatedView;

    public interface OnPositiveClickListener {
        public void onClick(View v);
    }

    public interface OnNegativeClickListener {
        public void onClick(View v);
    }

    public DialogSheet(Context context) {
        this.context = context;
        init(context);
    }

    public DialogSheet setTitle(CharSequence title) {
        titleTextView.setVisibility(View.VISIBLE);
        titleTextView.setText(title);
        return this;
    }

    public DialogSheet setTitle(@StringRes int titleRes) {
        setTitle(context.getResources().getString(titleRes));
        return this;
    }

    public DialogSheet setMessage(CharSequence message) {
        messageTextView.setVisibility(View.VISIBLE);
        messageTextView.setText(message);
        return this;
    }

    public DialogSheet setMessage(@StringRes int messageRes) {
        setMessage(context.getResources().getString(messageRes));
        return this;
    }

    public DialogSheet setIcon(Drawable icon) {
        showIcon();
        iconImageView.setImageDrawable(icon);
        return this;
    }

    public DialogSheet setIcon(Bitmap icon) {
        showIcon();
        iconImageView.setImageBitmap(icon);
        return this;
    }

    public DialogSheet setIcon(@DrawableRes int iconRes) {
        showIcon();
        iconImageView.setImageResource(iconRes);
        return this;
    }

    public DialogSheet setPositiveButton(CharSequence text, final OnPositiveClickListener onPositiveClickListener) {
        positiveButton.setVisibility(View.VISIBLE);
        positiveButton.setText(text);
        positiveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                if (onPositiveClickListener!=null)
                    onPositiveClickListener.onClick(view);
            }
        });
        showButtons = true;
        return this;
    }

    public DialogSheet setNegativeButton(CharSequence text, final OnNegativeClickListener onNegativeClickListener) {
        negativeButton.setVisibility(View.VISIBLE);
        negativeButton.setText(text);
        negativeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bottomSheetDialog.dismiss();
                if (onNegativeClickListener!=null)
                    onNegativeClickListener.onClick(view);
            }
        });
        showButtons = true;
        return this;
    }

    public DialogSheet setPositiveButton(@StringRes int textRes, OnPositiveClickListener onPositiveClickListener) {
        setPositiveButton(context.getResources().getString(textRes), onPositiveClickListener);
        return this;
    }

    public DialogSheet setNegativeButton(@StringRes int textRes, OnNegativeClickListener onNegativeClickListener) {
        setNegativeButton(context.getResources().getString(textRes), onNegativeClickListener);
        return this;
    }

    @Deprecated
    public DialogSheet setButtonsColor(@ColorInt int buttonsColor) {
        return this;
    }

    @Deprecated
    public DialogSheet setButtonsColorRes(@ColorRes int buttonsColorRes) {
        return this;
    }

    public DialogSheet setBackgroundColor(@ColorInt int backgroundColor) {
        this.backgroundColor = backgroundColor;
        return this;
    }

    public DialogSheet setBackgroundColorRes(@ColorRes int backgroundColorRes) {
        this.backgroundColor = ContextCompat.getColor(context, backgroundColorRes);
        return this;
    }

    public DialogSheet setCancelable(boolean cancelable) {
        bottomSheetDialog.setCancelable(cancelable);
        return this;
    }

    public DialogSheet setView(View view) {
        messageContainer.addView(view);
        if (inflatedView==null)
            inflatedView = view;
        return this;
    }

    public DialogSheet setView(@LayoutRes int layoutRes) {
        inflatedView = View.inflate(context, layoutRes, null);
        setView(inflatedView);
        return this;
    }

    public View getInflatedView() {
        return inflatedView;
    }

    public void show() {
        if (backgroundColor==-1)
            backgroundColor = Utils.getThemeBgColor(context);
        if (backgroundColor!=-1) {
            bottomSheetDialog.findViewById(R.id.mainDialogContainer).setBackgroundColor(backgroundColor);
            titleTextView.setTextColor(Utils.getTextColor(backgroundColor));
            messageTextView.setTextColor(Utils.getTextColorSec(backgroundColor));
        }

        if (!showButtons)
            textContainer.setPadding(0,0,0,0);

        bottomSheetDialog.show();

        Configuration configuration = context.getResources().getConfiguration();
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE &&
                configuration.screenWidthDp > 400) {
            // you can go more fancy and vary the bottom sheet width depending on the screen width
            // see recommendations on https://material.io/guidelines/components/bottom-sheets.html#bottom-sheets-specs
            bottomSheetDialog.getWindow().setLayout(Utils.dpToPx(400), -1);
        }
    }

    public void dismiss() {
        bottomSheetDialog.dismiss();
    }

    private void init(Context context) {
        int accentColor = Utils.getAttrColor(context, "dialogSheetAccent");
        int posButtonTextColor = Color.WHITE;

        if (accentColor != -1) {
            bottomSheetDialog = new BottomSheetDialog(context, R.style.DialogSheetTheme);
            posButtonTextColor = Utils.getTextColor(accentColor);
        } else bottomSheetDialog = new BottomSheetDialog(context);

        bottomSheetDialog.setContentView(R.layout.layout_bottomdialog);

        bottomSheetDialog.setOnShowListener(new DialogInterface.OnShowListener() {
            @Override
            public void onShow(DialogInterface dialog) {
                BottomSheetDialog d = (BottomSheetDialog) dialog;
                FrameLayout bottomSheet = (FrameLayout) d.findViewById(android.support.design.R.id.design_bottom_sheet);
                BottomSheetBehavior.from(bottomSheet)
                        .setState(BottomSheetBehavior.STATE_EXPANDED);
            }
        });

        titleTextView = (TextView) bottomSheetDialog.findViewById(R.id.dialogTitle);
        messageTextView = (TextView) bottomSheetDialog.findViewById(R.id.dialogMessage);
        iconImageView = (ImageView) bottomSheetDialog.findViewById(R.id.dialogIcon);
        positiveButton = (MaterialButton) bottomSheetDialog.findViewById(R.id.buttonPositive);
        negativeButton = (MaterialButton) bottomSheetDialog.findViewById(R.id.buttonNegative);
        textContainer = (RelativeLayout) bottomSheetDialog.findViewById(R.id.textContainer);
        messageContainer = (LinearLayout) bottomSheetDialog.findViewById(R.id.messageContainer);

        positiveButton.setTextColor(posButtonTextColor);
    }

    private void showIcon() {
        iconImageView.setVisibility(View.VISIBLE);
    }

}
