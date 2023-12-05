package com.rokneltayb.data.model.login.resetpassword


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class ResetPasswordResponse(
    @SerializedName("data")
    @Expose
    val resetPasswordData: ResetPasswordData?,
    @SerializedName("message")
    @Expose
    val message: String?
) : Parcelable