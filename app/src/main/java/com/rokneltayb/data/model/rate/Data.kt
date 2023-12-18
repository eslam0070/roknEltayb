package com.rokneltayb.data.model.rate


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class Data(
    @SerializedName("description")
    @Expose
    val description: String?,
    @SerializedName("product_id")
    @Expose
    val productId: String?,
    @SerializedName("rate")
    @Expose
    val rate: String?,
    @SerializedName("user_id")
    @Expose
    val userId: Int?
) : Parcelable