package com.rokneltayb.data.model.products


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class Data(
    @SerializedName("products")
    @Expose
    val products: List<Product?>?
) : Parcelable