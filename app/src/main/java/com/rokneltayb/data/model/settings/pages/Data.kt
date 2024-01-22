package com.rokneltayb.data.model.settings.pages


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class Data(
    @SerializedName("ar_description")
    @Expose
    val arDescription: String?,
    @SerializedName("ar_title")
    @Expose
    val arTitle: String?,
    @SerializedName("created_at")
    @Expose
    val createdAt: String?,
    @SerializedName("description")
    @Expose
    val description: String?,
    @SerializedName("en_description")
    @Expose
    val enDescription: String?,
    @SerializedName("en_title")
    @Expose
    val enTitle: String?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("image")
    @Expose
    val image: String?,
    @SerializedName("is_active")
    @Expose
    val isActive: String?,
    @SerializedName("position")
    @Expose
    val position: String?,
    @SerializedName("title")
    @Expose
    val title: String?,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String?
)