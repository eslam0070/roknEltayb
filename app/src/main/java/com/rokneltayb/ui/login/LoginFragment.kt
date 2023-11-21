package com.rokneltayb.ui.login

import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rokneltayb.R
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {


    private val binding by lazy {FragmentLoginBinding.inflate(layoutInflater) }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpTxt.paintFlags = binding.signUpTxt.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.signUpTxt.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.etLoginPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
                binding.passLayout.isPasswordVisibilityToggleEnabled = true
            }

            override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {}
            override fun afterTextChanged(editable: Editable) {}
        })
    }

    private fun validInputs(): Boolean {
        return if (binding.etLoginUser.text!!.isEmpty()) {
            binding.etLoginUser.error = getString(R.string.please_write_phone_number)
            false
        } else if (binding.etLoginPassword.text!!.isEmpty()) {
            binding.passLayout.isPasswordVisibilityToggleEnabled = false
            binding.etLoginPassword.error = "please wrote password"
            false
        }else {

            true
        }

    }
}