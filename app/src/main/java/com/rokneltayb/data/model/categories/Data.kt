package com.rokneltayb.data.model.categories


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class Data(
    @SerializedName("Categories")
    @Expose
    val categories: List<Category?>?
) : Parcelable