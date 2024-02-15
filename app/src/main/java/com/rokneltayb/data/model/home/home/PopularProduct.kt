package com.rokneltayb.data.model.home.home


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class PopularProduct(
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
    val images: List<Image?>?,
    @SerializedName("is_discount")
    @Expose
    val isDiscount: String?,
    @SerializedName("price")
    @Expose
    val price: Float?,
    @SerializedName("rate")
    @Expose
    val rate: Int?,
    @SerializedName("shapes")
    @Expose
    val shapes: List<Shape?>?,
    @SerializedName("title")
    @Expose
    val title: String?,
    @Expose
    @SerializedName("is_favorite")
    val isFavorite:Int?
) : Parcelable