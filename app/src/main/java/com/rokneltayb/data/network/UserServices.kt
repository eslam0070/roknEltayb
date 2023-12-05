package com.rokneltayb.data.network


import com.rokneltayb.data.model.login.changepassword.ChangePasswordResponse
import com.rokneltayb.data.model.login.delete.DeleteAccountResponse
import com.rokneltayb.data.model.login.login.LoginResponse
import com.rokneltayb.data.model.login.logout.LogoutResponse
import com.rokneltayb.data.model.login.resetpassword.ResetPasswordResponse
import com.rokneltayb.data.model.signup.SignUpResponse
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST

interface UserServices {
    @FormUrlEncoded
    @POST("client/auth/login")
    suspend fun login(
        @Field("fcm_token") token: String,
        @Field("phone") phone: String,
        @Field("password") password: String): Response<LoginResponse>

    @FormUrlEncoded
    @POST("client/auth/forget-password")
    suspend fun sendOTP(
        @Field("phone") phone: String,
    ): Response<SignUpResponse>

    @FormUrlEncoded
    @POST("client/auth/reset-password")
    suspend fun resetPassword(
        @Field("country_code") countryCode: String,
        @Field("phone") phone: String,
        @Field("token") code: String
    ): Response<ResetPasswordResponse>

    @FormUrlEncoded
    @POST("client/auth/change-password")
    suspend fun changePassword(
        @Field("password") password: String,
        @Field("password_confirmation") passwordConfirmation: String
    ): Response<ChangePasswordResponse>

    @Headers("Accept: application/json")
    @GET("client/auth/logout")
    suspend fun logout(): Response<LogoutResponse>

    @FormUrlEncoded
    @POST("client/auth/delete-account")
    suspend fun deleteAccount(): Response<DeleteAccountResponse>


    @FormUrlEncoded
    @POST("client/auth/sign-up")
    suspend fun signUp(
        @Field("name")name:String,
        @Field("phone")phone:String,
        @Field("email")email:String,
        @Field("password")password:String,
        @Field("password_confirmation")passwordConfirmation:String,
        @Field("fcm_token")fcmToken:String
    ): Response<SignUpResponse>


}