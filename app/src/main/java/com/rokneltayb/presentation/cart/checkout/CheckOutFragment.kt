package com.rokneltayb.presentation.cart.checkout

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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
import com.rokneltayb.data.model.cart.Data
import com.rokneltayb.data.model.cart.delivery.DeliveryTime
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.databinding.FragmentCartBinding
import com.rokneltayb.databinding.FragmentCheckOutBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.logoutNoPremission
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.cart.CartAdapter
import com.rokneltayb.presentation.cart.CartViewModel
import com.rokneltayb.presentation.more.order.OrderViewModel
import com.rokneltayb.presentation.more.profile.address.AddressViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CheckOutFragment : Fragment() {


    private val binding by lazy { FragmentCheckOutBinding.inflate(layoutInflater) }
    private val viewModel: CartViewModel by viewModels()
    private val addressViewModel: AddressViewModel by viewModels()
    private val orderViewModel: OrderViewModel by viewModels()
    private val checkOutAdapter: CheckOutAdapter by lazy { CheckOutAdapter() }
    private var addressId = 0
    private var deliveryTimeId = 0
    private val addressList = mutableListOf<AddressData>()
    private val timeList = mutableListOf<DeliveryTime>()
    var addressAdapter: ArrayAdapter<AddressData>? = null
    var deliveryTimeAdapter: ArrayAdapter<DeliveryTime>? = null

    private fun observeUIState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }
        lifecycleScope.launch {
            addressViewModel.uiState.flowWithLifecycle(lifecycle).collect(::addressUI)
        }
        lifecycleScope.launch {
            orderViewModel.uiState.flowWithLifecycle(lifecycle).collect(::orderUi)
        }

    }
    private fun orderUi(uiState: OrderViewModel.UiState) {
        when (uiState) {
            is OrderViewModel.UiState.Loading -> {
                showProgress()
            }

            is OrderViewModel.UiState.Error -> {
                if (uiState.errorData.status == 401)
                    logoutNoAuth(requireActivity())
                else
                    toastError(uiState.errorData.message)
                hideProgress()
            }

            is OrderViewModel.UiState.AddOrderSuccess ->{
                toast(uiState.data.message!!)
                findNavController().navigate(CheckOutFragmentDirections.actionCheckOutFragmentToHomeFragment())
            }


            else ->{}
        }
    }
    private fun updateUI(uiState: CartViewModel.UiState) {
        when (uiState) {
            is CartViewModel.UiState.Loading -> {
                showProgress()
            }

            is CartViewModel.UiState.GetCartSuccess -> {
                hideProgress()
                checkOutAdapter.submitList(uiState.data.data!!.cart)
                setData(uiState.data.data)
            }

            is CartViewModel.UiState.Error -> {
                if (uiState.errorData.status == 401)
                    logoutNoAuth(requireActivity())
                else
                    toastError(uiState.errorData.message)
                hideProgress()
            }

            is CartViewModel.UiState.GetDeliveryTimeSuccess ->{
                timeList.clear()
                timeList.add(0, DeliveryTime(id = 0, title = getString(R.string.select_your_delivery_time)))
                timeList.addAll(uiState.data.data.DeliveryTime)
                setItemDeliveryTime()
            }
            else -> {}
        }
    }



    private fun setData(data: Data) {
        binding.itemCountTextView.text = "("+data!!.cart!!.size.toString()+ getString(R.string.items)+")"
        binding.priceCartTextView.text = data.total.toString()
        if (data.tax == 0)
            binding.shippingFeeTextView.text = getString(R.string.free)
        else
            binding.shippingFeeTextView.text = data.tax.toString()

        binding.totalCartTextView.text = data.total_after_tax
        binding.totalTextView.text = data.total_after_tax

        binding.addNewAddressTextView.setOnClickListener {
            findNavController().navigate(CheckOutFragmentDirections.actionCheckOutFragmentToNewAddressFragment())
        }
    }

    private fun addressUI(uiState: AddressViewModel.UiState) {
        when (uiState) {
            is AddressViewModel.UiState.Loading -> {
                showProgress()
            }

            is AddressViewModel.UiState.AddressSuccess -> {
                addressList.clear()
                addressList.add(0, AddressData(id = 0, name = getString(R.string.select_your_location)))
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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        observeUIState()
        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text =
            getString(R.string.checkout)
        (requireActivity() as BaseActivity).binding!!.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        addressViewModel.address()
        viewModel.getCart()
        viewModel.deliveryTimes()

        binding.ordersRecyclerView.layoutAnimation = AnimationUtils.loadLayoutAnimation(requireContext(), R.anim.layout_animation)
        binding.ordersRecyclerView.adapter = checkOutAdapter


        binding.checkOutButton.setOnClickListener {
                if (addressId == 0)
                    toastError(getString(R.string.please_select_address))
                else if (deliveryTimeId == 0)
                    toastError(getString(R.string.please_select_delivery_time))
                else
                    orderViewModel.addOrder(addressId,deliveryTimeId)
        }

        return binding.root
    }


    private fun setItemCitiesSpinner() {
        addressAdapter = ArrayAdapter<AddressData>(requireActivity(), R.layout.spinner_list, addressList)
        binding.addressSpinner.adapter = addressAdapter!!
        binding.addressSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                        addressId = addressAdapter!!.getItem(binding.addressSpinner.selectedItemPosition)!!.id!!
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }

    private fun setItemDeliveryTime() {
        deliveryTimeAdapter = ArrayAdapter<DeliveryTime>(requireActivity(), R.layout.spinner_list, timeList)
        binding.timeSpinner.adapter = addressAdapter!!
        binding.timeSpinner.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    deliveryTimeId = deliveryTimeAdapter!!.getItem(binding.timeSpinner.selectedItemPosition)!!.id
                }

                override fun onNothingSelected(parent: AdapterView<*>?) {

                }
            }
    }
}