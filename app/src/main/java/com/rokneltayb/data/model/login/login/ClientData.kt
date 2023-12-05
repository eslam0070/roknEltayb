package com.rokneltayb.data.model.login.login


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class ClientData(
    @SerializedName("created_at")
    @Expose
    val createdAt: String? = null,
    @SerializedName("email")
    @Expose
    val email: String? = null,
    @SerializedName("email_verified_at")
    @Expose
    val emailVerifiedAt: String? = null,
    @SerializedName("fcm_token")
    @Expose
    val fcmToken: String? = null,
    @SerializedName("id")
    @Expose
    val id: Int? = null,
    @SerializedName("is_active")
    @Expose
    val isActive: String? = null,
    @SerializedName("name")
    @Expose
    val name: String? = null,
    @SerializedName("phone")
    @Expose
    val phone: String? = null,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String? = null
) : Parcelable