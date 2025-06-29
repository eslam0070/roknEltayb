package com.rokneltayb.presentation.home.search

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.rokneltayb.R
import com.rokneltayb.databinding.FragmentHomeBinding
import com.rokneltayb.databinding.FragmentSearchBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.addBasicItemDecoration
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.home.HomeViewModel
import com.rokneltayb.presentation.home.adapters.HomeCategoriesAdapter
import com.rokneltayb.presentation.home.details.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchHomeFragment : Fragment() {


    private val binding by lazy { FragmentSearchBinding.inflate(layoutInflater) }
    private val viewModel: SearchHomeViewModel by viewModels()
    private lateinit var searchAdapter: SearchAdapter
    private val cartViewModel: CartViewModel by viewModels()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        observeUIState()

        searchAdapter = SearchAdapter({
                                      findNavController().navigate(SearchHomeFragmentDirections.actionSearchHomeFragmentToProductDetailsFragment(it)) }
            ,{product ->
                cartViewModel.storeCart(product.id.toString(), product.shapes!![0]!!.id.toString(),"1")
            })

        binding.searchRecyclerView.addBasicItemDecoration()
        binding.searchRecyclerView.adapter = searchAdapter
        binding.ivBack.setOnClickListener {
            findNavController().navigateUp()
        }
        binding.searchEditText.doAfterTextChanged {
            if (it!!.length >3){
                viewModel.search(it.toString())
                showProgress()
                Log.d("TAG", "onCreateView: "+it.toString())
            }
        }
        binding.sizeItemFoundTextView.text = "0 "+ getString(R.string.items_found)

        return binding.root
    }

    private fun observeUIState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

        lifecycleScope.launch {
            cartViewModel.uiState.flowWithLifecycle(lifecycle).collect(::cartUI)
        }
    }

    private fun updateUI(uiState: SearchHomeViewModel.UiState) {
        when (uiState) {
            is SearchHomeViewModel.UiState.Loading -> {}

            is SearchHomeViewModel.UiState.Success -> {
                if (uiState.data.data!!.products!!.data.isNotEmpty()){
                    binding.sizeItemFoundTextView.visibility = View.VISIBLE
                    binding.sizeItemFoundTextView.text = uiState.data.data.products!!.data.size.toString()+" "+ getString(R.string.items_found)
                    searchAdapter.submitList(uiState.data.data.products.data)
                }else
                    binding.sizeItemFoundTextView.visibility = View.INVISIBLE



                hideProgress()
            }

            is SearchHomeViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }

            is SearchHomeViewModel.UiState.Idle -> hideProgress()
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

            else ->{}
        }
    }


}