package com.marcoscg.dialogsheet

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.content.res.Configuration
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.text.TextUtils
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.annotation.*
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.content.ContextCompat
import androidx.core.graphics.drawable.DrawableCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.marcoscg.dialogsheet.Utils.adjustAlpha
import com.marcoscg.dialogsheet.Utils.dpToPx
import com.marcoscg.dialogsheet.Utils.getAttrColor
import com.marcoscg.dialogsheet.Utils.getTextColor
import com.marcoscg.dialogsheet.Utils.getTextColorSec
import com.marcoscg.dialogsheet.Utils.getWindowBackground
import com.marcoscg.dialogsheet.Utils.isColorLight
import com.marcoscg.dialogsheet.Utils.orDefault

/**
 * Created by @MarcosCGdev on 01/12/2017.
 */
open class DialogSheet(private val context: Context, private var useNewStyle: Boolean = false) {

    private var bottomSheetDialog: ExpandedBottomSheetDialog? = null
    private var roundedCorners = true
    private var backgroundColor = 0
    private var titleTextColor = 0
    private var messageTextColor = 0
    private var coloredNavigationBar = false
    private var titleTextView: AppCompatTextView? = null
    private var messageTextView: AppCompatTextView? = null
    private var iconImageView: AppCompatImageView? = null
    private var positiveButton: MaterialButton? = null
    private var negativeButton: MaterialButton? = null
    private var neutralButton: MaterialButton? = null
    private var textContainer: View? = null
    private var messageContainer: LinearLayout? = null
    private var iconCardView: MaterialCardView? = null
    var inflatedView: View? = null
        private set

    fun interface OnPositiveClickListener {
        fun onClick(v: View?)
    }

    fun interface OnNegativeClickListener {
        fun onClick(v: View?)
    }

    fun interface OnNeutralClickListener {
        fun onClick(v: View?)
    }

    fun setNewDialogStyle(): DialogSheet {
        this.useNewStyle = true
        init(context)

        return this
    }

    fun setTitle(title: CharSequence?): DialogSheet {
        if (title == null) titleTextView?.visibility = View.GONE else {
            titleTextView?.visibility = View.VISIBLE
            titleTextView?.text = title
        }

        return this
    }

    fun setTitle(@StringRes titleRes: Int): DialogSheet {
        setTitle(context.resources.getString(titleRes))
        return this
    }

    fun setMessage(message: CharSequence?): DialogSheet {
        if (message == null) messageTextView?.visibility = View.GONE else {
            messageTextView?.visibility = View.VISIBLE
            messageTextView?.text = message
        }

        return this
    }

    fun setMessage(@StringRes messageRes: Int): DialogSheet {
        setMessage(context.resources.getString(messageRes))
        return this
    }

    fun setIconDrawable(icon: Drawable?): DialogSheet {
        if (icon == null) hideIcon() else {
            showIcon()
            iconImageView?.setImageDrawable(icon)
        }

        return this
    }

    fun setIconBitmap(icon: Bitmap?): DialogSheet {
        if (icon == null) hideIcon() else {
            showIcon()
            iconImageView?.setImageBitmap(icon)
        }

        return this
    }

    fun setIconResource(@DrawableRes iconRes: Int): DialogSheet {
        showIcon()
        iconImageView?.setImageResource(iconRes)

        return this
    }

    /**
     * @param icon
     * @return
     */
    @Deprecated("use {@link #setIconDrawable(Drawable)} instead.")
    fun setIcon(icon: Drawable?): DialogSheet {
        setIconDrawable(icon)
        return this
    }

    /**
     * @param icon
     * @return
     */
    @Deprecated("use {@link #setIconBitmap(Bitmap)} instead.")
    fun setIcon(icon: Bitmap?): DialogSheet {
        setIconBitmap(icon)
        return this
    }

    /**
     * @param iconRes
     * @return
     */
    @Deprecated("use {@link #setIconResource(int)} instead.")
    fun setIcon(@DrawableRes iconRes: Int): DialogSheet {
        setIconResource(iconRes)
        return this
    }

    fun setPositiveButton(text: CharSequence?, shouldDismiss: Boolean, onPositiveClickListener: OnPositiveClickListener? = null): DialogSheet {
        if (text == null) positiveButton?.visibility = View.GONE else {
            positiveButton?.visibility = View.VISIBLE
            positiveButton?.text = text
            positiveButton?.setOnClickListener { view ->
                if (shouldDismiss) bottomSheetDialog?.dismiss()
                onPositiveClickListener?.onClick(view)
            }
        }

        return this
    }

    fun setPositiveButton(text: CharSequence?, onPositiveClickListener: OnPositiveClickListener? = null): DialogSheet {
        setPositiveButton(text, true, onPositiveClickListener)
        return this
    }

    fun setPositiveButton(@StringRes textRes: Int, shouldDismiss: Boolean, onPositiveClickListener: OnPositiveClickListener? = null): DialogSheet {
        setPositiveButton(context.resources.getString(textRes), shouldDismiss, onPositiveClickListener)
        return this
    }

    fun setPositiveButton(@StringRes textRes: Int, onPositiveClickListener: OnPositiveClickListener? = null): DialogSheet {
        setPositiveButton(context.resources.getString(textRes), true, onPositiveClickListener)
        return this
    }

    fun setNegativeButton(text: CharSequence?, shouldDismiss: Boolean, onNegativeClickListener: OnNegativeClickListener? = null): DialogSheet {
        if (text == null) negativeButton?.visibility = View.GONE else {
            negativeButton?.visibility = View.VISIBLE
            negativeButton?.text = text
            negativeButton?.setOnClickListener { view ->
                if (shouldDismiss) bottomSheetDialog?.dismiss()
                onNegativeClickListener?.onClick(view)
            }
        }

        return this
    }

    fun setNegativeButton(text: CharSequence?, onNegativeClickListener: OnNegativeClickListener? = null): DialogSheet {
        setNegativeButton(text, true, onNegativeClickListener)
        return this
    }

    fun setNegativeButton(@StringRes textRes: Int, shouldDismiss: Boolean, onNegativeClickListener: OnNegativeClickListener? = null): DialogSheet {
        setNegativeButton(context.resources.getString(textRes), shouldDismiss, onNegativeClickListener)
        return this
    }

    fun setNegativeButton(@StringRes textRes: Int, onNegativeClickListener: OnNegativeClickListener? = null): DialogSheet {
        setNegativeButton(context.resources.getString(textRes), true, onNegativeClickListener)
        return this
    }

    fun setNeutralButton(text: CharSequence?, shouldDismiss: Boolean, onNegativeClickListener: OnNeutralClickListener? = null): DialogSheet {
        if (text == null) neutralButton?.visibility = View.GONE else {
            neutralButton?.visibility = View.VISIBLE
            neutralButton?.text = text
            neutralButton?.setOnClickListener { view ->
                if (shouldDismiss) bottomSheetDialog?.dismiss()
                onNegativeClickListener?.onClick(view)
            }
        }

        return this
    }

    fun setNeutralButton(text: CharSequence?, onNegativeClickListener: OnNeutralClickListener? = null): DialogSheet {
        setNeutralButton(text, true, onNegativeClickListener)
        return this
    }

    fun setNeutralButton(@StringRes textRes: Int, shouldDismiss: Boolean, onNegativeClickListener: OnNeutralClickListener? = null): DialogSheet {
        setNeutralButton(context.resources.getString(textRes), shouldDismiss, onNegativeClickListener)
        return this
    }

    fun setNeutralButton(@StringRes textRes: Int, onNegativeClickListener: OnNeutralClickListener? = null): DialogSheet {
        setNeutralButton(context.resources.getString(textRes), true, onNegativeClickListener)
        return this
    }

    fun setButtonsTextAllCaps(textAllCaps: Boolean): DialogSheet {
        positiveButton?.isAllCaps = textAllCaps
        negativeButton?.isAllCaps = textAllCaps
        neutralButton?.isAllCaps = textAllCaps
        return this
    }

    @SuppressLint("RestrictedApi")
    fun setPositiveButtonColor(@ColorInt buttonColor: Int): DialogSheet {
        positiveButton?.supportBackgroundTintList = ColorStateList.valueOf(buttonColor)
        return this
    }

    fun setPositiveButtonColorRes(@ColorRes buttonColorRes: Int): DialogSheet {
        setPositiveButtonColor(ContextCompat.getColor(context, buttonColorRes))
        return this
    }

    fun setNegativeButtonColor(@ColorInt buttonColor: Int): DialogSheet {
        setSecondaryButtonColor(negativeButton, buttonColor)
        return this
    }

    fun setNegativeButtonColorRes(@ColorRes buttonColorRes: Int): DialogSheet {
        setNegativeButtonColor(ContextCompat.getColor(context, buttonColorRes))
        return this
    }

    fun setNeutralButtonColor(@ColorInt buttonColor: Int): DialogSheet {
        setSecondaryButtonColor(neutralButton, buttonColor)
        return this
    }

    fun setNeutralButtonColorRes(@ColorRes buttonColorRes: Int): DialogSheet {
        setNeutralButtonColor(ContextCompat.getColor(context, buttonColorRes))
        return this
    }

    fun setButtonsColor(@ColorInt buttonsColor: Int): DialogSheet {
        setPositiveButtonColor(buttonsColor)
        return this
    }

    fun setButtonsColorRes(@ColorRes buttonsColorRes: Int): DialogSheet {
        setButtonsColor(ContextCompat.getColor(context, buttonsColorRes))
        return this
    }

    fun setBackgroundColor(@ColorInt backgroundColor: Int): DialogSheet {
        this.backgroundColor = backgroundColor
        return this
    }

    fun setBackgroundColorRes(@ColorRes backgroundColorRes: Int): DialogSheet {
        setBackgroundColor(ContextCompat.getColor(context, backgroundColorRes))
        return this
    }

    fun setTitleTextColor(@ColorInt titleTextColor: Int): DialogSheet {
        this.titleTextColor = titleTextColor
        return this
    }

    fun setTitleTextColorRes(@ColorRes titleTextColorRes: Int): DialogSheet {
        setTitleTextColor(ContextCompat.getColor(context, titleTextColorRes))
        return this
    }

    fun setMessageTextColor(@ColorInt messageTextColor: Int): DialogSheet {
        this.messageTextColor = messageTextColor
        return this
    }

    fun setMessageTextColorRes(@ColorRes messageTextColorRes: Int): DialogSheet {
        setMessageTextColor(ContextCompat.getColor(context, messageTextColorRes))
        return this
    }

    fun setSingleLineTitle(singleLine: Boolean): DialogSheet {
        titleTextView?.isSingleLine = singleLine
        return this
    }

    fun setColoredNavigationBar(coloredNavigationBar: Boolean): DialogSheet {
        this.coloredNavigationBar = coloredNavigationBar
        return this
    }

    fun setTitleTypeface(typeface: Typeface?): DialogSheet {
        titleTextView?.typeface = typeface
        return this
    }

    fun setMessageTypeface(typeface: Typeface?): DialogSheet {
        messageTextView?.typeface = typeface
        return this
    }

    fun setButtonsTypeface(typeface: Typeface?): DialogSheet {
        positiveButton?.typeface = typeface
        negativeButton?.typeface = typeface
        neutralButton?.typeface = typeface
        return this
    }

    fun setTitleTextSize(sizeSp: Int): DialogSheet {
        titleTextView?.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp.toFloat())
        return this
    }

    fun setMessageTextSize(sizeSp: Int): DialogSheet {
        messageTextView?.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp.toFloat())
        return this
    }

    fun setButtonsTextSize(sizeSp: Int): DialogSheet {
        positiveButton?.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp.toFloat())
        negativeButton?.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp.toFloat())
        neutralButton?.setTextSize(TypedValue.COMPLEX_UNIT_SP, sizeSp.toFloat())
        return this
    }

    fun setCancelable(cancelable: Boolean): DialogSheet {
        bottomSheetDialog?.setCancelable(cancelable)
        return this
    }

    fun setRoundedCorners(roundedCorners: Boolean): DialogSheet {
        if (roundedCorners) {
            val bgView = bottomSheetDialog?.findViewById<View>(R.id.mainDialogContainer)
            bgView?.setBackgroundResource(if (iconCardView?.visibility != View.GONE) {
                if (useNewStyle) R.drawable.dialog_sheet_main_background_round_margin else R.drawable.dialog_sheet_main_background_round
            } else {
                R.drawable.dialog_sheet_main_background_round
            })
        } else {
            val bgView = bottomSheetDialog?.findViewById<View>(R.id.mainDialogContainer)
            bgView?.setBackgroundResource(if (iconCardView?.visibility != View.GONE) {
                if (useNewStyle) R.drawable.dialog_sheet_main_background_margin else R.drawable.dialog_sheet_main_background
            } else {
                R.drawable.dialog_sheet_main_background
            })
        }

        this.roundedCorners = roundedCorners

        return this
    }

    fun setView(view: View?): DialogSheet {
        removePreviousMessageViews()
        messageContainer?.addView(view)
        inflatedView = view
        return this
    }

    fun setView(@LayoutRes layoutRes: Int): DialogSheet {
        removePreviousMessageViews()
        inflatedView = View.inflate(context, layoutRes, null)
        setView(inflatedView)
        return this
    }

    fun setOnDismissListener(onDismissListener: DialogInterface.OnDismissListener?): DialogSheet {
        bottomSheetDialog?.setOnDismissListener(onDismissListener)
        return this
    }

    fun show() {
        if (backgroundColor == 0) backgroundColor = getWindowBackground(context)
        if (backgroundColor != 0) {
            var bgDrawable: Drawable? = null
            val bgView = bottomSheetDialog?.findViewById<View>(R.id.mainDialogContainer)
            if (bgView != null) bgDrawable = bgView.background

            if (bgDrawable != null) {
                val wrappedDrawable: Drawable = DrawableCompat.wrap(bgDrawable)
                DrawableCompat.setTint(wrappedDrawable, backgroundColor)
            }

            iconCardView?.setCardBackgroundColor(if (useNewStyle) Color.WHITE else backgroundColor)
        }

        if (titleTextColor == 0) titleTextColor = getTextColor(backgroundColor)
        if (messageTextColor == 0) messageTextColor = getTextColorSec(backgroundColor)

        titleTextView?.setTextColor(titleTextColor)
        messageTextView?.setTextColor(messageTextColor)
        setColoredNavBar(coloredNavigationBar)

        if (positiveButton?.visibility != View.VISIBLE) (negativeButton?.layoutParams as RelativeLayout.LayoutParams)
                .addRule(RelativeLayout.ALIGN_PARENT_RIGHT)

        if (!areButtonsVisible()) {
            var bottomPadding = 0
            var topPadding = 0
            if (messageTextView?.text != null && !TextUtils.isEmpty(messageTextView?.text)) {
                bottomPadding = dpToPx(24)
                if (titleTextView?.text == null || TextUtils.isEmpty(titleTextView?.text)) topPadding = dpToPx(24)
            }

            textContainer?.setPadding(0, topPadding, 0, bottomPadding)
        } else {
            if ((titleTextView?.text == null || TextUtils.isEmpty(titleTextView?.text))
                    && messageTextView?.text != null && !TextUtils.isEmpty(messageTextView?.text)) textContainer?.setPadding(0, dpToPx(24), 0, 0)
        }

        setRoundedCorners(roundedCorners)

        bottomSheetDialog?.show()

        // Landscape fixed width
        val configuration = context.resources.configuration
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE &&
                configuration.screenWidthDp > 400) {
            if (bottomSheetDialog?.window != null) bottomSheetDialog?.window?.setLayout(dpToPx(400), -1)
        }
    }

    fun dismiss() {
        bottomSheetDialog?.dismiss()
    }

    private fun init(context: Context) {
        val accentColor = getAttrColor(context, R.attr.dialogSheetAccent)
        var posButtonTextColor = Color.WHITE

        if (accentColor != -1) {
            bottomSheetDialog = ExpandedBottomSheetDialog(context, R.style.DialogSheetTheme_Colored)
            posButtonTextColor = getTextColor(accentColor)
        } else bottomSheetDialog = ExpandedBottomSheetDialog(context, R.style.DialogSheetTheme)

        bottomSheetDialog?.setContentView(if (useNewStyle) R.layout.layout_dialog_sheet_v2 else R.layout.layout_dialog_sheet)

        if (bottomSheetDialog?.window != null)
            bottomSheetDialog?.window?.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE)

        titleTextView = bottomSheetDialog?.findViewById(R.id.dialogTitle)
        messageTextView = bottomSheetDialog?.findViewById(R.id.dialogMessage)
        iconImageView = bottomSheetDialog?.findViewById(R.id.dialogIcon)
        positiveButton = bottomSheetDialog?.findViewById(R.id.buttonPositive)
        negativeButton = bottomSheetDialog?.findViewById(R.id.buttonNegative)
        neutralButton = bottomSheetDialog?.findViewById(R.id.buttonNeutral)
        textContainer = bottomSheetDialog?.findViewById(R.id.textContainer)
        messageContainer = bottomSheetDialog?.findViewById(R.id.messageContainer)
        iconCardView = bottomSheetDialog?.findViewById(R.id.iconCardView)

        positiveButton?.setTextColor(posButtonTextColor)
    }

    private fun showIcon() {
        iconCardView?.visibility = View.VISIBLE
    }

    private fun hideIcon() {
        iconCardView?.visibility = View.GONE
    }

    private fun setColoredNavBar(coloredNavigationBar: Boolean) {
        if (coloredNavigationBar && bottomSheetDialog?.window != null && Build.VERSION.SDK_INT >= 21) {
            if (isColorLight(backgroundColor)) {
                if (Build.VERSION.SDK_INT >= 26) {
                    bottomSheetDialog?.window?.navigationBarColor = backgroundColor
                    var flags = bottomSheetDialog?.window?.decorView?.systemUiVisibility

                    if (flags != null) {
                        flags = flags or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
                        bottomSheetDialog?.window?.decorView?.systemUiVisibility = flags
                    }
                }
            } else {
                bottomSheetDialog?.window?.navigationBarColor = backgroundColor
                if (Build.VERSION.SDK_INT >= 26) {
                    var flags = bottomSheetDialog?.window?.decorView?.systemUiVisibility

                    if (flags != null) {
                        flags = flags and View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR.inv()
                        bottomSheetDialog?.window?.decorView?.systemUiVisibility = flags
                    }
                }
            }
        }
    }

    private fun setSecondaryButtonColor(button: MaterialButton?, @ColorInt buttonColor: Int) {
        val rippleColor = adjustAlpha(buttonColor, 0.2f)
        val secondaryButtonColor = ColorStateList(arrayOf(intArrayOf(android.R.attr.state_pressed),
                intArrayOf(android.R.attr.state_focused), intArrayOf(android.R.attr.state_activated), intArrayOf()), intArrayOf(
                rippleColor,
                rippleColor,
                rippleColor,
                Color.TRANSPARENT
        ))

        button?.setTextColor(buttonColor)
        button?.rippleColor = secondaryButtonColor
    }

    private fun areButtonsVisible(): Boolean {
        return positiveButton?.visibility == View.VISIBLE || negativeButton?.visibility == View.VISIBLE || neutralButton?.visibility == View.VISIBLE
    }

    private fun removePreviousMessageViews() {
        for (i in 1 until messageContainer?.childCount.orDefault()) {
            messageContainer?.removeViewAt(i)
        }
    }

    init {
        init(context)
    }
}