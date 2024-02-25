package com.rokneltayb.presentation.more.privacy

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentAboutUsBinding
import com.rokneltayb.databinding.FragmentPrivacyPolicyBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.more.contactus.ContactUsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
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
                viewModel.removeState()
            }

            is ContactUsViewModel.UiState.PagesSuccess -> {
                binding.privacyPolicyTextView.text = Html.fromHtml(uiState.data.data!![1]!!.description, Html.FROM_HTML_MODE_COMPACT)
                Glide.with(requireActivity()).load(uiState.data.data[1]!!.image).into(binding.imageView)
                hideProgress()
                viewModel.removeState()
            }

            is ContactUsViewModel.UiState.Idle ->{
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
        viewModel.getPages()
        return binding.root
    }



}