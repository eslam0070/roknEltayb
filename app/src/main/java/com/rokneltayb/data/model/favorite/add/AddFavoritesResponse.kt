package com.rokneltayb.data.model.favorite.add


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class AddFavoritesResponse(
    @SerializedName("data")
    @Expose
    val `data`: String?,
    @SerializedName("message")
    @Expose
    val message: String?,
    @SerializedName("status")
    @Expose
    val status: Int?
) : Parcelable