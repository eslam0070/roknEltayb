package com.rokneltayb.presentation.login.forgetpassword.resetpassword

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.core.widget.doBeforeTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentResetYourPasswordBinding
import com.rokneltayb.domain.util.Constants.passwordCharNumber
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.login.forgetpassword.verifyaccount.VerifyYourAccountFragmentDirections
import com.rokneltayb.presentation.login.forgetpassword.verifyaccount.VerifyYourAccountViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ResetYourPasswordFragment : Fragment() {

    private val binding by lazy { FragmentResetYourPasswordBinding.inflate(layoutInflater) }
    private val viewModel: ResetYourPasswordViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        observeUIState()

        binding.newPasswordTextInputEditText.doAfterTextChanged {
            if (it.toString().isNotEmpty())
                binding.newPasswordTextInputLayout.hint = ""
            else
                binding.newPasswordTextInputLayout.hint = getString(R.string.password)
        }

        binding.confirmPasswordTextInputEditText.doAfterTextChanged {
            if (it.toString().isNotEmpty())
                binding.confirmPasswordTextInputLayout.hint = ""
            else
                binding.confirmPasswordTextInputLayout.hint = getString(R.string.confirm_password)
        }

        binding.continueButton.setOnClickListener {
            if (checkPasswordFields()) {
                viewModel.changePassword(
                    binding.newPasswordTextInputEditText.text.toString(),
                    binding.confirmPasswordTextInputEditText.text.toString()
                )
            }
        }
        return binding.root
    }

    private fun observeUIState() =
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    private fun updateUI(uiState: ResetYourPasswordViewModel.UiState) {
        when (uiState) {
            is ResetYourPasswordViewModel.UiState.Loading -> {
                showProgress()
            }

            is ResetYourPasswordViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }

            is ResetYourPasswordViewModel.UiState.ChangePasswordSuccess -> {
                findNavController().navigate(ResetYourPasswordFragmentDirections.actionResetYourPasswordFragmentToLoginFragment())
                hideProgress()
            }

            else -> {}
        }
    }

    private fun checkPasswordFields(): Boolean {

        if (binding.newPasswordTextInputEditText.text.toString().isEmpty()) {
            binding.newPasswordTextInputLayout.error =
                requireContext().getString(R.string.password_is_empty)
            return false
        }
        binding.newPasswordTextInputLayout.isErrorEnabled = false

        if (binding.confirmPasswordTextInputEditText.text.toString().isEmpty()) {
            binding.confirmPasswordTextInputLayout.error =
                requireContext().getString(R.string.password_is_empty)
            return false
        }

        if (binding.newPasswordTextInputEditText.text.toString().length < passwordCharNumber) {
            binding.newPasswordTextInputLayout.isErrorEnabled = true
            binding.newPasswordTextInputLayout.error =
                requireContext().getString(R.string.password_is_too_short)
            return false
        }
        binding.newPasswordTextInputLayout.isErrorEnabled = false

        if (binding.confirmPasswordTextInputEditText.text.toString().length < passwordCharNumber) {
            binding.confirmPasswordTextInputLayout.isErrorEnabled = true
            binding.confirmPasswordTextInputLayout.error =
                requireContext().getString(R.string.password_is_too_short)
            return false
        }
        binding.confirmPasswordTextInputLayout.isErrorEnabled = false

        if (binding.confirmPasswordTextInputEditText.text.toString() != binding.newPasswordTextInputEditText.text.toString()) {
            binding.confirmPasswordTextInputLayout.isErrorEnabled = true
            binding.confirmPasswordTextInputLayout.error =
                requireContext().getString(R.string.passwords_dont_match)

            binding.newPasswordTextInputLayout.isErrorEnabled = true
            binding.newPasswordTextInputLayout.error =
                requireContext().getString(R.string.passwords_dont_match)
            return false
        }
        binding.confirmPasswordTextInputLayout.isErrorEnabled = false
        binding.newPasswordTextInputLayout.isErrorEnabled = false

        return true
    }

}