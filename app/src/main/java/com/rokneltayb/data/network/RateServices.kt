package com.rokneltayb.data.network


import com.rokneltayb.data.model.rate.AddRateResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
interface RateServices {
    @FormUrlEncoded
    @POST("client/store-rate")
    suspend fun storeRate(@Field("user_id")userId:Int,@Field("product_id") productId:Int,@Field("rate") rate:String,@Field("description") description:String): Response<AddRateResponse>

}