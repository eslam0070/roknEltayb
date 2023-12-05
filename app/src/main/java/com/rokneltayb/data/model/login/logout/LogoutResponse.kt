package com.rokneltayb.data.model.login.logout


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class LogoutResponse(
    @SerializedName("message")
    @Expose
    val message: String?
) : Parcelable