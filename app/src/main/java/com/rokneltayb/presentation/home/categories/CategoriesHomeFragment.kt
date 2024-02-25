package com.rokneltayb.presentation.home.categories

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.rokneltayb.R
import com.rokneltayb.data.model.categories.Category
import com.rokneltayb.databinding.FragmentCategoriesBinding
import com.rokneltayb.databinding.FragmentHomeBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.home.HomeFragmentDirections
import com.rokneltayb.presentation.home.HomeViewModel
import com.rokneltayb.presentation.home.adapters.HomeCategoriesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class CategoriesHomeFragment : Fragment() {

    private val binding by lazy { FragmentCategoriesBinding.inflate(layoutInflater) }

    private val viewModel: CategoriesHomeViewModel by viewModels()
    private lateinit var categoriesAdapter: CategoriesHomeAdapter

    private fun observeUIState() =
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        observeUIState()
        categoriesAdapter = CategoriesHomeAdapter {
            findNavController().navigate(CategoriesHomeFragmentDirections.actionCategoriesHomeFragmentToProductsFragment(it.id!!,it.title!!))
        }

        binding.categoriesRecyclerView.adapter = categoriesAdapter
        viewModel.categories()
        return binding.root
    }

    private fun updateUI(uiState: CategoriesHomeViewModel.UiState) {
        when (uiState) {
            is CategoriesHomeViewModel.UiState.Loading -> {
                showProgress()
            }

            is CategoriesHomeViewModel.UiState.Success -> {
                categoriesAdapter.submitList(uiState.data.data!!.categories)
                hideProgress()
            }

            is CategoriesHomeViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }
        }
    }
}