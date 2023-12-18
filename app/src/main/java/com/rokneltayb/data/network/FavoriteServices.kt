package com.rokneltayb.data.network


import com.rokneltayb.data.model.StringMessageResponse
import com.rokneltayb.data.model.favorite.FavoritesResponse
import com.rokneltayb.data.model.favorite.add.AddFavoritesResponse
import com.rokneltayb.data.model.favorite.delete.DeleteFavoritesResponse
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

interface FavoriteServices {

    @GET("client/favorites")
    suspend fun favorites(): Response<FavoritesResponse>

    @FormUrlEncoded
    @POST("client/store-favorites")
    suspend fun storeFavorites(
        @Field("product_id") productId: Int
    ): Response<AddFavoritesResponse>


    @FormUrlEncoded
    @POST("client/delete-favorites")
    suspend fun deleteFavorites(@Field("product_id")productId:Int):Response<DeleteFavoritesResponse>

}