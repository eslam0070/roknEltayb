package com.rokneltayb.data.model.cart.update


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Cart(
    @SerializedName("count")
    @Expose
    val count: Int?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("price")
    @Expose
    val price: String?,
    @SerializedName("product_id")
    @Expose
    val productId: Int?,
    @SerializedName("product_image")
    @Expose
    val productImage: String?,
    @SerializedName("product_title")
    @Expose
    val productTitle: String?,
    @SerializedName("shape_id")
    @Expose
    val shapeId: Int?
)