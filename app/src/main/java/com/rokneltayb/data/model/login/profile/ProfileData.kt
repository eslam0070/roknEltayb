package com.rokneltayb.data.model.login.profile


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class ProfileData(
    @SerializedName("email")
    @Expose
    val email: String?,
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