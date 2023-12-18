package com.rokneltayb.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rokneltayb.R
import com.rokneltayb.data.model.home.home.Category
import com.rokneltayb.data.model.home.home.PopularProduct
import com.rokneltayb.data.model.home.home.Slider
import com.rokneltayb.databinding.FragmentHomeBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.domain.util.ui.MarginItemDecoration
import com.rokneltayb.presentation.categories.products.ProductsAdapter
import com.rokneltayb.presentation.categories.products.ProductsFragmentDirections
import com.rokneltayb.presentation.favorite.FavoritesViewModel
import com.rokneltayb.presentation.home.adapters.AdvSliderAdapter
import com.rokneltayb.presentation.home.adapters.HomeCategoriesAdapter
import com.rokneltayb.presentation.home.adapters.HomeDailyBestSellsProductsAdapter
import com.rokneltayb.presentation.home.adapters.HomeProductsAdapter
import com.rokneltayb.presentation.home.details.cart.CartViewModel
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }
    private val viewModel: HomeViewModel by viewModels()
    private var adapter: AdvSliderAdapter? = null
    private lateinit var homeCategoriesAdapter: HomeCategoriesAdapter
    private lateinit var homeProductsAdapter: HomeProductsAdapter
    private lateinit var homeDailyBestSellsProductsAdapter: HomeDailyBestSellsProductsAdapter
    private val favoriteviewModel: FavoritesViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()

    private fun observeUIState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

        lifecycleScope.launch {
            favoriteviewModel.uiState.flowWithLifecycle(lifecycle).collect(::favoritesUI)
        }

        lifecycleScope.launch {
            cartViewModel.uiState.flowWithLifecycle(lifecycle).collect(::cartUI)
        }
    }

    private fun updateUI(uiState: HomeViewModel.UiState) {
        when (uiState) {
            is HomeViewModel.UiState.Loading -> {
                showProgress()
            }

            is HomeViewModel.UiState.Success -> {
                setSliderImage(uiState.data.data!!.slider!!)
                homeCategoriesAdapter.submitList(uiState.data.data.categories)
                homeProductsAdapter.submitList(uiState.data.data.popularProducts)
                homeDailyBestSellsProductsAdapter.submitList(uiState.data.data.dailyProducts)

                hideProgress()
            }

            is HomeViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }

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
                viewModel.home()
                hideProgress()
            }

            is FavoritesViewModel.UiState.DeleteFavoriteSuccess -> {
                viewModel.home()
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
    private fun setCategoriesRecyclerView() {
        homeCategoriesAdapter = HomeCategoriesAdapter {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(it.id!!)) }
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.categoriesRecyclerView.adapter = homeCategoriesAdapter
    }

    private fun setProductsRecyclerView() {
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


    private fun setDailySellsProductsRecyclerView() {
        homeDailyBestSellsProductsAdapter = HomeDailyBestSellsProductsAdapter {}
        binding.dailyBestProductsRecyclerView.adapter = homeDailyBestSellsProductsAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()

        setCategoriesRecyclerView()
        setDailySellsProductsRecyclerView()
        setProductsRecyclerView()
        viewModel.home()
        return binding.root
    }


    private fun setSliderImage(slider: List<Slider?> = ArrayList()) {
        adapter = AdvSliderAdapter(slider)
        binding.imageSlider.setSliderAdapter(adapter!!)
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.DROP)
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding.imageSlider.currentPagePosition = 0
    }



}