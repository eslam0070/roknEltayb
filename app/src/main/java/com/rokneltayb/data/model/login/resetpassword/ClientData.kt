package com.rokneltayb.data.model.login.resetpassword


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class ClientData(
    @SerializedName("address")
    @Expose
    val address: String?,
    @SerializedName("country_code")
    @Expose
    val countryCode: String?,
    @SerializedName("email")
    @Expose
    val email: String?,
    @SerializedName("fcm_token")
    @Expose
    val fcmToken: String?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("phone")
    @Expose
    val phone: String?
) : Parcelable