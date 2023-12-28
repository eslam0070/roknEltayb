package com.rokneltayb.data.model.settings


import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import android.os.Parcelable
import com.google.gson.annotations.Expose

@Parcelize
data class Data(
    @SerializedName("about")
    @Expose
    val about: String?,
    @SerializedName("address")
    @Expose
    val address: String?,
    @SerializedName("ar_name")
    @Expose
    val arName: String?,
    @SerializedName("created_at")
    @Expose
    val createdAt: String?,
    @SerializedName("customer_phone")
    @Expose
    val customerPhone: String?,
    @SerializedName("delivery_fees")
    @Expose
    val deliveryFees: Int?,
    @SerializedName("email")
    @Expose
    val email: String?,
    @SerializedName("en_name")
    @Expose
    val enName: String?,
    @SerializedName("facebook")
    @Expose
    val facebook: String?,
    @SerializedName("hours")
    @Expose
    val hours: String?,
    @SerializedName("id")
    @Expose
    val id: Int?,
    @SerializedName("instagram")
    @Expose
    val instagram: String?,
    @SerializedName("lat")
    @Expose
    val lat: String?,
    @SerializedName("lng")
    @Expose
    val lng: String?,
    @SerializedName("logo")
    @Expose
    val logo: String?,
    @SerializedName("name")
    @Expose
    val name: String?,
    @SerializedName("notify_email")
    @Expose
    val notifyEmail: String?,
    @SerializedName("notify_email2")
    @Expose
    val notifyEmail2: String?,
    @SerializedName("notify_email3")
    @Expose
    val notifyEmail3: String?,
    @SerializedName("phone")
    @Expose
    val phone: String?,
    @SerializedName("tax")
    @Expose
    val tax: Int?,
    @SerializedName("twitter")
    @Expose
    val twitter: String?,
    @SerializedName("updated_at")
    @Expose
    val updatedAt: String?,
    @SerializedName("youtube")
    @Expose
    val youtube: String?
) : Parcelable