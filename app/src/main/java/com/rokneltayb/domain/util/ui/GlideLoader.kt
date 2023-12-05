package com.rokneltayb.domain.util.ui

import android.widget.ImageView
import com.bumptech.glide.Glide
import io.github.boldijar.pdfy.ui.PdfyImageLoader


class GlideLoader : PdfyImageLoader() {
    override fun loadImage(path: String, imageView: ImageView) {
        Glide.with(imageView).load(path).into(imageView)
    }
}
