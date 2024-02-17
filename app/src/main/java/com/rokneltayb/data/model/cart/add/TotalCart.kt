package com.rokneltayb.data.model.cart.add

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class TotalCart (
    @SerializedName("data")
    @Expose
    val total_cart: Int,
)