package com.rokneltayb.data.model.cart.coupon


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Coupon(
    @SerializedName("created_at")
    @Expose
    val createdAt: String?,
    @SerializedName("expire_date")
    @Expose
    val expireDate: String?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("is_active")
    @Expose
    val isActive: String?,
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("percent")
    @Expose
    val percent: Int?,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String?,
    @SerializedName("use_count")
    @Expose
    val useCount: Int?,
    @SerializedName("used_count")
    @Expose
    val usedCount: Int?
)