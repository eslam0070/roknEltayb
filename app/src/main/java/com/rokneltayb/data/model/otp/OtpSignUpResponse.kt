package com.rokneltayb.data.model.otp


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class OtpSignUpResponse(
    @SerializedName("message")
    var message: String?, // otp مطلوب.
    @SerializedName("status")
    var status: Int? // 400
)