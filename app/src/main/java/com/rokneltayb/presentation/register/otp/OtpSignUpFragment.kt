package com.rokneltayb.presentation.register.otp

import android.graphics.Paint
import android.os.Bundle
import android.os.CountDownTimer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
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
import com.rokneltayb.presentation.login.forgetpassword.ForgetPasswordViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OtpSignUpFragment : Fragment() {

    private val args: OtpSignUpFragmentArgs by navArgs()
    private val binding by lazy { FragmentVerifyYourAccountBinding.inflate(layoutInflater) }
    private val viewModel: OtpSignUpViewModel by viewModels()
    private val SecResendCode = 60

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()

        binding.resendcode.paintFlags = binding.resendcode.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        binding.verifyButton.setOnClickListener{
            if (binding.verifyCodePinView.text.toString().length == 4) {
                viewModel.verifyPhone(args.phone, binding.verifyCodePinView.text.toString())
            }else
                snackBarFailure(requireContext().getString(R.string.otp_code_must_be_4_digits))
        }

        binding.resendcode.setOnClickListener {
            binding.verifyCodePinView.clearComposingText()
            setTimer(SecResendCode)
            viewModel.resendVerifyPhone(args.phone)
        }
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTimer(SecResendCode)
        requireContext().openKeyBoard(binding.verifyCodePinView)
    }

    private fun setTimer(sec: Int) {
        binding.resendcode.isEnabled = false
        object : CountDownTimer((sec * 1000).toLong(), 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val seconds = (millisUntilFinished / 1000).toInt() % 60

                val strSec: String = if (seconds > 9) {
                    seconds.toString()
                } else {
                    "0$seconds"
                }

                binding.resendcode.isClickable = false

                binding.resendcode.text = getString(R.string.resend_verification_code) + "  " + strSec + getString(R.string.s)
                binding.resendcode.setTextColor(ContextCompat.getColor(requireContext(),R.color.main))

            }

            override fun onFinish() {
                binding.resendcode.isEnabled = true
                binding.resendcode.text = getString(R.string.resend_verification_code)
                binding.resendcode.setTextColor(ContextCompat.getColor(requireContext(),R.color.main))

            }
        }.start()
    }

    private fun observeUIState() =
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    private fun updateUI(uiState: OtpSignUpViewModel.UiState) {
        when (uiState) {
            is OtpSignUpViewModel.UiState.Loading -> {
                showProgress()
            }

            is OtpSignUpViewModel.UiState.Success -> {
                findNavController().navigate(OtpSignUpFragmentDirections.actionOtpSignUpFragmentToLoginFragment())
                hideProgress()
            }

            is OtpSignUpViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }

            is OtpSignUpViewModel.UiState.ResendSuccess -> {
                hideProgress()
            }

            OtpSignUpViewModel.UiState.Idle -> {
                hideProgress()
            }
        }
    }
}