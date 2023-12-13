package com.rokneltayb.data.model.products.details

data class Shape(
    val ar_title: String? = null,
    val created_at: String? = null,
    val default: Int? = null,
    val en_title: String? = null,
    val id: Int? = null,
    val price: String? = null,
    val product_id: Int? = null,
    val title: String? = null,
    val updated_at: String? = null
){
    override fun toString(): String {
        return title!!
    }
}