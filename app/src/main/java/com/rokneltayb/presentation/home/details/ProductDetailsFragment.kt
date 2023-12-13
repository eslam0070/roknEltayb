package com.rokneltayb.presentation.home.details

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.rokneltayb.BaseActivity
import com.rokneltayb.R
import com.rokneltayb.data.model.products.details.Data
import com.rokneltayb.data.model.products.details.Shape
import com.rokneltayb.databinding.FragmentProductDetailsBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.home.details.slideAdapter.SliderAdapter
import kotlinx.coroutines.launch
import kotlin.math.abs

class ProductDetailsFragment : Fragment() {


    private val binding by lazy { FragmentProductDetailsBinding.inflate(layoutInflater) }
    private val viewModel: ProductDetailsViewModel by viewModels()
    private val args: ProductDetailsFragmentArgs by navArgs()
    private var sliderHandler = Handler()
    private fun observeUIState() =
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

    private fun updateUI(uiState: ProductDetailsViewModel.UiState) {
        when (uiState) {
            is ProductDetailsViewModel.UiState.Loading -> {
                showProgress()
            }

            is ProductDetailsViewModel.UiState.Success -> {
                setProductDetailsData(uiState.data.data)
                hideProgress()
            }

            is ProductDetailsViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }

        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text =
            getString(R.string.product_details)


        viewModel.productDetails(args.productId)
        return binding.root
    }

    private fun setProductDetailsData(data: Data) {

        binding.titleProductTextView.text = data.title
        binding.priceTextView.text = data.price
        if (data.is_discount == "active"){
            binding.discountTextView.visibility = View.VISIBLE
            binding.discountTextView.text = data.discount_value
        }else
            binding.discountTextView.visibility = View.INVISIBLE

        binding.rateTextView.text = data.rate.toString()

        binding.descriptionProductTextView.text = data.description
        setImageSliders(data.images)

        setShapepinner(data.shapes)


    }

    var shapeId: Int? = 0
    private var shapesAdapter: ArrayAdapter<Shape>? = null

    private fun setShapepinner(shapes: List<Shape>) {
        val shapesList = mutableListOf<Shape>()
        shapesList.add(0, Shape(id =1, title = "Size/Weight" ))
        shapesList.addAll(shapes)
        shapesAdapter = ArrayAdapter(requireContext(), R.layout.shape_spinner_textview_align, shapesList)
        binding.shapeProductSpinner.adapter = shapesAdapter

        binding.shapeProductSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                shapeId = shapesAdapter!!.getItem(binding.shapeProductSpinner.selectedItemPosition)!!.id
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setImageSliders(images: List<Data.Images>) {
        val images = mutableListOf<Data.Images>()
        images.clear()
        images.add(
            0,
            Data.Images(1, "https://i.pinimg.com/originals/36/57/79/365779c9bca38b6403f0a799e19d49f8.jpg")
        )
        images.add(
            1,
            Data.Images(2, "https://papers.co/wallpaper/papers.co-me66-canada-mountain-sky-snow-high-nature-1-wallpaper.jpg")
        )
        images.add(
            2,
            Data.Images(3, "https://cdnfiles.hdrcreme.com/8057/medium/Montagne.jpg?1426873266")
        )
        images.add(
            3,
            Data.Images(4, "https://i.etsystatic.com/31806032/r/il/58bfb0/3888247640/il_570xN.3888247640_6mzg.jpg")
        )

        binding.viewPagerImageSlider.adapter = SliderAdapter(images,binding.viewPagerImageSlider)

        binding.viewPagerImageSlider.clipToPadding = false
        binding.viewPagerImageSlider.clipChildren = false
        binding.viewPagerImageSlider.offscreenPageLimit = 3
        binding.viewPagerImageSlider.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer()
        compositePageTransformer.addTransformer(MarginPageTransformer(40))
        compositePageTransformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.15f
        }
        binding.viewPagerImageSlider.setPageTransformer(compositePageTransformer)
        binding.viewPagerImageSlider.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                sliderHandler.removeCallbacks(sliderRunnable)
                sliderHandler.postDelayed(sliderRunnable,3000)
            }
        })
    }


    val sliderRunnable = Runnable {
        binding.viewPagerImageSlider.currentItem = binding.viewPagerImageSlider.currentItem + 1
    }
}