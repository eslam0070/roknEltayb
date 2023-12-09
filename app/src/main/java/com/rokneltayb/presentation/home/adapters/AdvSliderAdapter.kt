package com.rokneltayb.presentation.home.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.rokneltayb.R
import com.rokneltayb.data.model.home.home.Slider
import com.smarteist.autoimageslider.SliderViewAdapter

class AdvSliderAdapter(private var imgs: List<Slider?> = ArrayList()) :
    SliderViewAdapter<AdvSliderAdapter.SliderAdapterVH>() {

    override fun getCount(): Int {
        return imgs.size
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderAdapterVH? {
        val inflate: View =
            LayoutInflater.from(parent.context).inflate(R.layout.img_adv_slider_layout_item, null)
        return SliderAdapterVH(inflate)
    }

    override fun onBindViewHolder(viewHolder: SliderAdapterVH, position: Int) {
        val i = imgs[position]
        Glide.with(viewHolder.itemView.context).clear(viewHolder.img)

        Glide.with(viewHolder.itemView.context)
            .load(i!!.image)
            .into(viewHolder.img)
    }

    class SliderAdapterVH(itemView: View) : ViewHolder(itemView) {
        var img: ImageView

        init {
            img = itemView.findViewById(R.id.img)
        }
    }
}