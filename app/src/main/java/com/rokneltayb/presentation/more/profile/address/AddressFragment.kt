package com.rokneltayb.presentation.more.profile.address

import android.app.Dialog
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.DisplayMetrics
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.core.content.res.ResourcesCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.rokneltayb.R
import com.rokneltayb.data.model.home.home.PopularProduct
import com.rokneltayb.databinding.DeleteAddressDialogBinding
import com.rokneltayb.databinding.FragmentAddressBinding
import com.rokneltayb.databinding.FragmentProfileBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.addBasicItemDecoration
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.more.favorite.FavoritesViewModel
import com.rokneltayb.presentation.home.HomeFragmentDirections
import com.rokneltayb.presentation.home.HomeViewModel
import com.rokneltayb.presentation.home.adapters.HomeCategoriesAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class AddressFragment : Fragment() {

    private val binding by lazy { FragmentAddressBinding.inflate(layoutInflater) }
    private lateinit var addressAdapter: AddressAdapter
    private val viewModel: AddressViewModel by viewModels()

    private fun observeUIState() =
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    private fun updateUI(uiState: AddressViewModel.UiState) {
        when (uiState) {
            is AddressViewModel.UiState.Loading -> {
                showProgress()
            }

            is AddressViewModel.UiState.AddressSuccess -> {
                addressAdapter.submitList(uiState.data.data)
                hideProgress()
            }



            is AddressViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }

            else -> {}
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        observeUIState()

        viewModel.address()

        binding.addNewAddressTextView.setOnClickListener {
            findNavController().navigate(AddressFragmentDirections.actionAddressFragmentToNewAddressFragment())
        }

        addressAdapter = AddressAdapter { findNavController().navigate(AddressFragmentDirections.actionAddressFragmentToNewAddressFragment(it.id.toString())) }

        binding.addressRecyclerView.adapter = addressAdapter

        return binding.root
    }




}