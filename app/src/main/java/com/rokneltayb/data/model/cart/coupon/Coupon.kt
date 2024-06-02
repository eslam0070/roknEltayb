package com.rokneltayb.data.model.cart.coupon


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Coupon(
    val created_at: String,
    val expire_date: String,
    val id: Int,
    val is_active: String,
    val name: String,
    val percent: Int,
    val updated_at: String,
    val use_count: Int,
    val used_count: Int
)