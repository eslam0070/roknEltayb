package com.rokneltayb.domain.util.localization

import android.app.Activity
import android.content.Context
import android.util.DisplayMetrics
import android.util.Log
import java.util.*

object LocalizationUtils {
    val densities: ArrayList<Float> = object : ArrayList<Float>() {
        init {
            add(0.75f)
            add(1.0f)
            add(1.5f)
            add(2.0f)
            add(3.0f)
            add(4.0f)
        }
    }
    fun setDefaultFontConfiguration(context: Context) {
        try {
            val locale: Locale = if (LocaleHelper.getLanguage(context)
                    .equals("ar", ignoreCase = true)
            ) Locale(
                LocaleHelper.getLanguage(context).toString(),
                "SA"
            ) else Locale(LocaleHelper.getLanguage(context).toString())

            Log.d("local", "setDefaultFontConfiguration: "+locale.toString())
            Locale.setDefault(locale)
            val configuration = context.resources.configuration
            configuration.locale = locale
            configuration.fontScale = 1.toFloat() //0.85 small size, 1 normal size, 1,15 big etc
            val metrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(metrics)
            metrics.scaledDensity = configuration.fontScale * metrics.density
            if (!densities.contains(metrics.density))
                configuration.densityDpi = (getDensity(context) * 170f).toInt()
            context.getResources().updateConfiguration(configuration, metrics)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun getDensity(context: Context): Float {
        val density = context.resources.displayMetrics.density
        if (density >= 4.0) {
            return 4.0f
        }
        if (density >= 3.0) {
            return 3.0f
        }
        if (density >= 2.0) {
            return 2.0f
        }
        if (density >= 1.5) {
            return 1.5f
        }
        return if (density >= 1.0) {
            1.0f
        } else 0.75f
    }
}