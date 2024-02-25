package com.rokneltayb.presentation.more.profile

import android.app.Activity
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
import androidx.core.widget.doBeforeTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.rokneltayb.BaseActivity
import com.rokneltayb.R
import com.rokneltayb.data.model.login.profile.ProfileData
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.databinding.DeleteAccountDialogBinding
import com.rokneltayb.databinding.DeleteAddressDialogBinding
import com.rokneltayb.databinding.EditProfileDialogBinding
import com.rokneltayb.databinding.FragmentMoreBinding
import com.rokneltayb.databinding.FragmentProductsBinding
import com.rokneltayb.databinding.FragmentProfileBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.logoutNoAuth
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
                viewModel.removeState()
                hideProgress()
            }

            is ProfileViewModel.UiState.Error -> {
                if (uiState.errorData.status == 401)
                    logoutNoAuth(requireActivity())
                else
                    toastError(uiState.errorData.message)
                hideProgress()
                viewModel.removeState()
            }

            is ProfileViewModel.UiState.DeleteSuccess -> {
                toast(uiState.data.message!!)
                hideProgress()
                logout()
                viewModel.removeState()
            }
            is ProfileViewModel.UiState.LogoutSuccess -> {
                toast(getString(R.string.you_have_successfully_logged_out))
                hideProgress()
                viewModel.removeState()
            }

            is ProfileViewModel.UiState.ChangePasswordSuccess -> {
                viewModel.removeState()
                hideProgress()

            }

            is ProfileViewModel.UiState.UpdateSuccess -> {
                viewModel.profile()
                toast(uiState.data.message!!)
                hideProgress()
                viewModel.removeState()
            }

            ProfileViewModel.UiState.Idle -> hideProgress()
        }
    }

    private fun setDataProfile(profileData: ProfileData?) {
        binding.usernameEditText.setText(profileData!!.name)
        binding.emailAddressEditText.setText(profileData.email)
        binding.phoneEditText.setText(profileData.phone)
        if (profileData.addresses!!.isNotEmpty()){
            binding.addressTextView.text = profileData.addresses[0]!!.address
            /*binding.addressTextView.text = profileData.addresses!![0]!!.block+" , "+ getString(R.string.street) +" " +profileData.addresses!![0]!!.street +" "+
                    profileData.addresses!![0]!!.avenue +" , "+ getString(R.string.building_number)+" "+ profileData.addresses!![0]!!.building_num +" , "
            getString(R.string.floor) + " " + profileData.addresses[0]!!.floor_num +" , " + getString(R.string.apartment) +" "+ profileData.addresses!![0]!!.apartment*/
        }

        else
            binding.addressTextView.text = ""


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

        binding.updateProfileButton.setOnClickListener {
            viewModel.updateProfile(binding.usernameEditText.text.toString(),binding.phoneEditText.text.toString(),binding.emailAddressEditText.text.toString())
        }

        binding.editPasswordImageView.setOnClickListener {
            editProfileDialog()
        }

        binding.deleteAccountTextView.setOnClickListener {
            deleteAccountDialog()
        }

        viewModel.profile()
        return binding.root
    }
    private fun editProfileDialog() {
        val dialog = Dialog(requireContext())
        val binding: EditProfileDialogBinding = DataBindingUtil
            .inflate(LayoutInflater.from(requireContext()), R.layout.edit_profile_dialog, null, false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.window!!.setBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.drawable.rounded_whtite_button, null))


        binding.etOldPassword.doBeforeTextChanged { _, _, _, _ ->
            binding.layoutOldPass.isPasswordVisibilityToggleEnabled = true
        }
        binding.etLoginPassword.doBeforeTextChanged { _, _, _, _ ->
            binding.passLayout.isPasswordVisibilityToggleEnabled = true
        }
        binding.etLoginPasswordConfirm.doBeforeTextChanged { _, _, _, _ ->
            binding.passConfirmLayout.isPasswordVisibilityToggleEnabled = true
        }

        binding.forgotYourPassword.setOnClickListener {
            findNavController().navigate(ProfileFragmentDirections.actionProfileFragmentToForgetPasswordFragment())
        }

        binding.updateBtn.setOnClickListener {
            if (checkValidation(binding.etOldPassword,
                    binding.etLoginPassword,
                    binding.etLoginPasswordConfirm,
                    binding.layoutOldPass,
                    binding.passLayout,
                    binding.passConfirmLayout)) {
                viewModel.changePassword(
                    oldPassword = binding.etOldPassword.text.toString(),
                    newPassword = binding.etLoginPassword.text.toString(),
                    confirmNewPassword = binding.etLoginPasswordConfirm.text.toString()
                )

                dialog.dismiss()
            }

        }

        binding.buDismiss.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
        val metrics: DisplayMetrics = requireActivity().resources.displayMetrics
        val width: Int = metrics.widthPixels
        dialog.window?.setLayout(
            width - 58,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun deleteAccountDialog() {
        val dialog = Dialog(requireContext())
        val binding: DeleteAccountDialogBinding = DataBindingUtil
            .inflate(LayoutInflater.from(requireContext()), R.layout.delete_account_dialog, null, false)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.window!!.setBackgroundDrawable(ResourcesCompat.getDrawable(resources, R.drawable.rounded_whtite_button, null))

        binding.title.text = "هل تريد حذف حسابك ؟"

        binding.yesButton.setOnClickListener {
            viewModel.deleteAccout()
            dialog.dismiss()
        }
        binding.noButton.setOnClickListener { dialog.dismiss() }

        dialog.show()
        val metrics: DisplayMetrics = requireActivity().resources.displayMetrics
        val width: Int = metrics.widthPixels
        dialog.window?.setLayout(
            width - 58,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    private fun checkValidation(
        oldPassword: TextInputEditText,
        newPassword: TextInputEditText,
        confirmNewPassword: TextInputEditText,
        layoutOldPass: TextInputLayout,
        layoutNewPass: TextInputLayout,
        layoutConfirmNewPass: TextInputLayout
    ): Boolean {
        if (oldPassword.text.toString().isEmpty()) {
            layoutOldPass.isPasswordVisibilityToggleEnabled = false
            oldPassword.error = getString(R.string.w_pass_check)
            return false
        } else if (newPassword.text.toString().isEmpty()) {
            layoutNewPass.isPasswordVisibilityToggleEnabled = false
            newPassword.error = getString(R.string.w_pass_check)
            return false
        } else if (confirmNewPassword.text.toString().isEmpty()) {
            layoutConfirmNewPass.isPasswordVisibilityToggleEnabled = false
            confirmNewPassword.error = getString(R.string.w_pass_check)
            return false
        } else if (oldPassword.text.toString().length < 8) {
            layoutOldPass.isPasswordVisibilityToggleEnabled = false
            oldPassword.error = getString(R.string.label_enter_password_length)
            return false
        } else if (newPassword.text.toString().length < 8) {
            layoutNewPass.isPasswordVisibilityToggleEnabled = false
            newPassword.error = getString(R.string.label_enter_password_length)
            return false
        } else if (confirmNewPassword.text.toString().length < 8) {
            layoutConfirmNewPass.isPasswordVisibilityToggleEnabled = false
            confirmNewPassword.error = getString(R.string.label_enter_password_length)
            return false
        }else if ((confirmNewPassword.text.toString() != newPassword.text.toString())) {
            layoutConfirmNewPass.isPasswordVisibilityToggleEnabled = false
            confirmNewPassword.error = getString(R.string.Confirm_password_does_not_match)
            return false
        } else return true


    }

    fun logout() {
        val lang = SharedPreferencesImpl(requireContext()).getLanguage()
        SharedPreferencesImpl(requireContext()).clearAll()
        SharedPreferencesImpl(requireContext()).setLanguage(lang)
        Navigation.findNavController(requireActivity(), R.id.navHostFragment).navigate(R.id.loginFragment)
    }
}