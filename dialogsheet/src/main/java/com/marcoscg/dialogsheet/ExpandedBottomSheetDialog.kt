package com.marcoscg.dialogsheet

import android.content.Context
import android.os.Bundle
import android.widget.FrameLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

internal class ExpandedBottomSheetDialog(context: Context, theme: Int) : BottomSheetDialog(context, theme) {

    private var dialogBehavior: BottomSheetBehavior<FrameLayout>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        try {
            val privateField = BottomSheetDialog::class.java.getDeclaredField("behavior")
            privateField.isAccessible = true
            dialogBehavior = privateField[this] as BottomSheetBehavior<FrameLayout>
        } catch (e: NoSuchFieldException) {
            // do nothing
        } catch (e: IllegalAccessException) {
            // do nothing
        }
    }

    override fun onStart() {
        super.onStart()

        if (dialogBehavior != null) {
            dialogBehavior!!.skipCollapsed = true
            dialogBehavior!!.state = BottomSheetBehavior.STATE_EXPANDED
        }
    }
}