package com.rokneltayb.data.model.signup


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class Data(
    @SerializedName("otp")
    @Expose
    val otp: String?
) : Parcelable