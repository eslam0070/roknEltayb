package com.rokneltayb.presentation.categories.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import androidx.core.widget.doAfterTextChanged
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rokneltayb.BaseActivity
import com.rokneltayb.R
import com.rokneltayb.data.sharedPref.SharedPreferencesImpl
import com.rokneltayb.databinding.BottomSheetSortBinding
import com.rokneltayb.databinding.FragmentProductsBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.addBasicItemDecoration
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.cart.CartViewModel
import com.rokneltayb.presentation.more.favorite.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val binding by lazy { FragmentProductsBinding.inflate(layoutInflater) }
    private val viewModel: ProductsViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val favoriteviewModel: FavoritesViewModel by viewModels()

    private lateinit var productsAdapter: ProductsAdapter
    private val args: ProductsFragmentArgs by navArgs()
    private var sort = ""
    private val sharedPref by lazy { SharedPreferencesImpl(requireContext()) }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceSate: Bundle?
    ): View {
        observeUIState()

        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text = args.name
        viewModel.products(args.id.toString(), "", "", "")
        binding.searchEditText.doAfterTextChanged {
            if (it!!.length > 3) {
                viewModel.products(args.id.toString(), sort, it.toString(), "")
            } else
                viewModel.products(args.id.toString(), "", "", "")
        }

        binding.productsRecyclerView.addBasicItemDecoration(R.dimen.item_decoration_medium_margin)
        setCategoriesRecyclerView()

        binding.sortButton.setOnClickListener {
            showBottomSheetDialogSort()
        }

        return binding.root
    }


    private fun showBottomSheetDialogSort() {
        val bottomSheetDialog = BottomSheetDialog(requireContext(), R.style.BottomSheetDialogTheme)
        val binding: BottomSheetSortBinding = DataBindingUtil.inflate(
            LayoutInflater.from(requireContext()),
            R.layout.bottom_sheet_sort,
            null,
            false
        )

        bottomSheetDialog.setContentView(binding.root)
        binding.higtRadioButton.setOnClickListener{
            sort = "high_price"
            viewModel.products(args.id.toString(), sort, "", "")
            bottomSheetDialog.dismiss()
        }

        binding.lowRadioButton.setOnClickListener {
            sort = "low_price"
            viewModel.products(args.id.toString(), sort, "", "")
            bottomSheetDialog.dismiss()
        }

        binding.newRadioButton.setOnClickListener {
            sort = "new"
            viewModel.products(args.id.toString(), sort, "", "")
            bottomSheetDialog.dismiss()
        }

        bottomSheetDialog.show()
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
                productsAdapter.submitList(uiState.data.data!!.products)
                hideProgress()
            }

            is ProductsViewModel.UiState.Error -> {
                if (uiState.errorData.status == 401)
                    logoutNoAuth(requireActivity())
                else
                    toastError(uiState.errorData.message)
                hideProgress()
            }
        }
    }

    private fun cartUI(uiState: CartViewModel.UiState) {
        when (uiState) {
            is CartViewModel.UiState.Loading -> {
                showProgress()
            }

            is CartViewModel.UiState.Error -> {
                if (uiState.errorData.status == 401)
                    logoutNoAuth(requireActivity())
                else
                    toastError(uiState.errorData.message)
                hideProgress()
                cartViewModel.removeState()
            }

            is CartViewModel.UiState.AddCartSuccess -> {
                cartViewModel.removeState()
                hideProgress()
            }

            is CartViewModel.UiState.DeleteCartSuccess -> {
                cartViewModel.removeState()
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
                if (uiState.errorData.status == 401)
                    logoutNoAuth(requireActivity())
                else
                    toastError(uiState.errorData.message)
                hideProgress()
            }

            is FavoritesViewModel.UiState.StoreFavoriteSuccess -> {
                favoriteviewModel.removeState()
                hideProgress()
            }

            is FavoritesViewModel.UiState.DeleteFavoriteSuccess -> {
                favoriteviewModel.removeState()
                hideProgress()
            }

            else -> {}
        }
    }

    private fun setCategoriesRecyclerView() {
        productsAdapter = ProductsAdapter({
            findNavController().navigate(
                ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment(
                    it.id!!
                )
            )
        }, { position, product, count ->
            cartViewModel.addCard(
                product.id.toString(),
                product.shapes!![0]!!.id.toString(),
                count.toString()
            )
        }, { position, product ->
            cartViewModel.deleteCard(product.id.toString(), product.shapes!![0]!!.id.toString())
        }, { total, position, product ->
            cartViewModel.addCard(
                product.id.toString(),
                product.shapes!![0]!!.id.toString(),
                total.toString()
            )

        }, { total, position, product ->
            cartViewModel.addCard(
                product.id.toString(),
                product.shapes!![0]!!.id.toString(),
                total.toString()
            )

        }, { position, product, isFavorite ->
                if (product.isFavorite == 0) {
                    favoriteviewModel.storeFavorite(product.id!!)
                } else {
                    favoriteviewModel.deleteFavorite(product.id!!)
                }

        })
        binding.productsRecyclerView.adapter = productsAdapter
    }


}