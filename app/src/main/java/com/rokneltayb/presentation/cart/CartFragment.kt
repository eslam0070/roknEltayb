package com.rokneltayb.presentation.cart

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
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.databinding.FragmentCartBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.addBasicItemDecoration
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.logoutNoPremission
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.more.favorite.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {


    private val binding by lazy { FragmentCartBinding.inflate(layoutInflater) }
    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private val favoriteviewModel: FavoritesViewModel by viewModels()
    private val sharedPref by lazy { SharedPreferencesImpl(requireContext()) }

    private fun observeUIState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }
        lifecycleScope.launch {
            favoriteviewModel.uiState.flowWithLifecycle(lifecycle).collect(::favoritesUI)
        }

    }
    private fun favoritesUI(uiState: FavoritesViewModel.UiState) {
        when (uiState) {
            is FavoritesViewModel.UiState.Loading -> {
                showProgress()
            }

            is FavoritesViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }

            is FavoritesViewModel.UiState.StoreFavoriteSuccess -> {
                viewModel.getCart()
                hideProgress()
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
                getItemCart(uiState.data.data)
                cartAdapter.submitList(uiState.data.data!!.cart)

                if (uiState.data.data.coupon != null){
                    binding.applyCardView.visibility = View.VISIBLE
                    binding.nameDiscountTextView.text = uiState.data.data.coupon.name
                    binding.deleteDiscountImageView.setOnClickListener {
                        binding.applyCardView.visibility = View.GONE
                        viewModel.deleteCouponCart()
                    }
                    binding.layoutDiscount.visibility = View.GONE
                }else{
                    binding.applyCardView.visibility = View.GONE
                    binding.layoutDiscount.visibility = View.VISIBLE

                }
                viewModel.removeState()

                hideProgress()
            }

            is CartViewModel.UiState.Error -> {
                when (uiState.errorData.status) {
                    401 -> logoutNoAuth(requireActivity())
                    408 -> toastError(uiState.errorData.message)
                    else -> toastError(uiState.errorData.message)
                }
                hideProgress()
                viewModel.removeState()
            }

            is CartViewModel.UiState.AddOrDeleteCouponCartSuccess ->{
                toast(uiState.data.message.toString())
                viewModel.getCart()
                viewModel.removeState()

                hideProgress()
            }

            is CartViewModel.UiState.DeleteCartSuccess ->{
                viewModel.getCart()
                viewModel.removeState()
                hideProgress()
            }
           else ->{}
        }
    }

    private fun getItemCart(data: com.rokneltayb.data.model.cart.Data) {
        binding.itemCountTextView.text = "("+data!!.cart!!.size.toString()+ getString(R.string.items)+")"
        binding.priceCartTextView.text = data.total.toString()

        if (data.tax == 0)
            binding.shippingFeeTextView.text = getString(R.string.free)
        else
            binding.shippingFeeTextView.text = data.tax.toString()

        binding.totalCartTextView.text = data.total_after_tax.toString()
        binding.totalTextView.text = data.total_after_tax.toString()


    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()
        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text = getString(R.string.cart)


        binding.cartRecyclerView.addBasicItemDecoration()

        cartAdapter = CartAdapter(viewModel) { item, cart ->
            if (item == 1) {
                viewModel.deleteCard(cart.product_id.toString(), cart.shape_id.toString())
            } else {
                if (SharedPreferencesImpl(binding.root.context).getRememberMe()){
                    favoriteviewModel.storeFavorite(cart.product_id!!)
                }else
                    binding.root.context.toast(binding.root.context.getString(R.string.you_should_login))
            }
        }
        binding.cartRecyclerView.adapter = cartAdapter
        viewModel.getCart()


        binding.applyButton.setOnClickListener {
            viewModel.applyCouponCart(binding.discountCodeEditText.text.toString())
        }
        binding.checkOutButton.setOnClickListener {
            if (sharedPref.getRememberMe()){
                findNavController().navigate(CartFragmentDirections.actionCartFragmentToCheckOutFragment())
            }else
                logoutNoPremission(requireActivity())

        }

        return binding.root
    }
}