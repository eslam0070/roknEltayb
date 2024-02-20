package com.rokneltayb.data.model.cart


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class Cart(
    @SerializedName("count")
    @Expose
    val count: Int?,
    @SerializedName("id")
    @Expose
    val id: Int?,
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
    /*,
    @SerializedName("price")
    @Expose
    val price: Int?*/
) : Parcelable