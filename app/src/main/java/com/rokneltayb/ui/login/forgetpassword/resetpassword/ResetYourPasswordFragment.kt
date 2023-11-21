package com.rokneltayb.ui.login.forgetpassword.resetpassword

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentResetYourPasswordBinding
import com.rokneltayb.databinding.FragmentVerifyYourAccountBinding
import com.rokneltayb.ui.login.forgetpassword.verifyaccount.VerifyYourAccountFragmentArgs

class ResetYourPasswordFragment : Fragment() {

    private val args: ResetYourPasswordFragmentArgs by navArgs()
    private val binding by lazy { FragmentResetYourPasswordBinding.inflate(layoutInflater) }


    private lateinit var viewModel: ResetYourPasswordViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.password.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                binding.passLayout.isPasswordVisibilityToggleEnabled = true
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {}
        })

        binding.confirmPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                binding.passLayout.isPasswordVisibilityToggleEnabled = true
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {}
        })
    }

}