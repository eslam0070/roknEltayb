package com.rokneltayb.data.model.orders.details


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Shape(
    @SerializedName("ar_title")
    @Expose
    val arTitle: String?,
    @SerializedName("created_at")
    @Expose
    val createdAt: String?,
    @SerializedName("default")
    @Expose
    val default: Int?,
    @SerializedName("en_title")
    @Expose
    val enTitle: String?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("price")
    @Expose
    val price: String?,
    @SerializedName("product_id")
    @Expose
    val productId: Int?,
    @SerializedName("title")
    @Expose
    val title: String?,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String?
)