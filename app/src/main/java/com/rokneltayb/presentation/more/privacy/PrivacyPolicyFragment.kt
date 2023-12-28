package com.rokneltayb.presentation.more.privacy

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentAboutUsBinding
import com.rokneltayb.databinding.FragmentPrivacyPolicyBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.more.contactus.ContactUsViewModel
import kotlinx.coroutines.launch

class PrivacyPolicyFragment : Fragment() {

    private val binding by lazy { FragmentPrivacyPolicyBinding.inflate(layoutInflater) }

    private val viewModel: ContactUsViewModel by viewModels()

    private fun observeUIState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    }

    private fun updateUI(uiState: ContactUsViewModel.UiState) {
        when (uiState) {
            is ContactUsViewModel.UiState.Loading -> {
                showProgress()
            }

            is ContactUsViewModel.UiState.Error -> {
                if (uiState.errorData.status == 401)
                    logoutNoAuth(requireActivity())
                else
                    toastError(uiState.errorData.message)
                hideProgress()
            }

            is ContactUsViewModel.UiState.Success -> {

                hideProgress()
            }

            is ContactUsViewModel.UiState.StoreContactSuccess -> {
                toast(uiState.data.message!!)
                hideProgress()
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        observeUIState()
        return binding.root
    }



}