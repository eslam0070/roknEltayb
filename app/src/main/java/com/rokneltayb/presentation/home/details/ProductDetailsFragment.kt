package com.rokneltayb.presentation.home.details

import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.rokneltayb.BaseActivity
import com.rokneltayb.R
import com.rokneltayb.data.model.products.details.Data
import com.rokneltayb.data.model.products.details.Shape
import com.rokneltayb.databinding.AddComentDialogBinding
import com.rokneltayb.databinding.CountineCartDialogBinding
import com.rokneltayb.databinding.FragmentProductDetailsBinding
import com.rokneltayb.domain.util.LoadingScreen.hideProgress
import com.rokneltayb.domain.util.LoadingScreen.showProgress
import com.rokneltayb.domain.util.addBasicItemDecoration
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.home.details.slideAdapter.SliderAdapter
import kotlinx.coroutines.launch
import kotlin.math.abs
import com.rokneltayb.domain.util.sharedPrefrences
import com.rokneltayb.presentation.favorite.FavoritesViewModel
import com.rokneltayb.presentation.home.details.cart.CartViewModel
import com.rokneltayb.presentation.home.details.rate.RateAdapter
import com.rokneltayb.presentation.home.details.rate.StoreRateViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsFragment : Fragment() {


    private val binding by lazy { FragmentProductDetailsBinding.inflate(layoutInflater) }
    private val viewModel: ProductDetailsViewModel by viewModels()
    private val rateViewModel: StoreRateViewModel by viewModels()
    private val cartViewModel: CartViewModel by viewModels()
    private val favoriteviewModel: FavoritesViewModel by viewModels()
    private val args: ProductDetailsFragmentArgs by navArgs()
    private var sliderHandler = Handler()
    private lateinit var rateAdapter: RateAdapter
    private var nameProduct:String? = null
    private var priceProduct:String? = null

    private fun observeUIState() {
        lifecycleScope.launch {
            viewModel.uiState.flowWithLifecycle(lifecycle).collect(::updateUI)
        }

        lifecycleScope.launch {
            rateViewModel.uiState.flowWithLifecycle(lifecycle).collect(::rateUI)
        }

        lifecycleScope.launch {
            cartViewModel.uiState.flowWithLifecycle(lifecycle).collect(::cartUI)
        }

        lifecycleScope.launch {
            favoriteviewModel.uiState.flowWithLifecycle(lifecycle).collect(::favoritesUI)
        }
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
    private fun rateUI(uiState: StoreRateViewModel.UiState) {
        when (uiState) {
            is StoreRateViewModel.UiState.Loading -> {
                showProgress()
            }

            is StoreRateViewModel.UiState.Success -> {
                viewModel.productDetails(args.productId)
                toast(uiState.data.message.toString())
                hideProgress()
            }

            is StoreRateViewModel.UiState.Error -> {
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

            is CartViewModel.UiState.AddToCartSuccess -> {
                showCountineCart()
                toast(uiState.data.message.toString())
                hideProgress()
            }

            else ->{}
        }
    }

    private fun favoritesUI(uiState: FavoritesViewModel.UiState) {
        when (uiState) {
            is FavoritesViewModel.UiState.Loading -> {
                showProgress()
            }

            is FavoritesViewModel.UiState.Error -> {
                toastError(uiState.errorData.message)
                hideProgress()
            }

            is FavoritesViewModel.UiState.StoreFavoriteSuccess -> {
                viewModel.productDetails(args.productId)
                hideProgress()
            }

            is FavoritesViewModel.UiState.DeleteFavoriteSuccess -> {
                viewModel.productDetails(args.productId)
                hideProgress()
            }

            else ->{}
        }
    }


    private fun showCountineCart() {
        val dialog = BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme)
        val sheetBinding = CountineCartDialogBinding.inflate(layoutInflater, null, false)
        dialog.setContentView(sheetBinding.root)
        dialog.setCancelable(false)
        dialog.show()

        sheetBinding.nameProductCountine.text = nameProduct
        sheetBinding.priceTextView.text = priceProduct

        sheetBinding.continueShoppingButton.setOnClickListener {
            findNavController().navigate(ProductDetailsFragmentDirections.actionProductDetailsFragmentToHomeFragment())
            dialog.dismiss()
        }

        sheetBinding.ViewCartButton.setOnClickListener {
            findNavController().navigate(ProductDetailsFragmentDirections.actionProductDetailsFragmentToCartFragment())
            dialog.dismiss()
        }

    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        observeUIState()

        (requireActivity() as BaseActivity).binding!!.tvMainEmployeeName.text =
            getString(R.string.product_details)

        binding.reviewProductRecyclerView.addBasicItemDecoration(R.dimen.item_decoration_small_margin)

        viewModel.productDetails(args.productId)

        binding.addCommentImageView.setOnClickListener {
            addCommentDialog()
        }

        binding.addToCartButton.setOnClickListener {
            cartViewModel.storeCart(args.productId.toString(),shapeId.toString(),binding.countEditText.text.toString())
        }
        return binding.root
    }

    private fun addCommentDialog() {
        val dialog = BottomSheetDialog(requireActivity(), R.style.BottomSheetDialogTheme)
        val sheetBinding = AddComentDialogBinding.inflate(layoutInflater, null, false)
        dialog.setContentView(sheetBinding.root)
        dialog.setCancelable(false)
        dialog.show()

        sheetBinding.clossBtn.setOnClickListener {
            dialog.dismiss()
        }

        sheetBinding.sendComment.setOnClickListener {
            if (sheetBinding.etDesc.text!!.isEmpty())
                toastError("من فضلك اكتب تقييمك للمنتج")
            else
                rateViewModel.storeRate(sharedPrefrences(requireContext()).getUserId(),args.productId,sheetBinding.rateBar.rating.toString(),sheetBinding.etDesc.text.toString())

            dialog.dismiss()
        }
    }

    private fun setProductDetailsData(data: Data) {

        binding.titleProductTextView.text = data.title
        nameProduct = data.title
        if (data.price == null){
            binding.priceTextView.text = "100 KWD"
            priceProduct = "100 KWD"
        }
        else{
            binding.priceTextView.text = data.price
            priceProduct = data.price
        }

        if (data.is_favorite == 0)
            binding.addFavoriteImageView.setImageResource(R.drawable.deletefavourite)
        else
            binding.addFavoriteImageView.setImageResource(R.drawable.addfavourite)


        if (data.is_discount == "active"){
            binding.discountTextView.visibility = View.VISIBLE
            binding.discountTextView.text = data.discount_value
        }else
            binding.discountTextView.visibility = View.INVISIBLE

        binding.rateTextView.text = data.rate.toString()

        binding.descriptionProductTextView.text = data.description
        setImageSliders(data.images as MutableList<Data.Images>)

        setShapepinner(data.shapes)

        binding.addFavoriteImageView.setOnClickListener {
            if (data.is_favorite == 0)
                favoriteviewModel.storeFavorite(data.id)
            else
                favoriteviewModel.deleteFavorite(data.id)
        }



        setRatingsRecyclerView(data.rates)

    }

    private fun setRatingsRecyclerView(rateList: List<Data.Rates>) {
        rateAdapter = RateAdapter()
        rateAdapter.submitList(rateList)
        binding.reviewProductRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        binding.reviewProductRecyclerView.adapter = rateAdapter
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

    private fun setImageSliders(images: MutableList<Data.Images> = arrayListOf()) {
        Log.d("TAG", "setImageSliders: "+images)
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