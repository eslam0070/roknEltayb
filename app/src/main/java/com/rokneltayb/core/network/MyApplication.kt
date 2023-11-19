package com.rokneltayb.core.network

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class MyApplication : Application(){

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }

    companion object {
        lateinit  var appContext: Context
    }
}