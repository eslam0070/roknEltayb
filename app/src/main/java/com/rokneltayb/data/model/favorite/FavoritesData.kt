package com.rokneltayb.data.model.favorite


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose
import com.rokneltayb.data.model.products.details.Data

data class FavoritesData(
    @SerializedName("description")
    @Expose
    val description: String?,
    @SerializedName("discount_value")
    @Expose
    val discountValue: String?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("image")
    @Expose
    val image: String?,
    @SerializedName("images")
    @Expose
    val images: List<Data.Images?>?,
    @SerializedName("is_discount")
    @Expose
    val isDiscount: String?,
    @SerializedName("is_favorite")
    @Expose
    val isFavorite: Int?,
    @SerializedName("price")
    @Expose
    val price: String?,
    @SerializedName("rate")
    @Expose
    val rate: Int?,
    @SerializedName("rates")
    @Expose
    val rates: List<Rate?>?,
    @SerializedName("shapes")
    @Expose
    val shapes: List<Shape?>?,
    @SerializedName("title")
    @Expose
    val title: String?
)