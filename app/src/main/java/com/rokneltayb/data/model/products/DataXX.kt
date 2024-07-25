package com.rokneltayb.data.model.products

import com.google.gson.annotations.SerializedName

data class DataXX(
    val description: String,
    val discount_value: Any,
    val id: Int,
    val image: String,
    val images: List<Any>,
    val is_discount: String,
    val is_favorite: Int,
    val price: String,
    val rate: String,
    val rates: List<Rate>,
    val shapes: List<ShapeX>,
    val title: String,
    @SerializedName("in_stock")
    val inStock:Int
)