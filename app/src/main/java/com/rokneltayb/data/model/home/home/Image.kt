package com.rokneltayb.data.model.home.home


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class Image(
    @SerializedName("created_at")
    @Expose
    val createdAt: String?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("image")
    @Expose
    val image: String?,
    @SerializedName("product_id")
    @Expose
    val productId: Int?,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String?
) : Parcelable