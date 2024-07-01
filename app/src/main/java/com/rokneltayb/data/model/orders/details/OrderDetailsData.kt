package com.rokneltayb.data.model.orders.details


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class OrderDetailsData(
    @SerializedName("address")
    @Expose
    val address: Address?,
    @SerializedName("date")
    @Expose
    val date: String?,
    @SerializedName("delivery_fees")
    @Expose
    val deliveryFees: Int?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("order_details")
    @Expose
    val orderDetails: List<OrderDetail?>?,
    @SerializedName("order_num")
    @Expose
    val orderNum: Int?,
    @SerializedName("payment_type")
    @Expose
    val paymentType: String?,
    @SerializedName("tax")
    @Expose
    val tax: Int?,
    @SerializedName("total_price")
    @Expose
    val totalPrice: Double?,
    @SerializedName("type")
    @Expose
    val type: String?,
    @SerializedName("delivery_time")
    @Expose
    val delivery_time:String?,
    @SerializedName("delivery_date")
    @Expose
    val delivery_date: String?
)