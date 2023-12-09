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
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.rokneltayb.R
import com.rokneltayb.data.model.home.home.Category
import com.rokneltayb.data.model.home.home.Slider
import com.rokneltayb.databinding.FragmentHomeBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.domain.util.ui.MarginItemDecoration
import com.rokneltayb.presentation.home.adapters.AdvSliderAdapter
import com.rokneltayb.presentation.home.adapters.HomeCategoriesAdapter
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

    private fun observeUIState() =
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    private fun updateUI(uiState: HomeViewModel.UiState) {
        when (uiState) {
            is HomeViewModel.UiState.Loading -> {
                showProgress()
            }

            is HomeViewModel.UiState.Success -> {
                setSliderImage(uiState.data.data!!.slider!!)
                setCategoriesRecyclerView(uiState.data.data.categories)
                hideProgress()
            }

            is HomeViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }

        }
    }

    private fun setCategoriesRecyclerView(categories: List<Category?>?) {

        homeCategoriesAdapter = HomeCategoriesAdapter {
            findNavController().navigate(HomeFragmentDirections.actionHomeFragmentToProductDetailsFragment(it.id!!))
        }

        homeCategoriesAdapter.submitList(categories)

        binding.categoriesRecyclerView.layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.HORIZONTAL,false)
        binding.categoriesRecyclerView.adapter = homeCategoriesAdapter
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        observeUIState()


        viewModel.home()

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