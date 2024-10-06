package com.rokneltayb.presentation.home.details

import android.R.id.input
import android.os.Bundle
import android.os.Handler
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.fragment.app.Fragment
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
import com.rokneltayb.domain.util.logoutNoAuth
import com.rokneltayb.domain.util.sharedPrefrences
import com.rokneltayb.domain.util.toast
import com.rokneltayb.domain.util.toastError
import com.rokneltayb.presentation.home.details.cart.CartViewModel
import com.rokneltayb.presentation.home.details.rate.RateAdapter
import com.rokneltayb.presentation.home.details.rate.StoreRateViewModel
import com.rokneltayb.presentation.home.details.slideAdapter.SliderAdapter
import com.rokneltayb.presentation.more.favorite.FavoritesViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlin.math.abs


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
    private var count = 1


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
                Log.d("TAG", "setProductDetailsData: "+uiState.data.data.inStock)

                setProductDetailsData(uiState.data.data)
                hideProgress()
            }

            is ProductDetailsViewModel.UiState.Error -> {
                if (uiState.errorData.status == 401)
                    logoutNoAuth(requireActivity())
                else
                    toastError(uiState.errorData.message)
                hideProgress()
            }

        }
    }
    private fun rateUI(uiState: StoreRateViewModel.UiState) {
        when (uiState) {
            is StoreRateViewModel.UiState.Loading -> {
            }

            is StoreRateViewModel.UiState.Success -> {
                viewModel.productDetails(args.productId)
                toast(uiState.data.message.toString())
            }

            is StoreRateViewModel.UiState.Error -> {
                if (uiState.errorData.status == 401)
                    logoutNoAuth(requireActivity())
                else
                    toastError(uiState.errorData.message)
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
            }

            is CartViewModel.UiState.AddToCartSuccess -> {
                showCountineCart()
                hideProgress()
            }

            else ->{}
        }
    }

    private fun favoritesUI(uiState: FavoritesViewModel.UiState) {
        when (uiState) {
            is FavoritesViewModel.UiState.Loading -> {
            }

            is FavoritesViewModel.UiState.Error -> {
                if (uiState.errorData.status == 401)
                    logoutNoAuth(requireActivity())
                else
                    toastError(uiState.errorData.message)
            }

            is FavoritesViewModel.UiState.StoreFavoriteSuccess -> {
                viewModel.productDetails(args.productId)
            }

            is FavoritesViewModel.UiState.DeleteFavoriteSuccess -> {
                viewModel.productDetails(args.productId)
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


        binding.priceTextView.text = data.price + requireActivity().getString(R.string.kwd)



        if (data.is_favorite == 0)
            binding.addFavoriteImageView.setImageResource(R.drawable.deletefavourite)
        else
            binding.addFavoriteImageView.setImageResource(R.drawable.addfavourite)


        if (data.is_discount == "active"){
            binding.discountTextView.visibility = View.VISIBLE
            if (data.discount_value != null)
                binding.discountTextView.text = data.discount_value + requireActivity().getString(R.string.kwd)
            else
                binding.discountTextView.visibility = View.INVISIBLE

        }else
            binding.discountTextView.visibility = View.INVISIBLE

        binding.rateTextView.text = data.rate.substring(0,1)

        binding.descriptionProductTextView.text =
            Html.fromHtml(data.description, Html.FROM_HTML_MODE_COMPACT)


        if (data.images.isNotEmpty())
            setImageSliders(data.images as MutableList<Data.Images>)
        else{
            val images = mutableListOf<Data.Images>()
            images.add(Data.Images(1, data.image))
            setImageSliders(images)
        }


        setShapepinner(data.shapes)
        setCount(data.inStock,data.price)
        binding.addFavoriteImageView.setOnClickListener {
            if (data.is_favorite == 0)
                favoriteviewModel.storeFavorite(data.id)
            else
                favoriteviewModel.deleteFavorite(data.id)
        }


        if (data.inStock != 0){
            binding.unavailableTextView.visibility = View.GONE
            binding.addToCartButton.setOnClickListener {
                cartViewModel.storeCart(args.productId.toString(),shapeId.toString(), count.toString())
            }
        }
        else{
            binding.unavailableTextView.visibility = View.VISIBLE
            binding.addToCartButton.setOnClickListener { toast("غير متوفر المنتج") }

        }


        setRatingsRecyclerView(data.rates)

    }

    private fun setCount(inStock: Int, price: String) {

        binding.plusTextView.setOnClickListener {
            if (count == inStock)
                toast("لقد وصلت الى الحد الاقصي للكميه المتاحة")
            else{
                count++
                val value: Double = price.toDouble() // For double
                Log.d("TAG", "setCount1: "+value)

                val totalPrice =  count * value

                Log.d("TAG", "setCount2: "+totalPrice)
                Log.d("TAG", "setCount3: "+count)
                Log.d("TAG", "setCount4: "+value.toInt())
                binding.priceTextView.text = totalPrice.toString() + requireActivity().getString(R.string.kwd)
                binding.countTextView.text = count.toString()
            }
        }

        binding.munisTextView.setOnClickListener {
            count--
            if (count <= 0){
                count = 1
                binding.countTextView.text = "1"
            }
            else{
                val value: Double = price.toDouble() // For double
                val totalPrice =  count * value

                binding.priceTextView.text = totalPrice.toString() + requireActivity().getString(R.string.kwd)
                binding.countTextView.text = count.toString()
            }
        }
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
                binding.priceTextView.text = shapesAdapter!!.getItem(binding.shapeProductSpinner.selectedItemPosition)!!.price  + requireActivity().getString(R.string.kwd)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }
    }

    private fun setImageSliders(images: MutableList<Data.Images> = arrayListOf()) {
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