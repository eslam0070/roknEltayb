package com.rokneltayb.presentation.home.popular

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.rokneltayb.R
import com.rokneltayb.data.model.home.home.PopularProduct
import com.rokneltayb.databinding.FragmentHomeBinding
import com.rokneltayb.databinding.FragmentPopularProductsBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.categories.products.ProductsFragmentDirections
import com.rokneltayb.presentation.favorite.FavoritesViewModel
import com.rokneltayb.presentation.home.adapters.HomeProductsAdapter
import com.rokneltayb.presentation.home.details.cart.CartViewModel

class PopularProductsFragment : Fragment() {


    private val binding by lazy { FragmentPopularProductsBinding.inflate(layoutInflater) }
    private val args:PopularProductsFragmentArgs by navArgs()
    private lateinit var homeProductsAdapter: HomeProductsAdapter
    private val favoriteviewModel: FavoritesViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {


        setProductsRecyclerView()
        return binding.root
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

                hideProgress()
            }

            is FavoritesViewModel.UiState.DeleteFavoriteSuccess -> {
                
                hideProgress()
            }

            else ->{}
        }
    }

    private fun cartUI(uiState: CartViewModel.UiState) {
        when (uiState) {
            is CartViewModel.UiState.Loading -> {
                showProgress()
            }

            is CartViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }

            is CartViewModel.UiState.AddToCartSuccess -> {
                toast(uiState.data.message.toString())
                hideProgress()
            }

            else ->{}
        }
    }
   private fun setProductsRecyclerView() {
        homeProductsAdapter.submitList(args.popularProducts as MutableList<PopularProduct>)
        homeProductsAdapter = HomeProductsAdapter({
            findNavController().navigate(ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment(it.id!!))
        },{position,product,count->
            cartViewModel.storeCart(product.id.toString(), product.shapes!![position]!!.id.toString(),count.toString())
        },{ position,product ->
            cartViewModel.deleteCart(product.id.toString(),product.shapes!![position]!!.id.toString())
        },{total,position,product ->
            cartViewModel.storeCart(product.id.toString(), product.shapes!![position]!!.id.toString(),total.toString())

        },{total,position,product ->
            cartViewModel.storeCart(product.id.toString(), product.shapes!![position]!!.id.toString(),total.toString())
        },{ position,product,isFavorite->
            if (product.isFavorite == 0)
                favoriteviewModel.storeFavorite(product.id!!)
            else
                favoriteviewModel.deleteFavorite(product.id!!)

        })


        binding.popularProductsRecyclerView.adapter = homeProductsAdapter
    }

}