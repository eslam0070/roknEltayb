package com.rokneltayb.data.model.address.details


import com.google.gson.annotations.SerializedName
import com.google.gson.annotations.Expose

data class AddressDetailsResponse(
    @SerializedName("data")
    @Expose
    val addressDetailsData: AddressDetailsData?,
    @SerializedName("message")
    @Expose
    val message: String?,
    @SerializedName("status")
    @Expose
    val status: Int?
)