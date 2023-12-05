package com.rokneltayb.data.model.login.resetpassword


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class ResetPasswordData(
    @SerializedName("client_data")
    @Expose
    val clientData: ClientData?,
    @SerializedName("token")
    @Expose
    val token: String?
) : Parcelable