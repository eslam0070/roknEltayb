package com.rokneltayb.domain.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.text.InputFilter
import android.text.Spanned
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl

fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
    return ContextCompat.getDrawable(context, vectorResId)?.run {
        setBounds(0, 0, intrinsicWidth, intrinsicHeight)
        val bitmap =
            Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
        draw(Canvas(bitmap))
        BitmapDescriptorFactory.fromBitmap(bitmap)
    }
}

fun sharedPrefrences(context: Context):SharedPreferencesImpl{
    return SharedPreferencesImpl(context)
}

class InputFilterMinMax: InputFilter {
    private var min:Int = 0
    private var max:Int = 0
    constructor(min:Int, max:Int) {
        this.min = min
        this.max = max
    }
    constructor(min:String, max:String) {
        this.min = Integer.parseInt(min)
        this.max = Integer.parseInt(max)
    }
    override fun filter(source:CharSequence, start:Int, end:Int, dest: Spanned, dstart:Int, dend:Int): CharSequence? {
        try
        {
            val input = Integer.parseInt(dest.toString() + source.toString())
            if (isInRange(min, max, input))
                return null
        }
        catch (nfe:NumberFormatException) {}
        return ""
    }
    private fun isInRange(a:Int, b:Int, c:Int):Boolean {
        return if (b > a) c in a..b else c in b..a
    }
}