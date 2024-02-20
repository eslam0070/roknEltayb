package com.rokneltayb.presentation.login

import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.os.LocaleListCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.gms.tasks.Task
import com.google.firebase.messaging.FirebaseMessaging
import com.rokneltayb.R
import com.rokneltayb.data.model.login.login.ClientData
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.databinding.FragmentLoginBinding
import com.rokneltayb.domain.util.Constants
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.localization.LocaleHelper
import com.rokneltayb.domain.util.localization.LocalizationUtils
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class LoginFragment : Fragment() {


    private val binding by lazy {FragmentLoginBinding.inflate(layoutInflater) }
    private val viewModel: LoginViewModel by viewModels()
    private val sharedPref by lazy { SharedPreferencesImpl(requireContext()) }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()
        return binding.root
    }

    private fun observeUIState() =
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    private fun updateUI(uiState: LoginViewModel.UiState) {
        when (uiState) {
            is LoginViewModel.UiState.Loading -> {
                showProgress()
            }

            is LoginViewModel.UiState.Success -> {
                toast(getString(R.string.login_success))
                if (binding.checkboxRememberMe.isEnabled){
                    sharedPref.setUserId(uiState.data.data!!.clientData!!.id!!)
                    sharedPref.setRememberMe(true)
                    sharedPref.setApiKeyToken(uiState.data.data.token.toString())
                }
                findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
                hideProgress()
                viewModel.removeState()
            }

            is LoginViewModel.UiState.Error -> {
                when (uiState.errorData.status) {
                    400 -> toastError(uiState.errorData.message)
                    406 -> toastError(uiState.errorData.message)
                    422  -> {
                        val str: String = TextUtils.join(",", uiState.errorData.data)
                        toastError(str)
                    }
                }
                viewModel.removeState()
                hideProgress()
            }

            is LoginViewModel.UiState.Idle -> hideProgress()
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpTxt.paintFlags = binding.signUpTxt.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        binding.signUpTxt.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegisterFragment())
        }

        binding.etLoginUser.setText("5055050531")
        binding.etLoginPassword.setText("123456")

        if (sharedPref.getRememberMe())
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())

        binding.loginBtn.setOnClickListener {
            if (validInputs()){
                viewModel.login(fcmToken,binding.etLoginUser.text.toString(),binding.etLoginPassword.text.toString())
            }
        }

        binding.forgetpasswordTextView.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToForgetPasswordFragment())
        }

        binding.giestBtn.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToHomeFragment())
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
            toastError(getString(R.string.please_write_phone_number))
            false
        } else if (binding.etLoginPassword.text!!.isEmpty()) {
            binding.passLayout.isPasswordVisibilityToggleEnabled = false
            toastError("please wrote password")
            false
        }else {
            true
        }
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