package com.rokneltayb.data.model.otp


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class ResendOtpSignUpResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?, // تم إرسال كود التحقق من الهاتف مره اخرى بنجاح
    @SerializedName("status")
    var status: Int? // 200
) {
    @Keep
    data class Data(
        @SerializedName("otp")
        var otp: String? // 581634
    )
}