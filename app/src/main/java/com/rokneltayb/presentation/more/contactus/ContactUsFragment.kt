package com.rokneltayb.presentation.more.contactus

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentContactUsBinding
import com.rokneltayb.databinding.FragmentMoreBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.more.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ContactUsFragment : Fragment() {


    private val binding by lazy { FragmentContactUsBinding.inflate(layoutInflater) }
    private val viewModel: ContactUsViewModel by viewModels()

    private fun observeUIState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    }

    private fun updateUI(uiState: ContactUsViewModel.UiState) {
        when (uiState) {
            is ContactUsViewModel.UiState.Loading -> {
                showProgress()
            }

            is ContactUsViewModel.UiState.Error -> {
                if (uiState.errorData.status == 401)
                    logoutNoAuth(requireActivity())
                else
                    toastError(uiState.errorData.message)
                hideProgress()
            }

            is ContactUsViewModel.UiState.Success -> {
                binding.phoneTextView.text = uiState.data.data!!.phone
                binding.mailTextView.text = uiState.data.data.email
                binding.addressTextView.text = uiState.data.data.address
                hideProgress()
            }

            is ContactUsViewModel.UiState.StoreContactSuccess -> {
                toast(uiState.data.message!!)
                viewModel.removeState()
                findNavController().navigate(ContactUsFragmentDirections.actionContactUsFragmentToMoreFragment())
                hideProgress()
            }

            else ->{}
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()

        viewModel.settings()
        binding.sendMessageButton.setOnClickListener {
            if (binding.nameEditText.text.isEmpty() || binding.phoneEditText.text.isEmpty() ||
                binding.emailAddressEditText.text.isEmpty() || binding.subjectEditText.text.isEmpty() || binding.messageEditText.text.isEmpty())
                toastError(getString(R.string.fill_all_fildes))
            else
                viewModel.storeContact(binding.nameEditText.text.toString(),binding.phoneEditText.text.toString(),binding.emailAddressEditText.text.toString(),binding.subjectEditText.text.toString(),binding.messageEditText.text.toString())
        }

        return binding.root
    }



}