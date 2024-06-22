package com.rokneltayb.presentation.home.details.slideAdapter

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.rokneltayb.R
import com.rokneltayb.data.model.products.details.Data
import com.rokneltayb.databinding.SlideItemBinding
import com.zhpan.indicator.IndicatorView
import com.zhpan.indicator.enums.IndicatorSlideMode
import com.zhpan.indicator.enums.IndicatorStyle

class SliderAdapter(private val images: MutableList<Data.Images> = arrayListOf(), private var viewPagerImageSlider: ViewPager2):RecyclerView.Adapter<SliderAdapter.SliderHolder>(){




    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SliderHolder {
        val binding: SlideItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.context), R.layout.slide_item, parent, false)
        return SliderHolder(binding)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    override fun onBindViewHolder(holder: SliderHolder, position: Int) {
        val i = images[position]
        Glide.with(holder.itemView.context).load(i.image).into(holder.binding.imageSlider)

        if (position == images.size - 2)
            viewPagerImageSlider.post(sliderRunnable)



        holder.binding.imageSlider.setOnClickListener {
            var selectedItem = 0
            val img = ArrayList<String>()

            for (i in images){
                img.add(i.image)

                if (i.image == images[position].image) {
                    selectedItem = images.size - 1
                }
            }
            showFullImage(holder.itemView.context, img,selectedItem)
        }

    }

    private fun showFullImage(context: Context?, images: ArrayList<String>, selectedItem:Int) {
        val dialog2 = Dialog(context!!, android.R.style.Theme_DeviceDefault_Light_NoActionBar_Fullscreen)
        dialog2.setCancelable(true)
        dialog2.window!!.setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE)
        dialog2.window!!.setBackgroundDrawable(ColorDrawable(Color.parseColor("#77000000")))
        dialog2.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog2.setContentView(R.layout.dialoge_show_image_slider)
        val mPager: ViewPager = dialog2.findViewById(R.id.mPager)
        mPager.adapter = SlidingImagesAdapter(context, images)
        mPager.currentItem = selectedItem

        val indicator = dialog2.findViewById<IndicatorView>(R.id.indicator)
        indicator.setSliderColor(
            context.resources.getColor(R.color.white),
            context.resources.getColor(R.color.main)
        )
        indicator.setSliderWidth(10.2F)
        indicator.setSliderHeight(5.20F)
        indicator.setSliderGap(2.60F)
        indicator.setSlideMode(IndicatorSlideMode.SMOOTH)
        indicator.setIndicatorStyle(IndicatorStyle.ROUND_RECT)
        indicator.setupWithViewPager(mPager)

        val btn_close = dialog2.findViewById<ImageView>(R.id.btn_close)

        btn_close.setOnClickListener { dialog2.dismiss() }

        dialog2.show()
    }

    class SliderHolder(var binding: SlideItemBinding) : RecyclerView.ViewHolder( binding.root){
    }

    private val sliderRunnable = Runnable {
        images.addAll(images)
        notifyDataSetChanged()
    }
}