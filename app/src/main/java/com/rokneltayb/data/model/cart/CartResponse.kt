package com.rokneltayb.data.model.cart


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

data class CartResponse(
    val `data`: Data,
    val message: String,
    val status: Int
)