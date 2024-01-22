package com.rokneltayb.data.model.orders


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class OrdersData(
    @SerializedName("date")
    @Expose
    val date: String?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("image")
    @Expose
    val image: String?,
    @SerializedName("order_num")
    @Expose
    val orderNum: Int?,
    @SerializedName("total_price")
    @Expose
    val totalPrice: Double?,
    @SerializedName("type")
    @Expose
    val type: String?
)