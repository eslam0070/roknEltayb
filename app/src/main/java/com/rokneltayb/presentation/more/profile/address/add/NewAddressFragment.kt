package com.rokneltayb.presentation.more.profile.address.add

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rokneltayb.R
import com.rokneltayb.data.model.address.city.City
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
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()

        viewModel.cities()
        binding.addAddressButton.setOnClickListener {
            if (cityId == 0)
                toastError(getString(R.string.please_select_a_city))
            else if (binding.addressName.text.toString().isEmpty())
                toastError(getString(R.string.please_write_its_unique_tag))
            else if (binding.name.text.toString().isEmpty())
                toastError("Please write title address")
            else
                viewModel.add(binding.name.text.toString(),
                    binding.phone.text.toString(),
                    cityId.toString(),binding.block.text.toString(),binding.street.text.toString(),binding.avenue.text.toString(),
                    binding.buildingNumber.text.toString(),binding.floor.text.toString(),binding.apartment.text.toString(),binding.addressName.text.toString())
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
            }

            is AddressViewModel.UiState.AddressSuccess ->{
                toast(uiState.data.message)
                findNavController().navigate(NewAddressFragmentDirections.actionNewAddressFragmentToProfileFragment())
                hideProgress()
            }

            is AddressViewModel.UiState.CitiesSuccess ->{
                citiesList.clear()
                citiesList.addAll(uiState.data.data.cities)
                setItemCitiesSpinner()
            }

            is AddressViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }

            else -> {}
        }
    }

    private fun setItemCitiesSpinner() {
        citiesAdapter = ArrayAdapter<City>(requireActivity(), R.layout.spinner_list, citiesList)
        binding.citiesSpinner.adapter = citiesAdapter!!
        binding.citiesSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                cityId = citiesAdapter!!.getItem(binding.citiesSpinner.selectedItemPosition)!!.id

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }
    }

}