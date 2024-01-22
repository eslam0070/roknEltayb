package com.rokneltayb.data.model.orders.details


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class OrderDetailsResponse(
    @SerializedName("data")
    @Expose
    val orderDetailsData: OrderDetailsData?,
    @SerializedName("message")
    @Expose
    val message: String?,
    @SerializedName("status")
    @Expose
    val status: Int?
)