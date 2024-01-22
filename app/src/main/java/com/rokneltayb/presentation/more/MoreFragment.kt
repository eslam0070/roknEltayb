package com.rokneltayb.presentation.more

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rokneltayb.R
import com.rokneltayb.databinding.DeleteAccountDialogBinding
import com.rokneltayb.databinding.FragmentHomeBinding
import com.rokneltayb.databinding.FragmentMoreBinding
import com.rokneltayb.databinding.LogoutDialogBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.more.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MoreFragment : Fragment() {


    private val binding by lazy { FragmentMoreBinding.inflate(layoutInflater) }

    private val viewModel: ProfileViewModel by viewModels()

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
                toastError(uiState.errorData.message)
                hideProgress()
            }

            is ProfileViewModel.UiState.LogoutSuccess -> {
                toast("You have successfully logged out")
                logoutNoAuth(requireActivity())
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
        binding.myOrdersLinearLayout.setOnClickListener {
            findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToOrderFragment())
        }

        binding.myWishlistLinearLayout.setOnClickListener {
            findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToFavoritesFragment())
        }

        binding.myProfileLinearLayout.setOnClickListener {
            findNavController().navigate(MoreFragmentDirections.actionMoreFragmentToProfileFragment())
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

        }

        binding.logoutLinearLayout.setOnClickListener {
            logoutDialog()
        }
        return binding.root
    }

    private fun logoutDialog() {
        val dialog = Dialog(requireContext())
        val binding: LogoutDialogBinding = DataBindingUtil
            .inflate(LayoutInflater.from(requireContext()), R.layout.logout_dialog, null, false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.window!!.setBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.drawable.rounded_whtite_button, null))


        binding.yesButton.setOnClickListener {viewModel.logout() }
        binding.noButton.setOnClickListener { dialog.dismiss() }


        dialog.show()
        val metrics: DisplayMetrics = requireActivity().resources.displayMetrics
        val width: Int = metrics.widthPixels
        dialog.window?.setLayout(width - 58, ViewGroup.LayoutParams.WRAP_CONTENT)
    }


}