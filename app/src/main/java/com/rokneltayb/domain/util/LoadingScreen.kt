@file:OptIn(DelicateCoroutinesApi::class)

package com.rokneltayb.domain.util

import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.rokneltayb.R
import com.rokneltayb.BaseActivity
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import kotlinx.coroutines.*

object LoadingScreen {

    fun Fragment.showProgress() {
        if (activity != null)
            (requireActivity() as BaseActivity).showProgress(true)
    }

    fun Fragment.hideProgress() {
        if (activity != null)
            (requireActivity() as BaseActivity).showProgress(false)
    }

    fun Fragment.logOut() {
        val lang = SharedPreferencesImpl(requireActivity()).getLanguage()
        SharedPreferencesImpl(requireActivity()).clearAll()
        SharedPreferencesImpl(requireActivity()).setLanguage(lang)
        Navigation.findNavController(requireActivity(), R.id.navHostFragment).navigate(R.id.loginFragment)
    }




}