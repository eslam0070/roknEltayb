package com.rokneltayb.data.model.orders


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class OrdersResponse(
    @SerializedName("data")
    @Expose
    val `data`: List<OrdersData?>?,
    @SerializedName("message")
    @Expose
    val message: String?,
    @SerializedName("status")
    @Expose
    val status: Int?
)