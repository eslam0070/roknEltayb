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
    private val Login2ViewModel: RegisterViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()

        return binding.root
    }

    private fun observeUIState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //binding.signUpTxt.paintFlags = binding.signUpTxt.paintFlags or Paint.UNDERLINE_TEXT_FLAG


        binding.signUpButton.setOnClickListener {
            //if (checkValidation())
            viewModel.signUp(
                binding.username.text.toString(),
                binding.phone.text.toString(),
                binding.email.text.toString(),
                binding.etLoginPassword.text.toString(),
                binding.passwordConfirm.text.toString(),
                fcmToken
            )
        }
    }


    private fun updateUI(uiState: RegisterViewModel.UiState) {
        when (uiState) {
            is RegisterViewModel.UiState.Loading -> {
                hideProgress()
            }

            is RegisterViewModel.UiState.Success -> {
                toast(getString(R.string.register_success))
                findNavController().navigate(
                    RegisterFragmentDirections.actionRegisterFragmentToOtpSignUpFragment(
                        binding.phone.text.toString()
                    )
                )
                hideProgress()
               // binding.signUpButton.revertAnimation()
            }

            is RegisterViewModel.UiState.Error -> {
                toastError(uiState.errorData)
                hideProgress()
               // binding.signUpButton.revertAnimation()
            }

        }
    }

   /* private fun checkValidation(): Boolean {
        return if (binding.username.text.toString().isEmpty()) {
            toastError(getString(R.string.please_write_username))
            binding.signUpButton.revertAnimation()
            false
        } else if(binding.email.text!!.toString().isEmpty()) {
            binding.email.error = getString(R.string.please_write_email_address)
            binding.signUpButton.revertAnimation()
            false
        } else if (binding.etLoginPassword.text.toString().length < 8) {
            toastError(getString(R.string.password_is_less_than_8_letters))
            binding.signUpButton.revertAnimation()
            false
        } else if (binding.passwordConfirm.text.toString().length < 8) {
            toastError(getString(R.string.confirm_password_is_less_than_8_letters))
            binding.signUpButton.revertAnimation()
            false
        } else if ((binding.passwordConfirm.text.toString() != binding.etLoginPassword.text.toString())) {
            toastError(getString(R.string.confirm_password_does_not_match))
            binding.signUpButton.revertAnimation()
            false
        } else {
            binding.signUpButton.startAnimation()
            *//*viewModel.signUp(
                binding.username.text.toString(),
                binding.phone.text.toString(),
                binding.email.text.toString(),
                binding.etLoginPassword.text.toString(),
                binding.passwordConfirm.text.toString(),
                fcmToken
            )*//*

            true
        }
    }*/

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