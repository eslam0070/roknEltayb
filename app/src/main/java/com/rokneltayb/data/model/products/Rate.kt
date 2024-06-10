package com.rokneltayb.data.model.products

data class Rate(
    val date: String,
    val description: String,
    val id: Int,
    val product_id: Int,
    val rate: Int,
    val user_image: Any,
    val user_name: String
)