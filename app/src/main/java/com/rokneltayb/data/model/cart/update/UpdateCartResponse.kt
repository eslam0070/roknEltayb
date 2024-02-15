package com.rokneltayb.data.model.cart.update


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class UpdateCartResponse(
    @SerializedName("data")
    @Expose
    val `data`: Data?,
    @SerializedName("message")
    @Expose
    val message: String?,
    @SerializedName("status")
    @Expose
    val status: Int?
)