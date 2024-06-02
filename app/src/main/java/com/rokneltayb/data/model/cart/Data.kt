package com.rokneltayb.data.model.cart


import com.rokneltayb.data.model.cart.coupon.Coupon

data class Data(
    val cart: List<Cart>,
    val coupon: Coupon,
    val tax: Int,
    val total: String,
    val total_after_tax: String
)