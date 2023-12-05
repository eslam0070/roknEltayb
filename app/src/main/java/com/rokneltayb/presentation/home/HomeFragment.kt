package com.rokneltayb.presentation.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.rokneltayb.data.model.slider.Image
import com.rokneltayb.databinding.FragmentHomeBinding
import com.rokneltayb.presentation.home.adapters.AdvSliderAdapter
import com.smarteist.autoimageslider.IndicatorView.animation.type.IndicatorAnimationType
import com.smarteist.autoimageslider.SliderAnimations

class HomeFragment : Fragment() {

    private val binding by lazy { FragmentHomeBinding.inflate(layoutInflater) }

    var adapter: AdvSliderAdapter? = null

    private lateinit var viewModel: HomeViewModel
    var url1 = "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQq70AvDs-OIZrlcU0kAqvObihT8VhvedCurSyXCDCakQ&s"
    var url2 = "https://images.unsplash.com/photo-1575936123452-b67c3203c357?q=80&w=1000&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8Mnx8aW1hZ2V8ZW58MHx8MHx8fDA%3D"
    var url3 = "https://media.istockphoto.com/id/1322277517/photo/wild-grass-in-the-mountains-at-sunset.jpg?s=612x612&w=0&k=20&c=6mItwwFFGqKNKEAzv0mv6TaxhLN3zSE43bWmFN--J5w="
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        setSliderImage()



        return binding.root
    }

    private fun setSliderImage() {
        val images = mutableListOf<Image>()
        images.add(Image(1,url1))
        images.add(Image(2,url2))
        images.add(Image(3,url3))
        adapter = AdvSliderAdapter(images)
        binding.imageSlider.setSliderAdapter(adapter!!)
        binding.imageSlider.setIndicatorAnimation(IndicatorAnimationType.DROP)
        binding.imageSlider.setSliderTransformAnimation(SliderAnimations.SIMPLETRANSFORMATION)
        binding.imageSlider.currentPagePosition = 0
    }



}