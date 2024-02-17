package com.rokneltayb.data.model.cart.coupon


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class AddCouponResponse(
    @SerializedName("data")
    @Expose
    val `data`: List<Any?>?,
    @SerializedName("message")
    @Expose
    val message: String?,
    @SerializedName("status")
    @Expose
    val status: Int?
)