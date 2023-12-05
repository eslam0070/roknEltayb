package com.rokneltayb.presentation.login.forgetpassword.verifyaccount

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.rokneltayb.R
import com.rokneltayb.core.appUtils.snackBarFailure
import com.rokneltayb.databinding.FragmentVerifyYourAccountBinding

class VerifyYourAccountFragment : Fragment() {

    private val args: VerifyYourAccountFragmentArgs by navArgs()
    private val binding by lazy { FragmentVerifyYourAccountBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val text = getString(R.string.please_enter_your_phone_number_to_receive_a_verification_code_to_reset_the_password) + "  " + args.phone.toString().substring(7) + "*********"

        binding.number.text = text
        binding.resendcode.paintFlags = binding.resendcode.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        val pinCode = binding.verifyCodePinView.text.toString()
        if (pinCode.isEmpty())
            snackBarFailure(getString(R.string.please_enter_otp_code))
         else if (pinCode.length < 3)
            snackBarFailure(getString(R.string.otp_code_must_be_more_than_4_digits))
        else {

        }
        binding.resendcode.setOnClickListener {

        }
    }


}