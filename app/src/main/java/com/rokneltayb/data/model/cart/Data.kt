package com.rokneltayb.data.model.cart


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.rokneltayb.data.model.cart.coupon.Coupon

data class Data(
    @SerializedName("cart")
    @Expose
    val cart: List<Cart?>?,
    @SerializedName("tax")
    @Expose
    val tax: Int?,
    @SerializedName("total")
    @Expose
    val total: Double?,
    @SerializedName("total_after_tax")
    @Expose
    val totalAfterTax: Double?,
    @SerializedName("coupon")
    @Expose
    val coupon: Coupon?
)