package com.rokneltayb.presentation.register

import android.graphics.Paint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentRegisterBinding
import com.rokneltayb.domain.util.Validate
import com.rokneltayb.domain.util.toast
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RegisterFragment : Fragment() {

    private val binding by lazy { FragmentRegisterBinding.inflate(layoutInflater) }
    private var areaId:Int? =  0


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.signUpTxt.paintFlags = binding.signUpTxt.paintFlags or Paint.UNDERLINE_TEXT_FLAG

    }

    private fun checkValidation(): Boolean {
        var valid = true
        when {
            binding.username.text.toString().isEmpty() -> {
                binding.username.error = getString(R.string.please_write_username)
                valid = false
            }

            binding.email.text.toString().isEmpty() -> {
                binding.email.error = getString(R.string.please_write_email_address)
                valid = false
            }


            !Validate.isEmailValid(binding.email.text.toString()) -> {
                binding.email.error = getString(R.string.invalid_email)
                valid = false
            }
            binding.etLoginPassword.text.toString().length < 8 -> {
                binding.etLoginPassword.error = "password is less than 8 letters"
                valid = false
            }

            binding.passwordConfirm.text.toString().length < 8 -> {
                binding.passwordConfirm.error = "confirm password is less than 8 letters"
                valid = false
            }

            (binding.passwordConfirm.text.toString() != binding.etLoginPassword.text.toString()) -> {
                binding.passwordConfirm.error = getString(R.string.confirm_password_does_not_match)
                valid = false
            }

            areaId == 0 -> {
                toast(getString(R.string.please_select_area))
                valid = false
            }

            binding.block.text.toString().isEmpty() -> {
                binding.block.error = getString(R.string.please_write_block)
                valid = false
            }
            binding.street.text.toString().isEmpty() -> {
                binding.street.error = getString(R.string.please_write_street)
                valid = false
            }

            binding.avenue.text.toString().isEmpty() -> {
                binding.avenue.error = getString(R.string.please_write_avenue)
                valid = false
            }

            binding.buildingNumber.text.toString().isEmpty() -> {
                binding.buildingNumber.error = getString(R.string.please_write_building_number)
                valid = false
            }

            binding.floor.text.toString().isEmpty() -> {
                binding.floor.error = getString(R.string.please_write_floor)
                valid = false
            }

            binding.apartment.text.toString().isEmpty() -> {
                binding.apartment.error = getString(R.string.please_write_apartment)
                valid = false
            }

            binding.deliveryInstruction.text.toString().isEmpty() -> {
                binding.deliveryInstruction.error = getString(R.string.please_write_delivery_instruction)
                valid = false
            }
        }
        return valid

    }

}