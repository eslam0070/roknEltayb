package com.rokneltayb.presentation.home.details.slideAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.rokneltayb.R
import com.rokneltayb.data.model.products.details.Data
import com.rokneltayb.databinding.SlideItemBinding

class SliderAdapter(private val images: MutableList<Data.Images> = arrayListOf(), private var viewPagerImageSlider: ViewPager2):RecyclerView.Adapter<SliderAdapter.SliderHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderHolder {
        val binding: SlideItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.slide_item, parent, false)
        return SliderHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: SliderHolder, position: Int) {
        Glide.with(holder.itemView.context).load(images[position].image).into(holder.binding.imageSlider)
        if (position == images.size - 2)
            viewPagerImageSlider.post(sliderRunnable)
    }

    class SliderHolder(var binding: SlideItemBinding) : RecyclerView.ViewHolder( binding.root){
    }

    private val sliderRunnable = Runnable {
        images.addAll(images)
        notifyDataSetChanged()
    }
}