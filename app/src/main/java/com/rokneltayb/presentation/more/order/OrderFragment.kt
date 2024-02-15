package com.rokneltayb.presentation.more.order

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
import androidx.navigation.ui.navigateUp
import com.rokneltayb.BaseActivity
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentContactUsBinding
import com.rokneltayb.databinding.FragmentOrderBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.addBasicItemDecoration
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.cart.CartAdapter
import com.rokneltayb.presentation.more.contactus.ContactUsViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class OrderFragment : Fragment() {

    private val binding by lazy { FragmentOrderBinding.inflate(layoutInflater) }
    private val viewModel: OrderViewModel by viewModels()
    private lateinit var ordersAdapter: OrdersAdapter

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

            is OrderViewModel.UiState.GetAllOrdersSuccess -> {
                ordersAdapter.submitList(uiState.data.data)
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

        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text = getString(R.string.my_orders)
        (requireActivity() as BaseActivity).binding!!.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.myOrderRecyclerView.addBasicItemDecoration()

        ordersAdapter = OrdersAdapter{
            findNavController().navigate(OrderFragmentDirections.actionOrderFragmentToOrderDetailsFragment(it.id!!,it.type!!))
        }

        binding.myOrderRecyclerView.adapter = ordersAdapter

        viewModel.getOrders()


        return binding.root
    }


}