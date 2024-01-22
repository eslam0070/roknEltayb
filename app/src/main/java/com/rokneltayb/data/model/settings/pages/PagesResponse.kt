package com.rokneltayb.data.model.settings.pages


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class PagesResponse(
    @SerializedName("data")
    @Expose
    val `data`: List<Data?>?,
    @SerializedName("message")
    @Expose
    val message: String?,
    @SerializedName("status")
    @Expose
    val status: Int?
)