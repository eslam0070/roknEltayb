package com.rokneltayb.data.model.favorite


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class Shape(
    @SerializedName("date")
    @Expose
    val date: String?,
    @SerializedName("default")
    @Expose
    val default: Int?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("price")
    @Expose
    val price: String?,
    @SerializedName("title")
    @Expose
    val title: String?
) : Parcelable