package com.rokneltayb.data.model.products

data class DataXX(
    val description: String,
    val discount_value: Any,
    val id: Int,
    val image: String,
    val images: List<Any>,
    val is_discount: String,
    val is_favorite: Int,
    val price: String,
    val rate: Int,
    val rates: List<Rate>,
    val shapes: List<ShapeX>,
    val title: String
)