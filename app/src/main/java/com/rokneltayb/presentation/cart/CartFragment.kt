package com.rokneltayb.presentation.cart

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
import com.rokneltayb.BaseActivity
import com.rokneltayb.R
import com.rokneltayb.data.model.cart.coupon.Coupon
import com.rokneltayb.databinding.FragmentCartBinding
import com.rokneltayb.databinding.FragmentProfileBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.addBasicItemDecoration
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.cart.checkout.CheckOutAdapter
import com.rokneltayb.presentation.favorite.FavoritesAdapter
import com.rokneltayb.presentation.favorite.FavoritesViewModel
import com.rokneltayb.presentation.home.adapters.HomeCategoriesAdapter
import com.rokneltayb.presentation.more.profile.ProfileViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CartFragment : Fragment() {


    private val binding by lazy { FragmentCartBinding.inflate(layoutInflater) }
    private val viewModel: CartViewModel by viewModels()
    private lateinit var cartAdapter: CartAdapter
    private lateinit var couponAdapter: DiscountAdapter
    private val favoriteviewModel: FavoritesViewModel by viewModels()

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
                binding.itemCountTextView.text = "("+uiState.data.data!!.cart!!.size.toString()+ getString(R.string.items)+")"
                binding.priceCartTextView.text = uiState.data.data.total.toString()
                if (uiState.data.data.tax == 0)
                    binding.shippingFeeTextView.text = getString(R.string.free)
                else
                    binding.shippingFeeTextView.text = uiState.data.data.tax.toString()

                binding.totalCartTextView.text = uiState.data.data.totalAfterTax.toString()
                binding.totalTextView.text = uiState.data.data.totalAfterTax.toString()
                cartAdapter.submitList(uiState.data.data.cart)
                if (uiState.data.data.coupon != null){
                    val list = mutableListOf<Coupon>()
                    list.add(0,uiState.data.data.coupon)
                    couponAdapter.submitList(list)
                    binding.layoutDiscount.visibility = View.GONE
                }else{
                    binding.discountCodeRecyclerView.visibility = View.GONE
                    binding.layoutDiscount.visibility = View.VISIBLE
                }
                hideProgress()
            }

            is CartViewModel.UiState.Error -> {
                if (uiState.errorData.status == 401)
                    logoutNoAuth(requireActivity())
                else
                    toastError(uiState.errorData.message)
                hideProgress()
            }

            is CartViewModel.UiState.AddOrDeleteCouponCartSuccess ->{
                toast(uiState.data.message.toString())
                viewModel.getCart()
            }
           else ->{}
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()
        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text = getString(R.string.cart)


        binding.cartRecyclerView.addBasicItemDecoration()
        binding.discountCodeRecyclerView.addBasicItemDecoration()

        cartAdapter = CartAdapter(viewModel) { item, cart ->
            if (item == 1) {
                viewModel.deleteCard(cart.productId.toString(), cart.shapeId.toString())
            } else {
                favoriteviewModel.storeFavorite(cart.productId!!)
            }
        }
        couponAdapter = DiscountAdapter { viewModel.deleteCouponCart() }
        binding.cartRecyclerView.adapter = cartAdapter
        binding.discountCodeRecyclerView.adapter = couponAdapter
        viewModel.getCart()

        binding.applyButton.setOnClickListener {
            viewModel.applyCouponCart(binding.discountCodeEditText.text.toString())
        }
        binding.checkOutButton.setOnClickListener {
            findNavController().navigate(CartFragmentDirections.actionCartFragmentToCheckOutFragment())
        }

        return binding.root
    }
}