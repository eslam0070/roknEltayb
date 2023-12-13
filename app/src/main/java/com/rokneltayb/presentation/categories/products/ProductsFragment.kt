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
import com.rokneltayb.databinding.BottomSheetSortBinding
import com.rokneltayb.databinding.FragmentProductsBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.addBasicItemDecoration
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.cart.CartViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class ProductsFragment : Fragment() {

    private val binding by lazy { FragmentProductsBinding.inflate(layoutInflater) }
    private val viewModel: ProductsViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private lateinit var productsAdapter:ProductsAdapter
    private val args:ProductsFragmentArgs by navArgs()
    private var sort = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View {

        observeUIState()
        observeUIStateCart()
        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text = args.name
        viewModel.products(args.id,"","")
        binding.searchEditText.doAfterTextChanged {
            if (it!!.length > 3){
                viewModel.products(args.id,sort,it.toString())
            }else
                viewModel.products(args.id,"","")
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
        val binding: BottomSheetSortBinding = DataBindingUtil.inflate(LayoutInflater.from(requireContext()), R.layout.bottom_sheet_sort, null, false)


        binding.radioGroup.setOnClickListener {
            if (it is RadioButton){
                when(it.id){
                    R.id.higtRadioButton ->{
                        if (it.isChecked){
                            sort = "high_price"
                            viewModel.products(args.id,sort,"")
                            bottomSheetDialog.dismiss()
                        }
                    }
                    R.id.lowRadioButton ->{
                        if (it.isChecked){
                            sort = "low_price"
                            viewModel.products(args.id,sort,"")
                            bottomSheetDialog.dismiss()
                        }
                    }
                    R.id.newRadioButton ->{
                        if (it.isChecked){
                            sort = "new"
                            viewModel.products(args.id,sort,"")
                            bottomSheetDialog.dismiss()
                        }
                    }
                }
            }
        }
        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.show()
    }

    private fun observeUIState() =
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }
    private fun observeUIStateCart() =
        lifecycleScope.launch {
            cartViewModel.uiState.flowWithLifecycle(lifecycle).collect(::cartUI)
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
                toastError(uiState.errorData.message)
                hideProgress()
            }

            is CartViewModel.UiState.AddCartSuccess -> {
                hideProgress()
            }
            is CartViewModel.UiState.DeleteCartSuccess -> {
                hideProgress()
            }

            else ->{}
        }
    }

    private fun setCategoriesRecyclerView() {
        productsAdapter = ProductsAdapter({
                                          findNavController().navigate(ProductsFragmentDirections.actionProductsFragmentToProductDetailsFragment(it.id!!))
        },{position,product,count->
            cartViewModel.addCard(product.id.toString(), product.shapes!![position]!!.id.toString(),count.toString())
        },{ position,product ->
            cartViewModel.deleteCard(product.id.toString(),product.shapes!![position]!!.id.toString())
        },{total,position,product ->
            cartViewModel.addCard(product.id.toString(), product.shapes!![position]!!.id.toString(),total.toString())

        },{total,position,product ->
            cartViewModel.addCard(product.id.toString(), product.shapes!![position]!!.id.toString(),total.toString())

        },{ position,product,isFavorite->
            if (isFavorite == true){

            }else{

            }

        })
        binding.productsRecyclerView.adapter = productsAdapter
    }


}