package com.rokneltayb.data.model.login.login


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class ClientData(
    @SerializedName("created_at")
    @Expose
    val createdAt: String?,
    @SerializedName("email")
    @Expose
    val email: String?,
    @SerializedName("email_verified_at")
    @Expose
    val emailVerifiedAt: String?,
    @SerializedName("fcm_token")
    @Expose
    val fcmToken: String?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("is_active")
    @Expose
    val isActive: String?,
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("phone")
    @Expose
    val phone: String?,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String?
) : Parcelable