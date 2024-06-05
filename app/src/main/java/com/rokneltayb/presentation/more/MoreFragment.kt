package com.rokneltayb.presentation.more

import android.app.Activity
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.core.os.LocaleListCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.rokneltayb.BaseActivity
import com.rokneltayb.R
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.databinding.DeleteAccountDialogBinding
import com.rokneltayb.databinding.FragmentHomeBinding
import com.rokneltayb.databinding.FragmentMoreBinding
import com.rokneltayb.databinding.LogoutDialogBinding
import com.rokneltayb.domain.util.Constants
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.localization.LocaleHelper
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.logoutNoPremission
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.more.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoreFragment : Fragment() {


    private val binding by lazy { FragmentMoreBinding.inflate(layoutInflater) }

    private val viewModel: ProfileViewModel by viewModels()

    private val sharedPref by lazy { SharedPreferencesImpl(requireContext()) }
    private fun observeUIState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    }

    private fun updateUI(uiState: ProfileViewModel.UiState) {
        when (uiState) {
            is ProfileViewModel.UiState.Loading -> {
            }

            is ProfileViewModel.UiState.Error -> {
                if (uiState.errorData.status == 401){
                    SharedPreferencesImpl(requireActivity()).clearAll()
                    findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToLoginFragment())
                }
                else
                    toastError(uiState.errorData.message)
                hideProgress()
                viewModel.removeState()
            }

            is ProfileViewModel.UiState.LogoutSuccess -> {
                toast(R.string.you_have_successfully_logged_out)
                SharedPreferencesImpl(requireActivity()).clearAll()
                findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToLoginFragment())
                hideProgress()
                viewModel.removeState()
            }

            is ProfileViewModel.UiState.Success ->{
                binding.welcomeTextView.text = getString(R.string.welcome) + uiState.data.profileData!!.name
                binding.emailTextView.text = uiState.data.profileData.email
            }

            is ProfileViewModel.UiState.Idle ->{
                hideProgress()
            }
            else ->{}
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()
        if (Build.VERSION.SDK_INT >= 31) {
            requireActivity().window.decorView.layoutDirection = resources.configuration.layoutDirection
        }

        viewModel.profile()
        if (sharedPref.getLanguage() == "en") {
            binding.langTextView.text = "عربي"
        } else {
            binding.langTextView.text = "English"
        }


        binding.myOrdersLinearLayout.setOnClickListener {
            findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToOrderFragment())
        }

        binding.myWishlistLinearLayout.setOnClickListener {
            if (sharedPref.getRememberMe())
                findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToFavoritesFragment())
            else{
                logoutNoPremission(requireActivity())
            }
        }

        binding.myProfileLinearLayout.setOnClickListener {
            if (sharedPref.getRememberMe())
                findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToProfileFragment())
            else{
                logoutNoPremission(requireActivity())
            }

        }

        binding.aboutUsLinearLayout.setOnClickListener {
            findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToAboutUsFragment())
        }

        binding.contactUsLinearLayout.setOnClickListener {
            findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToContactUsFragment())
        }

        binding.privacyPolicyLinearLayout.setOnClickListener {
            findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToPrivacyPolicyFragment())
        }

        binding.changeLanguageLinearLayout.setOnClickListener {
            changeLanguage()
        }

        binding.logoutLinearLayout.setOnClickListener {
            if (sharedPref.getRememberMe())
                logoutDialog()
            else{
                logoutNoPremission(requireActivity())
            }
        }
        return binding.root
    }

    private fun changeLanguage() {
        val locale: LocaleListCompat?

        if (SharedPreferencesImpl(requireContext()).getLanguage() == Constants.LANGUAGE_ARABIC) {
            LocaleHelper.setLocale(requireContext(), "en")
            SharedPreferencesImpl(requireContext()).setLanguage(Constants.LANGUAGE_ENGLISH)
            locale = LocaleListCompat.forLanguageTags("en")
            binding.root.layoutDirection = View.LAYOUT_DIRECTION_RTL
        } else {
            LocaleHelper.setLocale(requireContext(), "ar")
            SharedPreferencesImpl(requireContext()).setLanguage(Constants.LANGUAGE_ARABIC)
            locale = LocaleListCompat.forLanguageTags("ar")
            binding.root.layoutDirection = View.LAYOUT_DIRECTION_LTR
        }

        when (Build.VERSION.SDK_INT) {
            Build.VERSION_CODES.S, Build.VERSION_CODES.S_V2 -> {
                val intent = Intent(requireContext(), BaseActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK //this will always start your activity as a new task
                startActivity(intent)
                requireActivity().finish()
            }

            else -> {
                AppCompatDelegate.setApplicationLocales(locale)
            }
        }
    }

    private fun logoutDialog() {
        val dialog = Dialog(requireContext())
        val binding: LogoutDialogBinding = DataBindingUtil
            .inflate(LayoutInflater.from(requireContext()), R.layout.logout_dialog, null, false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.window!!.setBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.drawable.rounded_whtite_button, null))


        binding.yesButton.setOnClickListener {
            viewModel.logout()
            dialog.dismiss()}
        binding.noButton.setOnClickListener { dialog.dismiss() }


        dialog.show()
        val metrics: DisplayMetrics = requireActivity().resources.displayMetrics
        val width: Int = metrics.widthPixels
        dialog.window?.setLayout(width - 58, ViewGroup.LayoutParams.WRAP_CONTENT)
    }

    fun logoutNoAuth(activity: Activity) {
        SharedPreferencesImpl(activity).clearAll()
        findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToLoginFragment())
    }

}