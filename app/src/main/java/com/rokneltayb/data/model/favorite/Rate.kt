package com.rokneltayb.data.model.favorite


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

data class Rate(
    @SerializedName("date")
    @Expose
    val date: String?,
    @SerializedName("description")
    @Expose
    val description: String?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("product_id")
    @Expose
    val productId: Int?,
    @SerializedName("rate")
    @Expose
    val rate: Int?,
    @SerializedName("user_image")
    @Expose
    val userImage: String?,
    @SerializedName("user_name")
    @Expose
    val userName: String?
)