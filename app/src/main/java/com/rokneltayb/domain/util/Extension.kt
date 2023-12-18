package com.rokneltayb.domain.util

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rokneltayb.R
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.domain.util.ui.MarginItemDecoration

fun RecyclerView.addBasicItemDecoration(dimensionId: Int = R.dimen.item_decoration_small_margin) {
    while (this.itemDecorationCount > 0) {
        this.removeItemDecorationAt(0)
    }

    this.addItemDecoration(
        MarginItemDecoration(
            spaceSize = resources.getDimensionPixelSize(dimensionId),
            spanCount = (this.layoutManager as GridLayoutManager).spanCount
        )
    )
}


fun Context.openKeyBoard(view: View) {
    view.requestFocus()
    val imm = ContextCompat.getSystemService(this, InputMethodManager::class.java)
    imm?.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
}

fun DialogFragment.setupDialogWindowForDialog() {
    dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    dialog?.window?.setLayout(
        requireContext().resources.displayMetrics.widthPixels - 72,
        requireContext().resources.displayMetrics.heightPixels - 400
    )
    val window = dialog?.window
    val windowParams = window?.attributes
    windowParams?.dimAmount = 0.80f
    windowParams?.flags = windowParams?.flags?.or(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    window?.attributes = windowParams
}
