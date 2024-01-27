package com.marcoscg.dialogsheet

import android.content.Context
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

internal class ExpandedBottomSheetDialog(context: Context, theme: Int) :
    BottomSheetDialog(context, theme) {

    override fun onStart() {
        super.onStart()

        behavior.skipCollapsed = true
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
    }
}