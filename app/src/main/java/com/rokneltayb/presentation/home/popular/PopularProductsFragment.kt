package com.rokneltayb.presentation.home.popular

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
import com.rokneltayb.data.model.products.DataXX
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.databinding.FragmentPopularProductsBinding
import com.rokneltayb.domain.util.EndlessRecyclerViewScrollListener
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.addBasicItemDecoration
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.categories.products.ProductsAdapter
import com.rokneltayb.presentation.categories.products.ProductsFragmentDirections
import com.rokneltayb.presentation.categories.products.ProductsViewModel
import com.rokneltayb.presentation.more.favorite.FavoritesViewModel
import com.rokneltayb.presentation.home.details.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PopularProductsFragment : Fragment() {


    private val binding by lazy { FragmentPopularProductsBinding.inflate(layoutInflater) }
    private lateinit var popularProductsAdapter: PopularProductsAdapter
    private val favoriteviewModel: FavoritesViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val viewModel: ProductsViewModel by viewModels()
    var page: Int = 1
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        observeUIState()
        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text = getString(R.string.popular_produts)


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
                popularProductsAdapter!!.addList(uiState.data.data.products.data)
                hideProgress()
            }

            is ProductsViewModel.UiState.Error -> {
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

    lateinit var scrollListener: EndlessRecyclerViewScrollListener


    private fun setProductsRecyclerView() {
       val staggeredGridLayoutManager =
           StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
       binding.popularProductsRecyclerView.layoutManager = staggeredGridLayoutManager

       popularProductsAdapter = PopularProductsAdapter({
            findNavController().navigate(PopularProductsFragmentDirections.actionPopularProductsFragmentToProductDetailsFragment(it.id!!))
        },{position,product,count->
            cartViewModel.storeCart(product.id.toString(), product.shapes!![0]!!.id.toString(),count.toString())
        },{ position,product ->
            cartViewModel.deleteCart(product.id.toString(),product.shapes!![0]!!.id.toString())
        },{total,position,product ->
            cartViewModel.storeCart(product.id.toString(), product.shapes!![0]!!.id.toString(),total.toString())

        },{total,position,product ->
            cartViewModel.storeCart(product.id.toString(), product.shapes!![0]!!.id.toString(),total.toString())
        },{ position,product,isFavorite->
               if (product.is_favorite == 0)
                   favoriteviewModel.storeFavorite(product.id!!)
               else
                   favoriteviewModel.deleteFavorite(product.id!!)

        })

       binding.popularProductsRecyclerView.adapter = popularProductsAdapter


       scrollListener = object : EndlessRecyclerViewScrollListener(staggeredGridLayoutManager) {
           override fun onLoadMore(page: Int, totalItemsCount: Int, view: RecyclerView) {
               var clickCount = page
               clickCount++
               viewModel.products(clickCount,"", "", "", "popular")
           }
       }

       binding.popularProductsRecyclerView.addOnScrollListener(scrollListener)

    }

    override fun onResume() {
        super.onResume()
        viewModel.products(page,"","","","popular")
    }
}