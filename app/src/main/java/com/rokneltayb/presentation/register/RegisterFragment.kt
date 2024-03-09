package com.rokneltayb.presentation.register

import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentRegisterBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.Validate
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.login.LoginFragmentDirections
import com.rokneltayb.presentation.login.LoginViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val binding by lazy { FragmentRegisterBinding.inflate(layoutInflater) }
    private val viewModel: RegisterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()
        binding.signUpTxt.paintFlags = binding.signUpTxt.paintFlags or Paint.UNDERLINE_TEXT_FLAG


        binding.signUpButton.setOnClickListener {
            if (checkValidation()){
                binding.signUpButton.startAnimation()
                viewModel.signUp(binding.username.text.toString(),
                    binding.phone.text.toString(),binding.email.text.toString(),binding.etLoginPassword.text.toString(),binding.passwordConfirm.text.toString(),fcmToken)
            }
        }
        return binding.root
    }

    private fun observeUIState() =
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    private fun updateUI(uiState: RegisterViewModel.UiState) {
        when (uiState) {
            is RegisterViewModel.UiState.Loading -> {
                showProgress()
            }

            is RegisterViewModel.UiState.Success -> {
                toast(getString(R.string.register_success))
                findNavController().navigate(RegisterFragmentDirections.actionRegisterFragmentToLoginFragment())
                hideProgress()
                binding.signUpButton.revertAnimation()
                viewModel.removeState()
            }

            is RegisterViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
                binding.signUpButton.revertAnimation()
                viewModel.removeState()
            }

            is RegisterViewModel.UiState.Idle -> hideProgress()
        }
    }
    private fun checkValidation(): Boolean {
        var valid = true
        when {
            binding.username.text.toString().isEmpty() -> {
                toastError(getString(R.string.please_write_username))
                valid = false
                binding.signUpButton.revertAnimation()
            }

            binding.email.text.toString().isEmpty() -> {
                toastError(getString(R.string.please_write_email_address))
                valid = false
                binding.signUpButton.revertAnimation()
            }

            !Validate.isEmailValid(binding.email.text.toString()) -> {
                toastError(getString(R.string.invalid_email))
                valid = false
                binding.signUpButton.revertAnimation()
            }
            binding.etLoginPassword.text.toString().length < 8 -> {
                toastError(getString(R.string.password_is_less_than_8_letters))
                valid = false
                binding.signUpButton.revertAnimation()
            }

            binding.passwordConfirm.text.toString().length < 8 -> {
                toastError(getString(R.string.confirm_password_is_less_than_8_letters))
                valid = false
                binding.signUpButton.revertAnimation()
            }

            (binding.passwordConfirm.text.toString() != binding.etLoginPassword.text.toString()) -> {
                toastError(getString(R.string.confirm_password_does_not_match))
                valid = false
                binding.signUpButton.revertAnimation()
            }
        }
        return valid

    }

    override fun onStart() {
        super.onStart()
        getFcmToken()
    }
    var fcmToken = ""

    private fun getFcmToken() {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener { task: Task<String?> ->
                if (!task.isSuccessful) {
                    //Could not get FirebaseMessagingToken
                    return@addOnCompleteListener
                }
                if (null != task.result) {
                    fcmToken = task.result.toString()

                }
            }
    }
}