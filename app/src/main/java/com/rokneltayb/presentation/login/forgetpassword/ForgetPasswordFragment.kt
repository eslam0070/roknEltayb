package com.rokneltayb.presentation.login.forgetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentForgetPasswordBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.login.LoginFragmentDirections
import com.rokneltayb.presentation.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ForgetPasswordFragment : Fragment() {

    private val binding by lazy { FragmentForgetPasswordBinding.inflate(layoutInflater) }
    private val viewModel: ForgetPasswordViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.sendForgetPasswordButton.setOnClickListener {
            if (validInputs()){
                viewModel.sendOtp(binding.phone.text.toString())
                showProgress()
            }
        }
    }

    private fun validInputs(): Boolean {
        return if (binding.phone.text!!.isEmpty()) {
            toastError(getString(R.string.please_write_phone_number))
            false
        } else {
            true
        }

    }

    private fun observeUIState() =
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    private fun updateUI(uiState: ForgetPasswordViewModel.UiState) {
        when (uiState) {
            is ForgetPasswordViewModel.UiState.Loading -> {
                showProgress()
            }

            is ForgetPasswordViewModel.UiState.Success -> {
                Toast.makeText(requireContext(), uiState.data.message, Toast.LENGTH_SHORT).show()
                findNavController().navigate(ForgetPasswordFragmentDirections.actionForgetPasswordFragmentToVerifyYourAccountFragment(binding.phone.text.toString(),uiState.data.data!!.otp.toString()))
                hideProgress()
                viewModel.removeState()
            }

            is ForgetPasswordViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
                viewModel.removeState()
            }

            is ForgetPasswordViewModel.UiState.Idle -> hideProgress()
        }
    }
}