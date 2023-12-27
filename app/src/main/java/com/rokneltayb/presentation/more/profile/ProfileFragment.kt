package com.rokneltayb.presentation.more.profile

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rokneltayb.BaseActivity
import com.rokneltayb.R
import com.rokneltayb.data.model.login.profile.ProfileData
import com.rokneltayb.databinding.DeleteAccountDialogBinding
import com.rokneltayb.databinding.DeleteAddressDialogBinding
import com.rokneltayb.databinding.FragmentMoreBinding
import com.rokneltayb.databinding.FragmentProductsBinding
import com.rokneltayb.databinding.FragmentProfileBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.toast
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

            is ProfileViewModel.UiState.DeleteSuccess -> {
                toast(uiState.data.message!!)
                hideProgress()
            }
            is ProfileViewModel.UiState.LogoutSuccess -> {
                toast("You have successfully logged out")
                hideProgress()
            }
        }
    }

    private fun setDataProfile(profileData: ProfileData?) {
        binding.usernameEditText.setText(profileData!!.name)
        binding.emailAddressEditText.setText(profileData.email)
        binding.phoneEditText.setText(profileData.phone)
        binding.addressTextView.text = profileData.addresses!![0]!!.block+" , "+ getString(R.string.street) +" " +profileData.addresses!![0]!!.street +" "+
        profileData.addresses!![0]!!.avenue +" , "+ getString(R.string.building_number)+" "+ profileData.addresses!![0]!!.building_num +" , "
        getString(R.string.floor) + " " + profileData.addresses[0]!!.floor_num +" , " + getString(R.string.apartment) +" "+ profileData.addresses!![0]!!.apartment

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

        binding.addressTextView.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToAddressFragment())
        }

        binding.deleteAccountTextView.setOnClickListener {
            deleteAccountDialog()
        }

        viewModel.profile()
        return binding.root
    }
    private fun deleteAccountDialog() {
        val dialog = Dialog(requireContext())
        val binding: DeleteAccountDialogBinding = DataBindingUtil
            .inflate(LayoutInflater.from(requireContext()), R.layout.delete_account_dialog, null, false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.window!!.setBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.drawable.rounded_whtite_button, null))


        binding.yesButton.setOnClickListener {viewModel.deleteAccout() }
        binding.noButton.setOnClickListener { dialog.dismiss() }

        dialog.show()
        val metrics: DisplayMetrics = requireActivity().resources.displayMetrics
        val width: Int = metrics.widthPixels
        dialog.window?.setLayout(
            width - 58,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

}