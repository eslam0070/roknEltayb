package com.rokneltayb.ui.login

import android.graphics.Paint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentLoginBinding


class LoginFragment : Fragment() {


    val binding by lazy {FragmentLoginBinding.inflate(layoutInflater) }


    private lateinit var viewModel: LoginViewModel

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
    }

}