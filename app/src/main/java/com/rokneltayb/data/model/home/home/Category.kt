package com.rokneltayb.data.model.home.home


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class Category(
    @SerializedName("icon")
    @Expose
    val icon: String?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("title")
    @Expose
    val title: String?
) : Parcelable