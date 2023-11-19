package com.rokneltayb.core.appUtils.localization

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.preference.PreferenceManager
import android.util.DisplayMetrics
import com.app.monasba.core.appUtils.Constants
import com.exas.qpmoemp.core.appUtils.localization.LocalizationUtils
import java.util.Locale

class LocaleHelper {
    private val TAG = LocaleHelper::class.java.name

    companion object {
        private const val SELECTED_LANGUAGE = "Locale.Helper.Selected.Language"
        fun onAttach(context: Context): Context {
            val lang = getPersistedData(context, Locale.getDefault().language)
            return setLocale(context, lang)
        }

        fun onAttach(context: Context, defaultLanguage: String): Context {
            val lang = getPersistedData(context, defaultLanguage)
            return setLocale(context, lang)
        }

        fun getLanguage(context: Context): String? {
//        return getPersistedData(context, Locale.getDefault().getLanguage());
            return getPersistedData(context, "ar")
        }

        fun setLocale(context: Context, language: String?): Context {
            persist(context, language)
            return updateResources(context, language)
        }

        private fun getPersistedData(context: Context, defaultLanguage: String): String? {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            return preferences.getString(SELECTED_LANGUAGE, defaultLanguage)
        }

        private fun persist(context: Context, language: String?) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(context)
            val editor = preferences.edit()
            editor.putString(SELECTED_LANGUAGE, language)
            editor.apply()
        }


        private fun updateResources(context: Context, language: String?): Context {
            val locale: Locale = if (language.equals("ar", ignoreCase = true)) Locale(
                language.toString(),
                "SA"
            ) else Locale(language.toString())
            Locale.setDefault(locale)
            val configuration = context.resources.configuration
            configuration.setLocale(locale)

            configuration.fontScale = 1.toFloat() //0.85 small size, 1 normal size, 1,15 big etc
            val metrics = DisplayMetrics()
            (context as Activity).windowManager.defaultDisplay.getMetrics(metrics)
            metrics.scaledDensity = configuration.fontScale * metrics.density
            if (!Constants.densities.contains(metrics.density))
                configuration.densityDpi = (LocalizationUtils.getDensity(context) * 170f).toInt()


            return context.createConfigurationContext(configuration)
        }

        private fun updateResourcesLegacy(context: Context, language: String?): Context {
            val locale: Locale = if (language.equals("ar", ignoreCase = true)) Locale(
                language,
                "SA"
            ) else Locale(language)
            Locale.setDefault(locale)
            val resources = context.resources

//        Configuration configuration = resources.getConfiguration();
            val configuration = Configuration()
            configuration.locale = locale
            resources.updateConfiguration(configuration, resources.displayMetrics)
            return context
        }
    }
}