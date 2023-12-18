package com.rokneltayb.data.model


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class StringMessageResponse(
    @SerializedName("message")
    @Expose
    val message: String?
) : Parcelable