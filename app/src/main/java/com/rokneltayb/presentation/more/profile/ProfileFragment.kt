package com.rokneltayb.presentation.more.profile

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
import com.rokneltayb.BaseActivity
import com.rokneltayb.R
import com.rokneltayb.data.model.login.profile.ProfileData
import com.rokneltayb.databinding.FragmentMoreBinding
import com.rokneltayb.databinding.FragmentProductsBinding
import com.rokneltayb.databinding.FragmentProfileBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.home.HomeViewModel
import com.rokneltayb.presentation.home.details.ProductDetailsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val binding by lazy { FragmentProfileBinding.inflate(layoutInflater) }
    private val viewModel: ProfileViewModel by viewModels()

    private fun observeUIState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    }

    private fun updateUI(uiState: ProfileViewModel.UiState) {
        when (uiState) {
            is ProfileViewModel.UiState.Loading -> {
                showProgress()
            }

            is ProfileViewModel.UiState.Success -> {
                setDataProfile(uiState.data.profileData)
                hideProgress()
            }

            is ProfileViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }

        }
    }

    private fun setDataProfile(profileData: ProfileData?) {
        binding.usernameEditText.setText(profileData!!.name)
        binding.emailAddressEditText.setText(profileData.email)
        binding.phoneEditText.setText(profileData.phone)
        binding.phoneEditText.setText(profileData.phone)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()

        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text = getString(R.string.my_profile)

        binding.addNewAddressImageView.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToNewAddressFragment())
        }


        viewModel.profile()
        return binding.root
    }


}