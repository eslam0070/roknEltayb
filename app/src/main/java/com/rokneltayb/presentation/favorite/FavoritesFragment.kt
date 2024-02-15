package com.rokneltayb.presentation.favorite

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
import com.rokneltayb.databinding.FragmentFavoritesBinding
import com.rokneltayb.databinding.FragmentHomeBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.addBasicItemDecoration
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.cart.CartViewModel
import com.rokneltayb.presentation.categories.CategoriesAdapter
import com.rokneltayb.presentation.categories.CategoriesFragmentDirections
import com.rokneltayb.presentation.home.HomeViewModel
import com.rokneltayb.presentation.home.adapters.HomeCategoriesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import com.rokneltayb.domain.util.toast

@AndroidEntryPoint
class FavoritesFragment : Fragment() {


    private val binding by lazy { FragmentFavoritesBinding.inflate(layoutInflater) }
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var favoritesAdapter: FavoritesAdapter
    private val cardCiewModel: CartViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()

        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text = getString(R.string.my_wishlist)

        favoritesAdapter = FavoritesAdapter(
            { findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToProductDetailsFragment(it))},
            { /*cardCiewModel.addCard(it.id,it.shapes) */},
            { viewModel.deleteFavorite(it) })

        binding.favoritesRecyclerView.addBasicItemDecoration(R.dimen.item_decoration_medium_margin)
        binding.favoritesRecyclerView.adapter = favoritesAdapter

        viewModel.favorites()
        return binding.root
    }

    private fun observeUIState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

        lifecycleScope.launch {
            cardCiewModel.uiState.flowWithLifecycle(lifecycle).collect(::cartUI)
        }
    }

    private fun cartUI(uiState: CartViewModel.UiState) {
        when (uiState) {
            is CartViewModel.UiState.Loading -> {
                showProgress()
            }

            is CartViewModel.UiState.AddCartSuccess -> {
                toast(uiState.data.message!!)
                viewModel.favorites()
                hideProgress()
            }

            is CartViewModel.UiState.Error -> {
                if (uiState.errorData.status == 401)
                    logoutNoAuth(requireActivity())
                else
                    toastError(uiState.errorData.message)
                hideProgress()
            }

            else ->{}
        }
    }
    private fun updateUI(uiState: FavoritesViewModel.UiState) {
        when (uiState) {
            is FavoritesViewModel.UiState.Loading -> {
                showProgress()
            }

            is FavoritesViewModel.UiState.Error -> {
                if (uiState.errorData.status == 401)
                    logoutNoAuth(requireActivity())
                else
                    toastError(uiState.errorData.message)
                hideProgress()
            }

            is FavoritesViewModel.UiState.FavoriteSuccess -> {
                favoritesAdapter.submitList(uiState.data.data)
                hideProgress()
            }
            is FavoritesViewModel.UiState.StoreFavoriteSuccess -> {
                hideProgress()
            }

            is FavoritesViewModel.UiState.DeleteFavoriteSuccess -> {
                viewModel.favorites()
                hideProgress()
            }
        }
    }

}


