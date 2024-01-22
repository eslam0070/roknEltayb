package com.rokneltayb.presentation.cart.checkout

import androidx.lifecycle.ViewModelProvider
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
import com.rokneltayb.BaseActivity
import com.rokneltayb.R
import com.rokneltayb.data.model.address.AddressData
import com.rokneltayb.data.model.address.city.City
import com.rokneltayb.databinding.FragmentCartBinding
import com.rokneltayb.databinding.FragmentCheckOutBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.cart.CartViewModel
import com.rokneltayb.presentation.more.profile.address.AddressViewModel
import kotlinx.coroutines.launch

class CheckOutFragment : Fragment() {


    private val binding by lazy { FragmentCheckOutBinding.inflate(layoutInflater) }
    private val viewModel: CheckOutViewModel by viewModels()
    private val addressViewModel: AddressViewModel by viewModels()
    private var addressId = 0
    private val addressList = mutableListOf<AddressData>()
    var addressAdapter: ArrayAdapter<AddressData>? = null
    private fun observeUIState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }
        lifecycleScope.launch {
            addressViewModel.uiState.flowWithLifecycle(lifecycle).collect(::addressUI)
        }

    }

    private fun updateUI(uiState: CartViewModel.UiState) {
        when (uiState) {
            is CartViewModel.UiState.Loading -> {
                showProgress()
            }

            is CartViewModel.UiState.GetCartSuccess -> {
                hideProgress()
            }

            is CartViewModel.UiState.Error -> {
                if (uiState.errorData.status == 401)
                    logoutNoAuth(requireActivity())
                else
                    toastError(uiState.errorData.message)
                hideProgress()
            }

            else ->{}
        }
    }

    private fun addressUI(uiState: AddressViewModel.UiState) {
        when (uiState) {
            is AddressViewModel.UiState.Loading -> {
                showProgress()
            }

            is AddressViewModel.UiState.AddressSuccess -> {
                addressList.clear()
                addressList.add(0,AddressData(id = 0, name = getString(R.string.select_your_location)))
                addressList.addAll(uiState.data.data)
                setItemCitiesSpinner()
                hideProgress()
            }

            is AddressViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }

            else -> {}
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {

        observeUIState()
        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text = getString(R.string.checkout)
        (requireActivity() as BaseActivity).binding!!.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        addressViewModel.address()

        return binding.root
    }


    private fun setItemCitiesSpinner() {
        addressAdapter = ArrayAdapter<AddressData>(requireActivity(), R.layout.spinner_list, addressList)
        binding.addressSpinner.adapter = addressAdapter!!
        binding.addressSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if (addressId >= 1)
                    addressId = addressAdapter!!.getItem(binding.addressSpinner.selectedItemPosition)!!.id!!

            }
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
        }
    }

}