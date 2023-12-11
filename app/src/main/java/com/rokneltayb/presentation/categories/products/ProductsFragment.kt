package com.rokneltayb.presentation.categories.products

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.rokneltayb.data.model.products.Product
import com.rokneltayb.databinding.FragmentProductsBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.categories.CategoriesViewModel
import com.rokneltayb.presentation.home.details.ProductDetailsFragmentArgs
import kotlinx.coroutines.launch

class ProductsFragment : Fragment() {

    private val binding by lazy { FragmentProductsBinding.inflate(layoutInflater) }
    private val viewModel: ProductsViewModel by viewModels()
    private lateinit var productsAdapter:ProductsAdapter
    private val args:ProductDetailsFragmentArgs by navArgs()

    //high_price | low_price | new
    private val sort = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()
        viewModel.products(args.productId,"","")
        return binding.root
    }

    private fun observeUIState() =
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    private fun updateUI(uiState: ProductsViewModel.UiState) {
        when (uiState) {
            is ProductsViewModel.UiState.Loading -> {
                showProgress()
            }

            is ProductsViewModel.UiState.Success -> {
                setCategoriesRecyclerView(uiState.data.data!!.products)
                hideProgress()
            }

            is ProductsViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }
        }
    }

    private fun setCategoriesRecyclerView(categories: List<Product?>?) {
        productsAdapter = ProductsAdapter {}
        productsAdapter.submitList(categories)
        binding.productsRecyclerView.adapter = productsAdapter
    }
}