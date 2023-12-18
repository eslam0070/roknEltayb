package com.rokneltayb.data.model.favorite.delete


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

data class DeleteFavoritesResponse(
    @SerializedName("data")
    @Expose
    val `data`: List<String>?,
    @SerializedName("message")
    @Expose
    val message: String?,
    @SerializedName("status")
    @Expose
    val status: Int?
)