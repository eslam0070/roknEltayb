package com.rokneltayb.data.network


import com.rokneltayb.data.model.StringMessageResponse
import com.rokneltayb.data.model.address.AddressResponse
import com.rokneltayb.data.model.address.add.AddAddressResponse
import com.rokneltayb.data.model.address.city.CityResponse
import com.rokneltayb.data.model.address.delete.DeleteAddressResponse
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

interface AddressServices {

    @GET("client/addresses")
    suspend fun address(): Response<AddressResponse>
    @GET("client/cities")
    suspend fun cities(): Response<CityResponse>
    @FormUrlEncoded
    @POST("client/store-address")
    suspend fun addAddress(@Field("name")name:String,
                           @Field("phone")phone:String,
                           @Field("city_id")cityId:String,
                           @Field("block")block:String,
                           @Field("street")street:String,
                           @Field("avenue")avenue:String,
                           @Field("building_num")buildingNum:String,
                           @Field("floor_num")floorNum:String,
                           @Field("apartment")apartment:String,
                           @Field("address")address:String):Response<AddAddressResponse>
    @FormUrlEncoded
    @POST("client/delete-address")
    suspend fun deleteAddress(@Field("id")id:Int):Response<DeleteAddressResponse>

}