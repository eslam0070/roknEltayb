package com.rokneltayb.data.model.cart


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

data class Cart(
    val count: Int,
    val id: Int,
    val price: Double,
    val product_id: Int,
    val product_image: String,
    val product_title: String,
    val shape_id: Int
)