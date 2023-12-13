package com.rokneltayb.data.model.products.details

data class Data(
    val description: String,
    val discount_value: String,
    val id: Int,
    val image: String,
    val images: List<Images>,
    val is_discount: String,
    val price: String,
    val rate: Int,
    val shapes: List<Shape>,
    val title: String
) {
    data class Images (
        val id: Int,
        val image:String
    )
}