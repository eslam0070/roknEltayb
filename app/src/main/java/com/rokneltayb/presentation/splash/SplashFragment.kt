package com.rokneltayb.presentation.splash

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rokneltayb.R
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.databinding.FragmentHomeBinding
import com.rokneltayb.databinding.FragmentSplashBinding
import com.rokneltayb.domain.util.Constants
import kotlinx.coroutines.delay


class SplashFragment : Fragment() {

    private val binding by lazy { FragmentSplashBinding.inflate(layoutInflater) }
    private val sharedPref by lazy { SharedPreferencesImpl(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        initScreen()
        return binding.root
    }

    private fun initScreen() {
        lifecycleScope.launchWhenCreated {
            val bounce: Animation = AnimationUtils.loadAnimation(requireActivity(), R.anim.bounce)
            bounce.also { binding.logoBounds.animation = it }
            delay(4000)
            startSplashTimer()
        }
    }

    private fun startSplashTimer() {
        if (sharedPref.getRememberMe())
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())
        else
            findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToLoginFragment())
    }


    override fun onStart() {
        super.onStart()
        if (sharedPref.getLanguage().isEmpty() ||
            sharedPref.getLanguage() == Constants.LANGUAGE_ARABIC
        ) {
            sharedPref.setLanguage(Constants.LANGUAGE_ARABIC)
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("ar"))
            binding.root.layoutDirection = View.LAYOUT_DIRECTION_RTL
        } else {
            AppCompatDelegate.setApplicationLocales(LocaleListCompat.forLanguageTags("en"))
            SharedPreferencesImpl(requireContext()).setLanguage(Constants.LANGUAGE_ENGLISH)
            binding.root.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }
    }
}