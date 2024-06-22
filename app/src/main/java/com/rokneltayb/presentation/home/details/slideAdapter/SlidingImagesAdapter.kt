package com.rokneltayb.presentation.home.details.slideAdapter

import android.content.Context
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.jsibbold.zoomage.ZoomageView
import com.rokneltayb.R

class SlidingImagesAdapter(private val context: Context, private val imageModelArrayList: ArrayList<String>) : PagerAdapter() {
    private val inflater: LayoutInflater
    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun getCount(): Int {
        return imageModelArrayList.size
    }

    override fun instantiateItem(view: ViewGroup, position: Int): Any {
        val imageLayout =
            inflater.inflate(R.layout.item_slider_img, view, false)!!
        val imageView = imageLayout.findViewById<View>(R.id.image) as ZoomageView

        Glide.with(context)
            .load(imageModelArrayList[position])
            .into(imageView)


        view.addView(imageLayout, 0)
        return imageLayout
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun restoreState(state: Parcelable?, loader: ClassLoader?) {}
    override fun saveState(): Parcelable? {
        return null
    }
    init { inflater = LayoutInflater.from(context) }
}