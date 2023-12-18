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
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.categories.CategoriesAdapter
import com.rokneltayb.presentation.categories.CategoriesFragmentDirections
import com.rokneltayb.presentation.home.HomeViewModel
import com.rokneltayb.presentation.home.adapters.HomeCategoriesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class FavoritesFragment : Fragment() {


    private val binding by lazy { FragmentFavoritesBinding.inflate(layoutInflater) }
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var favoritesAdapter: FavoritesAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()

        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text = getString(R.string.my_wishlist)

        favoritesAdapter = FavoritesAdapter(
            { findNavController().navigate(FavoritesFragmentDirections.actionFavoritesFragmentToProductDetailsFragment(it))},
            { viewModel.storeFavorite(it) },
            { viewModel.deleteFavorite(it) })

        binding.favoritesRecyclerView.addBasicItemDecoration()
        binding.favoritesRecyclerView.adapter = favoritesAdapter

        viewModel.favorites()
        return binding.root
    }

    private fun observeUIState() =
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    private fun updateUI(uiState: FavoritesViewModel.UiState) {
        when (uiState) {
            is FavoritesViewModel.UiState.Loading -> {
                showProgress()
            }

            is FavoritesViewModel.UiState.Error -> {
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