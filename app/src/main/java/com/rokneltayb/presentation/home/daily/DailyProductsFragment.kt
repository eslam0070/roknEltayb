package com.rokneltayb.presentation.home.daily

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.rokneltayb.BaseActivity
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentDailyProductsBinding
import com.rokneltayb.domain.util.EndlessRecyclerViewScrollListener
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.categories.products.ProductsViewModel
import com.rokneltayb.presentation.more.favorite.FavoritesViewModel
import com.rokneltayb.presentation.home.details.cart.CartViewModel
import com.rokneltayb.presentation.home.popular.PopularProductsAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class DailyProductsFragment : Fragment() {

    private val binding by lazy { FragmentDailyProductsBinding.inflate(layoutInflater) }
    private lateinit var dailyProductsAdapter: DailyProductsAdapter
    private val favoriteviewModel: FavoritesViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val viewModel: ProductsViewModel by viewModels()
    var page: Int = 1
    lateinit var scrollListener: EndlessRecyclerViewScrollListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()
        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text = getString(R.string.daily_best_sells)

        setProductsRecyclerView()
        return binding.root
    }
    private fun observeUIState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

        lifecycleScope.launch {
            cartViewModel.uiState.flowWithLifecycle(lifecycle).collect(::cartUI)
        }

        lifecycleScope.launch {
            favoriteviewModel.uiState.flowWithLifecycle(lifecycle).collect(::favoritesUI)
        }
    }
    private fun updateUI(uiState: ProductsViewModel.UiState) {
        when (uiState) {
            is ProductsViewModel.UiState.Loading -> {
                showProgress()
            }

            is ProductsViewModel.UiState.Success -> {
                dailyProductsAdapter.addList(uiState.data.data.products.data)
                hideProgress()
            }

            is ProductsViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }

            else -> {}
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
                favoriteviewModel.removeState()
            }

            is FavoritesViewModel.UiState.StoreFavoriteSuccess -> {
                favoriteviewModel.removeState()
                hideProgress()
            }

            is FavoritesViewModel.UiState.DeleteFavoriteSuccess -> {
                favoriteviewModel.removeState()
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
                cartViewModel.removeState()
            }

            is CartViewModel.UiState.AddToCartSuccess -> {
                toast(uiState.data.message.toString())
                hideProgress()
                cartViewModel.removeState()
            }

            is CartViewModel.UiState.Idle -> hideProgress()

            else ->{}
        }
    }

    private fun setProductsRecyclerView() {
        val staggeredGridLayoutManager =
            StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.dailyProductsRecyclerView.layoutManager = staggeredGridLayoutManager

        dailyProductsAdapter = DailyProductsAdapter({
            findNavController().navigate(DailyProductsFragmentDirections.actionDailyProductsFragmentToProductDetailsFragment(it.id!!))
        },{position,product,count->
            cartViewModel.run { storeCart(product.id.toString(), product.shapes!![0].id.toString(),count.toString()) }
        },{ position,product ->
            cartViewModel.deleteCart(product.id.toString(), product.shapes[0].id.toString())
        },{total,position,product ->
            cartViewModel.run { storeCart(product.id.toString(), product.shapes!![0].id.toString(),total.toString()) }

        },{total,position,product ->
            cartViewModel.storeCart(product.id.toString(), product.shapes[0].id.toString(),total.toString())
        },{ position,product,isFavorite->
                if (product.is_favorite == 0)
                    favoriteviewModel.storeFavorite(product.id)
                else
                    favoriteviewModel.deleteFavorite(product.id)
        })


        binding.dailyProductsRecyclerView.adapter = dailyProductsAdapter


        scrollListener = object : EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
            override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
                var clickCount = page
                clickCount++
                viewModel.products(clickCount,"", "", "", "daily")
            }
        }

        binding.dailyProductsRecyclerView.addOnScrollListener(scrollListener)

    }

    override fun onResume() {
        super.onResume()
        viewModel.products(page,"","","","daily")
    }
}