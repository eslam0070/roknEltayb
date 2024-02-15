package com.rokneltayb.data.model.cart.update


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Data(
    @SerializedName("cart")
    @Expose
    val cart: List<Cart?>?,
    @SerializedName("count")
    @Expose
    val count: String?,
    @SerializedName("ip")
    @Expose
    val ip: String?,
    @SerializedName("product_id")
    @Expose
    val productId: String?,
    @SerializedName("shape_id")
    @Expose
    val shapeId: String?,
    @SerializedName("tax")
    @Expose
    val tax: Int?,
    @SerializedName("total")
    @Expose
    val total: Double?,
    @SerializedName("total_after_tax")
    @Expose
    val totalAfterTax: Double?
)