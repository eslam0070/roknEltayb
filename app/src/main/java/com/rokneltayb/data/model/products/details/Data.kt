package com.rokneltayb.data.model.products.details

data class Data(
    val description: String,
    val discount_value: String,
    val id: Int,
    val image: String,
    val images: List<Images>,
    val is_discount: String,
    val price: Long,
    val rate: Int,
    val shapes: List<Shape>,
    val title: String,
    val is_favorite:Int,
    val rates:List<Rates>
) {
    data class Images (
        val id: Int,
        val image:String
    )

    data class Rates (
        val id: Int,
        val product_id: Int,
        val rate: Int,
        val description:String,
        val user_name:String,
        val date:String
    )
}