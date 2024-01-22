package com.rokneltayb.data.model.orders.add


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class AddOrderResponse(
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