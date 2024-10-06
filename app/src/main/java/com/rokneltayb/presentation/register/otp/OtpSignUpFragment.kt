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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()
        val text = getString(R.string.please_enter_your_phone_number_to_receive_a_verification_code_to_reset_the_password) + "  " + args.phone.substring(7) + "*********"

        binding.number.text = text
        binding.resendcode.paintFlags = binding.resendcode.paintFlags or Paint.UNDERLINE_TEXT_FLAG

        binding.verifyCodePinView.doAfterTextChanged {
            val code = it.toString()
            if (code.length > 3) {
                viewModel.verifyPhone(args.phone, code)
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
    val SecResendCode = 60

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setTimer(SecResendCode)
        requireContext().openKeyBoard(binding.verifyCodePinView)
    }

    private fun setTimer(sec: Int) {
        binding.resendcode.isEnabled = false
        //Todo change the timer to 60000
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

            }
        }
    }
}