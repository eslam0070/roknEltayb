package com.rokneltayb.presentation.login.forgetpassword

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentForgetPasswordBinding

class ForgetPasswordFragment : Fragment() {

    private val binding by lazy { FragmentForgetPasswordBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }

    private fun validInputs(): Boolean {
        return if (binding.phone.text!!.isEmpty()) {
            binding.phone.error = getString(R.string.please_write_phone_number)
            false
        } else {

            true
        }

    }
}