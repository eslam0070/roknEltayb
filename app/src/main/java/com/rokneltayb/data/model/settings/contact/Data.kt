package com.rokneltayb.data.model.settings.contact


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class Data(
    @SerializedName("email")
    @Expose
    val email: String?,
    @SerializedName("message")
    @Expose
    val message: String?,
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("phone")
    @Expose
    val phone: String?,
    @SerializedName("subject")
    @Expose
    val subject: String?
) : Parcelable