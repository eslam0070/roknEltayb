package com.rokneltayb.presentation.login.forgetpassword.verifyaccount

import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentVerifyYourAccountBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.openKeyBoard
import com.rokneltayb.domain.util.snackBarFailure
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.login.forgetpassword.ForgetPasswordFragmentDirections
import com.rokneltayb.presentation.login.forgetpassword.ForgetPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class VerifyYourAccountFragment : Fragment() {

    private val args: VerifyYourAccountFragmentArgs by navArgs()
    private val binding by lazy { FragmentVerifyYourAccountBinding.inflate(layoutInflater) }
    private val resendviewModel: ForgetPasswordViewModel by viewModels()
    private val viewModel: VerifyYourAccountViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()
        observeUIStateResendCode()

        val text = getString(R.string.please_enter_your_phone_number_to_receive_a_verification_code_to_reset_the_password) + "  " + args.phone.substring(7) + "*********"

        binding.number.text = text
        binding.resendcode.paintFlags = binding.resendcode.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        binding
        binding.verifyCodePinView.doAfterTextChanged {
            val code = it.toString()
            if (code.length > 3) {
                viewModel.resetPassword(args.phone, code)
            }
        }

        binding.resendcode.setOnClickListener {
            resendviewModel.sendOtp(args.phone)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTimer()
        requireContext().openKeyBoard(binding.verifyCodePinView)
    }

    private fun setTimer() {
        binding.resendcode.isEnabled = false
        //Todo change the timer to 60000
        object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.resendcode.text = getString(R.string.did_not_get_the_code, (millisUntilFinished / 1000).toInt())
            }

            override fun onFinish() {
                binding.resendcode.isEnabled = true
                binding.resendcode.text = getString(R.string.resend_verification_code)
            }
        }.start()
    }

    private fun observeUIState() =
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    private fun updateUI(uiState: VerifyYourAccountViewModel.UiState) {
        when (uiState) {
            is VerifyYourAccountViewModel.UiState.Loading -> {
                showProgress()
            }

            is VerifyYourAccountViewModel.UiState.Success -> {
                findNavController().navigate(VerifyYourAccountFragmentDirections.actionVerifyYourAccountFragmentToResetYourPasswordFragment())
                hideProgress()
                viewModel.removeState()
            }

            is VerifyYourAccountViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
                viewModel.removeState()
            }

            is VerifyYourAccountViewModel.UiState.Idle -> hideProgress()
        }
    }

    private fun observeUIStateResendCode() =
        lifecycleScope.launch {
            resendviewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUIReset)
        }
    private fun updateUIReset(uiState: ForgetPasswordViewModel.UiState) {
        when (uiState) {
            is ForgetPasswordViewModel.UiState.Loading -> {
                showProgress()
            }

            is ForgetPasswordViewModel.UiState.Success -> {
                Toast.makeText(requireContext(), uiState.data.message, Toast.LENGTH_SHORT).show()
                hideProgress()
                resendviewModel.removeState()
            }

            is ForgetPasswordViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
                resendviewModel.removeState()
            }

            is ForgetPasswordViewModel.UiState.Idle -> hideProgress()
        }
    }
}