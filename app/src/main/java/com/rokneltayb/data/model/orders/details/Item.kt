package com.rokneltayb.data.model.orders.details


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Item(
    @SerializedName("ar_description")
    @Expose
    val arDescription: String?,
    @SerializedName("ar_title")
    @Expose
    val arTitle: String?,
    @SerializedName("category_id")
    @Expose
    val categoryId: Int?,
    @SerializedName("created_at")
    @Expose
    val createdAt: String?,
    @SerializedName("description")
    @Expose
    val description: String?,
    @SerializedName("discount_value")
    @Expose
    val discountValue: Any?,
    @SerializedName("en_description")
    @Expose
    val enDescription: String?,
    @SerializedName("en_title")
    @Expose
    val enTitle: String?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("image")
    @Expose
    val image: String?,
    @SerializedName("in_stock")
    @Expose
    val inStock: Int?,
    @SerializedName("is_active")
    @Expose
    val isActive: String?,
    @SerializedName("is_discount")
    @Expose
    val isDiscount: String?,
    @SerializedName("IsFavorite")
    @Expose
    val isFavorite: Int?,
    @SerializedName("is_hot")
    @Expose
    val isHot: Int?,
    @SerializedName("is_new")
    @Expose
    val isNew: Int?,
    @SerializedName("is_popular")
    @Expose
    val isPopular: String?,
    @SerializedName("opening_balance")
    @Expose
    val openingBalance: Int?,
    @SerializedName("title")
    @Expose
    val title: String?,
    @SerializedName("unit_id")
    @Expose
    val unitId: Int?,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String?
)