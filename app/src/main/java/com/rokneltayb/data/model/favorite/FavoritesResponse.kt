package com.rokneltayb.data.model.favorite


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class FavoritesResponse(
    @SerializedName("data")
    @Expose
    val `data`: List<FavoritesData?>?,
    @SerializedName("message")
    @Expose
    val message: String?,
    @SerializedName("status")
    @Expose
    val status: Int?
)