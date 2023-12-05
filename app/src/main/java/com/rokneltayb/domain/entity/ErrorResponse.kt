package com.rokneltayb.domain.entity

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("message")
    val message:String,
    @SerializedName("status")
    val status:Int,
    @SerializedName("data")
    val data:List<String>
){

    override fun toString(): String {
        return data.toString()
    }
}