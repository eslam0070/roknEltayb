package com.rokneltayb.presentation.more.profile.address.add

import android.app.Dialog
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rokneltayb.R
import com.rokneltayb.data.model.address.city.City
import com.rokneltayb.data.model.address.details.AddressDetailsData
import com.rokneltayb.databinding.DeleteAddressDialogBinding
import com.rokneltayb.databinding.FragmentNewAddressBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.more.profile.address.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class NewAddressFragment : Fragment() {


    private val binding by lazy { FragmentNewAddressBinding.inflate(layoutInflater) }
    private val viewModel: AddressViewModel by viewModels()
    private var cityId = 0
    private val citiesList = mutableListOf<City>()
    var citiesAdapter: ArrayAdapter<City>? = null
    private val args: NewAddressFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()

        if (args.id != null) {
            binding.deleteAddressButton.visibility = View.VISIBLE
            binding.addAddressButton.text = getString(R.string.update_address)
            binding.deleteAddressButton.setOnClickListener { deleteAddressDialog(args.id!!.toInt()) }
            viewModel.getAddressById(args.id.toString())
        } else {
            binding.addAddressButton.text = getString(R.string.add_address)
            binding.deleteAddressButton.visibility = View.GONE
        }


        viewModel.cities()
        binding.addAddressButton.setOnClickListener {
            if (cityId == 0)
                toastError(getString(R.string.please_select_a_city))
            else if (binding.addressName.text.toString().isEmpty())
                toastError(getString(R.string.please_write_its_unique_tag))
            else if (binding.name.text.toString().isEmpty())
                toastError("Please write title address")
            else {
                if (args.id != null) {
                    viewModel.update(
                        args.id.toString(),
                        binding.name.text.toString(),
                        binding.phone.text.toString(),
                        cityId.toString(),
                        binding.block.text.toString(),
                        binding.street.text.toString(),
                        binding.avenue.text.toString(),
                        binding.buildingNumber.text.toString(),
                        binding.floor.text.toString(),
                        binding.apartment.text.toString(),
                        binding.addressName.text.toString()
                    )
                } else {
                    viewModel.add(
                        binding.name.text.toString(),
                        binding.phone.text.toString(),
                        cityId.toString(),
                        binding.block.text.toString(),
                        binding.street.text.toString(),
                        binding.avenue.text.toString(),
                        binding.buildingNumber.text.toString(),
                        binding.floor.text.toString(),
                        binding.apartment.text.toString(),
                        binding.addressName.text.toString()
                    )
                }
            }
        }
        return binding.root
    }

    private fun observeUIState() =
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    private fun updateUI(uiState: AddressViewModel.UiState) {
        when (uiState) {
            is AddressViewModel.UiState.Loading -> {
                showProgress()
            }

            is AddressViewModel.UiState.CitiesSuccess -> {
                citiesList.clear()
                citiesList.addAll(uiState.data.data.cities)
                setItemCitiesSpinner()
                hideProgress()
                viewModel.removeState()
            }

            is AddressViewModel.UiState.AddressDetailsSuccess -> {
                getAddressDetails(uiState.data.addressDetailsData!!)
                hideProgress()
                viewModel.removeState()
            }

            is AddressViewModel.UiState.UpdateDetailsSuccess -> {
                findNavController().navigateUp()
                hideProgress()
                viewModel.removeState()
            }

            is AddressViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
                viewModel.removeState()
            }

            is AddressViewModel.UiState.DeleteSuccess -> {
                findNavController().navigateUp()
                hideProgress()
                viewModel.removeState()
            }

            is AddressViewModel.UiState.AddSuccess -> {
                toast(uiState.data.message)
                findNavController().navigateUp()
                hideProgress()
                viewModel.removeState()
            }

            is AddressViewModel.UiState.Idle ->{
                hideProgress()
            }
            else -> {}
        }
    }

    private fun getAddressDetails(addressDetailsData: AddressDetailsData) {
        binding.name.setText(addressDetailsData.name.toString())
        binding.addressName.setText(addressDetailsData.address.toString())
        binding.phone.setText(addressDetailsData.phone.toString())
        binding.citiesSpinner.setSelection(getCitypinner(addressDetailsData.cityId!!))
        binding.block.setText(addressDetailsData.block.toString())
        binding.street.setText(addressDetailsData.street.toString())
        binding.avenue.setText(addressDetailsData.avenue.toString())
        binding.buildingNumber.setText(addressDetailsData.buildingNum.toString())
        binding.floor.setText(addressDetailsData.floorNum.toString())
        binding.apartment.setText(addressDetailsData.apartment.toString())
    }

    private fun setItemCitiesSpinner() {
        citiesAdapter = ArrayAdapter<City>(requireActivity(), R.layout.spinner_list, citiesList)
        binding.citiesSpinner.adapter = citiesAdapter!!
        binding.citiesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                cityId = citiesAdapter!!.getItem(binding.citiesSpinner.selectedItemPosition)!!.id

            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

    private fun getCitypinner(i: Int): Int {
        for (item: City in citiesList) {
            if (item.id == i)
                return citiesList.indexOf(item)
        }
        return 0
    }

    private fun deleteAddressDialog(id: Int) {
        val dialog = Dialog(requireContext())
        val binding: DeleteAddressDialogBinding = DataBindingUtil
            .inflate(
                LayoutInflater.from(requireContext()),
                R.layout.delete_address_dialog,
                null,
                false
            )
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(binding.root)
        dialog.window!!.setBackgroundDrawable(
            ResourcesCompat.getDrawable(
                resources,
                R.drawable.rounded_whtite_button,
                null
            )
        )


        binding.yesButton.setOnClickListener {
            viewModel.delete(id)
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

}