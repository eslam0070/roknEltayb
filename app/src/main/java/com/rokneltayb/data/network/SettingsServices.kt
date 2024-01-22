package com.rokneltayb.data.network


import com.rokneltayb.data.model.settings.SettingsResponse
import com.rokneltayb.data.model.settings.contact.ContactUsResponse
import com.rokneltayb.data.model.settings.pages.PagesResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
interface SettingsServices {

    @GET("client/settings")
    suspend fun settings():Response<SettingsResponse>

    @FormUrlEncoded
    @POST("client/store-contact")
    suspend fun storeContact(@Field("name")name:String,@Field("phone") phone:String,@Field("email") email:String,@Field("subject") subject:String,@Field("message") message:String): Response<ContactUsResponse>

    @GET("client/pages")
    suspend fun getPages(): Response<PagesResponse>
}