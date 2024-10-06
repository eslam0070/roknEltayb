package com.rokneltayb.data.model.notification_count


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class NotificationCountResponse(
    @SerializedName("data")
    var `data`: Data?,
    @SerializedName("message")
    var message: String?, // data displayed successfully
    @SerializedName("status")
    var status: Int? // 200
) {
    @Keep
    data class Data(
        @SerializedName("count")
        var count: Int? // 4
    )
}