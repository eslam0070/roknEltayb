package com.rokneltayb

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.rokneltayb.core.network.MyApplication
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class RoknelTaybApplication : Application()
{

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    companion object {
        lateinit  var appContext: Context
    }
}