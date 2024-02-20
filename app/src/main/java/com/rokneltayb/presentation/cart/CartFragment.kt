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
import com.rokneltayb.data.model.cart.Data
import com.rokneltayb.data.model.cart.coupon.Coupon
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.databinding.FragmentCartBinding
import com.rokneltayb.databinding.FragmentProfileBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.addBasicItemDecoration
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.logoutNoPremission
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

                if (uiState.data.data!!.coupon != null){
                    binding.applyCardView.visibility = View.VISIBLE
                    binding.nameDiscountTextView.text = uiState.data.data.coupon!!.name
                    binding.deleteDiscountImageView.setOnClickListener {
                        binding.applyCardView.visibility = View.GONE
                        viewModel.deleteCouponCart()
                    }
                    binding.layoutDiscount.visibility = View.GONE
                }else{
                    binding.applyCardView.visibility = View.GONE
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
                viewModel.removeState()
            }

            is CartViewModel.UiState.AddOrDeleteCouponCartSuccess ->{
                toast(uiState.data.message.toString())
                viewModel.getCart()
                viewModel.removeState()
            }
           else ->{}
        }
    }

    private fun getItemCart(data: Data?) {
        binding.itemCountTextView.text = "("+data!!.cart!!.size.toString()+ getString(R.string.items)+")"
        binding.priceCartTextView.text = data.total.toString()

        if (data.tax == 0)
            binding.shippingFeeTextView.text = getString(R.string.free)
        else
            binding.shippingFeeTextView.text = data.tax.toString()

        binding.totalCartTextView.text = data.totalAfterTax.toString()
        binding.totalTextView.text = data.totalAfterTax.toString()
        cartAdapter.submitList(data.cart)


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
                viewModel.deleteCard(cart.productId.toString(), cart.shapeId.toString())
            } else {
                if (SharedPreferencesImpl(binding.root.context).getRememberMe()){
                    favoriteviewModel.storeFavorite(cart.productId!!)
                }else
                    binding.root.context.toast(binding.root.context.getString(R.string.you_should_login))
            }
        }
        binding.cartRecyclerView.adapter = cartAdapter
        viewModel.getCart()

        if (!sharedPref.getRememberMe()){
            binding.discountCodeEditText.isEnabled = false
            binding.applyButton.isClickable = false
        }
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