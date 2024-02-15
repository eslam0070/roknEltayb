package com.rokneltayb.presentation.more.order.details

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
import androidx.navigation.fragment.navArgs
import com.rokneltayb.BaseActivity
import com.rokneltayb.R
import com.rokneltayb.data.model.orders.details.OrderDetailsData
import com.rokneltayb.databinding.FragmentOrderBinding
import com.rokneltayb.databinding.FragmentOrderDetailsBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.addBasicItemDecoration
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.more.order.OrderFragmentDirections
import com.rokneltayb.presentation.more.order.OrderViewModel
import com.rokneltayb.presentation.more.order.OrdersAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderDetailsFragment : Fragment() {

    private val binding by lazy { FragmentOrderDetailsBinding.inflate(layoutInflater) }
    private val viewModel: OrderViewModel by viewModels()
    private val args:OrderDetailsFragmentArgs by navArgs()
    private lateinit var orderDetailsAdapter: OrderDetailsAdapter

    private fun observeUIState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }
    }

    private fun updateUI(uiState: OrderViewModel.UiState) {
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

            is OrderViewModel.UiState.GetOrderDetailsSuccess -> {
                orderDetailsAdapter.submitList(uiState.data.orderDetailsData!!.orderDetails)

                setOrderDetails(uiState.data.orderDetailsData)
                hideProgress()
            }

            else ->{}
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text = getString(R.string.my_orders)
        (requireActivity() as BaseActivity).binding!!.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        observeUIState()
        binding.myOrderRecyclerView.addBasicItemDecoration()

        orderDetailsAdapter = OrderDetailsAdapter()

        binding.myOrderRecyclerView.adapter = orderDetailsAdapter

        viewModel.getOrderDetails(args.id)
        return binding.root
    }

    private fun setOrderDetails(orderDetailsData: OrderDetailsData?) {
        binding.nameOrderTextView.text = getString(R.string.order_id) + orderDetailsData!!.orderNum
        binding.placeOnTextView.text = getString(R.string.place_on) + orderDetailsData.date



        if (args.status == "canceled")
            binding.trackYouOrderButton.visibility = View.GONE
        else if (args.status == "delivered"){
            binding.trackYouOrderButton.visibility = View.VISIBLE
            binding.trackYouOrderButton.text = getString(R.string.see_status_history)
            binding.trackYouOrderButton.setOnClickListener {
                findNavController().navigate(OrderDetailsFragmentDirections.actionOrderDetailsFragmentToTrackOrderFragment(orderDetailsData.type!!))
            }
        }
        else{
            binding.trackYouOrderButton.visibility = View.VISIBLE
            binding.trackYouOrderButton.setOnClickListener {
                findNavController().navigate(OrderDetailsFragmentDirections.actionOrderDetailsFragmentToTrackOrderFragment(orderDetailsData.type!!))
            }
        }



        binding.itemCountTextView.text = "("+orderDetailsData.orderDetails!!.size.toString()+" "+getString(R.string.items)+")"

        if (orderDetailsData.tax == 0) {
            binding.shippingFeeTextView.text = getString(R.string.free)
        } else
            binding.shippingFeeTextView.text = orderDetailsData.tax.toString()

        binding.priceCartTextView.text = orderDetailsData.totalPrice.toString()
        binding.totalCartTextView.text = orderDetailsData.totalPrice.toString()

        binding.addressShippingTextView.text = orderDetailsData.address!!.block +""+" , "+ getString(R.string.street) +" " +orderDetailsData.address.street +" "+
                orderDetailsData.address.avenue +" , "+ getString(R.string.building_number)+" "+ orderDetailsData.address.buildingNum +" , "
        getString(R.string.floor) + " " + orderDetailsData.address.floorNum +" , " + getString(R.string.apartment) +" "+ orderDetailsData.address.apartment +" , "+ orderDetailsData.address.address
    }

}