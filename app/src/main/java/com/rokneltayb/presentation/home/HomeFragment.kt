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
import androidx.recyclerview.widget.LinearLayoutManager
import com.rokneltayb.data.model.home.home.Slider
import com.rokneltayb.databinding.FragmentHomeBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.home.adapters.AdvSliderAdapter
import com.rokneltayb.presentation.home.adapters.HomeCategoriesAdapter
import com.rokneltayb.presentation.home.adapters.HomeDailyBestSellsProductsAdapter
import com.rokneltayb.presentation.home.adapters.HomeProductsAdapter
import com.rokneltayb.presentation.home.details.cart.CartViewModel
import com.rokneltayb.presentation.more.favorite.FavoritesViewModel
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
                binding.layoutLoading.visibility = View.VISIBLE
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
            }

            is FavoritesViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                favoriteviewModel.removeState()
            }

            is FavoritesViewModel.UiState.StoreFavoriteSuccess -> {
                toast("تم اضافة المنتج الى المفضلة")
                favoriteviewModel.removeState()
            }

            is FavoritesViewModel.UiState.DeleteFavoriteSuccess -> {
                toast("تم حذف المنتج من المفضلة")
                favoriteviewModel.removeState()
            }

            else ->{}
        }
    }

    private fun cartUI(uiState: CartViewModel.UiState) {
        when (uiState) {
            is CartViewModel.UiState.Loading -> {
            }

            is CartViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                cartViewModel.removeState()
            }

            is CartViewModel.UiState.AddToCartSuccess -> {
                toast(uiState.data.message.toString())
                cartViewModel.removeState()
            }

            is CartViewModel.UiState.Idle ->{
            }

            else ->{}
        }
    }
    private fun setCategoriesRecyclerView() {
        homeCategoriesAdapter = HomeCategoriesAdapter {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToCategoriesHomeFragment()) }
        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.categoriesRecyclerView.adapter = homeCategoriesAdapter
    }

    private fun setProductsRecyclerView() {
        homeProductsAdapter = HomeProductsAdapter({
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(it.id!!))
        },{position,product,count->
            cartViewModel.storeCart(product.id.toString(), product.shapes!![0]!!.id.toString(),count.toString())
        },{ position,product ->
            cartViewModel.deleteCart(product.id.toString(),product.shapes!![0]!!.id.toString())
        },{total,position,product ->
            cartViewModel.storeCart(product.id.toString(), product.shapes!![0]!!.id.toString(),total.toString())

        },{total,position,product ->
            cartViewModel.storeCart(product.id.toString(), product.shapes!![0]!!.id.toString(),total.toString())
        },{ position,product,isFavorite->
            if (product.isFavorite == 0)
                favoriteviewModel.storeFavorite(product.id!!)
            else
                favoriteviewModel.deleteFavorite(product.id!!)
        })
        binding.popularProductsRecyclerView.adapter = homeProductsAdapter
    }


    private fun setDailySellsProductsRecyclerView() {
        homeDailyBestSellsProductsAdapter = HomeDailyBestSellsProductsAdapter ({
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(it.id!!))
        },{position,product,count->
            cartViewModel.storeCart(product.id.toString(), product.shapes!![0]!!.id.toString(),count.toString())
        },{ position,product ->
            cartViewModel.deleteCart(product.id.toString(),product.shapes!![0]!!.id.toString())
        },{total,position,product ->
            cartViewModel.storeCart(product.id.toString(), product.shapes!![0]!!.id.toString(),total.toString())

        },{total,position,product ->
            cartViewModel.storeCart(product.id.toString(), product.shapes!![0]!!.id.toString(),total.toString())
        },{ position,product,isFavorite->
            if (product.isFavorite == 0)
                favoriteviewModel.storeFavorite(product.id!!)
            else
                favoriteviewModel.deleteFavorite(product.id!!)


        })
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

        binding.seeAllPopularProductsTextView.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToPopularProductsFragment())
        }

        binding.seeAllDailyProductsTextView.setOnClickListener {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToDailyProductsFragment())
        }
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