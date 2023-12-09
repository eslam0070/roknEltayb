package com.rokneltayb.data.model.home.home


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class Slider(
    @SerializedName("description")
    @Expose
    val description: String?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("image")
    @Expose
    val image: String?,
    @SerializedName("link")
    @Expose
    val link: String?,
    @SerializedName("title")
    @Expose
    val title: String?
) : Parcelable